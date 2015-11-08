package zhang.zhixuan.mobileapp_airline;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalProfileSharingActivity;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class BookingPage extends Activity implements itineraryFragment.OnFragmentInteractionListener{
    Profile facebookProfile;


    TicketDB db;
    MyDB dbMember;
    String inArrCity;
    private FlightEntity chosenFlight;
    private PassengerEntity passengerEntity;
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction;

    EditText fnTV;
    EditText snTV;
    EditText adTV;
    EditText emTV;
    EditText ciTV;
    EditText coTV;
    EditText zcTV;
    EditText cnTV;
    long flightId;
    String firstNP;
    String lastNP;
    String titleP;
    String nationNP;
    String bc;
    String firstN;
    String lastN;
    String address;
    String title;
    String city;
    String country;
    String zipCode;
    String contactN;
    String passportP;
    String email;
    String emailAuto;
    String firstNAuto;
    String lastNAuto;
    String addressAuto;
    String cityAuto;
    String countryAuto;
    String zipCodeAuto;
    String contactNAuto;
    double price;
    LoginSessionDB dbLogin;
    Spinner spinner;
    String loginStatus ;

    String referenceN;
    //for Paypal
    private static final String TAG = "paymentExample";

    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;

    private static final String CONFIG_CLIENT_ID = "credential from developer.paypal.com";

    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    private static final int REQUEST_CODE_PROFILE_SHARING = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_page);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        db = new TicketDB(this);
        dbLogin = new LoginSessionDB(this);
        dbMember = new MyDB(this);
        chosenFlight = (FlightEntity)getIntent().getSerializableExtra("chosenFlight");
        passengerEntity = (PassengerEntity)getIntent().getSerializableExtra("passenger");
        System.out.println("new page!!!!!" + chosenFlight.getDepartureDate());

        fnTV = (EditText)findViewById(R.id.bk_et_fn);
        snTV = (EditText)findViewById(R.id.bk_et_sn);
        adTV = (EditText)findViewById(R.id.bk_et_ad);
        emTV = (EditText)findViewById(R.id.bk_et_em);
        ciTV = (EditText)findViewById(R.id.bk_et_ci);
        coTV = (EditText)findViewById(R.id.bk_et_co);
        zcTV = (EditText)findViewById(R.id.bk_et_zp);
        cnTV = (EditText)findViewById(R.id.bk_et_cn);
        addListenerOnSpinnerItemSelection();

        fragmentTransaction = fragmentManager.beginTransaction();
        Fragment itineraryFragment = new itineraryFragment();
        fragmentTransaction.add(R.id.bk_fl_itn, itineraryFragment);
        fragmentTransaction.commit();

        checkLoginStatus();
        System.out.println("check in status"+loginStatus);
        if(loginStatus==null)
            loginStatus = "";
        if(loginStatus.equals("true"))
            autoFillInMemberData();


        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);
    }

    public void checkLoginStatus(){
        dbLogin.open();
        Cursor c = dbLogin.getAllSession();
        System.out.println("enter checklOGIN status");
        System.out.println("enter checklOGIN status"+c);
        if(c==null)
            System.out.println("c == null");

        System.out.println("c.moveToFirst");

        if (c.moveToFirst()) {

            System.out.println("c!-null");
                loginStatus = c.getString(1);
                System.out.println("loginStatus"+loginStatus);
                emailAuto = c.getString(2);


        }
        System.out.println("out of checkLoginStatus");
        dbLogin.close();
    }
    public void autoFillInMemberData(){

        dbMember.open();
        Cursor c = dbMember.getMemberByEmail(emailAuto);
        System.out.println("enter autoFillInMemberData");
        if(c!=null) {
            System.out.println("c!=null");
            if (c.moveToFirst()) {
                firstNAuto = c.getString(1);
                lastNAuto = c.getString(2);
                addressAuto = c.getString(5);
                countryAuto = c.getString(6);
                cityAuto = c.getString(7);
                zipCodeAuto = c.getString(8);
                contactNAuto = c.getString(9);
                if(firstNAuto!=null)
                fnTV.setText(firstNAuto, TextView.BufferType.EDITABLE);
                if(lastNAuto!=null)
                snTV.setText(lastNAuto, TextView.BufferType.EDITABLE);
                if(addressAuto!=null)
                adTV.setText(addressAuto, TextView.BufferType.EDITABLE);
                if(cityAuto!=null)
                ciTV.setText(cityAuto, TextView.BufferType.EDITABLE);
                if(countryAuto!=null)
                coTV.setText(countryAuto, TextView.BufferType.EDITABLE);
                if(zipCodeAuto!=null)
                zcTV.setText(zipCodeAuto, TextView.BufferType.EDITABLE);
                if(contactNAuto!=null)
                cnTV.setText(contactNAuto, TextView.BufferType.EDITABLE);

                emTV.setText(emailAuto);


            } else
                Toast.makeText(this, "No member found.", Toast.LENGTH_LONG).show();
        }
        dbLogin.close();
    }
    public void addListenerOnSpinnerItemSelection() {
        System.out.println("注意!!!!!!哈哈哈哈哈哈");
        spinner = (Spinner) findViewById(R.id.bk_sp_title);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("lalalalla" + parent.getItemAtPosition(position).toString());
                title = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void addRecord(String rn, String pp, String em) {

        db.open();

        long id = db.insertMember(rn, pp, em, "false", chosenFlight.getOrigin(), chosenFlight.getDestination(), chosenFlight.getDepartureDate(), chosenFlight.getArrivalDate());

        if(id > 0){
            Toast.makeText(this, "Add successful.", Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this, "Add failed.", Toast.LENGTH_LONG).show();

        db.close();



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_booking_page, menu);
        return true;
    }

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            .merchantName("Example Merchant")
            .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));

    public void pay (View view) throws ParseException {




        flightId = chosenFlight.getId();
        firstNP = passengerEntity.getFirstName();
        lastNP = passengerEntity.getLastName();
        titleP = passengerEntity.getTitle();
        nationNP = passengerEntity.getNationality();
        bc = chosenFlight.getBookingClassName();
        firstN = fnTV.getText().toString();
        lastN = snTV.getText().toString();
        address = adTV.getText().toString();
        city = ciTV.getText().toString();
        country = coTV.getText().toString();
        zipCode = zcTV.getText().toString();
        contactN = cnTV.getText().toString();
        passportP = passengerEntity.getPassportNumber();
        email = emTV.getText().toString();
        price = chosenFlight.getPriceD();

        generateItinerary();

        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("d MMM yyyy HH:mm");
        Date dpD = simpleDateFormat1.parse(chosenFlight.getDepartureDate());
        Date arD = simpleDateFormat1.parse(chosenFlight.getArrivalDate());

        Calendar cal = Calendar.getInstance();
        cal.setTime(dpD);
        int yearD = cal.get(Calendar.YEAR);
        int monthD = cal.get(Calendar.MONTH);
        int dayD = cal.get(Calendar.DAY_OF_MONTH);
        int hourD = cal.get(Calendar.HOUR_OF_DAY);
        int minD = cal.get(Calendar.MINUTE);

        cal.setTime(arD);
        int yearA = cal.get(Calendar.YEAR);
        int monthA = cal.get(Calendar.MONTH);
        int dayA = cal.get(Calendar.DAY_OF_MONTH);
        int hourA = cal.get(Calendar.HOUR_OF_DAY);
        int minA = cal.get(Calendar.MINUTE);


        Intent intent2 = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
        intent2.putExtra(CalendarContract.Events.TITLE, "Flight from " + chosenFlight.getOrigin()+" to "+chosenFlight.getDestination());
        intent2.putExtra(CalendarContract.Events.DESCRIPTION, "");
        intent2.putExtra(CalendarContract.Events.EVENT_LOCATION, chosenFlight.getOriAirportName());
        Calendar calendarB = Calendar.getInstance();
        Calendar calendarE = Calendar.getInstance();

        calendarB.set(yearD, monthD, dayD, hourD, minD);
        TimeZone tz = TimeZone.getTimeZone("GMT");
        calendarB.setTimeZone(tz);
        calendarE.set(yearA, monthA, dayA, hourA, minA);
        calendarE.setTimeZone(tz);
        intent2.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calendarB.getTimeInMillis());
        intent2.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, calendarE.getTimeInMillis());
        intent2.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);


        Intent intent3 = new Intent(this,bookingConfirmationPage.class);

        intent3.putExtra("referenceN", referenceN);
        intent3.putExtra("email",email);

        startActivity(intent3);
        startActivity(intent2);
        PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

        startActivityForResult(intent, REQUEST_CODE_PAYMENT);

    }
    private PayPalPayment getThingToBuy(String paymentIntent) {
        String price = chosenFlight.getPrice().toString();
        return new PayPalPayment(new BigDecimal(price), "SGD", "Flight "+chosenFlight.getFlightNo(),
                paymentIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm =
                        data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        Log.i(TAG, confirm.toJSONObject().toString(4));
                        Log.i(TAG, confirm.getPayment().toJSONObject().toString(4));

                        Toast.makeText(
                                this,
                                "PaymentConfirmation info received from PayPal", Toast.LENGTH_LONG)
                                .show();

                    } catch (JSONException e) {
                        Log.e(TAG, "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i(TAG, "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(
                        TAG,
                        "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        } else if (requestCode == REQUEST_CODE_FUTURE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth =
                        data.getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        Log.i("FuturePaymentExample", auth.toJSONObject().toString(4));

                        String authorization_code = auth.getAuthorizationCode();
                        Log.i("FuturePaymentExample", authorization_code);

                        sendAuthorizationToServer(auth);
                        Toast.makeText(
                                getApplicationContext(),
                                "Future Payment code received from PayPal", Toast.LENGTH_LONG)
                                .show();

                    } catch (JSONException e) {
                        Log.e("FuturePaymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("FuturePaymentExample", "The user canceled.");
            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(
                        "FuturePaymentExample",
                        "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
            }
        } else if (requestCode == REQUEST_CODE_PROFILE_SHARING) {
            if (resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth =
                        data.getParcelableExtra(PayPalProfileSharingActivity.EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        Log.i("ProfileSharingExample", auth.toJSONObject().toString(4));

                        String authorization_code = auth.getAuthorizationCode();
                        Log.i("ProfileSharingExample", authorization_code);

                        sendAuthorizationToServer(auth);
                        Toast.makeText(
                                getApplicationContext(),
                                "Profile Sharing code received from PayPal", Toast.LENGTH_LONG)
                                .show();

                    } catch (JSONException e) {
                        Log.e("ProfileSharingExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("ProfileSharingExample", "The user canceled.");
            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(
                        "ProfileSharingExample",
                        "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
            }
        }
    }

    private void sendAuthorizationToServer(PayPalAuthorization authorization) {

    }
    @Override
    public void onDestroy() {
        // Stop service when done
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();

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
    public void onFragmentInteraction(Uri uri){
    }
    public void generateItinerary(){
        ClassAsyncTask_GenerateItinerary classAsyncTask_generateItinerary = new ClassAsyncTask_GenerateItinerary();
        classAsyncTask_generateItinerary.execute("http://192.168.1.106:8080/MerlionAirlinesSystem-war/webresources/generic/generateItinerary?flightId=" + flightId + "&firstNP=" + firstNP + "&lastNP=" + lastNP + "&titleP=" + titleP + "&bc=" + bc + "&nationNP=" + nationNP + "&title=" + title + "&firstN=" + firstN + "&lastN=" + lastN + "&address=" + address + "&city=" + city + "&country=" + country + "&zipCode=" + zipCode + "&contactN=" + contactN + "&passportP=" + passportP + "&email=" + email + "&price=" + price);





    }
    public class ClassAsyncTask_GenerateItinerary extends AsyncTask<String, Void, String> {



        public String doInBackground(String... str) {
            System.err.println("enter do in background");return getJSON(str[0]);
        }

        public void onPostExecute(String result) {

            try {

                referenceN = result;
                System.out.println("产生reference number,数值是:"+referenceN);
                addRecord(referenceN,passportP,email);


            } catch (Exception e) {
                Log.d("ReadCurrencyJSON", e.getLocalizedMessage());
            }

        }
    }
    public String getJSON(String urlStr) {

        URL url = convertToUrl(urlStr);

        HttpURLConnection httpURLConnection = null;

        int responseCode;

        StringBuilder stringBuilder = new StringBuilder();

        String line;

        System.err.println("enter getJSON URL"+urlStr);

        try {
            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            System.err.println("before connect");
            httpURLConnection.connect();
            System.err.println("after connect ");
            responseCode = httpURLConnection.getResponseCode();
            System.err.println("responseCode:"+responseCode);
            if (responseCode == httpURLConnection.HTTP_OK) {
                System.err.println("result is ok");

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();
                System.err.println("already read data");
            }
            System.err.println("result is not ok");
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
            e.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
        }
        System.err.println("stringBuilder.toString():"+stringBuilder.toString());
        return stringBuilder.toString();
    }


    // the following code convertToUrl is from
    // http://fancifulandroid.blogspot.sg/2013/07/android-convert-string-to-valid-url.html
    //
    private URL convertToUrl(String urlStr) {
        try {
            URL url = new URL(urlStr);
            URI uri = new URI(url.getProtocol(), url.getUserInfo(),
                    url.getHost(), url.getPort(), url.getPath(),
                    url.getQuery(), url.getRef());
            url = uri.toURL();
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;}
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
