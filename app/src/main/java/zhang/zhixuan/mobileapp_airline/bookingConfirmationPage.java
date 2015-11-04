package zhang.zhixuan.mobileapp_airline;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class bookingConfirmationPage extends Activity {
    String referenceN;
    TicketDB db;
    String email;
    String[] ticket;
    TextView cfm_rn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmation_page);
        db = new TicketDB(this);
        Intent intent = getIntent();
        System.out.println(intent.getStringExtra("referenceN"));
        email = intent.getStringExtra("email");

        ticket = getRecord(email);
        referenceN = ticket[1];


        cfm_rn = (TextView)findViewById(R.id.cfm_rn);
        cfm_rn.setText("Your Booking Reference Number: \n"+referenceN+"\n and you can look for your booking records in your own account: \n"+ticket[3]);
    }

    public String[] getRecord(String email){
        db.open();
        Cursor c = db.getMemberByEmail(email);

        String [] record = new String[4];

        if (c.moveToFirst()) {
            String[] temp = {c.getString(0), c.getString(1), c.getString(2),c.getString(3)};
            record = temp;
        } else
            Toast.makeText(this, "No contact found", Toast.LENGTH_LONG).show();


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
}
