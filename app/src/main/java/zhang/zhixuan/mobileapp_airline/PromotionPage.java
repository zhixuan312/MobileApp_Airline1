package zhang.zhixuan.mobileapp_airline;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonRectangle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

public class PromotionPage extends Activity {

    private TextView promotion_tv;
    private List<Address> addressList;
    String addressLine;
    String city;
    String state;
    String country;
    String postalCode;
    String knownName;
    List<FlightEntity> flights_ResultF;
    List<FlightEntity> flights_ResultB;
    List<FlightEntity> flights_ResultP;
    List<FlightEntity> flights_ResultE;

    ListView lv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_page);
        promotion_tv = (TextView) findViewById(R.id.promotion_tv);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            double myLatitude = location.getLatitude();
            double myLongitude = location.getLongitude();


            try{
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                addressList = geocoder.getFromLocation(myLatitude, myLongitude, 1);
//                if (addressList.size() <= 0){
//                    addressList = new Geocoder (this).getFromLocation(myLatitude,myLongitude,1);
//                }
                if (addressList.get(0) != null) {
                    Address address = addressList.get(0);
                    for (int b=0; b<address.getMaxAddressLineIndex(); b++) {
                         addressLine = addressList.get(0).getAddressLine(b);
                         city    = addressList.get(0).getLocality();
                         state   = addressList.get(0).getAdminArea();
                         country = addressList.get(0).getCountryName();
                         postalCode = addressList.get(0).getPostalCode();
                         knownName = addressList.get(0).getFeatureName();

                        promotion_tv.setText("You location is at " + addressLine + ", " + country +
                                ", " + postalCode);
                    }
                } else {
                    promotion_tv.setText("No data");
                }
            } catch (IOException e) {
                Log.e("Getting Address: ", "Error: ", e);
            }

        }


    }
    public void searchFlights_OneWay(String originStr1, String destinationStr1){
        System.err.println("enter searchFlights_Oneway");
        ClassAsyncTask_Flookup classAsyncTask_flookup = new ClassAsyncTask_Flookup();
        classAsyncTask_flookup.execute("http://192.168.1.106:8080/MerlionAirlinesSystem-war/webresources/generic/getPromotedFlights?cityName=" + country);

        System.out.println("listview");



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
                    if(flightEntity.getBookingClassName().equals("First Class"))
                        flights_ResultF.add(flightEntity);
                    else if (flightEntity.getBookingClassName().equals("Business Class"))
                        flights_ResultB.add(flightEntity);
                    else if(flightEntity.getBookingClassName().equals("Premium Economy Class"))
                        flights_ResultP.add(flightEntity);
                    else if (flightEntity.getBookingClassName().equals("Economy Class"))
                        flights_ResultE.add(flightEntity);
                }

                lv = (ListView)findViewById(R.id.promotion_lv);
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                    }
//                });

