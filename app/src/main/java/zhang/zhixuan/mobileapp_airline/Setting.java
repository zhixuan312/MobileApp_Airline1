package zhang.zhixuan.mobileapp_airline;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.facebook.FacebookSdk;
import com.facebook.Profile;

public class Setting extends Activity {

    CheckBox setting_check;
    SharedPreferences sharedPreferences;

    LoginSessionDB dbLogin;
    Profile facebookProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setting_check = (CheckBox) findViewById(R.id.setting_check);
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        SharedPreferences preferences = getSharedPreferences("promotion_function", Context.MODE_PRIVATE);
        Boolean switchFunction = preferences.getBoolean("promote", true);
        dbLogin = new LoginSessionDB(this);
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent baReryStatus = this.registerReceiver(null, ifilter);
        int level = baReryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = baReryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float baReryPct = level / (float) scale;
        String answer = Float.toString(baReryPct);

        if (switchFunction == true && baReryPct > 20) {
            setting_check.setChecked(true);
        } else {
            sharedPreferences = getSharedPreferences("promotion_function", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("promote", false);
            editor.commit();
            setting_check.setChecked(false);
        }


    }

    public void promotion_function(View view) {
        setting_check = (CheckBox) findViewById(R.id.setting_check);
        sharedPreferences = getSharedPreferences("promotion_function", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (setting_check.isChecked() == true) {
            editor.putBoolean("promote", true);
            editor.commit();


            new AlertDialog.Builder(this)
                    .setTitle("Delete entry")
                    .setMessage("Are you sure you want to delete this entry?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        } else {
            editor.putBoolean("promote", false);
            editor.commit();
        }

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
