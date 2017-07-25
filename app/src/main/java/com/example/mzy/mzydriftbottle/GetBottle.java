package com.example.mzy.mzydriftbottle;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by mzy on 2017/7/19.
 */
public class GetBottle extends Activity {

    private Context mContext;
    private ImageView bottle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getbottle);
        bottle = (ImageView) findViewById(R.id.getmybottle);
        getBottle(bottle);
    }

    public void getBottle(View view){

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "scaleX", 0f, 3f, 3f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "scaleY", 0f, 3f, 3f);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator1,animator2);
        set.setDuration(2500);
        set.start();
        Toast.makeText(GetBottle.this, "海里出现了一个漂流瓶", Toast.LENGTH_SHORT).show();
    }

    public void getMsg(View view){
        Toast.makeText(this, "我想做语音解析", Toast.LENGTH_SHORT).show();
    }

    public void back_get(View view){
        mContext = this;
        Intent intent = new Intent(mContext, MainActivity.class);
        mContext.startActivity(intent);
    }
}
