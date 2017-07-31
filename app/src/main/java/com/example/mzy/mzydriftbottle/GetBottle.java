package com.example.mzy.mzydriftbottle;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by mzy on 2017/7/19.
 */
public class GetBottle extends Activity {

    private Context mContext;
    private ImageView bottle;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getbottle);



        mContext = this;

        bottle = (ImageView) findViewById(R.id.getmybottle);
        getBottle(bottle);


        //播放海浪的音频
        MediaManager.playRaw(mContext, R.raw.getbottle);

        bottle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GetBottle.this, "我想做语音解析", Toast.LENGTH_SHORT).show();

                String fileRoot = Environment.getExternalStorageDirectory()+"/imooc_recorder_audios";
                Log.d("fileGetRandom", "fileRoot 是" + fileRoot);
                File myRoot = new File(fileRoot);
                final File[] files = myRoot.listFiles();
                if(files.length == 0){
                    Log.d("fileGetRandom", "getRandomFile: 不存在的");
                    return;
                }
                final int random = (int)(Math.random() * files.length);

                MediaManager.playSound(files[random]+"", new MediaPlayer.OnCompletionListener() {


                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Toast.makeText(GetBottle.this, "放完了" + files[random], Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
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



    public void back_get(View view){
        mContext = this;
        Intent intent = new Intent(mContext, Drift_main.class);
        mContext.startActivity(intent);
    }
}
