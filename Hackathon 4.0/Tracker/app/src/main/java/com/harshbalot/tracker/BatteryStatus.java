package com.harshbalot.tracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.widget.Toast;

/**
 * Created by Harsh Raj on 20-Mar-18.
 */

public class BatteryStatus extends BroadcastReceiver {
        Context c = null;
        @Override
        public void onReceive(Context context, Intent intent) {
//            Toast.makeText(context, "wtf", Toast.LENGTH_LONG).show();
            if (intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1) < 20){
                Toast.makeText(context, "Battery Low please charge.", Toast.LENGTH_LONG).show();

                if (intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1) < 15) {
                    Toast.makeText(context, "Battery Critically low Please Charge.", Toast.LENGTH_LONG).show();
                }
                if (intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1) < 10) {
                    new mReportLowBattery().execute();
                }
            }
        }

        private class mReportLowBattery extends AsyncTask<Void, Void, Void>{
            @Override
            protected Void doInBackground(Void... voids) {
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Toast.makeText(c,"Battery Low Please Charge!",Toast.LENGTH_LONG).show();
            }
        }
}
