package zhang.zhixuan.mobileapp_airline;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.gc.materialdesign.views.ButtonRectangle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



public class SearchResults extends Activity {
    TextView originTV;
    TextView destinationTV;
    TextView sr_tv_oriCity;
    TextView sr_tv_destCity;
    TextView search_departDate;

    String originStr;
    String destinationStr;
    String depDstr;
    Profile facebookProfile;
    LoginSessionDB dbLogin;
    //String retDstr;
    private String bcName = "All Classes";

 //   private int year_R,month_R,day_R;
    private int year_D,month_D,day_D;
    boolean oneWayOrNot;
    Handler handler;
    //    private List<Map<String, Object>> flights_Result;
    private List<FlightEntity> flights_Result;
    ListView lv;
    private int oldPostion = -1;
    private FlightEntity flight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        dbLogin = new LoginSessionDB(this);

        //depD = (TextView)findViewById(R.id.sr_tv_depD);
        //retD = (TextView)findViewById(R.id.sr_tv_retD);
        Intent intent = getIntent();
        //set parameter

        bcName = intent.getStringExtra("bcIndex");

//        switch (bcIndex){
//            case 2131558607: bcName = "First Class";break;
//            case 2131558608: bcName = "Business Class";break;
//            case 2131558609: bcName = "Premium Economy Class";break;
//            case 2131558610: bcName = "Economy Class";break;
//            case 2131558611: bcName = "All Classes";break;
//        }
        System.out.println("bcName!!!!!"+bcName);
        originStr = intent.getStringExtra("origin");
        destinationStr = intent.getStringExtra("destination");
        year_D = intent.getIntExtra("year_D", 0);
        month_D = intent.getIntExtra("month_D", 0);
        day_D = intent.getIntExtra("day_D", 0);
        oneWayOrNot = intent.getBooleanExtra("OneWayOrNot", false);
        sr_tv_oriCity = (TextView)findViewById(R.id.sr_tv_oriCity);
        sr_tv_oriCity.setText(originStr);
        sr_tv_destCity = (TextView)findViewById(R.id.sr_tv_destCity);
        sr_tv_destCity.setText(destinationStr);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMM yyyy");
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd-MM-yy");
        System.out.println("one way or not " + oneWayOrNot);
//        if(!oneWayOrNot){
//            year_R = intent.getIntExtra("year_R", 0);
//            month_R = intent.getIntExtra("month_R",0);
//            day_R = intent.getIntExtra("day_R",0);
//            Calendar calendar = Calendar.getInstance();
//            calendar.set(Calendar.YEAR, year_R);
//            calendar.set(Calendar.MONTH, month_R);
//            calendar.set(Calendar.DAY_OF_MONTH, day_R);
//            Date returnD = calendar.getTime();
//            retD.setText("Return Date: "+simpleDateFormat.format(returnD));
//            retDstr = simpleDateFormat1.format(returnD);
//
//        }
//        else
//        {
//        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year_D);
        calendar.set(Calendar.MONTH, month_D);
        calendar.set(Calendar.DAY_OF_MONTH, day_D);
        Date departureD = calendar.getTime();
        search_departDate = (TextView)findViewById(R.id.search_departDate);
        search_departDate.setText(simpleDateFormat.format(departureD));
        depDstr = simpleDateFormat1.format(departureD);


        flights_Result = new ArrayList<>();
//        if(oneWayOrNot){

            searchFlights_OneWay(originStr,destinationStr);
//        }else
//        {
//         searchFlights_TwoWay(destinationStr,originStr);
//        }





    }

    @Override
    public void onResume(){
        super.onResume();
    }

