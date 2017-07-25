package com.example.mzy.mzydriftbottle;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {



    private Context mContext;
    private ImageView drift;
    private ImageView get;
    private ImageView board;
    private ImageView voice;
    private TextView text1;
    private TextView text2;

    private boolean voice_flag = false;//能进行隐藏图标的flag，防止在未显示声音图标的状态下播放动画
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        record(voice);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final String TAG = "touch";
        View view = getCurrentFocus();
        Log.d(TAG, "按键范围外");
        float x = event.getRawX();
        float y = event.getRawY();
        Log.d(TAG, "x:" + x + ";y:" + y);
        if(voice_flag){
            hide_voice(view);
        }
        voice_flag = false;
        return super.onTouchEvent(event);
    }

    public void driftBottle(View view){
        mContext = this;
        Intent intent = new Intent(mContext, Drift.class);
        mContext.startActivity(intent);

    }

    public void getBottle(View view){
        mContext = this;
        Intent intent = new Intent(mContext, GetBottle.class);
        mContext.startActivity(intent);
    }

    public void show_voice(View view){
        final String TAG = "show_voice";

//        voice.onVisibilityAggregated(true);//API>=24
//        常量值为0，意思是可见的
//        常量值为4，意思是不可见的
//        常量值为8，意思是不可见的，而且不占用布局空间
        drift = (ImageView) findViewById(R.id.driftbottle);
        get = (ImageView) findViewById(R.id.getbottle);
        board = (ImageView) findViewById(R.id.board);
        voice = (ImageView) findViewById(R.id.voice_button);
        text1 = (TextView) findViewById(R.id.textView);
        text2 = (TextView) findViewById(R.id.textView2);

        //显示录音图标
        voice.setVisibility(View.VISIBLE);
        drift.setVisibility(View.INVISIBLE);
        get.setVisibility(View.INVISIBLE);
        board.setVisibility(View.INVISIBLE);
        text1.setVisibility(View.INVISIBLE);
        text2.setVisibility(View.INVISIBLE);

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(voice, "scaleX", 0f, 1.1f, 1f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(voice, "scaleY", 0f, 1.1f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(voice, "translationY", 0f, -100f);

        //差值器
        animator1.setInterpolator(new BounceInterpolator());
        animator2.setInterpolator(new BounceInterpolator());
        animator3.setInterpolator(new BounceInterpolator());

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator1,animator2,animator3);
        set.setDuration(1000);
        set.start();

        //动画结束，设置flag
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                voice_flag = true;
            }
        });
    }

    public void hide_voice(View view){
        drift = (ImageView) findViewById(R.id.driftbottle);
        get = (ImageView) findViewById(R.id.getbottle);
        board = (ImageView) findViewById(R.id.board);
        voice = (ImageView) findViewById(R.id.voice_button);
        text1 = (TextView) findViewById(R.id.textView);
        text2 = (TextView) findViewById(R.id.textView2);

        drift.setVisibility(View.VISIBLE);
        get.setVisibility(View.VISIBLE);
        board.setVisibility(View.VISIBLE);
        text1.setVisibility(View.VISIBLE);
        text2.setVisibility(View.VISIBLE);

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(voice, "scaleX", 1f, 0f, 0f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(voice, "scaleY", 1f, 0f, 0f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(voice, "translationY", 0f, 100f);
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(drift, "alpha", 0f, 1f, 1f);
        ObjectAnimator animator5 = ObjectAnimator.ofFloat(get, "alpha", 0f, 1f, 1f);
        ObjectAnimator animator6 = ObjectAnimator.ofFloat(board, "alpha", 0f, 1f, 1f);
        ObjectAnimator animator7 = ObjectAnimator.ofFloat(text1, "alpha", 0f, 1f, 1f);
        ObjectAnimator animator8 = ObjectAnimator.ofFloat(text2, "alpha", 0f, 1f, 1f);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator1,animator2,animator3,animator4,animator5,animator6,animator7,animator8);
        set.setDuration(1000);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //显示录音图标
                voice.setVisibility(View.INVISIBLE);

            }
        });

    }


    public void record(View view){
        //Toast.makeText(MainActivity.this, "点击录音", Toast.LENGTH_SHORT).show();
        voice = (ImageView) findViewById(R.id.voice_button);

        voice.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    voice.setImageResource(R.drawable.voice_button2_press);

                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    voice.setImageResource(R.drawable.voice_button2);
                    voice_flag = true;
                    driftBottle(v);
                }

                return false;
            }
        });
    }


}
