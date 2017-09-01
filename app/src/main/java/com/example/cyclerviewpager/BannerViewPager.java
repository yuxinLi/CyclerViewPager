package com.example.cyclerviewpager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

import static android.R.attr.action;

public class BannerViewPager extends ViewPager{
    private Handler handler;
    private CustomDurationScroller scroller;

    public static final int DEFAULT_INTERVAL = 2000;
    private final int interval = DEFAULT_INTERVAL;
    public static final int SCROLL_WHAT = 0;

    public BannerViewPager(Context context) {
        this(context , null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        handler = new MyHandler(this);
        setViewPagerScroller();

        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {}
            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case SCROLL_STATE_IDLE:
                        autoFix();
                        break;
                    case SCROLL_STATE_DRAGGING:
                        autoFix();
                        break;
                }
            }
        });
    }

    private void autoFix() {
        if (getCurrentItem() == 0) {
            setCurrentItem(getAdapter().getCount() - 2, false);
        } else if (getCurrentItem() == getAdapter().getCount() - 1) {
            setCurrentItem(1, false);
        }
    }

    private void setViewPagerScroller() {
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            Field interpolatorField = ViewPager.class.getDeclaredField("sInterpolator");
            interpolatorField.setAccessible(true);

            scroller = new CustomDurationScroller(getContext(), (Interpolator) interpolatorField.get(null));
            scrollerField.set(this, scroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);

        if (isScrollable()){
            setCurrentItem(1);
            startAutoScroll();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL
                || action == MotionEvent.ACTION_OUTSIDE) {
            startAutoScroll();
        } else if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            stopAutoScroll();
        }

        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isScrollable() && super.onInterceptTouchEvent(ev);
    }

    private void scrollOnce() {
        if (isScrollable()){
            int nextItem = getCurrentItem() + 1;
            if (nextItem > getAdapter().getCount() - 1){ // 如果现在是最后一个view ，效果是第一个，下一个应该是第二个view
                nextItem = 2;
            }
            setCurrentItem(nextItem, true);
        }
    }

    private boolean isScrollable(){
        return getAdapter() != null && getAdapter().getCount() > 3;
    }

    public void startAutoScroll() {
        if (isScrollable()){
            sendScrollMessage(interval);
        }
    }

    public void stopAutoScroll() {
        handler.removeMessages(SCROLL_WHAT);
    }

    private void sendScrollMessage(long delayTimeInMills) {
        handler.removeMessages(SCROLL_WHAT);
        handler.sendEmptyMessageDelayed(SCROLL_WHAT, delayTimeInMills);
    }


    private static class MyHandler extends Handler {
        private final WeakReference<BannerViewPager> mBannerViewPager;

        public MyHandler(BannerViewPager viewPager){
            mBannerViewPager = new WeakReference<>(viewPager);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BannerViewPager viewPager = mBannerViewPager.get();
            if(viewPager != null) {
                switch (msg.what) {
                    case SCROLL_WHAT:
                        viewPager.scrollOnce();
                        viewPager.startAutoScroll();
                    default:
                        break;
                }
            }
        }
    }

}