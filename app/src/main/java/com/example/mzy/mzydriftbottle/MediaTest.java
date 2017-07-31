package com.example.mzy.mzydriftbottle;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

/**
 * Created by mzy on 2017/7/31.
 */
public class MediaTest extends Activity {

    private Context mContext;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mediatest);
        mContext = this;

        //6.0+版本还需要动态获取权限
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }
    }

    public void playSound(View view){
        //播放音频
        try {
            mediaPlayer = MediaPlayer.create(mContext, R.raw.getbottle);
            if(mediaPlayer != null){
                mediaPlayer.start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
