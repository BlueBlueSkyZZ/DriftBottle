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
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
        getGetBottle(getGetBottleUrl);
        Toast.makeText(MainActivity.this, getGetBottleUrl, Toast.LENGTH_SHORT).show();
        Log.d("getBottle", "getBottle: " + getGetBottleUrl);
//        mContext = this;
//        Intent intent = new Intent(mContext, GetBottle.class);
//        mContext.startActivity(intent);
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
        set.setDuration(618);
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


        ObjectAnimator animator1 = ObjectAnimator.ofFloat(voice, "scaleX", 1f, 0f, 0f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(voice, "scaleY", 1f, 0f, 0f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(voice, "translationY", 0f, 100f);
        final ObjectAnimator animator4 = ObjectAnimator.ofFloat(drift, "alpha", 0f, 1f, 1f);
        final ObjectAnimator animator5 = ObjectAnimator.ofFloat(get, "alpha", 0f, 1f, 1f);
        final ObjectAnimator animator6 = ObjectAnimator.ofFloat(board, "alpha", 0f, 1f, 1f);
        final ObjectAnimator animator7 = ObjectAnimator.ofFloat(text1, "alpha", 0f, 1f, 1f);
        final ObjectAnimator animator8 = ObjectAnimator.ofFloat(text2, "alpha", 0f, 1f, 1f);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator1,animator2,animator3);
        set.setDuration(618);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //显示录音图标
                voice.setVisibility(View.INVISIBLE);

                drift.setVisibility(View.VISIBLE);
                get.setVisibility(View.VISIBLE);
                board.setVisibility(View.VISIBLE);
                text1.setVisibility(View.VISIBLE);
                text2.setVisibility(View.VISIBLE);
                AnimatorSet set2 = new AnimatorSet();
                set2.playTogether(animator4,animator5,animator6,animator7,animator8);
                set2.setDuration(618);
                set2.start();
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
                    //创建频道
                    postDrift("mzy","0",postDriftUrl);
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

    private String IPv4 = "http://192.168.43.25:8888/";

    private String postDriftUrl = IPv4 + "web01/servlet/CreateDriftbottleServlet";

    /**
     * 扔漂流瓶，创建房间，同时给搭建的后台服务器传输创建的房间
     * @param createUserId 创建者
     * @param isDisByUse 是否选择听后销毁
     * @param postDriftUrl 发送到的服务器地址
     * response:成功返回频道名，失败返回false
     */
    public void postDrift(String createUserId, String isDisByUse, String postDriftUrl) {
        OkHttpClient client = new OkHttpClient();
        FormBody formBody = new FormBody.Builder()
                .add("CreateUserId", createUserId)
                .add("IsDisByUse", isDisByUse)
                .build();
        Request request = new Request.Builder()
                .url(postDriftUrl)
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("postDrift", "onFailure:回调失败 ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.d("postDrift", "onResponse() returned: " +  result);
            }
        });
    }

    private String getGetBottleUrl = IPv4 + "web01/servlet/GetDriftbottleServlet";

    /**
     * 捡一个漂流瓶
     * @param getGetBottleUrl 捡漂流瓶的服务器地址
     * response:JSON格式 Name 频道名
     *                   IsDisByUse 是否选择听后销毁 0否 1是
     */
    public void getGetBottle(String getGetBottleUrl){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(getGetBottleUrl)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.d("getBottle", "onFailure: 回调失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.d("getBottle", "onResponse() returned: " +  result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String roomName = jsonObject.getString("Name");
                    String isDisByUse = jsonObject.getString("IsDisByUse");
                    Log.d("getBottle", "name = " + roomName + ", isDisByUse = " + isDisByUse);

                    if(isDisByUse.equals("0")){
                        //不销毁房间
                        Log.d("getBottle", "不用销毁房间" + roomName);
                    }else if(isDisByUse.equals("1")){
                        //销毁房间
                        Log.d("getBottle", "销毁房间" + roomName);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 创建频道方法
     * @param selfId
     * @param channelName
     * @param pw
     */
    public void myCreateChannel(int selfId, String channelName, String pw){

        //createPublicChannel(selfId, channelName, pw);
    }


    /**
     * 删除频道方法
     * @param uid
     * @param type
     * @param channelId
     */
    public void myDeleteChannel(int uid, String type, String channelId){
        //deletePublicChannel(uid, type, channelId);

    }

    /**
     * 进入频道方法
     */
    public void myJoinChannel(){

    }
}
