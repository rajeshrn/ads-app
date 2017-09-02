package com.myapp.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private static  Button parseButton = null ;
    private static int DELAY =5000;
    private static String start_time = null;
    private static String end_time = null;
    private static int delay = 2;
    private static String url = null;
    private static String unique_id = null;
    private static RelativeLayout banner = null;
    private static ImageView imgView = null;
    private int j =0;
    private  AnimationDrawable frameAnimation;
    private TextView scroller ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.banner);
         imgView = (ImageView)findViewById(R.id.imgView);
        scroller = (TextView)findViewById(R.id.scroller);
        frameAnimation = new AnimationDrawable();
        scroller.setSelected(true);
        for(j=0;j<3;j++) {
            Drawable drawable = getResources().getDrawable(getResources().getIdentifier
                    ("img_" + j, "drawable", getPackageName()));
            System.out.println(drawable.getCurrent().toString());
            frameAnimation.addFrame(drawable, DELAY);
        }
        frameAnimation.setOneShot(false);
        imgView.setBackground(frameAnimation);
        frameAnimation.start();

//        parseButton = (Button) findViewById(R.id.parseButton);
//    parseButton.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//        parseJSON();
//        display();
//    }
//});
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        super.onActivityResult(requestCode, resultCode, data);
//        // check if the request code is same as what is passed  here it is 2
//        if(resultCode==RESULT_OK)
//        {
//            //Start fetching JSON data and parse it
//            Toast.makeText(getApplicationContext(),"Splash screen success",Toast.LENGTH_LONG).show();
//        }else{
//            Toast.makeText(getApplicationContext(),"Splash screen failure",Toast.LENGTH_LONG).show();
//        }
//    }

    public void parseJSON(){
        String json = null;
        try {
            InputStream inputStream = getAssets().open("data.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        }catch (IOException e){e.printStackTrace();}
        try {
            JSONObject jsonObj = new JSONObject(json);
            JSONArray mainNode = jsonObj.optJSONArray("ooh");
            int jsonArrLen = mainNode.length();
            System.out.println("jsonArrLength = "+jsonArrLen);
            for (int i = 0; i < jsonArrLen; i++) {
                JSONObject jsonChildNode = mainNode.getJSONObject(i);
                start_time = jsonChildNode.optString("start_time");
                end_time = jsonChildNode.optString("end_time");
                delay = Integer.parseInt(jsonChildNode.optString("delay"));
                url = jsonChildNode.optString("url");
                unique_id = jsonChildNode.optString("unique_id");
                System.out.println(start_time+ " "+end_time+" "+delay+" "+" "+url+" "+unique_id);
            }
        }catch(org.json.JSONException je){je.printStackTrace();}

    }

    public void display(){

//         for(j=0;j<2;j++) {
//             Drawable drawable = getResources().getDrawable(getResources().getIdentifier
//                     ("img_" + j, "drawable", getPackageName()));
//             System.out.println(drawable.getCurrent().toString());
//             frameAnimation.addFrame(drawable, 2);
//         }
//            new Handler().postDelayed(new Runnable() {
//
//                @Override
//                    //Check network connectivity and server connectivity here and proceed
//                    public void run()
//                    {  //try {
//                        //Thread.sleep(j * 1000); // REMARK HERE!
//                    //}catch(Exception e){}
//
//                        Drawable drawable = getResources().getDrawable(getResources().getIdentifier
//                                ("img_" + j, "drawable", getPackageName()));
//                        System.out.println(drawable.getCurrent().toString());
//                        frameAnimation.addFrame(drawable,DELAY);
//
//                }
//            }, DELAY);

    }

    private class PostTask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {
            return null;
        }
    }
}
