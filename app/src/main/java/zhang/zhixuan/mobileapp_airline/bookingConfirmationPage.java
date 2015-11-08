package zhang.zhixuan.mobileapp_airline;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.Profile;

public class bookingConfirmationPage extends Activity {
    String referenceN;

    String email;
    String[] ticket;
    TextView cfm_rn1T;
    TextView cfm_rnT;

    Fragment facebookFragment;
    Profile facebookProfile;
    LoginSessionDB dbLogin;
    TicketDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmation_page);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        dbLogin = new LoginSessionDB(this);
        db = new TicketDB(this);
        Intent intent = getIntent();
        System.out.println(intent.getStringExtra("referenceN"));
        email = intent.getStringExtra("email");

        ticket = getRecord(email);
        referenceN = ticket[1];
        if(referenceN==null||ticket[3]==null){
            referenceN = getIntent().getStringExtra("referenceN");
            ticket[3] = getIntent().getStringExtra("email");
         }


        cfm_rn1T = (TextView)findViewById(R.id.cfm_rn1T);
        cfm_rnT = (TextView)findViewById(R.id.cfm_rnT);
        cfm_rn1T.setText(ticket[3]);
        cfm_rnT.setText(referenceN);
    }

    //test github

    public String[] getRecord(String email){
        db.open();
        Cursor c = db.getMemberByEmail(email);
        System.out.println("最新的在这里"+email);

        String [] record = new String[4];

        if (c.moveToFirst()) {
            String[] temp = {c.getString(0), c.getString(1), c.getString(2),c.getString(3)};
            record = temp;
        }


        db.close();

        return record;


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_booking_confirmation_page, menu);
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
