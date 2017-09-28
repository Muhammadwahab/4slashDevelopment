package com.example.smart_will;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.HashMap;

public class Info_block extends AppCompatActivity {

    Button infNext;
    RadioGroup Rg1, Rg2, Rg3;
    RadioButton r1,r2,r3;
    ImageButton but;
    SessionManager session;

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_block);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        final String uid = user.get(SessionManager.KEY_NAME);
        but=(ImageButton)findViewById(R.id.back);
        but.setOnClickListener(new View.OnClickListener() {
                @Override
               public void onClick(View view) {
                    startActivity(new Intent(Info_block.this, Gifts.class));
                }
            });
        Rg1 = (RadioGroup) findViewById(R.id.RG1);
        Rg2 = (RadioGroup) findViewById(R.id.RG2);
        Rg3 = (RadioGroup) findViewById(R.id.RG3);

//        Intent intent = new Intent();
//        intent.putExtras(getIntent());
//        String flag=intent.getExtras().getString("flag");
//        Toast.makeText(this, flag, Toast.LENGTH_SHORT).show();
//        if(flag.equals("true")) {
//            but.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    startActivity(new Intent(Info_block.this, Gifts.class));
//                }
//            });
//        }
//        else
//        {
//            but.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    startActivity(new Intent(Info_block.this, Gifts_and_Legacy2.class));
//                }
//            });
//        }

        infNext = (Button) findViewById(R.id.infNext);
        infNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Nextfunc();

            }
        });
    }


    private void Nextfunc()

    {



        r1=(RadioButton) findViewById(Rg1.getCheckedRadioButtonId());
        r2=(RadioButton) findViewById(Rg2.getCheckedRadioButtonId());
        r3=(RadioButton) findViewById(Rg3.getCheckedRadioButtonId());
            if (Rg1.getCheckedRadioButtonId() == -1)
        {
            Rg1.requestFocus();
            Toast.makeText(Info_block.this, "All field required", Toast.LENGTH_SHORT).show();
        }
        else if(Rg2.getCheckedRadioButtonId() == -1)
{
    Rg1.requestFocus();
    Toast.makeText(Info_block.this, "All field required", Toast.LENGTH_SHORT).show();

}
else if(Rg3.getCheckedRadioButtonId() == -1)
{
    Rg1.requestFocus();
    Toast.makeText(Info_block.this, "All field required", Toast.LENGTH_SHORT).show();

}



        else if (r1.getText().toString().equals("No") &&r2.getText().toString().equals("No") && r3.getText().toString().equals("No"))

        {

            Intent intent = new Intent(this, Special_request.class );

            startActivity(intent);
        }




    else
{

    Intent intent1 = new Intent(this, Residue_of_estate.class);


    startActivity(intent1);
}





    }

}