//    public void searchFlights_TwoWay(String originStr1, String destinationStr1){
//        System.err.println("enter searchFlights_Oneway");
//        ClassAsyncTask_Flookup classAsyncTask_flookup = new ClassAsyncTask_Flookup();
//        classAsyncTask_flookup.execute("http://172.25.99.129:8080/MerlionAirlinesSystem-war/webresources/generic/getOneWayFlightsByRouteDate?origin=" + originStr1 + "&destination=" + destinationStr1 + "&departureD=" + depDstr+"&bcName="+bcName);
//
//        System.out.println("listview");
//        System.out.println(flights_Result);
//
//
//
//    }

    public void searchFlights_OneWay(String originStr1, String destinationStr1){
        System.err.println("enter searchFlights_Oneway");
        ClassAsyncTask_Flookup classAsyncTask_flookup = new ClassAsyncTask_Flookup();
        classAsyncTask_flookup.execute("http://192.168.1.106:8080/MerlionAirlinesSystem-war/webresources/generic/getOneWayFlightsByRouteDate?origin=" + originStr1 + "&destination=" + destinationStr1 + "&departureD=" + depDstr+"&bcName="+bcName);

        System.out.println("listview");
        System.out.println(flights_Result);



    }
    public class ClassAsyncTask_Flookup extends AsyncTask<String, Void, String> {



        public String doInBackground(String... str) {
            System.err.println("enter do in background");return getJSON(str[0]);
        }

        public void onPostExecute(String result) {

            try {
                System.out.println("start json");
                JSONArray jsonArray = new JSONArray(result);

                System.out.println("before iterator");
                System.out.println(jsonArray.getJSONObject(0).getString("flightNo"));
                System.out.println("jsonArray.length():"+jsonArray.length());
                for(int i=0;i<jsonArray.length();i++) {
                    JSONObject flight = jsonArray.getJSONObject(i);
                    String fn = flight.getString("flightNo");
                    String dpD = flight.getString("departureDate");
                    String ariD = flight.getString("arrivalDate");
                    String bookingClassName = flight.getString("bookingClassName");
                    Double price = flight.getDouble("price");
                    String origin = flight.getString("origin");
                    String destination = flight.getString("destination");
                    String oriAirportName = flight.getString("oriAirportName");
                    String desAirportName = flight.getString("desAirportName");
                    String oriAirportCode = flight.getString("oriAirportCode");
                    String desAirportCode = flight.getString("desAirportCode");
                    String depDayWE = flight.getString("depDayWE");
                    String ariDayWE = flight.getString("ariDayWE");
                    String depTimeE = flight.getString("depTimeE");
                    String ariTimeE = flight.getString("ariTimeE");
                    String timeD = flight.getString("timeDuration");
                    Double timeDD = Double.parseDouble(timeD);
                    Integer hours = timeDD.intValue();
                    Double minutes = (timeDD - hours)*60;
                    Integer minutesD = minutes.intValue();
                    String aircraftTailN = flight.getString("aircraftTailN");
                    if(aircraftTailN==null){
                        aircraftTailN = "AK704";
                    }
//                    Map<String, Object> map = new HashMap<String, Object>();
                    FlightEntity flightEntity = new FlightEntity();
                    flightEntity.setFlightNo(fn);
                    flightEntity.setDepartureDate(dpD);
                    flightEntity.setArrivalDate(ariD);
                    flightEntity.setPriceD(price);
                    flightEntity.setBookingClassName(bookingClassName);
                    DecimalFormat df = new DecimalFormat("0.00");
                    String priceStr = df.format(price);
                    flightEntity.setPrice(priceStr);
                    flightEntity.setOrigin(origin);
                    flightEntity.setDestination(destination);
                    flightEntity.setOriAirportName(oriAirportName);
                    flightEntity.setOriAirportCode(oriAirportCode);
                    flightEntity.setDesAirportName(desAirportName);
                    flightEntity.setDesAirportCode(desAirportCode);
                    flightEntity.setDepDayWE(depDayWE);
                    flightEntity.setDepTimeE(depTimeE);
                    flightEntity.setAriDayWE(ariDayWE);
                    flightEntity.setAircraftTailN(aircraftTailN);
                    flightEntity.setTimeDuration(hours.toString());
                    flightEntity.setTimeDminutes(minutesD.toString());
                    flightEntity.setId(flight.getLong("id"));

                    flightEntity.setAriTimeE(ariTimeE);
//                    map.put("flightNo", fn);
//                    map.put("departureDate", dpD);
//                    map.put("arrivalDate", ariD);
                    flights_Result.add(flightEntity);
                }

                lv = (ListView)findViewById(R.id.sr_lv);
                originTV = (TextView)findViewById(R.id.sr_tv_ori);
                destinationTV = (TextView)findViewById(R.id.sr_tv_dest);

                if(flights_Result.isEmpty()){
                    originTV.setText("No Flights");
                    destinationTV.setText("No Flights");
                }else {
                    originTV.setText(flights_Result.get(0).getOriAirportCode());
                    destinationTV.setText(flights_Result.get(0).getDesAirportCode());
                }
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                    }
//                });

//                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), flights_Result, R.layout.listresult,
//                        new String[]{"flightNo", "departureDate", "arrivalDate"},
//                        new int[]{R.id.flightNo, R.id.departureDate, R.id.arrivalDate});
//                lv.setAdapter(adapter);
                final MyAdapter adapter = new MyAdapter(getApplicationContext());
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        FlightEntity data = flights_Result.get(position);
                        if (oldPostion == position) {
                            if (data.expand) {
                                oldPostion = -1;
                            }
                            data.expand = !data.expand;
                        } else {
                            oldPostion = position;
                            data.expand = true;
                        }

//                        int totalHeight = 0;
//                        for (int i = 0; i < adapter.getCount(); i++) {
//                            View viewItem = adapter.getView(i, null, lv);//这个很重要，那个展开的item的measureHeight比其他的大
//                            viewItem.measure(0, 0);
//                            totalHeight += viewItem.getMeasuredHeight();
//                        }
//
//                        ViewGroup.LayoutParams params = lv.getLayoutParams();
//                        params.height = totalHeight
//                                + (lv.getDividerHeight() * (lv.getCount() - 1));
//                        lv.setLayoutParams(params);
                        adapter.notifyDataSetChanged();
                    }
                });




            } catch (Exception e) {
                Log.d("ReadCurrencyJSON", e.getLocalizedMessage());
            }

        }
    }
    public String getJSON(String urlStr) {

        URL url = convertToUrl(urlStr);

        HttpURLConnection httpURLConnection = null;

        int responseCode;

        StringBuilder stringBuilder = new StringBuilder();

        String line;

        System.err.println("enter getJSON URL"+urlStr);

        try {
            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            System.err.println("before connect");
            httpURLConnection.connect();
            System.err.println("after connect ");
            responseCode = httpURLConnection.getResponseCode();
            System.err.println("responseCode:"+responseCode);
            if (responseCode == httpURLConnection.HTTP_OK) {
                System.err.println("result is ok");

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();
                System.err.println("already read data");
            }
            System.err.println("result is not ok");
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
            e.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
        }
        System.err.println("stringBuilder.toString():"+stringBuilder.toString());
        return stringBuilder.toString();
    }


    // the following code convertToUrl is from
    // http://fancifulandroid.blogspot.sg/2013/07/android-convert-string-to-valid-url.html
    //
    private URL convertToUrl(String urlStr) {
        try {
            URL url = new URL(urlStr);
            URI uri = new URI(url.getProtocol(), url.getUserInfo(),
                    url.getHost(), url.getPort(), url.getPath(),
                    url.getQuery(), url.getRef());
            url = uri.toURL();
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;}

    public final class ViewHolder{

        public TextView listResult_departTime;
        public TextView listResult_reachTime;
        public TextView listResult_class;
        public TextView listResult_flightNumber;
        public TextView listResult_price;
        public TextView listResult_Duration;
        public ButtonRectangle bookbtn;
        public RelativeLayout search_expandLayout;

    }


    public class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;


        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            System.out.println("getCount");
            return flights_Result.size();
        }

        //        @Override
