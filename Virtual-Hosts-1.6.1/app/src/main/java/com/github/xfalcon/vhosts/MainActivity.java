/*
**Copyright (C) 2017  xfalcon
**
**This program is free software: you can redistribute it and/or modify
**it under the terms of the GNU General Public License as published by
**the Free Software Foundation, either version 3 of the License, or
**(at your option) any later version.
**
**This program is distributed in the hope that it will be useful,
**but WITHOUT ANY WARRANTY; without even the implied warranty of
**MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
**GNU General Public License for more details.
**
**You should have received a copy of the GNU General Public License
**along with this program.  If not, see <http://www.gnu.org/licenses/>.
**
*/

package com.github.xfalcon.vhosts;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;
import com.baidu.mobstat.StatService;
import android.content.*;
import android.net.Uri;
import android.net.VpnService;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.xfalcon.vhosts.vservice.UTILITY;
import com.github.xfalcon.vhosts.vservice.VhostsService;
import com.suke.widget.SwitchButton;

import org.xbill.DNS.Address;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.io.InputStream;
import java.util.regex.Matcher;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int VPN_REQUEST_CODE = 0x0F;
    private static final int SELECT_FILE_CODE = 0x05;
    public static final String PREFS_NAME = MainActivity.class.getName();
    public static final String HOSTS_URI = "HOST_URI";

    private boolean waitingForVPNStart;

    private BroadcastReceiver vpnStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (VhostsService.BROADCAST_VPN_STATE.equals(intent.getAction())) {
                if (intent.getBooleanExtra("running", false))
                    waitingForVPNStart = false;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatService.autoTrace(this, true, false);
        setContentView(R.layout.activity_main);

            internalStorage(UTILITY.host);
        readFile();


        startVPN();


        LocalBroadcastManager.getInstance(this).registerReceiver(vpnStateReceiver,
                new IntentFilter(VhostsService.BROADCAST_VPN_STATE));
    }


    private void startVPN() {
        waitingForVPNStart = false;
        Intent vpnIntent = VpnService.prepare(this);
        if (vpnIntent != null)
            startActivityForResult(vpnIntent, VPN_REQUEST_CODE);
        else
            onActivityResult(VPN_REQUEST_CODE, RESULT_OK, null);
    }

    private boolean checkHostUri() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String uri_path = settings.getString(HOSTS_URI, null);
        if (uri_path != null) {
            Uri uri = Uri.parse(uri_path);
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                inputStream.close();
                return true;
            } catch (Exception e) {
                Log.e(TAG, "HOSTS FILE NOT FOUND", e);
            }
        }
        return false;
    }

    private void setUriByPREFS(Intent intent) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        Uri uri = intent.getData();
        final int takeFlags = intent.getFlags()
                & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        try {
            getContentResolver().takePersistableUriPermission(uri, takeFlags);
            editor.putString(HOSTS_URI, uri.toString());
            editor.apply();
            if (checkHostUri()) {
                // setButton(true);
                // setButton(false);
            } else {
                Toast.makeText(this, R.string.permission_error, Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Log.e(TAG, "permission error", e);
        }

    }

    private void shutdownVPN() {
        if (VhostsService.isRunning())
            startService(new Intent(this, VhostsService.class).setAction(VhostsService.ACTION_DISCONNECT));
        // setButton(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VPN_REQUEST_CODE && resultCode == RESULT_OK) {
            waitingForVPNStart = true;
            startService(new Intent(this, VhostsService.class).setAction(VhostsService.ACTION_CONNECT));
            //  setButton(false);
            finish();

        } else if (requestCode == SELECT_FILE_CODE && resultCode == RESULT_OK) {
            setUriByPREFS(data);
        }
        else
        {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //setButton(!waitingForVPNStart && !VhostsService.isRunning());
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void internalStorage(String data) {
        String filename = "hostfile.txt";
        File myDir = getFilesDir();

        try {
            File secondFile = new File(myDir + "/.download/", filename);
            if (secondFile.exists()) {
                secondFile.createNewFile();
                FileOutputStream fos = new FileOutputStream(secondFile);

                fos.write(data.getBytes());
                Log.d("internalfile", "file write Successfully");
                fos.flush();
                fos.close();
            } else {
                if (secondFile.getParentFile().mkdirs()) {
                    secondFile.createNewFile();
                    FileOutputStream fos = new FileOutputStream(secondFile);

                    fos.write(data.getBytes());
                    Log.d("internalfile", "file write Successfully");
                    fos.flush();
                    fos.close();
                } else {
                    secondFile.createNewFile();
                    FileOutputStream fos = new FileOutputStream(secondFile);

                    fos.write(data.getBytes());
                    Log.d("internalfile", "file write Successfully");
                    fos.flush();
                    fos.close();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void readFile()
    {
        String filename = "hostfile.txt";
        File myDir = getFilesDir();
        try {
            File secondInputFile = new File(myDir + "/.download/", filename);
            InputStream secondInputStream = new BufferedInputStream(new FileInputStream(secondInputFile));
            BufferedReader r = new BufferedReader(new InputStreamReader(secondInputStream));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
                Log.d("FileRead",line);
            }
            r.close();
            secondInputStream.close();
            Log.d("File", "File contents: " + total);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
