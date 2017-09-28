package com.example.smart_will;

import android.bluetooth.BluetoothAssignedNumbers;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Submission extends AppCompatActivity {

    Button summary, smrt,edit;
    SessionManager session;
    String url_testator ="http://110.37.231.10:8080/projects/Test_laravel/public/testator";
    String url_spouse ="http://110.37.231.10:8080/projects/Test_laravel/public/spouse";
    String url_executor ="http://110.37.231.10:8080/projects/Test_laravel/public/executor";
    String url_guardians ="http://110.37.231.10:8080/projects/Test_laravel/public/guardians";
    String url_gifts_legacy ="http://110.37.231.10:8080/projects/Test_laravel/public/gifts_legacy";
    String url_gifts ="http://110.37.231.10:8080/projects/Test_laravel/public/gift";
    String url_ros="http://110.37.231.10:8080/projects/Test_laravel/public/residue";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        final String uid = user.get(SessionManager.KEY_NAME);
        edit=(Button)findViewById(R.id.imageButton);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Submission.this,Edit.class);
                startActivity(intent);

            }
        });



        summary=(Button)findViewById(R.id.vsummary);
        summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Submission.this, viewSummary.class);
                intent.putExtras(getIntent());
                startActivity(intent);

            }
        });

        smrt=(Button)findViewById(R.id.sm);
        smrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //insert();
                Intent intent = new Intent(Submission.this, thankyou2.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.logout:
            session.logoutUser();
            Intent in=new Intent(Submission.this,logina1.class);
            startActivity(in);
            return(true);


    }
        return(super.onOptionsItemSelected(item));
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();

    }
    public void insert(){

        Intent intent = new Intent();
        intent.putExtras(getIntent());


        String flag_no =  getIntent().getExtras().getString("flag");

        Toast.makeText(this, flag_no, Toast.LENGTH_SHORT).show();
        Insert_testator();
        Insert_spouse();
        Insert_executor();
        Insert_guardians();
        Insert_residue_of_estate();

        if(flag_no.equals("false"))
        {
            Insert_giftslegacy();//no
        }
        else
        {
            Insert_gifts();//yes

        }

    }

    public void reg(View view) {
        Intent intern = new Intent(this, thankyou2.class);
        startActivity(intern);
    }
    public void Tc(View view) {
        Intent intern = new Intent(this, Terms_condition.class);
        startActivity(intern);
    }

    public void solicitor(View view) {
        Intent intern = new Intent(this, thankyou3.class);
        startActivity(intern);
    }


    public void Insert_testator() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_testator, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                Toast.makeText(getApplication(), "1111", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(Submission.this, error + "", Toast.LENGTH_SHORT).show();

            }
        }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String,String> params=new HashMap<String, String>();

