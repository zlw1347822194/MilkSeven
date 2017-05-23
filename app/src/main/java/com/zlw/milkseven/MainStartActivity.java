package com.zlw.milkseven;

import android.animation.Animator;
import android.content.Intent;
import android.animation.Animator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.zlw.milkseven.ui.activity.LoginActivity;

import java.util.Timer;
import java.util.TimerTask;
public class
MainStartActivity extends AppCompatActivity implements Animator.AnimatorListener{
    private Animation toLargeAnimation;
    private Animation toSmallAnimation;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_start);

        imageView = (ImageView) findViewById(R.id.imageView);
        toLargeAnimation = AnimationUtils.loadAnimation(this,R.anim.to_large);
        toSmallAnimation = AnimationUtils.loadAnimation(this,R.anim.to_small);
        imageView.startAnimation(toLargeAnimation);


        final Intent it = new Intent(this, MainActivity.class);

        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(it); //执行
                finish();
            }
        };
        timer.schedule(task, 1000 * 3); //10秒后
    }

    @Override
    public void onAnimationStart(Animator animation) {
        if (animation.hashCode()==toLargeAnimation.hashCode())
            imageView.startAnimation(toSmallAnimation);
        else
            imageView.startAnimation(toLargeAnimation);
    }

    @Override
    public void onAnimationEnd(Animator animation) {

    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
