package zhang.zhixuan.mobileapp_airline;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class DetailPage extends Activity implements itineraryFragment.OnFragmentInteractionListener{
    // testdata: outdate(3 variables day month and year),time depart, time arrive, from city, to city. For both inbound and outbound
    String [] testData = {"12/12/2015","2350","0525","03/04/2016","1320","1930","Singapore","Malaysia","Malaysia","Singapore"};
    TextView outBoundDate;
    TextView outBoundContent;
    String inDeTime,inArrTime,outDeTime,outArrTime;
    String inDeCity,inArrCity,outDeCity,outArrCity;
    String outBoundString, inBoundString;
    LayoutInflater mInflater;
    private FlightEntity chosenFlight;
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction;

    //for Paypal
//    private static final String TAG = "paymentExample";
//
//    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;
//
//    private static final String CONFIG_CLIENT_ID = "credential from developer.paypal.com";

//    private static final int REQUEST_CODE_PAYMENT = 1;
//    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
//    private static final int REQUEST_CODE_PROFILE_SHARING = 3;

    //    private static PayPalConfiguration config = new PayPalConfiguration()
//            .environment(CONFIG_ENVIRONMENT)
//            .clientId(CONFIG_CLIENT_ID)
//            .merchantName("Example Merchant")
//            .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
//            .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);
        outBoundDate = (TextView)findViewById(R.id.detail_tv_outDate);
        outBoundContent = (TextView)findViewById(R.id.detail_outboundContent);

        chosenFlight = (FlightEntity)getIntent().getSerializableExtra("chosenFlight");
        System.out.println("new page!!!!!"+chosenFlight.getDepartureDate());


        fragmentTransaction = fragmentManager.beginTransaction();
        Fragment itineraryFragment = new itineraryFragment();
        fragmentTransaction.add(R.id.detail_fl_itn, itineraryFragment);
        fragmentTransaction.commit();

//        mInflater = getLayoutInflater();
//        View frgV = (View)mInflater.inflate(R.layout.fragment_itinerary, null);
//        TextView itnTV = (TextView)frgV.findViewById(R.id.fl_tv_itn);
//        itnTV.setText(chosenFlight.getFlightNo());

        outBoundDate.setText(chosenFlight.getDepDayWE());

        inDeTime = chosenFlight.getDepTimeE();
        inArrTime = chosenFlight.getAriTimeE();
        outDeTime = testData [4];
        outArrTime = testData [5];
        inDeCity = chosenFlight.getOriAirportName();
        inArrCity = chosenFlight.getDesAirportName();
        outDeCity = testData [8];
        outArrCity = testData [9];
        outBoundString = "\n" + inDeTime +  " " + inDeCity +" ("+chosenFlight.getOriAirportCode()+")"+ "\n" + "\n" + inArrTime + " " + inArrCity +" ("+chosenFlight.getDesAirportCode()+")"+"\n"+ "\n"+"Aircraft Type: "+chosenFlight.getAircraftTailN()+"\n" +"\n" + "Total time: "+chosenFlight.getTimeDuration()+" hours" + "\n";
        inBoundString = "\n" + outDeTime +  " " +outDeCity + "\n" + "\n" + outArrTime + " " + outArrCity + "\n" + "\n"+ "Total time: 2 hours" + "\n"+ "\n"+ "\n";
        outBoundContent.setText(outBoundString);



    }
    public void book(View view){
        Intent intent = new Intent(this, PassengerPage.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("chosenFlight",chosenFlight);
        intent.putExtras(bundle);
        startActivity(intent);
    }

//    public void pay (View view) {
//        Intent intent2 = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
//        intent2.putExtra(CalendarContract.Events.TITLE, "Flight to " + inArrCity);
//        intent2.putExtra(CalendarContract.Events.DESCRIPTION, "");
//        intent2.putExtra(CalendarContract.Events.EVENT_LOCATION, inArrCity);
//        Calendar calendarB = Calendar.getInstance();
//        Calendar calendarE = Calendar.getInstance();
//        calendarB.set(2015, 11, 12);
//        TimeZone tz = TimeZone.getTimeZone("GMT");
//        calendarB.setTimeZone(tz);
//        calendarE.set(2016,03,03);
//        calendarE.setTimeZone(tz);
//        intent2.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calendarB.getTimeInMillis());
//        intent2.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, calendarE.getTimeInMillis());
//        intent2.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
//        startActivity(intent2);
//        PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE);
//
//        Intent intent = new Intent(this, PaymentActivity.class);
//
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
//
//        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
//
//        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
//
//    }

//    private PayPalPayment getThingToBuy(String paymentIntent) {
//        return new PayPal @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_CODE_PAYMENT) {
//            if (resultCode == Activity.RESULT_OK) {
//                PaymentConfirmation confirm =
//                        data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
//                if (confirm != null) {
//                    try {
//                        Log.i(TAG, confirm.toJSONObject().toString(4));
//                        Log.i(TAG, confirm.getPayment().toJSONObject().toString(4));
//
//                        Toast.makeText(
//                                this,
//                                "PaymentConfirmation info received from PayPal", Toast.LENGTH_LONG)
//                                .show();
//
//                    } catch (JSONException e) {
//                        Log.e(TAG, "an extremely unlikely failure occurred: ", e);
//                    }
//                }
//            } else if (resultCode == Activity.RESULT_CANCELED) {
//                Log.i(TAG, "The user canceled.");
//            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
//                Log.i(
//                        TAG,
//                        "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
//            }
//        } else if (requestCode == REQUEST_CODE_FUTURE_PAYMENT) {
//            if (resultCode == Activity.RESULT_OK) {
//                PayPalAuthorization auth =
//                        data.getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
//                if (auth != null) {
//                    try {
//                        Log.i("FuturePaymentExample", auth.toJSONObject().toString(4));
//
//                        String authorization_code = auth.getAuthorizationCode();
//                        Log.i("FuturePaymentExample", authorization_code);
//
//                        sendAuthorizationToServer(auth);
//                        Toast.makeText(
//                                getApplicationContext(),
//                                "Future Payment code received from PayPal", Toast.LENGTH_LONG)
//                                .show();
//
//                    } catch (JSONException e) {
//                        Log.e("FuturePaymentExample", "an extremely unlikely failure occurred: ", e);
//                    }
//                }
//            } else if (resultCode == Activity.RESULT_CANCELED) {
//                Log.i("FuturePaymentExample", "The user canceled.");
//            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
//                Log.i(
//                        "FuturePaymentExample",
//                        "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
//            }
//        } else if (requestCode == REQUEST_CODE_PROFILE_SHARING) {
//            if (resultCode == Activity.RESULT_OK) {
//                PayPalAuthorization auth =
//                        data.getParcelableExtra(PayPalProfileSharingActivity.EXTRA_RESULT_AUTHORIZATION);
//                if (auth != null) {
//                    try {
//                        Log.i("ProfileSharingExample", auth.toJSONObject().toString(4));
//
//                        String authorization_code = auth.getAuthorizationCode();
//                        Log.i("ProfileSharingExample", authorization_code);
//
//                        sendAuthorizationToServer(auth);
//                        Toast.makeText(
//                                getApplicationContext(),
//                                "Profile Sharing code received from PayPal", Toast.LENGTH_LONG)
//                                .show();
//
//                    } catch (JSONException e) {
//                        Log.e("ProfileSharingExample", "an extremely unlikely failure occurred: ", e);
//                    }
//                }
//            } else if (resultCode == Activity.RESULT_CANCELED) {
//                Log.i("ProfileSharingExample", "The user canceled.");
//            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
//                Log.i(
//                        "ProfileSharingExample",
//                        "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
//            }
//        }
//    }
//
//    private void sendAuthorizationToServer(PayPalAuthorization authorization) {
//
//    }
//
//    @Override
//    public void onDestroy() {
//        // Stop service when done
//        stopService(new Intent(this, PayPalService.class));
//        super.onDestroy();
//    }Payment(new BigDecimal("950"), "SGD", "Sample Flight",
//                paymentIntent);
//    }

//

    public void onFragmentInteraction(Uri uri){
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_two_way_page, menu);
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