//                String newString = newString.getText().toString();
//                String email = mail.getText().toString();
//                String passwrd = pass.getText().toString();
                Intent intent = new Intent();
                intent.putExtras(getIntent());
                String fnm = getIntent().getExtras().getString("fname");
                String mdnm = getIntent().getExtras().getString("midname");
                String srnm = getIntent().getExtras().getString("surname");
                String ad = getIntent().getExtras().getString("address");
                String pst = getIntent().getExtras().getString("post");

                String tl = getIntent().getExtras().getString("tell");
                String em = getIntent().getExtras().getString("email");
                String dt = getIntent().getExtras().getString("date");

                String rd = getIntent().getExtras().getString("Radiogroup");
                String uid = getIntent().getExtras().getString("u_id");
                intent.putExtra("u_id",uid);

                params.put("name", fnm);
                params.put("mdname", mdnm);
                params.put("srname", srnm);
                params.put("adres", ad);
                params.put("pst-code", pst);
                params.put("telephone", tl);
                params.put("email", em);
                params.put("dob", dt);
                params.put("gndr", rd);
                params.put("uid",uid);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        }

    public void Insert_spouse() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_spouse, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                Toast.makeText(getApplication(), "2222", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(Submission.this, error + "", Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String,String> params=new HashMap<String, String>();

//                String newString = newString.getText().toString();
//                String email = mail.getText().toString();
//                String passwrd = pass.getText().toString();
                Intent intent = new Intent();
                intent.putExtras(getIntent());
                String fnm2 = getIntent().getExtras().getString("sp1");
                String mdnm2 = getIntent().getExtras().getString("sp2");
                String srnm2 = getIntent().getExtras().getString("sp3");
                String ad2 = getIntent().getExtras().getString("sp4");
                String pst2 = getIntent().getExtras().getString("sp5");


                String tl2 = getIntent().getExtras().getString("sp6");
                String em2 = getIntent().getExtras().getString("sp7");
                String dt2 = getIntent().getExtras().getString("sp8");

                String rd2 = getIntent().getExtras().getString("sp9");
                String uid2 = getIntent().getExtras().getString("u_id");
                intent.putExtra("u_id",uid2);

                params.put("name", fnm2);
                params.put("mdname", mdnm2);
                params.put("srname", srnm2);
                params.put("adres", ad2);
                params.put("pst-code", pst2);
                params.put("telephone", tl2);
                params.put("email", em2);
                params.put("dob", dt2);
                params.put("gndr", rd2);
                params.put("uid",uid2);


                return params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void Insert_executor(){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_executor, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                Toast.makeText(getApplication(), "3333", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(Submission.this, error + "", Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String,String> params=new HashMap<String, String>();

//                String newString = newString.getText().toString();
//                String email = mail.getText().toString();
//                String passwrd = pass.getText().toString();
                Intent intent = new Intent();
                intent.putExtras(getIntent());
                String fnm1 = getIntent().getExtras().getString("grdn_fn");
                String mdnm1= getIntent().getExtras().getString("grdn_md");
                String srnm1 = getIntent().getExtras().getString("grdn_sr");
                String ad1 = getIntent().getExtras().getString("grdn_ad");
                String pst1 = getIntent().getExtras().getString("grdn_ps");


                String db1 = getIntent().getExtras().getString("grdn_db");
                String rl1 = getIntent().getExtras().getString("grdn_rel");

                String uid1 = getIntent().getExtras().getString("u_id");
                intent.putExtra("u_id",uid1);


                params.put("name", fnm1);
                params.put("mdname", mdnm1);
                params.put("srname", srnm1);
                params.put("adres", ad1);
                params.put("pst-code", pst1);
                params.put("dob", db1);
                params.put("relation", rl1);
                params.put("uid",uid1);


                return params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void Insert_guardians(){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_guardians, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                Toast.makeText(getApplication(), "44444", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(Submission.this, error + "", Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String,String> params=new HashMap<String, String>();

//                String newString = newString.getText().toString();
//                String email = mail.getText().toString();
//                String passwrd = pass.getText().toString();
                Intent intent = new Intent();
                intent.putExtras(getIntent());
                String gr1 = getIntent().getExtras().getString("gar1");
                String gr2= getIntent().getExtras().getString("gar2");
                String gr3 = getIntent().getExtras().getString("gar3");
                String gr4 = getIntent().getExtras().getString("gar4");
                String gr5 = getIntent().getExtras().getString("gar5");


                String gr6 = getIntent().getExtras().getString("gar6");
                String gr7 = getIntent().getExtras().getString("gar7");

                String uid3 = getIntent().getExtras().getString("u_id");
                intent.putExtra("u_id",uid3);


                params.put("name", gr1);
                params.put("mdname", gr2);
                params.put("srname", gr3);
                params.put("adres", gr4);
                params.put("pst-code", gr5);
                params.put("dob", gr6);
                params.put("relation", gr7);
                params.put("uid",uid3);


                return params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void Insert_giftslegacy(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_gifts_legacy, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                Toast.makeText(getApplication(), "5555", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(Submission.this, error + "", Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String,String> params=new HashMap<String, String>();

//                String newString = newString.getText().toString();
//                String email = mail.getText().toString();
//                String passwrd = pass.getText().toString();
                Intent intent = new Intent();
                intent.putExtras(getIntent());

                String gl1 = getIntent().getExtras().getString("ed1");
                String gl2= getIntent().getExtras().getString("ed2");
                String gl3 = getIntent().getExtras().getString("ed3");


                String uid4 = getIntent().getExtras().getString("u_id");
                intent.putExtra("u_id",uid4);


                params.put("foundation1", gl1);
                params.put("foundation2", gl2);
                params.put("foundation3", gl3);

                params.put("uid",uid4);


                return params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }

    public void Insert_gifts(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_gifts, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                Toast.makeText(getApplication(), "6666", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(Submission.this, error + "", Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String,String> params=new HashMap<String, String>();

//                String newString = newString.getText().toString();
//                String email = mail.getText().toString();
//                String passwrd = pass.getText().toString();
                Intent intent = new Intent();
                intent.putExtras(getIntent());
               // Toast.makeText(Submission.this, getIntent().getExtras().getString("gfts1") , Toast.LENGTH_SHORT).show();
                String gft1 = getIntent().getExtras().getString("gfts1");
                String gft2= getIntent().getExtras().getString("gfts2");
                String gft3 = getIntent().getExtras().getString("gfts3");
                String gft4 = getIntent().getExtras().getString("gfts4");
                String gft5 = getIntent().getExtras().getString("gfts5");


                String uid5 = getIntent().getExtras().getString("u_id");
                intent.putExtra("u_id",uid5);


                params.put("name", gft1);
                params.put("mdname", gft2);
                params.put("srname", gft3);
                params.put("relation", gft4);
                params.put("gift", gft5);
                params.put("uid",uid5);


                return params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    public void Insert_residue_of_estate(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_ros, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                Toast.makeText(getApplication(), "7777", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(Submission.this, error + "", Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String,String> params=new HashMap<String, String>();

//                String newString = newString.getText().toString();
//                String email = mail.getText().toString();
//                String passwrd = pass.getText().toString();
                Intent intent = new Intent();
                intent.putExtras(getIntent());
                // Toast.makeText(Submission.this, getIntent().getExtras().getString("gfts1") , Toast.LENGTH_SHORT).show();
                String rof1 = getIntent().getExtras().getString("rfs1");
                String rof2= getIntent().getExtras().getString("rfs2");
                String rof3 = getIntent().getExtras().getString("rfs3");
                String rof4 = getIntent().getExtras().getString("rfs4");
                String rof5 = getIntent().getExtras().getString("rfs5");


                String uid5 = getIntent().getExtras().getString("u_id");
                intent.putExtra("u_id",uid5);


                params.put("name", rof1);
                params.put("mdname", rof2);
                params.put("srname", rof3);
                params.put("relation", rof4);
                params.put("estate", rof5);
                params.put("uid",uid5);


                return params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }

}

