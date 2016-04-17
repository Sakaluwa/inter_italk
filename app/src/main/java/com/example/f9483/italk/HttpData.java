package com.example.f9483.italk;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by 94839 on 2016/4/17.
 */
//继承自异步任务，做通信
public class HttpData extends AsyncTask<String,Void,String> {
    //Google推荐使用HttpUrlConnect,
    //创建客户端
    private HttpClient mHttpClient;
    //请求方式get
    private HttpGet mHttpGet;
    private HttpResponse mHttpResponse;
    //创建Http的实体
    private HttpEntity mHttpEntity;
    //流文件来处理数据
    private InputStream in;

    private String url;
    //实现HttpGetDataListener接口
    private HttpGetDataListener listener;
    //构造方法,参数通过构造方法来传递
    public HttpData(String url,HttpGetDataListener listener){
        this.url=url;
        this.listener=listener;
    }
    @Override
    protected String doInBackground(String... params) {
        try{
            //实例化客户端
            mHttpClient=new DefaultHttpClient();
            //通过Get发送请求
            mHttpGet=new HttpGet(url);
            //获取请求的返回,通过客户端来执行
            mHttpResponse=mHttpClient.execute(mHttpGet);
            //Http的实体，通过Response来获取数据
            mHttpEntity=mHttpResponse.getEntity();
            //获取的数据转换成流文件来处理
            in=mHttpEntity.getContent();
            //获取的内容通过缓冲区来读取
            BufferedReader br=new BufferedReader(new InputStreamReader(in));
            //接下来，我们要获取数据
            String line=null;
            //储存所有的数据
            StringBuffer sb=new StringBuffer();
            //数据非空，循环读取
            while((line=br.readLine())!=null){
                sb.append(line);
            }
            return sb.toString();


        }catch (Exception e){

        }
        return null;
    }

    //以上工作获取到了可视化数据，下面覆写

    @Override
    protected void onPostExecute(String s) {
        //通过接口方法将数据返回
        listener.getDataUrl(s);
        super.onPostExecute(s);
    }
}
