package zhang.zhixuan.mobileapp_airline;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.Profile;

import java.util.ArrayList;

public class RegisterPage extends Activity {

    MyDB db;
    EditText emTV;
    EditText unTV;
    EditText psTV;
    EditText cnTV;
    Profile facebookProfile;
    String email;
    String username;
    String password;
    String contactN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        emTV = (EditText)findViewById(R.id.register_et_email);
        unTV = (EditText)findViewById(R.id.register_et_username);
        psTV = (EditText)findViewById(R.id.register_et_password);
        cnTV = (EditText)findViewById(R.id.register_et_confirmPassword);
        db = new MyDB(this);

    }

    public void register(View view){
        email = emTV.getText().toString();
        username = unTV.getText().toString();
        password = psTV.getText().toString();
        contactN = cnTV.getText().toString();
        if (email.matches("")) {
            Toast.makeText(this, "You did not enter a email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (username.matches("")) {
            Toast.makeText(this, "You did not enter a username", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.matches("")) {
            Toast.makeText(this, "You did not enter a password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!contactN.matches(password)) {
            Toast.makeText(this, "The password enter is not correct", Toast.LENGTH_SHORT).show();
            return;
        }
        ArrayList<String> members = checkMemberExistence();

        boolean memberExistence = false;
        for(String member: members){
            if(member.equals(email)) {
                memberExistence = true;
            }
        }
        if(memberExistence){
            Toast.makeText(this, "The member alreay exists, please change a new username", Toast.LENGTH_LONG).show();
            return;
        }
        addMemberRecord(email,username,password);
        Intent intent = new Intent(this, AccountManagementPage.class);
        intent.putExtra("email", email);
        startActivity(intent);

    }

    public ArrayList<String> checkMemberExistence(){
        db.open();

        Cursor c = db.getAllMembers();
        ArrayList<String> results = new ArrayList<String>();

        if (c.moveToFirst()) {
            do {
                String record = c.getString(0);
                results.add(record);
            } while (c.moveToNext());
        }

        db.close();
        return results;
    }
    public void addMemberRecord(String email, String username, String password){
        db.open();

        long id = db.insertMember(username,password,email);

        if(id > 0){
            Toast.makeText(this, "Add successful.", Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this, "Add failed.", Toast.LENGTH_LONG).show();

        db.close();
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
