package dk.via.sharestead.broadcastreceiver;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

public class ConnectivityReceiver extends BroadcastReceiver {
    public boolean isConnected = true;
    String status;
    Activity activity;
    Activity parent;
    AlertDialog alert;

    public ConnectivityReceiver() {

    }

    public ConnectivityReceiver(Activity a) {
        parent = a;
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        activity = (Activity) context;
        status = NetworkUtil.getConnectivityStatusString(context);
        returnStatus(status, context);
    }

    private void returnStatus(String s, final Context context) {

        if (s.equals("Mobile data enabled") || s.equals("Wifi enabled")) {
            isConnected = true;
            if(alert != null)
            {
                if (alert.isShowing()) {
                    alert.dismiss();
                }
            }
        } else {
            isConnected = false;
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            // Set the Alert Dialog Message
            builder.setTitle("Internet lost")
                    .setMessage("Internet connection required. Please check your settings.")
                    .setCancelable(false)
                    .setPositiveButton("Open settings",
                            (dialog, id) -> activity.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)))
                    .setNegativeButton("Cancel", (dialog, id) -> {
                        if (alert.isShowing()) {
                            isConnected = false;
                            alert.dismiss();
                            activity.finish();
                        }
                    });

            alert = builder.create();
            alert.show();
        }
    }
}
