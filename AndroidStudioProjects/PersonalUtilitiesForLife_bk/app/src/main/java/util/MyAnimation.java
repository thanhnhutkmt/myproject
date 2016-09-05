package util;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import software.nhut.personalutilitiesforlife.R;

/**
 * Created by Nhut on 6/23/2016.
 */
public class MyAnimation {
    public static void runAnimation(final View v, int animID, final boolean repeat, Context context) {
        final Animation animation = AnimationUtils.loadAnimation(context, animID);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (repeat) v.startAnimation(animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(animation);
    }

    public static void runAnimationDrawable(View v, int animID) {
        v.setBackgroundResource(animID);
        final AnimationDrawable ad = (AnimationDrawable) v.getBackground();
        v.setVisibility(View.VISIBLE);
        ad.start();
    }
}
