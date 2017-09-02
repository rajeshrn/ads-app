package com.myapp.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static  Button parseButton = null ;
    private static int DELAY =5000;
    private static String start_time = null;
    private static String end_time = null;
    private static int delay = 2;
    private static String unit = null;
    private static String imageDetails = null;
    private static String unique_id = null;
    private static RelativeLayout banner = null;
    private static ImageView imgView = null;
    private int j =0;
    private  AnimationDrawable frameAnimation;
    private TextView scroller ;
    private String LOG_TAG="com.myapp";
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
        new webService().execute();
//        for(j=0;j<3;j++) {
//            Drawable drawable = getResources().getDrawable(getResources().getIdentifier
//                    ("img_" + j, "drawable", getPackageName()));
//            System.out.println(drawable.getCurrent().toString());
//            frameAnimation.addFrame(drawable, DELAY);
//        }
//        frameAnimation.setOneShot(false);
      //  imgView.setBackground(frameAnimation);
        //frameAnimation.start();

//        parseButton = (Button) findViewById(R.id.parseButton);
//    parseButton.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//     //   parseJSON();
//   //     display();
//       new webService().execute();
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

    public void parseJSON(String s){
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
              //  url = jsonChildNode.optString("url");
                unique_id = jsonChildNode.optString("unique_id");
//                System.out.println(start_time+ " "+end_time+" "+delay+" "+" "+url+" "+unique_id);
            }
        }catch(org.json.JSONException je){je.printStackTrace();}

    }


    public class webService extends AsyncTask<String, Void, String>{
        StringBuilder sb = new StringBuilder();
        @Override
        protected String doInBackground(String... params) {
            try {
                URL mUrl = new URL("http://192.168.1.102:8080/Spring4MVCFileUploadDownloadWithHibernate/ads?id=1");
                HttpURLConnection httpConnection = (HttpURLConnection) mUrl.openConnection();
                httpConnection.setRequestMethod("POST");
                httpConnection.setDoInput(true);
                httpConnection.setDoInput(true);
                httpConnection.setAllowUserInteraction(false);
                httpConnection.setConnectTimeout(100000);
                httpConnection.setReadTimeout(100000);
                httpConnection.connect();

                int responseCode = httpConnection.getResponseCode();
                System.out.println("response Code "+responseCode);
                if (responseCode == HttpURLConnection.HTTP_OK){
                    Log.e(LOG_TAG,"HttpConnection Status "+responseCode);
                    BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    Log.e(LOG_TAG,"String Built "+sb.toString());
                    return sb.toString();
                }
            } catch(MalformedURLException e){e.printStackTrace();
            } catch (IOException e) {e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            parsePOSTResult(sb.toString());
            Toast.makeText(getApplicationContext(), result,
                    Toast.LENGTH_LONG).show();

        }
    }
    public void parsePOSTResult(String s){
        String [] urllist = null;
        try {
            JSONObject jsonObj = new JSONObject(s);
            JSONArray mainNode = jsonObj.optJSONArray("response");
            int jsonMainNodeArrLen = mainNode.length();
            Log.e(LOG_TAG,"jsonMainNodeArrLength = "+jsonMainNodeArrLen);

            for (int i = 0; i < jsonMainNodeArrLen; i++) {
                JSONObject jsonChildNode = mainNode.getJSONObject(i);
                start_time = jsonChildNode.optString("startDateTime");
                end_time = jsonChildNode.optString("endDateTime");
                delay = Integer.parseInt(jsonChildNode.optString("delay"));
                unit = jsonChildNode.optString("unit");

                JSONArray childNode = jsonChildNode.optJSONArray("campaignContents");
                Log.e(LOG_TAG,"child node to string "+childNode.toString());
                int jsonChildNodeArrLen = childNode.length();
                urllist = new String[jsonChildNodeArrLen];
                for(int j=0;j<jsonChildNodeArrLen;j++){
                    JSONObject jsonChildNode1 = childNode.getJSONObject(j);
                    imageDetails = jsonChildNode1.optString("ImageDetails");
                    unique_id = jsonChildNode1.optString("unqiqueIdentifier");
                    Log.e(LOG_TAG, start_time+ " "+end_time+" "+delay+" "+" "+unit+" "+imageDetails+" "+unique_id);
                    urllist[j]=imageDetails;
                    Log.e(LOG_TAG,"imgurl sending to download "+urllist[j]);
                    new DownloadImageTask().execute(urllist);
                }
            }
            Log.e(LOG_TAG,imageDetails);

        }catch(org.json.JSONException je){je.printStackTrace();}

    }

    public class DownloadImageTask extends AsyncTask<String,Void,Drawable>{
        Bitmap tmpBmp = null;
        Drawable [] tmpDrawable = null;
        @Override
        protected Drawable doInBackground(String... params) {
            String imgUrl = params[0];
            Log.e(LOG_TAG,"# of imgurl received for downloading "+params.length);
            tmpDrawable = new Drawable[imgUrl.length()];
             for(int k=0;k<imgUrl.length();k++) {
                Log.e(LOG_TAG, "imgurl in downloadImageTask " + imgUrl);
                try {
                    InputStream in = new java.net.URL(imgUrl).openStream();
                    tmpBmp = BitmapFactory.decodeStream(in);
                    tmpDrawable [k] = new BitmapDrawable(getResources(), tmpBmp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return tmpDrawable[k];
            }
            return null;
        }
        protected void onPostExecute(Drawable drawable){
            Log.e(LOG_TAG,"Count on postexecute");
            frameAnimation.addFrame(drawable,DELAY);
            Log.e(LOG_TAG,"# of frames added "+frameAnimation.getNumberOfFrames());
            frameAnimation.setOneShot(false);
            imgView.setBackground(frameAnimation);
            frameAnimation.stop();
            frameAnimation.start();
        }
    }
//    public void getImage(String link) {
//        try {
//            URL url = new URL(link);
//            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            Drawable drawable = new BitmapDrawable(getResources(),bmp);
//            frameAnimation.addFrame(drawable,10);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}

