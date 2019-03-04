package mg.studio.weather;

/**
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Logger;
import  org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
private TextView date1;
private TextView date2;
private Button refreshbutton;
private int weekday;
private int year;
private int month;
private int day;
private  String response;
private String location;
private String curtemp;
private  TextView date3;
private  TextView date4;
private JSONObject jsonData;
private JSONObject info;

private TextView temp1;
private TextView temp2;
private  TextView temp3;
private  TextView temp4;
private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    private ImageView img5;
    private String updatetime="";
    String Ctiyid;
    URLConnection connectionData;
    StringBuilder sb;
    BufferedReader br;// 读取data数据流

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
  date1=findViewById(R.id.tv_date);
   date2= findViewById(R.id.tv_week);
date3=findViewById(R.id.tv_location);
date4=findViewById(R.id.tv_temt);
img1=findViewById(R.id.image1);
img2=findViewById(R.id.image2);
img3=findViewById(R.id.image3);
img4=findViewById(R.id.image4);
img5=findViewById(R.id.img_weather_condition);
temp1=findViewById(R.id.temp1);
temp2=findViewById(R.id.temp2);
temp3=findViewById(R.id.temp3);
temp4=findViewById(R.id.temp4);
   refreshbutton=findViewById(R.id.refreshbut);






    refreshbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

run();


            }
        });

       run();
    }


    void run()
    {
        setConnection();
        jsondate();
    }





    void setConnection()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection urlConnection = null;
                try {
                    URL url = new URL("http://t.weather.sojson.com/api/weather/city/101040100");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setConnectTimeout(8000);
                urlConnection.setReadTimeout(8000);
                    InputStream in = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuffer sb = new StringBuffer();
                    String str;
                    while((str=reader.readLine())!=null)
                    {
                        sb.append(str);
                        Log.d("date from url",str);
                    }
                    response = sb.toString();

                    Log.d("response",response);
                }catch (Exception e)
                {
                    e.printStackTrace();
                } }
        }).start();

      if(response==null)
        {
            setConnection();
        }
    }




    void jsondate()
    {


if (response!=null) {
    try {


String updatetimetemp="this is a test";

        jsonData = new JSONObject(response);
        JSONArray array=new JSONArray("["+response+"]");//第一层
        JSONObject temp=array.getJSONObject(0).getJSONObject("cityInfo");
        updatetimetemp=temp.getString("updateTime");
       if(!updatetime.equals(updatetimetemp))
        {
           updatetime=temp.getString("updateTime");
            date1.setText(jsonData.getString("date"));
            for (int i = 0; i < array.length(); i++) {
                Log.d("array", array.getJSONObject(i).toString() + "\n");
            }

            info = jsonData.getJSONObject("data");


            date3.setText(temp.getString("city"));
            date4.setText(array.getJSONObject(0).getString("time"));


            date4.setText(info.getString("wendu"));

            JSONArray op2 = info.getJSONArray("forecast");

            setblowtemp(op2);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"已经是最新更新",Toast.LENGTH_SHORT).show();
        }






    }  catch(JSONException e){
        e.printStackTrace();
    }  }
else{
    Toast.makeText(getApplicationContext(),"信息错误",Toast.LENGTH_SHORT).show();
}

    }

private void setblowtemp(JSONArray array)
{
    String cmp;String day;
    try {
        changepic(img5,array.getJSONObject(0).getString("type"));
    } catch (JSONException e) {
        e.printStackTrace();
    }
    for(int i=0;i<=4;i++)
    {
        try {
            if (i == 0)
            {
                cmp=array.getJSONObject(i).getString("type");
                day=array.getJSONObject(i).getString("week");
                date2.setText(day);
                changepic(img5,cmp);
            }
            else if(i==1){
            cmp=array.getJSONObject(i).getString("type");
            day=array.getJSONObject(i).getString("week");
temp1.setText(day);

          changepic(img1,cmp);
                }
            else if(i==2)
            {
                cmp=array.getJSONObject(i).getString("type");
                day=array.getJSONObject(i).getString("week");
                temp2.setText(day);
                changepic(img2,cmp);

            }
            else if(i==3)
            {
                cmp=array.getJSONObject(i).getString("type");
                day=array.getJSONObject(i).getString("week");
                temp3.setText(day);
                changepic(img3,cmp);
            }
            else if (i==4)
            {
                cmp=array.getJSONObject(i).getString("type");
                day=array.getJSONObject(i).getString("week");
                temp4.setText(day);
                changepic(img4,cmp);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

public void changepic(ImageView img, String str)
{

    if(str.equals("小雨"))
    img.setImageResource(getImageResourceId("rainy_small"));
else if(str.equals("大雨"))
        img.setImageResource(getImageResourceId("rainy_up"));
else if(str.equals("晴"))
        img.setImageResource(getImageResourceId("sunny_small"));
else if(str.equals("阴"))
        img.setImageResource(getImageResourceId("partly_sunny_small"));
else img.setImageResource(getImageResourceId("notification"));
}


    public int getImageResourceId(String name) {
        R.drawable drawables=new R.drawable();
        //默认的id
        int resId=0x7f02000b;
        try {
            //根据字符串字段名，取字段//根据资源的ID的变量名获得Field的对象,使用反射机制来实现的
            java.lang.reflect.Field field=R.drawable.class.getField(name);
            //取值
            resId=(Integer)field.get(drawables);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resId;
    }



}








