package com.github.xfalcon.vhosts.vservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.github.xfalcon.vhosts.MainActivity;

/**
 * Created by 4slashDevelopers on 10/2/2017.
 */

public class OnBootBroadcastReceiver extends BroadcastReceiver {
    String[] dnsNameList = {"Google1", "Google2", "Level31", "Comodo Secure DNS", "OpenDNS Home3", "DNS Advantage", "Norton ConnectSafe4", "GreenTeamDNS5", "SafeDNS6", "OpenNIC7",
            "Public-Root8", "SmartViper", "Dyn", "censurfridns.dk9", "Hurricane Electric10", "puntCAT11"};
    // DNS Values for List
    String[] dnsList = {"8.8.8.8", "8.8.4.4", "209.244.0.3", "8.26.56.26", "208.67.222.222", "156.154.70.1", "199.85.126.10", "81.218.119.11", "195.46.39.39", "216.87.84.211",
            "199.5.157.131", "208.76.50.50", "216.146.35.35", "89.233.43.71", "74.82.42.42", "109.69.8.51"};

    private Intent intentVpnService;
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent mainactivity=new Intent(context, MainActivity.class);
        mainactivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mainactivity);
        Toast.makeText(context.getApplicationContext(), "DNS Start On Boot on VU", Toast.LENGTH_LONG).show();

    }
}