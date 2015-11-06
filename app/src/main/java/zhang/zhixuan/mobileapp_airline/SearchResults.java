package zhang.zhixuan.mobileapp_airline;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
    TextView depD;
    TextView retD;
    String originStr;
    String destinationStr;
    String depDstr;
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

        originTV = (TextView)findViewById(R.id.sr_tv_ori);
        destinationTV = (TextView)findViewById(R.id.sr_tv_dest);
        depD = (TextView)findViewById(R.id.sr_tv_depD);
        retD = (TextView)findViewById(R.id.sr_tv_retD);
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
        month_D = intent.getIntExtra("month_D",0);
        day_D = intent.getIntExtra("day_D", 0);
        oneWayOrNot = intent.getBooleanExtra("OneWayOrNot", false);
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
            retD.setText("");
//        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year_D);
        calendar.set(Calendar.MONTH, month_D);
        calendar.set(Calendar.DAY_OF_MONTH, day_D);
        Date departureD = calendar.getTime();
        depD.setText(simpleDateFormat.format(departureD));
        depDstr = simpleDateFormat1.format(departureD);

        originTV.setText(originStr);
        destinationTV.setText(destinationStr);
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
                    DecimalFormat df = new DecimalFormat("0.0");
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
                    flightEntity.setTimeDuration(timeD);
                    flightEntity.setId(flight.getLong("id"));

                    flightEntity.setAriTimeE(ariTimeE);
//                    map.put("flightNo", fn);
//                    map.put("departureDate", dpD);
//                    map.put("arrivalDate", ariD);
                    flights_Result.add(flightEntity);
                }

                lv = (ListView)findViewById(R.id.sr_lv);
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

                        int totalHeight = 0;
                        for (int i = 0; i < adapter.getCount(); i++) {
                            View viewItem = adapter.getView(i, null, lv);//这个很重要，那个展开的item的measureHeight比其他的大
                            viewItem.measure(0, 0);
                            totalHeight += viewItem.getMeasuredHeight();
                        }

                        ViewGroup.LayoutParams params = lv.getLayoutParams();
                        params.height = totalHeight
                                + (lv.getDividerHeight() * (lv.getCount() - 1));
                        lv.setLayoutParams(params);
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

        public TextView flightNo;
        public TextView departureDate;
        public TextView arrivalDate;
        public TextView bookingClass;
        public TextView price;
        public ButtonRectangle bookbtn;
        public LinearLayout fDetails;

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

                holder.flightNo = (TextView) convertView.findViewById(R.id.flightNo);
                holder.departureDate = (TextView) convertView.findViewById(R.id.departureDate);
                holder.arrivalDate = (TextView) convertView.findViewById(R.id.arrivalDate);
                holder.bookbtn = (ButtonRectangle) convertView.findViewById(R.id.bookbtn);
                holder.fDetails = (LinearLayout) convertView.findViewById(R.id.fDetails);
                holder.bookingClass = (TextView) convertView.findViewById(R.id.bookingClass);
                holder.price = (TextView) convertView.findViewById(R.id.price);
                convertView.setTag(holder);

            } else {

                holder = (ViewHolder) convertView.getTag();
            }
            if(flightEntity1.expand) {
                holder.fDetails.setVisibility(View.VISIBLE);
            }else{
                holder.fDetails.setVisibility(View.GONE);
            }

            holder.flightNo.setText((String) flights_Result.get(position).getFlightNo());
            holder.departureDate.setText("Depart: "+(String) flights_Result.get(position).getDepTimeE());
            holder.arrivalDate.setText("Arrive: "+(String) flights_Result.get(position).getAriTimeE()+"\n Time Duration: "+flights_Result.get(position).getTimeDuration()+" hours");
            holder.bookingClass.setText((String) flights_Result.get(position).getBookingClassName());
            String priceName = flights_Result.get(position).getPrice()+" SGD";
            holder.price.setText(priceName);

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
}
