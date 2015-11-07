package zhang.zhixuan.mobileapp_airline;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.Profile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class WebCheckInStatusPage extends AppCompatActivity {


    String referenceN;
    String passportN;
    Profile facebookProfile;
    TicketCheckInEntity ticketCheckInEntity;
    TextView confirmation;

    //test comment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_check_in_status_page);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        referenceN = getIntent().getStringExtra("referenceN");
        passportN = getIntent().getStringExtra("passportN");
        ticketCheckInEntity = (TicketCheckInEntity)getIntent().getSerializableExtra("chosenTicket");


        //test
    }
    public void checkInTicket(){
        System.err.println("enter searchFlights_Oneway");
        ClassAsyncTask_CheckInTicket classAsyncTask_checkIn = new ClassAsyncTask_CheckInTicket();
        classAsyncTask_checkIn.execute("http://192.168.1.106:8080/MerlionAirlinesSystem-war/webresources/generic/webCheckIn?ticketId=" + ticketCheckInEntity.getId());

        System.out.println("listview");

    }
    public class ClassAsyncTask_CheckInTicket extends AsyncTask<String, Void, String> {



        public String doInBackground(String... str) {
            System.err.println("enter do in background");return getJSON(str[0]);
        }

        public void onPostExecute(String result) {

            try {
                confirmation = (TextView)findViewById(R.id.status_seatnumber);
                confirmation.setText("You seat number is "+result);







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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_web_check_in_status_page, menu);
        return true;
    }
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
    public void back(View view){
        Intent intent = new Intent(this,WebCheckIn.class);
        intent.putExtra("referenceN", referenceN);
        intent.putExtra("passportN",passportN);
        startActivity(intent);
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
