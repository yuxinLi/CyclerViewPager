package com.example.cyclerviewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BannerViewPager vp = (BannerViewPager) findViewById(R.id.vp);

        List<BannerItem> items = new ArrayList<>();
        for (int i = 0 ; i < 2 ; i++){
            BannerItem bannerItem = new BannerItem();
            bannerItem.title = i+ " "+ i+ " "+i+ " "+i;
            items.add(bannerItem);
        }

        BannerAdapter bannerAdapter = new BannerAdapter(items);
        vp.setAdapter(bannerAdapter);

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(vp);
    }
}
