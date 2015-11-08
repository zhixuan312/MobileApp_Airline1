package zhang.zhixuan.mobileapp_airline;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.Profile;

public class DetailTwoWayPage extends Activity implements itineraryRFragment.OnFragmentInteractionListener{
    // testdata: outdate(3 variables day month and year),time depart, time arrive, from city, to city. For both inbound and outbound
    String [] testData = {"12/12/2015","2350","0525","03/04/2016","1320","1930","Singapore","Malaysia","Malaysia","Singapore"};
    //TextView outBoundDate, inBoundDate;
   // TextView outBoundContent,inBoundContent;
    String inDeTime,inArrTime,outDeTime,outArrTime;
    String inDeCity,inArrCity,outDeCity,outArrCity;
    String outBoundString, inBoundString;
    TextView detail_date_TVR;
    TextView detail_flightNumberR;
    TextView detail_outTime_TVR;
    TextView detail_reachTime_TVR;
    TextView detail_fromAirport_TVR;
    TextView detail_reachAirport_TVR;
    TextView detail_aircraftType_TVR;
    TextView detail_duration_TVR;

    TextView detail_flightNumberRR;
    TextView detail_outTime_TVRR;
    TextView detail_reachTime_TVRR;
    TextView detail_fromAirport_TVRR;
    TextView detail_reachAirport_TVRR;
    TextView detail_aircraftType_TVRR;
    TextView detail_duration_TVRR;
    TextView detail_dateR_TVRR;
    LoginSessionDB dbLogin;
    Profile facebookProfile;
    LayoutInflater mInflater;
    private FlightEntity chosenFlight;
    private FlightEntity chosenFlightR;
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_two_way_page);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        dbLogin = new LoginSessionDB(this);
        detail_date_TVR = (TextView)findViewById(R.id.detail_date_TVR);
        detail_flightNumberR = (TextView)findViewById(R.id.detail_flightNumberR);
        detail_outTime_TVR = (TextView)findViewById(R.id.detail_outTime_TVR);
        detail_reachTime_TVR = (TextView)findViewById(R.id.detail_reachTime_TVR);
        detail_fromAirport_TVR = (TextView)findViewById(R.id.detail_fromAirport_TVR);
        detail_reachAirport_TVR = (TextView)findViewById(R.id.detail_reachAirport_TVR);
        detail_aircraftType_TVR = (TextView)findViewById(R.id.detail_aircraftType_TVR);
        detail_duration_TVR = (TextView)findViewById(R.id.detail_duration_TVR);

        detail_dateR_TVRR = (TextView)findViewById(R.id.detail_dateR_TVR);
        detail_flightNumberRR = (TextView)findViewById(R.id.detail_flightNumber);
        detail_outTime_TVRR = (TextView)findViewById(R.id.detail_inTime_TVR);
        detail_reachTime_TVRR = (TextView)findViewById(R.id.detail_reachTimeR_TVR);
        detail_fromAirport_TVRR = (TextView)findViewById(R.id.detail_fromAirportR_TVR);
        detail_reachAirport_TVRR = (TextView)findViewById(R.id.detail_reachAirportR_TVR);
        detail_aircraftType_TVRR = (TextView)findViewById(R.id.detail_aircraftTypeR_TVR);
        detail_duration_TVRR = (TextView)findViewById(R.id.detail_durationR_TVR);