//                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), flights_Result, R.layout.listresult,
//                        new String[]{"flightNo", "departureDate", "arrivalDate"},
//                        new int[]{R.id.flightNo, R.id.departureDate, R.id.arrivalDate});
//                lv.setAdapter(adapter);
                final MyAdapterF myAdapterF = new MyAdapterF(getApplicationContext());
                lv.setAdapter(myAdapterF);




            } catch (Exception e) {
                Log.d("ReadCurrencyJSON", e.getLocalizedMessage());
            }

        }
    }
    public void pm_fc(View view){
        lv = (ListView)findViewById(R.id.promotion_lv);
        final MyAdapterF myAdapterF = new MyAdapterF(getApplicationContext());
        lv.setAdapter(myAdapterF);
    }

    public void pm_bc(View view){
        lv = (ListView)findViewById(R.id.promotion_lv);
        final MyAdapterB myAdapterB = new MyAdapterB(getApplicationContext());
        lv.setAdapter(myAdapterB);
    }

    public void pm_pc(View view){
        lv = (ListView)findViewById(R.id.promotion_lv);
        final MyAdapterP myAdapterP = new MyAdapterP(getApplicationContext());
        lv.setAdapter(myAdapterP);
    }

    public void pm_ec(View view){
        lv = (ListView)findViewById(R.id.promotion_lv);
        final MyAdapterE myAdapterE = new MyAdapterE(getApplicationContext());
        lv.setAdapter(myAdapterE);
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

        public TextView price;
        public TextView path;
        public TextView date;
        public ButtonRectangle btn;

    }



    public class MyAdapterF extends BaseAdapter {

        private LayoutInflater mInflater;


        public MyAdapterF(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            System.out.println("getCount");
            return flights_ResultF.size();
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
            return flights_ResultF.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            System.out.println("enter getView"+position);
            ViewHolder holder = new ViewHolder();

            FlightEntity flightEntity1 = flights_ResultF.get(position);
            if (convertView == null) {


                convertView = mInflater.inflate(R.layout.promoteresults, null);

                holder.price = (TextView) convertView.findViewById(R.id.pm_price);
                holder.path = (TextView) convertView.findViewById(R.id.pm_path);
                holder.date = (TextView) convertView.findViewById(R.id.pm_date);
                holder.btn = (ButtonRectangle)convertView.findViewById(R.id.pm_bookbtn);
                convertView.setTag(holder);

            } else {

                holder = (ViewHolder) convertView.getTag();
            }


            holder.price.setText((String) flights_ResultF.get(position).getPrice());
            holder.path.setText((String) flights_ResultF.get(position).getOrigin()+" to "+flights_ResultF.get(position).getDestination());
            holder.date.setText(flights_ResultF.get(position).getDepDayWE());


            holder.btn.setTag(position + "");
            holder.btn.setOnClickListener(new View.OnClickListener() {

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
                    FlightEntity f = flights_ResultF.get(index);
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

    public class MyAdapterB extends BaseAdapter {

        private LayoutInflater mInflater;


        public MyAdapterB(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            System.out.println("getCount");
            return flights_ResultB.size();
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
            return flights_ResultB.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            System.out.println("enter getView"+position);
            ViewHolder holder = new ViewHolder();

            FlightEntity flightEntity1 = flights_ResultB.get(position);
            if (convertView == null) {


                convertView = mInflater.inflate(R.layout.promoteresults, null);

                holder.price = (TextView) convertView.findViewById(R.id.pm_price);
                holder.path = (TextView) convertView.findViewById(R.id.pm_path);
                holder.date = (TextView) convertView.findViewById(R.id.pm_date);
                holder.btn = (ButtonRectangle)convertView.findViewById(R.id.pm_bookbtn);
                convertView.setTag(holder);

            } else {

                holder = (ViewHolder) convertView.getTag();
            }


            holder.price.setText((String) flights_ResultB.get(position).getPrice());
            holder.path.setText((String) flights_ResultB.get(position).getOrigin()+" to "+flights_ResultB.get(position).getDestination());
            holder.date.setText(flights_ResultB.get(position).getDepDayWE());


            holder.btn.setTag(position+"");
            holder.btn.setOnClickListener(new View.OnClickListener() {

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
                    FlightEntity f = flights_ResultB.get(index);
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
    public class MyAdapterP extends BaseAdapter {

        private LayoutInflater mInflater;


        public MyAdapterP(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            System.out.println("getCount");
            return flights_ResultP.size();
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
            return flights_ResultP.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            System.out.println("enter getView"+position);
            ViewHolder holder = new ViewHolder();

            FlightEntity flightEntity1 = flights_ResultP.get(position);
            if (convertView == null) {


                convertView = mInflater.inflate(R.layout.promoteresults, null);

                holder.price = (TextView) convertView.findViewById(R.id.pm_price);
                holder.path = (TextView) convertView.findViewById(R.id.pm_path);
                holder.date = (TextView) convertView.findViewById(R.id.pm_date);
                holder.btn = (ButtonRectangle)convertView.findViewById(R.id.pm_bookbtn);
                convertView.setTag(holder);

            } else {

                holder = (ViewHolder) convertView.getTag();
            }


            holder.price.setText((String) flights_ResultP.get(position).getPrice());
            holder.path.setText((String) flights_ResultP.get(position).getOrigin()+" to "+flights_ResultP.get(position).getDestination());
            holder.date.setText(flights_ResultP.get(position).getDepDayWE());


            holder.btn.setTag(position+"");
            holder.btn.setOnClickListener(new View.OnClickListener() {

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
                    FlightEntity f = flights_ResultP.get(index);
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
    public class MyAdapterE extends BaseAdapter {

        private LayoutInflater mInflater;


        public MyAdapterE(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            System.out.println("getCount");
            return flights_ResultB.size();
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
            return flights_ResultE.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            System.out.println("enter getView"+position);
            ViewHolder holder = new ViewHolder();

            FlightEntity flightEntity1 = flights_ResultE.get(position);
            if (convertView == null) {


                convertView = mInflater.inflate(R.layout.promoteresults, null);

                holder.price = (TextView) convertView.findViewById(R.id.pm_price);
                holder.path = (TextView) convertView.findViewById(R.id.pm_path);
                holder.date = (TextView) convertView.findViewById(R.id.pm_date);
                holder.btn = (ButtonRectangle)convertView.findViewById(R.id.pm_bookbtn);
                convertView.setTag(holder);

            } else {

                holder = (ViewHolder) convertView.getTag();
            }


            holder.price.setText((String) flights_ResultE.get(position).getPrice());
            holder.path.setText((String) flights_ResultE.get(position).getOrigin()+" to "+flights_ResultE.get(position).getDestination());
            holder.date.setText(flights_ResultE.get(position).getDepDayWE());


            holder.btn.setTag(position+"");
            holder.btn.setOnClickListener(new View.OnClickListener() {

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
                    FlightEntity f = flights_ResultE.get(index);
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
}
