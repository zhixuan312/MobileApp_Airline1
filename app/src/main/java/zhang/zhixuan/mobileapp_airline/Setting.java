package zhang.zhixuan.mobileapp_airline;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import java.text.DecimalFormat;

public class Setting extends Activity {

    CheckBox setting_check;
    SharedPreferences sharedPreferences;
    String answer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setting_check = (CheckBox) findViewById(R.id.setting_check);
        SharedPreferences preferences = getSharedPreferences("promotion_function", Context.MODE_PRIVATE);
        Boolean switchFunction = preferences.getBoolean("promote", true);

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent baReryStatus = this.registerReceiver(null, ifilter);
        int level = baReryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = baReryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        double baReryPct = level / (double) scale *100;
        DecimalFormat df = new DecimalFormat("0");
        answer = df.format(baReryPct) + "%";

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
                    .setTitle("Notice")
                    .setMessage("Your current battery level is " + answer + ", promotion search will be disabled when your battery level is lower than 20%")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        } else {
            editor.putBoolean("promote", false);
            editor.commit();
        }

    }


}
