package com.sonali.swipeanimation;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sonali.swipeanimation.databinding.ActivityMainBinding;

/**
 * Created by Sonali on 08-07-2017.
 */

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mViewBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinder = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mViewBinder.parentGroup.setEventListner(new EventListener() {
            @Override
            public void onSwipeRight() {
                Toast.makeText(MainActivity.this, getString(R.string.swipe_right), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeLeft() {
                Toast.makeText(MainActivity.this, getString(R.string.swipe_left), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeTop() {
                collapseViewAnimation(mViewBinder.swipableView);
            }

            @Override
            public void onSwipeBottom() {
                expandViewAnimation(mViewBinder.swipableView);
            }

            @Override
            public void onTap() {
                Toast.makeText(MainActivity.this, getString(R.string.Single_tap), Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void expandViewAnimation(final View v) {

        final int defaultHeight = LinearLayout.LayoutParams.WRAP_CONTENT ;
        final int targtetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {

                v.getLayoutParams().height = interpolatedTime == 1
                        ? defaultHeight
                        : (int)(targtetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration((int)(targtetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }


    public void collapseViewAnimation(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

}
