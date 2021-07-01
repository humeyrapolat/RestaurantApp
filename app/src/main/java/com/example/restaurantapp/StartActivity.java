package com.example.restaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.SharedElementCallback;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class StartActivity extends AppCompatActivity {

    private GifImageView logo;
    private static final int NO_PAGE = 2;
    private ViewPager viewPager;
    private ScreenSliderPagerAdapter pagerAdapter;
    private static int SPLASH_TIME_OUT = 5000;
    SharedPreferences mSharePref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);

        logo = findViewById(R.id.gifImageView);
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSliderPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.myanim);
        viewPager.startAnimation(anim);

        logo.animate().translationX(-1400).setDuration(2000).setStartDelay(4000);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSharePref = getSharedPreferences("SharedPref", MODE_PRIVATE);
                boolean isFirstTime = mSharePref.getBoolean("firstTime", true);

                if (isFirstTime) {
                    SharedPreferences.Editor editor = mSharePref.edit();
                    editor.putBoolean("firstTime", false);
                    editor.commit();
                } else {
                    Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);

    }

    private class ScreenSliderPagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSliderPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    OnBoardingFragmentOne tabOne = new OnBoardingFragmentOne();
                    return tabOne;

                case 1:
                    OnBoardingFragmentSecond tabTwo = new OnBoardingFragmentSecond();
                    return tabTwo;
            }
            return null;
        }

        @Override
        public int getCount() {
            return NO_PAGE;
        }
    }

}