package com.example.a4slashdevelopers.readfile;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            File file1=new File(Environment.getExternalStorageDirectory() + "/download/" + "example_hosts.txt");
            InputStream inputStream=new FileInputStream(file1);
            int len1 = 0;
            while ((len1 = inputStream.read()) != -1) {
                Log.d("readFile",len1+"");
            }

            inputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
