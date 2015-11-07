package zhang.zhixuan.mobileapp_airline;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
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
    TextView outBoundDate, inBoundDate;
    TextView outBoundContent,inBoundContent;
    String inDeTime,inArrTime,outDeTime,outArrTime;
    String inDeCity,inArrCity,outDeCity,outArrCity;
    String outBoundString, inBoundString;
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

        outBoundDate = (TextView)findViewById(R.id.detail_tv_outDateR);
        outBoundContent = (TextView)findViewById(R.id.detail_outboundContentR);
        inBoundDate = (TextView)findViewById(R.id.detail_tv_inDateR);
        inBoundContent = (TextView)findViewById(R.id.detail_inboundContentR);

        chosenFlight = (FlightEntity)getIntent().getSerializableExtra("chosenFlight");
        chosenFlightR = (FlightEntity)getIntent().getSerializableExtra("chosenFlightR");
        System.out.println("new page!!!!!"+chosenFlight.getDepartureDate());


        fragmentTransaction = fragmentManager.beginTransaction();
        Fragment itineraryRFragment = new itineraryRFragment();
        fragmentTransaction.add(R.id.detail_fl_itnR, itineraryRFragment);
        fragmentTransaction.commit();



        outBoundDate.setText(chosenFlight.getDepDayWE());
        inBoundDate.setText(chosenFlightR.getDepDayWE());

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
        outBoundContent.setText(outBoundString);
        inBoundContent.setText(inBoundString);



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
}
