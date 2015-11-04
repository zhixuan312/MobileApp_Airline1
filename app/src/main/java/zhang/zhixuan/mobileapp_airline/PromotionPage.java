package zhang.zhixuan.mobileapp_airline;

import android.app.Activity;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class PromotionPage extends Activity {

    private TextView promotion_tv;
    private List<Address> addressList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_page);
        promotion_tv = (TextView) findViewById(R.id.promotion_tv);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            double myLatitude = location.getLatitude();
            double myLongitude = location.getLongitude();

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());

            try {
                addressList = geocoder.getFromLocation(myLatitude, myLongitude, 1);
                if (addressList.get(0) != null) {
                    Address address = addressList.get(0);
                    for (int b=0; b<address.getMaxAddressLineIndex(); b++) {
                        String addressLine = addressList.get(0).getAddressLine(b);
                        String city    = addressList.get(0).getLocality();
                        String state   = addressList.get(0).getAdminArea();
                        String country = addressList.get(0).getCountryName();
                        String postalCode = addressList.get(0).getPostalCode();
                        String knownName = addressList.get(0).getFeatureName();

                        promotion_tv.setText("Address=" + addressLine + "  City=" + city +
                                "   State=" + state + "   Country=" + country +
                                "   PostalCode=" + postalCode + "knownName=" + knownName);
                    }
                } else {
                    promotion_tv.setText("No data");
                }
            } catch (IOException e) {
                Log.e("Getting Address: ", "Error: ", e);
            }

        }
    }
}