//        outBoundDate = (TextView)findViewById(R.id.detail_tv_outDateR);
//        outBoundContent = (TextView)findViewById(R.id.detail_outboundContentR);
//        inBoundDate = (TextView)findViewById(R.id.detail_tv_inDateR);
//        inBoundContent = (TextView)findViewById(R.id.detail_inboundContentR);

        chosenFlight = (FlightEntity)getIntent().getSerializableExtra("chosenFlight");
        chosenFlightR = (FlightEntity)getIntent().getSerializableExtra("chosenFlightR");
        System.out.println("new page!!!!!"+chosenFlight.getDepartureDate());


        fragmentTransaction = fragmentManager.beginTransaction();
        Fragment itineraryRFragment = new itineraryRFragment();
        fragmentTransaction.add(R.id.detail_fl_itnR, itineraryRFragment);
        fragmentTransaction.commit();

        detail_date_TVR.setText(chosenFlight.getDepDayWE());
        detail_flightNumberR.setText(chosenFlight.getFlightNo());
        detail_outTime_TVR.setText(chosenFlight.getDepTimeE());
        detail_reachTime_TVR.setText(chosenFlight.getAriTimeE());
        detail_fromAirport_TVR.setText(chosenFlight.getOriAirportName());
        detail_reachAirport_TVR.setText(chosenFlight.getDesAirportName());
        detail_aircraftType_TVR.setText("Aircraft: " + chosenFlight.getAircraftTailN());
        if(chosenFlight.getTimeDminutes().equals("0"))
            detail_duration_TVR.setText("Duration: "+chosenFlight.getTimeDuration()+" hours "+chosenFlight.getTimeDminutes()+" mins");
        else
            detail_duration_TVR.setText("Duration: "+chosenFlight.getTimeDuration()+" hours ");


        detail_flightNumberRR.setText(chosenFlightR.getFlightNo());
        detail_outTime_TVRR.setText(chosenFlightR.getDepTimeE());
        detail_reachTime_TVRR.setText(chosenFlightR.getAriTimeE());
        detail_fromAirport_TVRR.setText(chosenFlightR.getOriAirportName());
        detail_reachAirport_TVRR.setText(chosenFlightR.getDesAirportName());
        detail_aircraftType_TVRR.setText("Aircraft: "+chosenFlightR.getAircraftTailN());
        if(chosenFlightR.getTimeDminutes().equals("0"))
            detail_duration_TVRR.setText("Duration: "+chosenFlightR.getTimeDuration()+" hours "+chosenFlightR.getTimeDminutes()+" mins");
        else
            detail_duration_TVRR.setText("Duration: "+chosenFlightR.getTimeDuration()+" hours ");
        detail_dateR_TVRR.setText(chosenFlightR.getDepDayWE());

        //   outBoundDate.setText(chosenFlight.getDepDayWE());
      //  inBoundDate.setText(chosenFlightR.getDepDayWE());

        inDeTime = chosenFlight.getDepTimeE();
        inArrTime = chosenFlight.getAriTimeE();
        outDeTime = chosenFlightR.getDepTimeE();
        outArrTime = chosenFlightR.getAriTimeE();
        inDeCity = chosenFlight.getOriAirportName();
        inArrCity = chosenFlight.getDesAirportName();
        outDeCity = chosenFlightR.getOriAirportName();
        outArrCity = chosenFlightR.getDesAirportName();
        outBoundString = "\n" + inDeTime +  " " + inDeCity +" ("+chosenFlight.getOriAirportCode()+")"+ "\n" + "\n" + inArrTime + " " + inArrCity +" ("+chosenFlight.getDesAirportCode()+")"+"\n"+ "\n"+"Aircraft Type: "+chosenFlight.getAircraftTailN()+"\n" +"\n" + "Total time: "+chosenFlight.getTimeDuration()+" hours" + "\n";
        inBoundString = "\n" + outDeTime +  " " + outDeCity +" ("+chosenFlightR.getOriAirportCode()+")"+ "\n" + "\n" + outArrTime + " " + outArrCity +" ("+chosenFlightR.getDesAirportCode()+")"+"\n"+ "\n"+"Aircraft Type: "+chosenFlightR.getAircraftTailN()+"\n" +"\n" + "Total time: "+chosenFlightR.getTimeDuration()+" hours" + "\n";
  //      outBoundContent.setText(outBoundString);
   //     inBoundContent.setText(inBoundString);



    }
    public void book(View view){
        Intent intent = new Intent(this, PassengerTwoWayPage.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("chosenFlight",chosenFlight);
        bundle.putSerializable("chosenFlightR",chosenFlightR);
        intent.putExtras(bundle);
        startActivity(intent);
    }



    public void onFragmentInteraction(Uri uri){
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_two_way_page, menu);
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
