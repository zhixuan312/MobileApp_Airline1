package zhang.zhixuan.mobileapp_airline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class WebCheckInStatusPage extends AppCompatActivity {

    String referenceN;
    String passportN;

    //test comment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_check_in_status_page);
        referenceN = getIntent().getStringExtra("referenceN");
        passportN = getIntent().getStringExtra("passportN");
        //test
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_web_check_in_status_page, menu);
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
    public void back(View view){
        Intent intent = new Intent(this,WebCheckIn.class);
        intent.putExtra("referenceN", referenceN);
        intent.putExtra("passportN",passportN);
        startActivity(intent);
    }
}
