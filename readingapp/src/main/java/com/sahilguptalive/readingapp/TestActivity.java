package com.sahilguptalive.readingapp;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import pageturner.sahilguptalive.com.readingapp.R;

public class TestActivity extends AppCompatActivity {

    private static final long DURATION_OF_ANIMATION = 10000;
    protected static final float MAX_SCALE_Y = 1.0F;
    protected static final float MIN_SCALE_Y = 0.0F;
    protected static final float MAX_SCALE_X = 1.2F;
    protected static final float MIN_SCALE_X = 1.0F;
    TextView mTopHalf;
    TextView mBottomHalf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mTopHalf = (TextView) findViewById(R.id.top_half);
        mBottomHalf = (TextView) findViewById(R.id.bottom_half);
        mTopHalf.post(new Runnable() {
            @Override
            public void run() {
                mTopHalf.setPivotX(mTopHalf.getWidth() / 2);
                mTopHalf.setPivotY(mTopHalf.getHeight());
            }
        });
        mBottomHalf.post(new Runnable() {
            @Override
            public void run() {
                mBottomHalf.setPivotX(mBottomHalf.getWidth() / 2);
                mBottomHalf.setPivotY(0);
            }
        });
        getAnimatorForTopHalf().start();
    }

    private void applyAnimationOnViewsV1_Simple() {
        ObjectAnimator animatorForTopHalf = ObjectAnimator.ofFloat(mTopHalf, "scaleY", MAX_SCALE_Y, MIN_SCALE_Y);
        animatorForTopHalf.setDuration(DURATION_OF_ANIMATION);
        animatorForTopHalf.setInterpolator(new LinearInterpolator());
//        animatorForTopHalf.setRepeatCount(ValueAnimator.INFINITE);
//        animatorForTopHalf.setRepeatMode(ValueAnimator.REVERSE);
        animatorForTopHalf.addListener(new TopHalfListener());

        ObjectAnimator animatorForSecondHalf = ObjectAnimator.ofFloat(mBottomHalf, "scaleY", MAX_SCALE_Y, MIN_SCALE_Y);
        animatorForSecondHalf.setDuration(DURATION_OF_ANIMATION);
        animatorForSecondHalf.setInterpolator(new LinearInterpolator());
        animatorForSecondHalf.addListener(new BottomHalfListener());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.playTogether(animatorForTopHalf, animatorForSecondHalf);
        animatorSet.addListener(new TextAnimatorListener());
        animatorSet.start();
    }

    private Animator getAnimatorForTopHalf() {

        AnimatorSet animatorSet = new AnimatorSet();
        //scale Y
        ObjectAnimator animatorForScalingY = ObjectAnimator.ofFloat(mTopHalf, "scaleY", MAX_SCALE_Y, MIN_SCALE_Y);
        animatorForScalingY.setDuration(DURATION_OF_ANIMATION);
        animatorForScalingY.setInterpolator(new LinearInterpolator());
        //scale X
        ObjectAnimator animatorForScalingX = ObjectAnimator.ofFloat(mTopHalf, "scaleX", MIN_SCALE_X, MAX_SCALE_X);
        animatorForScalingX.setDuration(DURATION_OF_ANIMATION);
        animatorForScalingX.setInterpolator(new LinearInterpolator());
        //animator set
        animatorSet.addListener(new TopHalfListener());
        animatorSet.playTogether(animatorForScalingX, animatorForScalingY);
        return animatorSet;
    }

    private Animator getAnimatorForSecondHalf() {
        AnimatorSet animatorSet = new AnimatorSet();
        //scale Y
        ObjectAnimator animatorForScalingY = ObjectAnimator.ofFloat(mBottomHalf, "scaleY", MAX_SCALE_Y, MIN_SCALE_Y);
        animatorForScalingY.setDuration(DURATION_OF_ANIMATION);
        animatorForScalingY.setInterpolator(new LinearInterpolator());
        //scale X
        ObjectAnimator animatorForScalingX = ObjectAnimator.ofFloat(mBottomHalf, "scaleX", MIN_SCALE_X, MAX_SCALE_X);
        animatorForScalingX.setDuration(DURATION_OF_ANIMATION);
        animatorForScalingX.setInterpolator(new LinearInterpolator());
        //animator set
        animatorSet.addListener(new BottomHalfListener());
        animatorSet.playTogether(animatorForScalingX, animatorForScalingY);
        return animatorSet;
    }

    private class TopHalfListener implements Animator.AnimatorListener {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            getAnimatorForSecondHalf().start();
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }

    private class BottomHalfListener implements Animator.AnimatorListener {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            getAnimatorForTopHalf().start();
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }
}
