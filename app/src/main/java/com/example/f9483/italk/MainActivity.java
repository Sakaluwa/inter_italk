package com.example.f9483.italk;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//先实现接口
public class MainActivity extends AppCompatActivity  implements HttpGetDataListener,
        View.OnClickListener { //按钮监听事件，覆写
    //异步请求对象
    private HttpData httpData;
    //创建一个集合
    private List<ListData> lists;
    private ListView lv;
    private EditText sendtext;
    private Button send_btn;
    //自己输入内容
    private  String content_str;
    //Adapter内容提供
    private TextAdapter adapter;
    private String[] welcome_array;
    private double currentTime,oldTime=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //异步请求对象启动
//        httpData= (HttpData) new HttpData("http://www.tuling123.com/openapi/api" +
//                "?key=9bec00e0be3c1b7106055785fc0c71a6&info=你好啊",this).execute();//启动异步通信
    }
    //通过此方法获取字符数组欢迎语
    protected String getRandomWelcomeTips(){
        String welcome_tip=null;
        welcome_array=this.getResources().getStringArray(R.array.welcome_tips);
        int index=(int)(Math.random()*welcome_array.length-1);
        welcome_tip=welcome_array[index];
        return welcome_tip;
    }


    @Override
    //通过覆写接口函数操作获取的数据
    public void getDataUrl(String data) {
        parse(data);
    }
    //初始化视窗
    public void initView(){
        lv= (ListView) findViewById(R.id.lv);
        sendtext= (EditText) findViewById(R.id.sendText);
        send_btn= (Button) findViewById(R.id.send_btn);
        lists=new ArrayList<ListData>();
        send_btn.setOnClickListener(this);//开始给按钮设置监听事件
        adapter=new TextAdapter(lists,this);
        //为列表视图绑定Adapter
        lv.setAdapter(adapter);
        ListData listData;
        listData=new ListData(getRandomWelcomeTips(),ListData.RECEIVER,getTime());
        lists.add(listData);
        adapter.notifyDataSetChanged();
    }

    //Json数据格式解析
    public void parse(String str){
        try {
            //json数据对象实例化
            JSONObject jb=new JSONObject(str);
            //输出json键值
//            System.out.println(jb.getString("code"));
//            System.out.println(jb.getString("text"));
            //封装数据
            ListData listData;
            //获得一条数据
            listData=new ListData(jb.getString("text"),ListData.RECEIVER,getTime());
            //将数据添加到lists集合中去
            lists.add(listData);
            //重新适配
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        getTime();
        //文本编辑框获取内容
        content_str=sendtext.getText().toString();
        //去除空格
        String dropk=content_str.replace(" ","");
        //去除回车
        String droph=dropk.replace("\n","");
        ListData listData;
        listData=new ListData(content_str,ListData.SEND,getTime());
        lists.add(listData);
        //若lists数据过多，则将其部分数据移除
        if(lists.size()>30){
            for (int i=0;i<lists.size();i++){
                //调用remove函数
                lists.remove(i);
            }
        }
        //重新适配
        adapter.notifyDataSetChanged();

        httpData= (HttpData) new HttpData("http://www.tuling123.com/openapi/api" +
                "?key=9bec00e0be3c1b7106055785fc0c71a6&info="+droph,this).execute();//启动异步通信

    }
    private String getTime(){
        currentTime=System.currentTimeMillis();
        SimpleDateFormat format=new SimpleDateFormat("yyyy年mm月dd日  HH:mm:ss");
        Date curDate=new Date();
        String str=format.format(curDate);
        if(currentTime-oldTime>=1000){
            oldTime=currentTime;
            return str;
        }
        else {
            return "";
        }
    }
}
