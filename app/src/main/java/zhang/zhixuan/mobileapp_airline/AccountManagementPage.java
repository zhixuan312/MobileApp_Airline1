package zhang.zhixuan.mobileapp_airline;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.FacebookSdk;
import com.facebook.Profile;

public class AccountManagementPage extends Activity implements View.OnClickListener {

    LinearLayout mTabBooking;
    LinearLayout mTabProfile;
    BookingFragment fBooking;
    ProfileFragment fProfile;
    LoginSessionDB db;
    String email;
    Profile facebookProfile;
    //    TextView fnTV;
//    TextView snTV;
//    TextView adTV;
//    TextView emTV;
//    TextView ciTV;
//    TextView coTV;
//    TextView zcTV;
//    TextView cnTV;
//
//    String firstN;
//    String lastN;
//    String address;
//    String title;
//    String city;
//    String country;
//    String zipCode;
//    String contactN;
//    String email;
//    Spinner spinner;
//    MyDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_management_page);
        db = new LoginSessionDB(this);
        email = getIntent().getStringExtra("email");
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        changeLoginStatus();
        mTabBooking = (LinearLayout) findViewById(R.id.ll_mybooking);
        mTabProfile = (LinearLayout) findViewById(R.id.ll_myprofile);
        mTabBooking.setOnClickListener(this);
        mTabProfile.setOnClickListener(this);

        // 设置默认的Fragment
        //test github
        setDefaultFragment();
    }
    public void changeLoginStatus(){
        db.open();
        Cursor c = db.getAllSession();
        if(c == null){
            db.insertLoginSession("true", email);
        }
        else{
            db.updateLoginStatus("true", email);
        }
        db.close();
    }
    private void setDefaultFragment()
    {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        fBooking = new BookingFragment();
        transaction.replace(R.id.id_content, fBooking);
        transaction.commit();
    }

    @Override
    public void onClick(View v)
    {
        FragmentManager fm = getFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();

        switch (v.getId())
        {
            case R.id.ll_mybooking:
                if (fBooking == null)
                {
                    fBooking = new BookingFragment();
                }
                // 使用当前Fragment的布局替代id_content的控件
                transaction.replace(R.id.id_content, fBooking);
                break;
            case R.id.ll_myprofile:
                if (fProfile == null)
                {
                    fProfile = new ProfileFragment();
                }
                transaction.replace(R.id.id_content, fProfile);
                break;
        }
        // transaction.addToBackStack();
        // 事务提交
        transaction.commit();
    }

    public void logout(){
        changeLogoutStatus();
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
    }
    public void changeLogoutStatus(){
        db.open();
        Cursor c = db.getAllSession();
        if(c == null){
            db.insertLoginSession("false", email);
        }
        else{
            db.updateLoginStatus("false", email);
        }
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_account_management_page, menu);
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
