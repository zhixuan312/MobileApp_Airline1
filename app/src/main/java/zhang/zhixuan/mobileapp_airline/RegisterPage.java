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
    EditText ceTV;

    String email;
    String username;
    String password;
    String confirmE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        emTV = (EditText)findViewById(R.id.register_et_email);
        unTV = (EditText)findViewById(R.id.register_et_username);
        psTV = (EditText)findViewById(R.id.register_et_password);
        ceTV = (EditText)findViewById(R.id.register_et_confirmPassword);
        db = new MyDB(this);

    }

    public void register(View view){
        email = emTV.getText().toString();
        username = unTV.getText().toString();
        password = psTV.getText().toString();
        confirmE = ceTV.getText().toString();
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
        if (confirmE.matches("")) {
            Toast.makeText(this, "You did not enter the confirmed password", Toast.LENGTH_SHORT).show();
            return;
        }
        addMemberRecord(email,username,password,confirmE);
        Intent intent = new Intent(this, AccountManagementPage.class);
        intent.putExtra("email", email);
        startActivity(intent);

    }
    public void addMemberRecord(String email, String username, String password, String contactN){
        db.open();

        long id = db.insertMember(username,password,email,contactN);

        if(id > 0){
            Toast.makeText(this, "Add successful.", Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this, "Add failed.", Toast.LENGTH_LONG).show();

        db.close();
    }
}