//        public Object getItem(int arg0) {
//            // TODO Auto-generated method stub
//            return null;
//        }
//
//        @Override
//        public long getItemId(int arg0) {
//            // TODO Auto-generated method stub
//            return 0;
//        }
        @Override
        public Object getItem(int position) {
            return flights_Result.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            System.out.println("enter getView"+position);
            ViewHolder holder = new ViewHolder();

            FlightEntity flightEntity1 = flights_Result.get(position);
            if (convertView == null) {


                convertView = mInflater.inflate(R.layout.listresult, null);

               /* holder.flightNo = (TextView) convertView.findViewById(R.id.flightNo);
                holder.departureDate = (TextView) convertView.findViewById(R.id.departureDate);
                holder.arrivalDate = (TextView) convertView.findViewById(R.id.arrivalDate);
                holder.bookbtn = (ButtonRectangle) convertView.findViewById(R.id.bookbtn);
                holder.fDetails = (RelativeLayout) convertView.findViewById(R.id.fDetails);
                holder.bookingClass = (TextView) convertView.findViewById(R.id.bookingClass);
                holder.price = (TextView) convertView.findViewById(R.id.price);*/
                holder.listResult_departTime = (TextView)convertView.findViewById(R.id.listResult_departTime);
                holder.listResult_reachTime = (TextView)convertView.findViewById(R.id.listResult_reachTime);
                holder.listResult_class = (TextView)convertView.findViewById(R.id.listResult_class);
                holder.listResult_price = (TextView)convertView.findViewById(R.id.listResult_price);
                holder.listResult_flightNumber = (TextView)convertView.findViewById(R.id.listResult_flightNumber);
                holder.listResult_Duration = (TextView)convertView.findViewById(R.id.listResult_Duration);
                holder.search_expandLayout = (RelativeLayout)convertView.findViewById(R.id.search_expandLayout);
                holder.bookbtn = (ButtonRectangle)convertView.findViewById(R.id.bookbtn);
                convertView.setTag(holder);

            } else {

                holder = (ViewHolder) convertView.getTag();
            }
            if(flightEntity1.expand) {
                holder.search_expandLayout.setVisibility(View.VISIBLE);
            }else{
                holder.search_expandLayout.setVisibility(View.GONE);
            }

            holder.listResult_departTime.setText(flights_Result.get(position).getDepTimeE());
            holder.listResult_reachTime.setText(flights_Result.get(position).getAriTimeE());
            if(flights_Result.get(position).getBookingClassName().equals("Premium Economy Class"))
                flights_Result.get(position).setBookingClassName("Premium Economy");
            holder.listResult_class.setText(flights_Result.get(position).getBookingClassName());
            holder.listResult_flightNumber.setText(flights_Result.get(position).getFlightNo());
            holder.listResult_price.setText("S$ "+flights_Result.get(position).getPrice());
            if(flights_Result.get(position).getTimeDminutes().equals("0"))
            holder.listResult_Duration.setText("Duration: "+flights_Result.get(position).getTimeDuration()+"hours "+flights_Result.get(position).getTimeDminutes()+" mins");


            holder.bookbtn.setTag(position+"");
            holder.bookbtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
//                    mInflater = LayoutInflater.from(getApplicationContext());
//                    System.out.println("start1");
//                    View view = mInflater.inflate(R.layout.listresult, null);
//                    System.out.println("use convertView");
//
//
//                    TextView flightNoChosen = (TextView)view.findViewById(R.id.flightNo);
//                    TextView depDChosen = (TextView)findViewById(R.id.departureDate);
                    int index = Integer.parseInt(v.getTag().toString());
                    FlightEntity f = flights_Result.get(index);
                    Intent intent = new Intent(getApplicationContext(), DetailPage.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("chosenFlight",f);

                    intent.putExtras(bundle);
//                    System.out.println("button"+flightNoChosen+"depdCHOSEN"+depDChosen);
//                    System.out.println("getText1"+flightNoChosen.getText());
//                    System.out.println("getText2"+depDChosen.getText());
                    startActivity(intent);
                }
            });


            return convertView;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_results, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void main_float_account (View view) {
        facebookProfile = Profile.getCurrentProfile();
        if (facebookProfile != null) {
            Intent intent = new Intent(this, FacebookAccountPage.class);
            startActivity(intent);
        } else {
            dbLogin.open();
            Cursor c = dbLogin.getAllSession();
            System.out.println("after get All session");
            if(!c.moveToFirst()){
                System.out.println("c==null insertLoginSession");
                dbLogin.close();
                Intent intent = new Intent(this, LoginPage.class);
                startActivity(intent);
            }else {
                System.out.println("c!-null");
                String loginStatus = c.getString(1);
                System.out.println("loginStatus" + loginStatus);
                String emailAuto = c.getString(2);
                dbLogin.close();

                if (loginStatus.equals("true")) {
                    Intent intent = new Intent(this, AccountManagementPage.class);
                    intent.putExtra("email", emailAuto);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, LoginPage.class);
                    startActivity(intent);
                }
            }
        }
    }

    public void main_float_checkIn (View view) {
        Intent intent = new Intent(this,WebCheckInHomePage.class);
        startActivity(intent);
    }

    public void main_float_search (View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
