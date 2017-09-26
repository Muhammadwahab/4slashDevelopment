package com.example.a4slashdevelopers.filedownload;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new downloadFile().execute();
    }
    public class downloadFile extends AsyncTask
    {

        @Override
        protected Object doInBackground(Object[] objects) {
          //  loadFile("https://static.pexels.com/photos/39517/rose-flower-blossom-bloom-39517.jpeg");
           loadFile("http://sirius.androidapks.com/sdata/bf77952865b725cd4b1fa2c54b057bc9/com.facebook.lite_v60.0.0.5.140-72097660_Android-2.3.apk");
            return objects;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }
    }
    private void loadFile(String url) {
        try {
            URL u = new URL(url);
            URLConnection conn = u.openConnection();
            int contentLength = conn.getContentLength();

            // writing file Start
            String PATH = Environment.getExternalStorageDirectory() + "/download/";
            File file = new File(PATH);
            file.mkdirs();
            File outputFile = new File(file, "wahab.apk");
            FileOutputStream fos = new FileOutputStream(outputFile);

            InputStream is = conn.getInputStream();

            byte[] buffer = new byte[contentLength];
            int len1 = 0;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
            }
            fos.close();
            is.close();

            // writing file Complete

            // Read Apk file from file directory

            Intent promptInstall = new Intent(Intent.ACTION_VIEW);
            File file1=new File(Environment.getExternalStorageDirectory() + "/download/" + "wahab.apk");
             if (file1.exists())
             {
                 Log.d("Apk","read ");

                //  siliently install app
                  String command = "adb install "+Environment.getExternalStorageDirectory() + "/download/wahab.apk";
                java.lang.Process install= Runtime.getRuntime().exec((command));
                    install.wait();
//                 if (isSuccess==1)
//                 {
//
//                 }

               //  command = "adb install " + file1.getAbsolutePath();
                // Process proc = Runtime.getRuntime().exec(new String[] { "su", "-c", command });
               //  proc.waitFor();
                // Runtime.getRuntime().exec("adb install wahab.apk");

             }
//            promptInstall.setDataAndType(Uri.fromFile(file1), "application/vnd.android.package-archive");
//            promptInstall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//            startActivity(promptInstall);

            // Complete Read Apk from file Directory


            //till here, it works fine - .apk is download to my sdcard in download file


//            String PATH = Environment.getExternalStorageDirectory() + "/download/";
//            File file = new File(PATH);
          //  file.mkdirs();

//            if (file.mkdirs())
//            {
//                File outputFile = new File(file, "app.apk");
//                Log.d("file","file create");
//            }
//            else
//            {
//                Log.d("file","file did not create");
//
//            }


//            DataInputStream stream = new DataInputStream(u.openStream());
//
//            byte[] buffer = new byte[contentLength];
//            stream.readFully(buffer);
//            stream.close();
//
//
//            DataOutputStream fos = new DataOutputStream(new FileOutputStream(file));
//            fos.write(buffer);
//            fos.flush();
//            fos.close();
        } catch(FileNotFoundException e) {
            return; // swallow a 404
        } catch (IOException e) {
            return; // swallow a 404
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void installApk(String filename) {
        File file = new File(filename);
        if(file.exists()){
            try {
//                final String command = "pm install -r " + file.getAbsolutePath();
//                Process proc = Runtime.getRuntime().exec(new String[] { "su", "-c", command });
//                proc.waitFor();

                String command;
               // filename = StringUtil.insertEscape(filename);
                command = "adb install -r " + file.getAbsolutePath()+"/wahab.apk";
//                Process proc = Runtime.getRuntime().exec(new String[] { "su", "-c", command });
//                proc.waitFor();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
