package zhang.zhixuan.mobileapp_airline;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

public class PassengerTwoWayPage extends Activity implements itineraryRFragment.OnFragmentInteractionListener{
    private FlightEntity chosenFlight;
    private FlightEntity chosenFlightR;
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction;

    Spinner spinner;
    TextView fnTV;
    TextView snTV;
    private boolean female = false;
    private String title;
    TextView ppTV;
    TextView emTV;
    PassengerEntity passengerEntity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_two_way_page);
        chosenFlight = (FlightEntity)getIntent().getSerializableExtra("chosenFlight");
        chosenFlightR = (FlightEntity)getIntent().getSerializableExtra("chosenFlightR");
        System.out.println("new page!!!!!"+chosenFlight.getDepartureDate());




        fragmentTransaction = fragmentManager.beginTransaction();
        Fragment itineraryRFragment = new itineraryRFragment();
        fragmentTransaction.add(R.id.ps_fl_itnR, itineraryRFragment);
        fragmentTransaction.commit();
        addListenerOnSpinnerItemSelection();
    }

    public void onClick_fm(View view){
        female = true;


    }

    public void onClick_m(View view){
        female = false;
    }
    public void onclick_Continue(View view){
        fnTV = (TextView)findViewById(R.id.ps_et_fnR);
        snTV = (TextView)findViewById(R.id.ps_et_snR);
        ppTV = (TextView)findViewById(R.id.ps_et_ppR);
        emTV = (TextView)findViewById(R.id.ps_et_nnR);
        String fnStr = fnTV.getText().toString();
        String snStr = snTV.getText().toString();
        String ppStr = ppTV.getText().toString();
        String nnStr = emTV.getText().toString();
        String gender = "Male";

        if(female){
            gender = "Female";
        }
        if(title==null)
            title = "Mr";

        passengerEntity = new PassengerEntity();
        passengerEntity.setTitle(title);
        passengerEntity.setFirstName(fnStr);
        passengerEntity.setLastName(snStr);
        passengerEntity.setGender(gender);
        passengerEntity.setPassportNumber(ppStr);
        passengerEntity.setNationality(nnStr);

        Bundle bundle = new Bundle();
        bundle.putSerializable("passenger", passengerEntity);
        bundle.putSerializable("chosenFlight",chosenFlight);
        bundle.putSerializable("chosenFlightR", chosenFlightR);
        Intent intent = new Intent(this, BookingTwoWayPage.class);
        intent.putExtras(bundle);
        startActivity(intent);



    }
    public void addListenerOnSpinnerItemSelection() {
        System.out.println("注意!!!!!!哈哈哈哈哈哈");
        spinner = (Spinner) findViewById(R.id.ps_sp_titleR);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("lalalalla"+parent.getItemAtPosition(position).toString());
                title = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_passenger_two_way_page, menu);
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
    public void onFragmentInteraction(Uri uri){
    }
}
