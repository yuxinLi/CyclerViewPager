package com.example.cyclerviewpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class BannerAdapter extends PagerAdapter {

    private List<View> viewList = new ArrayList<>();
    private List<BannerItem> mBannerItems = new ArrayList<>();
    private OnBannerClickListener mOnBannerClickListener;

    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
        mOnBannerClickListener = onBannerClickListener;
    }

    public BannerAdapter(@NonNull List<BannerItem> items) {
        mBannerItems.clear();
        if (items.size() > 0){
            mBannerItems.addAll(items);
        }
    }

    @Override
    public int getCount() {
        if (mBannerItems.size() == 0){
            return 0;
        }else if (mBannerItems.size() == 1){  // 一个 item 不用滑动
            return 1;
        }
        return mBannerItems.size() + 2; // 2个以上 ， 添加头尾2个item
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        maybeInitItems(container);
        View view = viewList.get(position);
        if (container.equals(view.getParent())) {
            container.removeView(view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    private void maybeInitItems(ViewGroup container) {
        if (viewList.size() != 0 || mBannerItems.size() == 0) return;

        if (mBannerItems.size() == 1){ // 一个item ， 不用滑动
            View view = createView(container, mBannerItems.get(0));

            if(mOnBannerClickListener != null){
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnBannerClickListener.onBannerClick(mBannerItems.get(0));
                    }
                });
            }
            viewList.add(view);

        }else {  // 2个以上 ， 添加头尾2个item
            for (int i = 0 ; i <= mBannerItems.size() + 1 ; i++) {
                final BannerItem item;
                if (i == 0){
                    item = mBannerItems.get(mBannerItems.size() - 1); // 第一个

                }else if (i == mBannerItems.size() + 1){  // 最后一个
                    item = mBannerItems.get(0);

                }else { // 其他
                    item = mBannerItems.get(i - 1);
                }

                View view = createView(container, item);

                if(mOnBannerClickListener != null){
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnBannerClickListener.onBannerClick(item);
                        }
                    });
                }
                viewList.add(view);
            }
        }


    }

    private View createView(ViewGroup parent, BannerItem item) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_banner_item, parent, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView textView = (TextView) view.findViewById(R.id.title);
        if (!TextUtils.isEmpty(item.title)) {
            // textview bug , 多一个字会直接截断 ，不会显示 ...
            String text = item.title + " ";
            textView.setText(text);
        } else {
            view.findViewById(R.id.background).setVisibility(View.GONE);
        }
        return view;
    }

    public interface OnBannerClickListener {
        void onBannerClick(BannerItem item);
    }
}