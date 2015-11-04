package zhang.zhixuan.mobileapp_airline;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.Profile;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private int year_R,month_R,day_R;
    private int year_D,month_D,day_D;
    private int departDialogId = 1;
    private int returnDialogId = 2;
    Button main_btn_departDate;
    Button main_btn_returnDate;
    RadioButton main_radioBtn_roundTrip;

    RelativeLayout main_rl_moreOption;
    CheckBox main_cb_moreOption;
    Profile facebookProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        Calendar calendar =Calendar.getInstance();
        year_D = calendar.get(Calendar.YEAR);
        month_D = calendar.get(Calendar.MONTH);
        day_D = calendar.get(Calendar.DAY_OF_MONTH);
        year_R = calendar.get(Calendar.YEAR);
        month_R = calendar.get(Calendar.MONTH);
        day_R = calendar.get(Calendar.DAY_OF_MONTH);

    }

    public void main_btn_pickDate (View view) {
        if (view.getId() == R.id.main_btn_departDate) {
            showDialog(departDialogId);
        } else if (view.getId() == R.id.main_btn_returnDate) {
            showDialog(returnDialogId);
        }


    }

    @Override
    protected Dialog onCreateDialog (int id){
        if (id == departDialogId) {
            return new DatePickerDialog(this,datePickerListenerD, year_D, month_D,day_D);
        } else if (id == returnDialogId){
            return new DatePickerDialog(this,datePickerListenerR, year_R, month_R,day_R);
        } else {
            return null;
        }
    }

    private DatePickerDialog.OnDateSetListener datePickerListenerD = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_D = year;
            month_D = monthOfYear;
            day_D = dayOfMonth;
            main_btn_departDate = (Button)findViewById(R.id.main_btn_departDate);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year_D);
            calendar.set(Calendar.MONTH, month_D);
            calendar.set(Calendar.DAY_OF_MONTH, day_D);
            Date departureD = calendar.getTime();
            main_btn_departDate.setText(simpleDateFormat.format(departureD).toString());
            Toast.makeText(MainActivity.this, simpleDateFormat.format(departureD).toString(), Toast.LENGTH_SHORT).show();
        }
    };
    private DatePickerDialog.OnDateSetListener datePickerListenerR = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_R = year;
            month_R = monthOfYear;
            day_R = dayOfMonth;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year_R);
            calendar.set(Calendar.MONTH, month_R);
            calendar.set(Calendar.DAY_OF_MONTH, day_R);
            Date returnD = calendar.getTime();
            main_btn_returnDate = (Button)findViewById(R.id.main_btn_returnDate);
            main_btn_returnDate.setText(simpleDateFormat.format(returnD).toString());
            Toast.makeText(MainActivity.this, simpleDateFormat.format(returnD).toString(), Toast.LENGTH_SHORT).show();
        }
    };

    public void main_radioBtn_roundTrip(View view) {
        main_btn_returnDate = (Button)findViewById(R.id.main_btn_returnDate);
        main_btn_returnDate.setEnabled(true);
    }

    public void main_radioBtn_oneWay(View view) {
        main_btn_returnDate = (Button)findViewById(R.id.main_btn_returnDate);
        main_btn_returnDate.setEnabled(false);
    }

    public void main_btn_search (View view) {

        main_radioBtn_roundTrip = (RadioButton)findViewById(R.id.main_radioBtn_roundTrip);

        if (main_radioBtn_roundTrip.isChecked()){
            Intent intent = new Intent (this, SearchResultsTwoWay.class);
            EditText destinationET = (EditText)findViewById(R.id.main_eT_to);
            EditText orginET = (EditText)findViewById(R.id.main_eT_from);
            System.out.println("Round trip is checked");
            intent.putExtra("year_R",year_R);
            intent.putExtra("month_R",month_R);
            intent.putExtra("day_R",day_R);
            intent.putExtra("OneWayOrNot", false);

            RadioButton firstClass = (RadioButton) findViewById(R.id.firstClass);
            RadioButton businessClass = (RadioButton) findViewById(R.id.businessClass);
            RadioButton preClass = (RadioButton) findViewById(R.id.premiumEconomyClass);
            RadioButton ecoClass = (RadioButton) findViewById(R.id.economyClass);
            intent.putExtra("bcIndex", "All Classes");
            if (firstClass.isChecked()) {
                intent.putExtra("bcIndex","First Class");
            }
            if (businessClass.isChecked()) {
                intent.putExtra("bcIndex","Business Class");
            }
            if (preClass.isChecked()) {
                intent.putExtra("bcIndex","Premium Economy Class");
            }
            if (ecoClass.isChecked()) {
                intent.putExtra("bcIndex","Economy Class");
            }
//        RadioGroup rg = (RadioGroup) findViewById(R.id.main_rg_bcs);
//        int bcIndex = rg.getCheckedRadioButtonId();
            String origin = orginET.getText().toString();
            String destination = destinationET.getText().toString();
            intent.putExtra("year_D",year_D);
            intent.putExtra("month_D",month_D);
            intent.putExtra("day_D", day_D);

            intent.putExtra("origin",origin);
            intent.putExtra("destination",destination);
            startActivity(intent);

        }
        else{
            Intent intent = new Intent (this, SearchResults.class);
            EditText destinationET = (EditText)findViewById(R.id.main_eT_to);
            EditText orginET = (EditText)findViewById(R.id.main_eT_from);
            intent.putExtra("OneWayOrNot", true);

            RadioButton firstClass = (RadioButton) findViewById(R.id.firstClass);
            RadioButton businessClass = (RadioButton) findViewById(R.id.businessClass);
            RadioButton preClass = (RadioButton) findViewById(R.id.premiumEconomyClass);
            RadioButton ecoClass = (RadioButton) findViewById(R.id.economyClass);
            intent.putExtra("bcIndex", "All Classes");
            if (firstClass.isChecked()) {
                intent.putExtra("bcIndex","First Class");
            }
            if (businessClass.isChecked()) {
                intent.putExtra("bcIndex","Business Class");
            }
            if (preClass.isChecked()) {
                intent.putExtra("bcIndex","Premium Economy Class");
            }
            if (ecoClass.isChecked()) {
                intent.putExtra("bcIndex","Economy Class");
            }
//        RadioGroup rg = (RadioGroup) findViewById(R.id.main_rg_bcs);
//        int bcIndex = rg.getCheckedRadioButtonId();
            String origin = orginET.getText().toString();
            String destination = destinationET.getText().toString();
            intent.putExtra("year_D",year_D);
            intent.putExtra("month_D",month_D);
            intent.putExtra("day_D", day_D);

            intent.putExtra("origin",origin);
            intent.putExtra("destination",destination);
            startActivity(intent);
        }

    }

    public void main_checkBox (View view) {
        main_cb_moreOption = (CheckBox)findViewById(R.id.main_cb_moreOption);
        if (main_cb_moreOption.isChecked() == true) {
            main_rl_moreOption = (RelativeLayout) findViewById(R.id.main_rl_moreOption);
            main_rl_moreOption.setVisibility(view.VISIBLE);
        } else {
            main_rl_moreOption = (RelativeLayout) findViewById(R.id.main_rl_moreOption);
            main_rl_moreOption.setVisibility(view.GONE);
        }
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
