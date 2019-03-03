package com.xiaokun.customview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

/**
 * Created by xiaokun on 2019/3/3.
 *
 * @author xiaokun
 * @date 2019/3/3
 */
public class LoadingActivity extends AppCompatActivity {


    private ImageView ball1;
    private ImageView ball2;
    private ImageView ball3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        initView();
    }

    Handler mHandler = new Handler(Looper.getMainLooper());

    private void initView() {
        ball1 = findViewById(R.id.ball1);
        ball2 = findViewById(R.id.ball2);
        ball3 = findViewById(R.id.ball3);


        AnimatorSet set = new AnimatorSet();
        set.setDuration(1600);

        ObjectAnimator anim1 = animXCreate(ball1);
        ObjectAnimator anim2 = animYCreate(ball1);

        anim1.setInterpolator(new LinearInterpolator());
        anim2.setInterpolator(new LinearInterpolator());

//        anim1.setDuration(400);
//        anim2.setDuration(400);

        final ObjectAnimator anim3 = animXCreate(ball2);
        final ObjectAnimator anim4 = animYCreate(ball2);

        anim3.setInterpolator(new LinearInterpolator());
        anim4.setInterpolator(new LinearInterpolator());

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimatorSet set = new AnimatorSet();
                set.setDuration(1600);
                set.play(anim3).with(anim4);
                set.start();
            }
        }, 400);


        configAnim(anim1);
        configAnim(anim2);
        configAnim(anim3);
        configAnim(anim4);


        set.play(anim1).with(anim2);

        set.start();
    }

    private ObjectAnimator animXCreate(ImageView imageView) {
        return ObjectAnimator.ofFloat(imageView, "scaleX",
                0.0f, 1f, 0.0f);
    }

    private ObjectAnimator animYCreate(ImageView imageView) {
        return ObjectAnimator.ofFloat(imageView, "scaleY",
                0.0f, 1f, 0.0f);
    }

    private void configAnim(ObjectAnimator objectAnimator) {
//        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
//        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
    }

}
