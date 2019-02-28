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
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.logging.Logger;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
  date1=findViewById(R.id.tv_date);
   date2= findViewById(R.id.tv_week);
date3=findViewById(R.id.tv_location);
date4=findViewById(R.id.tv_temt);

        refreshbutton=findViewById(R.id.refreshbut);
        getWeatherDatafromNet("101040100");
        refreshbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


           settime();
             setcurrtemp();



            }
        });


    }
void settime()//获取系统时间
{
    Calendar temp=gettime();

    year = temp.get(Calendar.YEAR);
//月
    month = temp.get(Calendar.MONTH)+1;
//日
    day = temp.get(Calendar.DAY_OF_MONTH);

    weekday=temp.get(Calendar.DAY_OF_WEEK);

    //    Toast.makeText(getApplicationContext(),weekday,Toast.LENGTH_SHORT).show();
    date1.setText(year+"/"+month+"/"+day);

    date2.setText(getweek(weekday-1));
}
void setcurrtemp()
{
    getWeatherDatafromNet("101040100");
    if(response!=null)
    {
location=subString(response,"<city>","</city>");
curtemp=subString(response,"<wendu>","</wendu>");
date3.setText(location);
date4.setText(curtemp);
    }
    else
    {
      return;
    }
}

    private void getWeatherDatafromNet(String cityCode)

    {  final String address = "http://wthrcdn.etouch.cn/WeatherApi?citykey="+cityCode;
    Log.d("Address:",address);
    new Thread(new Runnable() {
        @Override
        public void run() {
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(address);
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
    }).start(); }



    Calendar gettime()
    {

        Calendar calendar = Calendar.getInstance();
         return calendar;
    }
    String getweek(int n)
    {

          if(n==1)
                return "Monday";
      else    if(n==2)
                return "Tuesday" ;
          else   if(n==3)
                return "Wednesday";
         else if(n==4)
                return "Thursday" ;
          else if(n==5)
                return "Friday";
           else if(n==6)
                return "Saturday";
          else if(n==0)
                return "Sunday";
else
    return "UNCLEAR";

    }

    /**
     * 截取字符串str中指定字符 strStart、strEnd之间的字符串
     *
     * @param string
     * @param str1
     * @param str2
     * @return
     */
    public static String subString(String str, String strStart, String strEnd) {

        /* 找出指定的2个字符在 该字符串里面的 位置 */
        int strStartIndex = str.indexOf(strStart);
        int strEndIndex = str.indexOf(strEnd);

        /* index 为负数 即表示该字符串中 没有该字符 */
        if (strStartIndex < 0) {
            return "字符串 :---->" + str + "<---- 中不存在 " + strStart + ", 无法截取目标字符串";
        }
        if (strEndIndex < 0) {
            return "字符串 :---->" + str + "<---- 中不存在 " + strEnd + ", 无法截取目标字符串";
        }
        /* 开始截取 */
        String result = str.substring(strStartIndex, strEndIndex).substring(strStart.length());
        return result;
    }

}
