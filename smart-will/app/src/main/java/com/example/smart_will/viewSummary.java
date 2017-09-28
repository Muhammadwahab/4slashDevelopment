package com.example.smart_will;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class viewSummary extends AppCompatActivity {
SessionManager session;
    ImageButton but;
    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,
            t11,t12,t13,t14,t15,t16,t17,t18,t19,t20,
            t21,t22,t23,t24,t25,t26,t27,t28,t29, t30,
            t31,t32,t33,t34,t35,t36,t37,t38,t39,t40,
            t41,t42,t43,t44,t45,t46,t47,t48,t49,t50,t51;
    //
//        roe1=(EditText) findViewById(R.id.rfs1);
//        roe1.setText(getIntent().getExtras().getString("fname"));
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();

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
            Intent in=new Intent(viewSummary.this,logina1.class);
            startActivity(in);
            return(true);
        case R.id.back:
            // session.logoutUser();
            Intent in1=new Intent(viewSummary.this,Submission.class);
            startActivity(in1);
            return(true);



    }
        return(super.onOptionsItemSelected(item));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_summary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        final String uid = user.get(SessionManager.KEY_NAME);
        but = (ImageButton) findViewById(R.id.back);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(viewSummary.this, Submission.class));
            }
        });
       // noor=(TextView)findViewById(R.id.Tnoor);
        t1=(TextView)findViewById(R.id.T1);
        t2=(TextView)findViewById(R.id.T2);
        t3=(TextView)findViewById(R.id.T3);
        t4=(TextView)findViewById(R.id.T4);
        t5=(TextView)findViewById(R.id.T5);

        t6=(TextView)findViewById(R.id.T6);

        t7=(TextView)findViewById(R.id.T7);


        t8=(TextView)findViewById(R.id.T8);

        t9=(TextView)findViewById(R.id.T9);
        t10=(TextView)findViewById(R.id.T10);
        t11=(TextView)findViewById(R.id.T11);
        t12=(TextView)findViewById(R.id.T12);
        t13=(TextView)findViewById(R.id.T13);

        t14=(TextView)findViewById(R.id.T14);

        t15=(TextView)findViewById(R.id.T15);
        t16=(TextView)findViewById(R.id.T16);

        t17=(TextView)findViewById(R.id.T17);
        t18=(TextView)findViewById(R.id.T18);
        t19=(TextView)findViewById(R.id.T19);
        t20=(TextView)findViewById(R.id.T20);
        t21=(TextView)findViewById(R.id.T21);

        t22=(TextView)findViewById(R.id.T22);

        t23=(TextView)findViewById(R.id.T23);
        t24=(TextView)findViewById(R.id.T24);

        t25=(TextView)findViewById(R.id.T25);
        t26=(TextView)findViewById(R.id.T26);
        t27=(TextView)findViewById(R.id.T27);
        t28=(TextView)findViewById(R.id.T28);
        t29=(TextView)findViewById(R.id.T29);

        t30=(TextView)findViewById(R.id.T30);

        t31=(TextView)findViewById(R.id.T31);
        t32=(TextView)findViewById(R.id.T32);

        t33=(TextView)findViewById(R.id.T33);
        t34=(TextView)findViewById(R.id.T34);
        t35=(TextView)findViewById(R.id.T35);
        t36=(TextView)findViewById(R.id.T36);
        t37=(TextView)findViewById(R.id.T37);

        t38=(TextView)findViewById(R.id.T38);

        t39=(TextView)findViewById(R.id.T39);
        t40=(TextView)findViewById(R.id.T40);

        t41=(TextView)findViewById(R.id.T41);
        t42=(TextView)findViewById(R.id.T42);
        t43=(TextView)findViewById(R.id.T43);
        t44=(TextView)findViewById(R.id.T44);
        t45=(TextView)findViewById(R.id.T45);

        t47=(TextView)findViewById(R.id.T47);
        t48=(TextView)findViewById(R.id.T48);

        t49=(TextView)findViewById(R.id.T49);
        t50=(TextView)findViewById(R.id.T50);
        t51=(TextView)findViewById(R.id.T51);







        get_testator_Data(uid);
        get_spouse(uid);
        get_executor_Data(uid);
        get_guardian_Data(uid);
        get_gift_legacy_Data(uid);
        get_gift_Data(uid);
        get_residue_Data(uid);
        get_step_Data(uid);

        //spouse t10 to t18
        //executor t19 to t25
        //guardian t26 to t32
        //gift and legacy t33 to t35
        //36 s 40 gift
        //41 s 45 residue
        //other 46 s 51


    }
    private void get_spouse(final String uid) {

        String url = "http://110.37.231.10:8080/projects/Test_laravel/public/spouse_fatch";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(login.this, response, Toast.LENGTH_SHORT).show();
                        show_spouse_JSON(response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(viewSummary.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("u_id",uid);
//                map.put(key_pass,passwrd);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }
    private void show_spouse_JSON(String response){
        String name="N/A";
        String mname="N/A";
        String sname = "N/A";
        String addres="N/A";
        String post="N/A";
        String tele = "N/A";
        String email="N/A";
        String dob="N/A";

        try {
            if(response.equals("{\"result\":[]}")) {

                t10.setText(name, TextView.BufferType.EDITABLE);
                t11.setText(mname, TextView.BufferType.EDITABLE);
                t12.setText(sname, TextView.BufferType.EDITABLE);
                t13.setText(addres, TextView.BufferType.EDITABLE);
                t14.setText(post, TextView.BufferType.EDITABLE);
                t15.setText(tele, TextView.BufferType.EDITABLE);

                t16.setText(email, TextView.BufferType.EDITABLE);
                t17.setText(dob, TextView.BufferType.EDITABLE);
                t18.setText(dob, TextView.BufferType.EDITABLE);
            }
            else
            {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
                JSONObject collegeData = result.getJSONObject(0);
                name = collegeData.getString(Config.KEY_NAME);
                mname = collegeData.getString(Config.KEY_Mname);
                sname = collegeData.getString(Config.KEY_Sname);
                addres = collegeData.getString(Config.KEY_Address);
                post = collegeData.getString(Config.KEY_Postal);
                tele = collegeData.getString(Config.KEY_Telephone);
                email = collegeData.getString(Config.KEY_Email);
                dob = collegeData.getString(Config.KEY_DOB);
                t10.setText(name, TextView.BufferType.EDITABLE);
                t11.setText(mname, TextView.BufferType.EDITABLE);
                t12.setText(sname, TextView.BufferType.EDITABLE);
                t13.setText(addres, TextView.BufferType.EDITABLE);
                t14.setText(post, TextView.BufferType.EDITABLE);
                t15.setText(tele, TextView.BufferType.EDITABLE);

                t16.setText(email, TextView.BufferType.EDITABLE);
                t17.setText(dob, TextView.BufferType.EDITABLE);
                t18.setText(dob, TextView.BufferType.EDITABLE);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // textViewResult.setText("Name:\t"+name+"\nAddress:\t" +address+ "\nVice Chancellor:\t"+ vc);


        //fn.setText(name, TextView.BufferType.EDITABLE);

    }

    private void get_executor_Data(final String uid) {

        String url = "http://128.199.50.69/executor_fatch";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(login.this, response, Toast.LENGTH_SHORT).show();
                        show_executor_JSON(response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(viewSummary.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("u_id",uid);
//                map.put(key_pass,passwrd);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }
    private void show_executor_JSON(String response){
        String name="N/A";
        String mname="N/A";
        String sname = "N/A";
        String addres="N/A";
        String post="N/A";
        String rel = "N/A";
       // String email="";
        String dob="N/A";

        try {
            if(response.equals("{\"result\":[]}"))
            {
                t19.setText(name, TextView.BufferType.EDITABLE);
                t20.setText(mname, TextView.BufferType.EDITABLE);
                t21.setText(sname, TextView.BufferType.EDITABLE);
                t22.setText(addres, TextView.BufferType.EDITABLE);
                t23.setText(post, TextView.BufferType.EDITABLE);
                t24.setText(dob, TextView.BufferType.EDITABLE);
                t25.setText(rel, TextView.BufferType.EDITABLE);
            }
            else {


                JSONObject jsonObject = new JSONObject(response);
                JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
                JSONObject collegeData = result.getJSONObject(0);
                name = collegeData.getString(Config.KEY_NAME);
                mname = collegeData.getString(Config.KEY_Mname);
                sname = collegeData.getString(Config.KEY_Sname);
                addres = collegeData.getString(Config.KEY_Address);
                post = collegeData.getString(Config.KEY_Postal);
                dob = collegeData.getString(Config.KEY_DOB);
                rel = collegeData.getString(Config.KEY_REL);
                t19.setText(name, TextView.BufferType.EDITABLE);
                t20.setText(mname, TextView.BufferType.EDITABLE);
                t21.setText(sname, TextView.BufferType.EDITABLE);
                t22.setText(addres, TextView.BufferType.EDITABLE);
                t23.setText(post, TextView.BufferType.EDITABLE);
                t24.setText(dob, TextView.BufferType.EDITABLE);
                t25.setText(rel, TextView.BufferType.EDITABLE);
            }
           // email = collegeData.getString(Config.KEY_Email);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // textViewResult.setText("Name:\t"+name+"\nAddress:\t" +address+ "\nVice Chancellor:\t"+ vc);



    }

    private void get_guardian_Data(final String uid) {

        String url = "http://110.37.231.10:8080/projects/Test_laravel/public/guardians_fatch";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(login.this, response, Toast.LENGTH_SHORT).show();
                        show_guardian_JSON(response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(viewSummary.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                 map.put("u_id",uid);
//                map.put(key_pass,passwrd);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }
    private void show_guardian_JSON(String response){
        String name="N/A";
        String mname="N/A";
        String sname = "N/A";
        String addres="N/A";
        String post="N/A";
        String dob="N/A";
        String rel = "N/A";



        try {
            if(response.equals("{\"result\":[]}"))
            {
                t26.setText(name, TextView.BufferType.EDITABLE);
                t27.setText(mname, TextView.BufferType.EDITABLE);
                t28.setText(sname, TextView.BufferType.EDITABLE);
                t29.setText(addres, TextView.BufferType.EDITABLE);
                t30.setText(post, TextView.BufferType.EDITABLE);
                t31.setText(dob, TextView.BufferType.EDITABLE);

                t32.setText(rel, TextView.BufferType.EDITABLE);
            }
            else {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
                JSONObject collegeData = result.getJSONObject(0);
                name = collegeData.getString(Config.KEY_NAME);
                mname = collegeData.getString(Config.KEY_Mname);
                sname = collegeData.getString(Config.KEY_Sname);
                addres = collegeData.getString(Config.KEY_Address);
                post = collegeData.getString(Config.KEY_Postal);
                dob = collegeData.getString(Config.KEY_DOB);
                rel = collegeData.getString(Config.KEY_REL);
                t26.setText(name, TextView.BufferType.EDITABLE);
                t27.setText(mname, TextView.BufferType.EDITABLE);
                t28.setText(sname, TextView.BufferType.EDITABLE);
                t29.setText(addres, TextView.BufferType.EDITABLE);
                t30.setText(post, TextView.BufferType.EDITABLE);
                t31.setText(dob, TextView.BufferType.EDITABLE);

                t32.setText(rel, TextView.BufferType.EDITABLE);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
        // textViewResult.setText("Name:\t"+name+"\nAddress:\t" +address+ "\nVice Chancellor:\t"+ vc);

         //fn.setText(name, TextView.BufferType.EDITABLE);

    }

    private void get_gift_legacy_Data(final String uid) {

        String url = "http://110.37.231.10:8080/projects/Test_laravel/public/gifts_legacy_fatch";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(login.this, response, Toast.LENGTH_SHORT).show();
                        Log.d("*****", response);
                        show_legacy_JSON(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(viewSummary.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("u_id",uid);
//                map.put(key_pass,passwrd);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }
    private void show_legacy_JSON(String response){

        String name="N/A";
        String mname="N/A";
        String sname = "N/A";


        try {
            if(response.equals("{\"result\":[]}")) {

                t33.setText(name, TextView.BufferType.EDITABLE);
                t34.setText(mname, TextView.BufferType.EDITABLE);
                t35.setText(sname, TextView.BufferType.EDITABLE);


            }
            else
            {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
                JSONObject collegeData = result.getJSONObject(0);

                name = collegeData.getString("foundation1");
                mname = collegeData.getString("foundation2");
                sname = collegeData.getString("foundation3");
                t33.setText(name, TextView.BufferType.EDITABLE);
                t34.setText(mname, TextView.BufferType.EDITABLE);
                t35.setText(sname, TextView.BufferType.EDITABLE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // textViewResult.setText("Name:\t"+name+"\nAddress:\t" +address+ "\nVice Chancellor:\t"+ vc);


    }

    private void get_gift_Data(final String uid) {

        String url = "http://110.37.231.10:8080/projects/Test_laravel/public/gift_fatch";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(login.this, response, Toast.LENGTH_SHORT).show();
                        show_gift_JSON(response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(viewSummary.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("u_id",uid);
//                map.put(key_pass,passwrd);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }
    private void show_gift_JSON(String response){
        String name="N/A";
        String mname="N/A";
        String sname = "N/A";
        String rel="N/A";
        String gift="N/A";


        try {
            if(response.equals("{\"result\":[]}"))
            {
                t36.setText(name, TextView.BufferType.EDITABLE);
                t37.setText(mname, TextView.BufferType.EDITABLE);
                t38.setText(sname, TextView.BufferType.EDITABLE);
                t39.setText(rel, TextView.BufferType.EDITABLE);
                t40.setText(gift, TextView.BufferType.EDITABLE);
            }
            else
            {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
                JSONObject collegeData = result.getJSONObject(0);
                name = collegeData.getString(Config.KEY_NAME);
                mname = collegeData.getString(Config.KEY_Mname);
                sname = collegeData.getString(Config.KEY_Sname);
                rel = collegeData.getString(Config.KEY_REL);
                gift = collegeData.getString("gifts");
                t36.setText(name, TextView.BufferType.EDITABLE);
                t37.setText(mname, TextView.BufferType.EDITABLE);
                t38.setText(sname, TextView.BufferType.EDITABLE);
                t39.setText(rel, TextView.BufferType.EDITABLE);
                t40.setText(gift, TextView.BufferType.EDITABLE);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // textViewResult.setText("Name:\t"+name+"\nAddress:\t" +address+ "\nVice Chancellor:\t"+ vc);


        //fn.setText(name, TextView.BufferType.EDITABLE);

    }

    private void get_residue_Data(final String uid) {

        String url = "http://110.37.231.10:8080/projects/Test_laravel/public/residue_fatch";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(login.this, response, Toast.LENGTH_SHORT).show();
                        show_res_JSON(response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(viewSummary.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("u_id",uid);
//                map.put(key_pass,passwrd);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }
    private void show_res_JSON(String response){
        String name="N/A";
        String mname="N/A";
        String sname = "N/A";
        String rel="N/A";
        String estate="N/A";


        try {
            if(response.equals("{\"result\":[]}"))
            {
                t41.setText(name, TextView.BufferType.EDITABLE);
                t42.setText(mname, TextView.BufferType.EDITABLE);
                t43.setText(sname, TextView.BufferType.EDITABLE);
                t44.setText(rel, TextView.BufferType.EDITABLE);
                t45.setText(estate, TextView.BufferType.EDITABLE);
            }
            else {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
                JSONObject collegeData = result.getJSONObject(0);
                name = collegeData.getString(Config.KEY_NAME);
                mname = collegeData.getString(Config.KEY_Mname);
                sname = collegeData.getString(Config.KEY_Sname);
                rel = collegeData.getString(Config.KEY_REL);
                estate = collegeData.getString(Config.KEY_estate);
                t41.setText(name, TextView.BufferType.EDITABLE);
                t42.setText(mname, TextView.BufferType.EDITABLE);
                t43.setText(sname, TextView.BufferType.EDITABLE);
                t44.setText(rel, TextView.BufferType.EDITABLE);
                t45.setText(estate, TextView.BufferType.EDITABLE);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // textViewResult.setText("Name:\t"+name+"\nAddress:\t" +address+ "\nVice Chancellor:\t"+ vc);


    }

    private void get_step_Data(final String uid) {

        String url = "http://110.37.231.10:8080/projects/Test_laravel/public/step_child_fatch";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(login.this, response, Toast.LENGTH_SHORT).show();
                        show_step_JSON(response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(viewSummary.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("u_id",uid);
//                map.put(key_pass,passwrd);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }
    private void show_step_JSON(String response){
        String name="N/A";
        String mname="N/A";
        String sname = "N/A";
        String rel="N/A";
        String estate="N/A";


        try {
            if(response.equals("{\"result\":[]}"))
            {
                t47.setText(name, TextView.BufferType.EDITABLE);
                t48.setText(mname, TextView.BufferType.EDITABLE);
                t49.setText(sname, TextView.BufferType.EDITABLE);
                t50.setText(rel, TextView.BufferType.EDITABLE);
                t51.setText(estate, TextView.BufferType.EDITABLE);
            }
            else {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
                JSONObject collegeData = result.getJSONObject(0);
                name = collegeData.getString(Config.KEY_NAME);
                mname = collegeData.getString(Config.KEY_Mname);
                sname = collegeData.getString(Config.KEY_Sname);
                rel = collegeData.getString(Config.KEY_REL);
                estate = collegeData.getString(Config.KEY_estate);
                t47.setText(name, TextView.BufferType.EDITABLE);
                t48.setText(mname, TextView.BufferType.EDITABLE);
                t49.setText(sname, TextView.BufferType.EDITABLE);
                t50.setText(rel, TextView.BufferType.EDITABLE);
                t51.setText(estate, TextView.BufferType.EDITABLE);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // textViewResult.setText("Name:\t"+name+"\nAddress:\t" +address+ "\nVice Chancellor:\t"+ vc);


    }




    private void get_testator_Data(final String uid) {

        String url = "http://110.37.231.10:8080/projects/Test_laravel/public/testator_fatch";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(login.this, response, Toast.LENGTH_SHORT).show();
                        showJSON(response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(viewSummary.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("u_id",uid);
//                map.put(key_pass,passwrd);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }
    private void showJSON(String response){
        String name="N/A";
        String mname="N/A";
        String sname = "N/A";
        String addres="N/A";
        String post="N/A";
        String tele = "N/A";
        String email="N/A";
        String dob="N/A";

        try {
            if(response.equals("{\"result\":[]}"))
            {
                t1.setText(name, TextView.BufferType.EDITABLE);
                t2.setText(mname, TextView.BufferType.EDITABLE);
                t3.setText(sname, TextView.BufferType.EDITABLE);
                t4.setText(addres, TextView.BufferType.EDITABLE);
                t5.setText(post, TextView.BufferType.EDITABLE);
                t6.setText(tele, TextView.BufferType.EDITABLE);

                t7.setText(email, TextView.BufferType.EDITABLE);
                t8.setText(dob, TextView.BufferType.EDITABLE);
            }
            else {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
                JSONObject collegeData = result.getJSONObject(0);
                name = collegeData.getString(Config.KEY_NAME);
                mname = collegeData.getString(Config.KEY_Mname);
                sname = collegeData.getString(Config.KEY_Sname);
                addres = collegeData.getString(Config.KEY_Address);
                post = collegeData.getString(Config.KEY_Postal);
                tele = collegeData.getString(Config.KEY_Telephone);
                email = collegeData.getString(Config.KEY_Email);
                dob = collegeData.getString(Config.KEY_DOB);
                t1.setText(name, TextView.BufferType.EDITABLE);
                t2.setText(mname, TextView.BufferType.EDITABLE);
                t3.setText(sname, TextView.BufferType.EDITABLE);
                t4.setText(addres, TextView.BufferType.EDITABLE);
                t5.setText(post, TextView.BufferType.EDITABLE);
                t6.setText(tele, TextView.BufferType.EDITABLE);

                t7.setText(email, TextView.BufferType.EDITABLE);
                t8.setText(dob, TextView.BufferType.EDITABLE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // textViewResult.setText("Name:\t"+name+"\nAddress:\t" +address+ "\nVice Chancellor:\t"+ vc);

        //fn.setText(name, TextView.BufferType.EDITABLE);

    }
}
