package zhang.zhixuan.mobileapp_airline;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.Profile;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WebCheckIn extends Activity {
    String referenceN;
    String passportN;
    TicketDB db;
    List<TicketCheckInEntity> tickets_results;
    ListView lv;
    TicketCheckInEntity chosenTicket;
    Profile facebookProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_check_in);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        referenceN = getIntent().getStringExtra("referenceN");
        passportN = getIntent().getStringExtra("passportN");
        db = new TicketDB(this);
        tickets_results = new ArrayList<>();

        displayCheckInTickets();


    }
    public void displayCheckInTickets(){
        System.err.println("enter searchFlights_Oneway");
        ClassAsyncTask_CheckIn classAsyncTask_checkIn = new ClassAsyncTask_CheckIn();
        classAsyncTask_checkIn.execute("http://192.168.1.106:8080/MerlionAirlinesSystem-war/webresources/generic/webCheckInObj?referenceN=" + referenceN + "&passport=" + passportN);

        System.out.println("listview");

    }
    public class ClassAsyncTask_CheckIn extends AsyncTask<String, Void, String> {



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
                    JSONObject ticket = jsonArray.getJSONObject(i);
                    long id = ticket.getLong("id");
                    String originCity = ticket.getString("originCity");
                    String destinationCity = ticket.getString("destinationCity");
                    String originAN = ticket.getString("originAN");
                    String destinationAN = ticket.getString("destinationAN");
                    String flightNo = ticket.getString("flightNo");
                    long flightId = ticket.getLong("flightId");
                    String depD = ticket.getString("depD");
                    String ariD = ticket.getString("ariD");

//                    Map<String, Object> map = new HashMap<String, Object>();
                    TicketCheckInEntity ticketCheckInEntity = new TicketCheckInEntity();
                    ticketCheckInEntity.setId(id);
                    ticketCheckInEntity.setAriD(ariD);
                    ticketCheckInEntity.setDepD(depD);
                    ticketCheckInEntity.setDestinationAN(destinationAN);
                    ticketCheckInEntity.setDestinationCity(destinationCity);
                    ticketCheckInEntity.setFlightId(flightId);
                    ticketCheckInEntity.setFlightNo(flightNo);
                    ticketCheckInEntity.setOriginAN(originAN);
                    ticketCheckInEntity.setOriginCity(originCity);



//                    map.put("flightNo", fn);
//                    map.put("departureDate", dpD);
//                    map.put("arrivalDate", ariD);
                    tickets_results.add(ticketCheckInEntity);
                }

                lv = (ListView)findViewById(R.id.wc_lv_tickets);
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
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        chosenTicket = tickets_results.get(position);
                        LinearLayout item = (LinearLayout)view.findViewById(R.id.wc_ticket);
                        item.setBackgroundColor(Color.parseColor("#FFF59D"));

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
        public TextView path;
        public TextView date;


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
            return tickets_results.size();
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
            return tickets_results.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            System.out.println("enter getView"+position);
            ViewHolder holder = new ViewHolder();

            TicketCheckInEntity ticketCheckInEntity = tickets_results.get(position);
            if (convertView == null) {


                convertView = mInflater.inflate(R.layout.ticketresults, null);

                holder.flightNo = (TextView) convertView.findViewById(R.id.wc_flightNo);
                holder.path = (TextView) convertView.findViewById(R.id.wc_path);
                holder.date = (TextView) convertView.findViewById(R.id.wc_depD);

                convertView.setTag(holder);

            } else {

                holder = (ViewHolder) convertView.getTag();
            }


            holder.flightNo.setText((String) tickets_results.get(position).getFlightNo());
            holder.path.setText((String) tickets_results.get(position).getOriginCity()+" to "+tickets_results.get(position).getDestinationCity());
            holder.date.setText(tickets_results.get(position).getDepD());




            return convertView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_web_check_in, menu);
        return true;
    }

    public void ticketCheckIn(View view){
        db.open();

        db.updateCheckedInStatus(referenceN, passportN);

        db.close();


        Intent intent = new Intent(this,WebCheckInStatusPage.class);
        Bundle bundle = new Bundle();
        System.out.println("chosenTicket"+chosenTicket.getFlightNo() );
        bundle.putSerializable("chosenTicket",chosenTicket);
        intent.putExtras(bundle);
        intent.putExtra("referenceN", referenceN);
        intent.putExtra("passportN",passportN);
        startActivity(intent);
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
            Intent intent = new Intent(this, LoginPage.class);
            startActivity(intent);
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
