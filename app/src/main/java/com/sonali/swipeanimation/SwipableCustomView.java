package com.sonali.swipeanimation;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Housejoy on 08-07-2017.
 */

public class SwipableCustomView extends LinearLayout implements View.OnTouchListener{

    private EventListener mEventListener;
    private GestureDetector mGestureDetector = null;
    Context mContext;

    public SwipableCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void setEventListner(EventListener l){
        mEventListener = l;
        init();
    }

    private void init() {
        mGestureDetector = new GestureDetector(mContext, new GestureListener());
        setOnTouchListener(this);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mGestureDetector.onTouchEvent(ev);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 1;

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD) {
                        if (diffX > 0) {
                            mEventListener.onSwipeRight();
                        } else {
                            mEventListener.onSwipeLeft();
                        }
                    }
                    result = true;
                } else if (Math.abs(diffY) > SWIPE_THRESHOLD) {
                    if (diffY > 0) {
                        mEventListener.onSwipeBottom();
                    } else {
                        mEventListener.onSwipeTop();
                    }
                }
                result = true;

            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }

    }

}



