package com.example.smart_will;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class sign_up extends AppCompatActivity {
    SessionManager session;

    EditText usr_nm, mail, pass;
    Button btn;
    ImageButton but;
    String url ="http://128.199.50.69/insert";


    public void reg(View view) {
        Intent intern = new Intent(this, logina1.class);
        startActivity(intern);
    }
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
        setContentView(R.layout.activity_sign_up);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        usr_nm=(EditText) findViewById(R.id.usr_nme);
        mail = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        btn=(Button)findViewById(R.id.btn_reg);
        session = new SessionManager(getApplicationContext());

        but=(ImageButton)findViewById(R.id.back);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(sign_up.this,logina1.class));
            }
        });

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if(usr_nm.length()==0)
                {
                    usr_nm.requestFocus();
                    usr_nm.setError("Please Enter User Name");
                }
                else if(mail.length()==0)
                {
                    mail.requestFocus();
                    mail.setError("Please Enter Your Email");
                }
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(mail.getText().toString()).matches())
                {
                    mail.setError("Invalid Email");
                }
                else if(pass.length()==0)
                {
                    pass.requestFocus();
                    pass.setError("Please Enter Password");
                }
//                else if(rg.getCheckedRadioButtonId()==0)
//                {
//                    rg.requestFocus();
//                    Toast.makeText(testator_details.this, "please select male Female", Toast.LENGTH_SHORT).show();
//                }
                else
                {
//                    Toast.makeText(testator_details.this,"Validation Successful",Toast.LENGTH_LONG).show();
                    InsertSV();
                }


            }
        });
    }

    private void InsertSV() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                Toast.makeText(getApplication(), response, Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(sign_up.this, error + "", Toast.LENGTH_SHORT).show();
                Toast.makeText(sign_up.this, "Please fill all the Required fields", Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                String user_nme = usr_nm.getText().toString();
                String email = mail.getText().toString();
                String passwrd = pass.getText().toString();
                params.put("user_name", user_nme);
                params.put("email", email);
                params.put("password", passwrd);
                Intent intent = new Intent(sign_up.this, logina1.class);
                startActivity(intent);

                return params;
            }


    };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}