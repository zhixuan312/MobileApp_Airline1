package zhang.zhixuan.mobileapp_airline;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.Profile;

public class PassengerTwoWayPage extends Activity implements itineraryRFragment.OnFragmentInteractionListener{
    private FlightEntity chosenFlight;
    private FlightEntity chosenFlightR;
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction;
    Profile facebookProfile;
    Spinner spinner;
    TextView fnTV;
    TextView snTV;
    private boolean female = false;
    private String title;
    TextView ppTV;
    TextView emTV;
    LoginSessionDB dbLogin;
    PassengerEntity passengerEntity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_two_way_page);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        chosenFlight = (FlightEntity)getIntent().getSerializableExtra("chosenFlight");
        chosenFlightR = (FlightEntity)getIntent().getSerializableExtra("chosenFlightR");
        System.out.println("new page!!!!!"+chosenFlight.getDepartureDate());
        dbLogin = new LoginSessionDB(this);



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

        if(fnStr==null||fnStr.equals("")){
            Toast.makeText(this,"Please enter your first name",Toast.LENGTH_SHORT).show();
            return;
        }
        if(snStr==null||snStr.equals("")){
            Toast.makeText(this,"Please enter your second name",Toast.LENGTH_SHORT).show();
            return;
        }

        if(ppStr==null||ppStr.equals("")){
            Toast.makeText(this, "Please enter your passport number", Toast.LENGTH_SHORT).show();
            return;
        }
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
