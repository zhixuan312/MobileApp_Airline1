package zhang.zhixuan.mobileapp_airline;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;

public class FacebookAccountPage extends Activity {

    TextView facebookAccount_tv_profile;
    Fragment facebookFragment;
    Profile profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_account_page);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction;

        facebookFragment= new FacebookFragment();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.facebookAccount_fl_facebook, facebookFragment);
        fragmentTransaction.commit();

        profile = Profile.getCurrentProfile();
        facebookAccount_tv_profile = (TextView)findViewById(R.id.facebookAccount_tv_profile);
        facebookAccount_tv_profile.setText(profile.getName());
        ProfilePictureView profilePictureView;

        profilePictureView = (ProfilePictureView) findViewById(R.id.facebook_profileImage);

        profilePictureView.setProfileId(profile.getId());

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookFragment.onActivityResult(requestCode, resultCode, data);
    }

    public void facebookLogin(View view) {
//            new GraphRequest(
//                    AccessToken.getCurrentAccessToken(),
//                    "/{user-id}",
//                    null,
//                    HttpMethod.GET,
//                    new GraphRequest.Callback() {
//                        public void onCompleted(GraphResponse response) {
//                            if(response != null){
//                                try {
//
//                                } catch (Exception e){
//
//                                }
//                            }
//                        }
//                    }
//            ).executeAsync();
        Intent i = new Intent(this,AccountManagementPage.class);
        i.putExtra("email","zhangzhixuan312@gmail.com");
        startActivity(i);
        }


}