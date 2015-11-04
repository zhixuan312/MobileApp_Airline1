package zhang.zhixuan.mobileapp_airline;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.Profile;

public class LoginPage extends Activity {

    EditText login_et_id;
    EditText login_et_password;
    String loginId;
    String loginPassword;
    MyDB db;
    Profile facebookProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        db = new MyDB(this);
        android.app.FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction;

        Fragment facebookFragment = new FacebookFragment();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.login_fl_Facebook, facebookFragment);
        fragmentTransaction.commit();

        login_et_password = (EditText)findViewById(R.id.login_et_password);
    }

    public void login_register(View View) {
        Intent intent = new Intent(this, RegisterPage.class);
        startActivity(intent);

    }

    public void login(View view){
        login_et_id = (EditText)findViewById(R.id.login_et_id);
        login_et_password = (EditText)findViewById(R.id.login_et_password);
        loginId = login_et_id.getText().toString();
        loginPassword = login_et_password.getText().toString();
        String psw = getPassword();
        if(!psw.equals(loginPassword)){
            Toast.makeText(this.getApplicationContext(), "Password is not correct!", Toast.LENGTH_LONG).show();

        }else{
            Intent intent = new Intent(this, AccountManagementPage.class);
            intent.putExtra("email",loginId);
            startActivity(intent);
        }
    }

    public String getPassword(){
        db.open();
        Cursor c = db.getMemberPasswordByEmail(loginId);
        String psw1 = "";
        if(c.moveToFirst()){
            psw1 = c.getString(1);

        }else{
            Toast.makeText(this.getApplicationContext(), "No username found", Toast.LENGTH_LONG).show();

        }
        db.close();
        return psw1;
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
        Intent intent = new Intent(this,RegisterPage.class);
        startActivity(intent);
    }

    public void main_float_search (View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}

