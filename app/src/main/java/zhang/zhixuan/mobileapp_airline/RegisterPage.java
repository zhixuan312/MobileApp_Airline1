package zhang.zhixuan.mobileapp_airline;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterPage extends Activity {

    MyDB db;
    EditText emTV;
    EditText unTV;
    EditText psTV;
    EditText cnTV;

    String email;
    String username;
    String password;
    String contactN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
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
        addMemberRecord(email,username,password);
        Intent intent = new Intent(this, AccountManagementPage.class);
        intent.putExtra("email", email);
        startActivity(intent);

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
}
