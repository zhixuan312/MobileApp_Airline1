package zhang.zhixuan.mobileapp_airline;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class AccountManagementPage extends Activity implements View.OnClickListener {

    LinearLayout mTabBooking;
    LinearLayout mTabProfile;
    BookingFragment fBooking;
    ProfileFragment fProfile;

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
        mTabBooking = (LinearLayout) findViewById(R.id.ll_mybooking);
        mTabProfile = (LinearLayout) findViewById(R.id.ll_myprofile);
        mTabBooking.setOnClickListener(this);
        mTabProfile.setOnClickListener(this);


        // 设置默认的Fragment
        setDefaultFragment();
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
}
