package formatfa.craw.andcrawclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import formatfa.craw.client.AndClient;
import formatfa.craw.client.Connection;
import formatfa.craw.protocol.Request;
import formatfa.craw.protocol.Response;

public class ViewActivity extends AppCompatActivity {

    private String host="192.168.0.123";
    private int port=2333;
    private LayoutView layoutView;
    private Request layout_req = new Request("get",null);
    Connection connection;
    boolean running=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            Intent intent = getIntent();
            if(intent!=null)
            {
                this.host=intent.getStringExtra("host");
                this.port=Integer.parseInt( intent.getStringExtra("port"));
            }
            layoutView = new LayoutView(this);
            setContentView(layoutView);


            new UpdateTask().execute(layoutView);

    }


    String tag = "andcraw client";
    class UpdateTask extends AsyncTask<LayoutView, JSONObject,String>
    {

        @Override
        protected void onPreExecute() {

            Log.w(tag,"on preExecute");
        }

        @Override
        protected void onPostExecute(String s) {
            Log.e(tag,"运行结束，结果:"+s);
        }

        @Override
        protected void onProgressUpdate(JSONObject... values) {
            Log.d(tag,"on progress update:"+values[0]);
            layoutView.setLayouts(values[0]);
            layoutView.invalidate();

        }

        @Override
        protected String doInBackground(LayoutView... layoutViews) {
            try {
                connection = AndClient.connect(host,port);
            } catch (IOException e) {

                e.printStackTrace();
                return "connection 连接异常";
            }
            while(running)
            {

                Response response = null;
                try {
                    response = connection.request(layout_req);
                } catch (Exception e) {
                    e.printStackTrace();
                    return "request请求失败1";
                }
                if(response==null)
                {
                    return "response null";
                }
                JSONObject layouts = JSONObject.parseObject(response.getBody());
                publishProgress(layouts);
//                running=false;

            }
            return null;
        }
    }

}
