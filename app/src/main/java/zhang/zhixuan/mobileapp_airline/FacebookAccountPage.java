package zhang.zhixuan.mobileapp_airline;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;

public class FacebookAccountPage extends Activity {

    TextView facebookAccount_tv_profile;
    Fragment facebookFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_account_page);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction;

        facebookFragment= new FacebookFragment();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.facebookAccount_fl_facebook, facebookFragment);
        fragmentTransaction.commit();

        Profile profile = Profile.getCurrentProfile();
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



}