package zhang.zhixuan.mobileapp_airline;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;



public class PersistentReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            SharedPreferences preferences = context.getSharedPreferences("promotion_function", Context.MODE_PRIVATE);
            boolean switchFunction = preferences.getBoolean("promote", true);
            if (isConnected && switchFunction) {
                Intent i = new Intent();
                i.setClassName("zhang.zhixuan.mobileapp_airline", "zhang.zhixuan.mobileapp_airline.PromotionPage");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        }
    }
}
