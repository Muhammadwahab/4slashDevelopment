package com.example.smart_will;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import static android.R.attr.checked;
import static android.R.attr.ems;
import static android.R.attr.fragment;
public class testator_details extends AppCompatActivity {
    private TextView mDisplayDate,showdate1;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    SessionManager session;
    Button btnLogout;


//    public void reg(View view) {
//        Intent intern = new Intent(this, Executors.class);
//        startActivity(intern);
//    }

//    public void spouse(View view) {
//        Intent intern = new Intent(this, Spouse.class);
//        startActivity(intern);
//    }

    //--Fields Variable--//
    EditText fn,mn,sn,ad,ps,tl,em;
    TextView dt;
    RadioGroup radioSexGroup;
    RadioButton radioButton;
    Button btn;
    ImageButton but;
    Button btn_spouse;
    String url_testator ="http://128.199.50.69/testator";
    String url_check ="http://128.199.50.69/check";

    //--Fields Variable--//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        final String uid = user.get(SessionManager.KEY_NAME);
        check(uid);

        setContentView(R.layout.activity_testator_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        mDisplayDate = (TextView) findViewById(R.id.picdate);
        showdate1 = (TextView) findViewById(R.id.showdate);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        testator_details.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
               // Log.d(, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = year+"-"+ month+"-"+day ;
                showdate1.setText(date);
            }
        };

        but=(ImageButton)findViewById(R.id.back);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.logoutUser();
                startActivity(new Intent(testator_details.this,logina1.class));
            }
        });

        fn =(EditText)findViewById(R.id.fname);
        mn =(EditText)findViewById(R.id.mname);
        sn =(EditText)findViewById(R.id.sname);
        ad =(EditText)findViewById(R.id.add);
        ps =(EditText)findViewById(R.id.pst);
        tl =(EditText)findViewById(R.id.tel);
        em =(EditText)findViewById(R.id.mail);
        dt =(TextView)findViewById(R.id.showdate);

        //--Radiogroup--//
        radioSexGroup = (RadioGroup) findViewById(R.id.Rbg);

 //--Button Next Function--//
        btn = (Button) findViewById(R.id.next);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fn.length()==0)
                {
                    fn.requestFocus();
                    fn.setError("Please Enter First Name");
                }

                else if(sn.length()==0)
                {
                    sn.requestFocus();
                    sn.setError("Please Enter Surname");
                }
                else if(ad.length()==0)
                {
                    ad.requestFocus();
                    ad.setError("Please Enter Address");
                }
                else if(ps.length()==0)
                {
                    ps.requestFocus();
                    ps.setError("Please Enter Postal Code");
                }
                else if(tl.length()==0)
                {
                    tl.requestFocus();
                    tl.setError("Please Enter Telephone number");
                }
                else if(em.length()==0)
                {
                    em.requestFocus();
                    em.setError("Please Enter Email");
                }
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(em.getText().toString()).matches())
                {
                    em.setError("Invalid Email");
                }
                else if(dt.length()==0)
                {
                    dt.requestFocus();
                    dt.setError("Please Select Date");
                }
                else if (radioSexGroup.getCheckedRadioButtonId() == -1)
                {
                    radioSexGroup.requestFocus();
                    Toast.makeText(testator_details.this, "Please enter Gender", Toast.LENGTH_SHORT).show();
                }
                else
                {
//                    Toast.makeText(testator_details.this,"Validation Successful",Toast.LENGTH_LONG).show();
                    Insert_testator(uid);

                   // intent_executer(uid);
                }



            }
        });//--Button Next Function--//


        //--Button Spouse Function--//
        btn_spouse = (Button)findViewById(R.id.spouse) ;
        btn_spouse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(fn.length()==0)
                {
                    fn.requestFocus();
                    fn.setError("Please Enter First Name");
                }
                else if(sn.length()==0)
                {
                    sn.requestFocus();
                    sn.setError("Please Enter Surname");
                }
                else if(ad.length()==0)
                {
                    ad.requestFocus();
                    ad.setError("Please Enter Address");
                }
                else if(ps.length()==0)
                {
                    ps.requestFocus();
                    ps.setError("Please Enter Postal Code");
                }
                else if(tl.length()==0)
                {
                    tl.requestFocus();
                    tl.setError("Please Enter Telephone Number");
                }
                else if(em.length()==0)
                {
                    em.requestFocus();
                    em.setError("Please Enter Email");
                }
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(em.getText().toString()).matches())
                {
                    em.setError("Invalid Email");
                }
                else if(dt.length()==0)
                {
                    dt.requestFocus();
                    dt.setError("Please Select Date");
                }
              else if (radioSexGroup.getCheckedRadioButtonId() == -1)
                {
                    radioSexGroup.requestFocus();
                    Toast.makeText(testator_details.this, "Please Enter Gender", Toast.LENGTH_SHORT).show();
                }
                else
                {

//                    Toast.makeText(testator_details.this,"Validation Successful",Toast.LENGTH_LONG).show();
                    Insert_testator_spouse(uid);
                   // Intent_spouns(uid);
                }




            }
        });

        //--Button Spouse Function--//
    }

    private void Insert_testator_spouse(final String uid) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_testator, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Intent in=new Intent(testator_details.this,Spouse.class);
                startActivity(in);
                //Toast.makeText(getApplication(), "1111", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                int selectedId = radioSexGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);
                Toast.makeText(testator_details.this, radioButton.getText(), Toast.LENGTH_SHORT).show();
                Toast.makeText(testator_details.this, error + "", Toast.LENGTH_SHORT).show();

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
                String fnm =fn.getText().toString();
                String mdnm =mn.getText().toString();
                String srnm = sn.getText().toString();
                String ad1 = ad.getText().toString();
                String pst = ps.getText().toString();

                String tl1 = tl.getText().toString();
                String em1 = em.getText().toString();
                String dt1 =dt.getText().toString();
                String rv = ((RadioButton) findViewById(radioSexGroup.getCheckedRadioButtonId())).getText().toString();


//                String uid = getIntent().getExtras().getString("u_id");
//                intent.putExtra("u_id",uid);

                params.put("name", fnm);
                params.put("mdname", mdnm);
                params.put("srname", srnm);
                params.put("adres", ad1);
                params.put("pst-code", pst);
                params.put("telephone", tl1);
                params.put("email", em1);
                params.put("dob", dt1);
                params.put("gndr", rv);
                params.put("uid",uid);

                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);


    }

    private void check(final String uid) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_check, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                 if(response.equals("executor"))
                {
                    Intent in=new Intent(testator_details.this,Executors.class);
                    startActivity(in);
                }
                else if (response.equals("special_request"))
                {
                    Intent in=new Intent(testator_details.this,Special_request.class);
                    startActivity(in);
                }
                else if (response.equals("gifts"))
                {
                    Intent in=new Intent(testator_details.this,Gifts.class);
                    startActivity(in);
                }
                else if (response.equals("guardians"))
                {
                    Intent in=new Intent(testator_details.this,Guardians.class);
                    startActivity(in);
                }
                else if (response.equals("resedue_of_estate"))
                {
                    Intent in=new Intent(testator_details.this,Residue_of_estate.class);
                    startActivity(in);
                }
                else if (response.equals("spouse"))
                {
                    Intent in=new Intent(testator_details.this,Spouse.class);
                    startActivity(in);
                }
                else if (response.equals("step_children"))
                {
                    Intent in=new Intent(testator_details.this,Residue_of_estate.class);
                    startActivity(in);
                }
                else if (response.equals("summary"))
                {
                    Intent in=new Intent(testator_details.this,Submission.class);
                    startActivity(in);
                }
               // Toast.makeText(getApplication(), response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                 Toast.makeText(testator_details.this, error + "", Toast.LENGTH_SHORT).show();

            }
        }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String,String> params=new HashMap<String, String>();

                params.put("u_id",uid);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.logout:
            session.logoutUser();
            Intent in=new Intent(testator_details.this,logina1.class);
            startActivity(in);
            return(true);



    }
        return(super.onOptionsItemSelected(item));
    }





    public void Insert_testator(final String uid) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_testator, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Intent in=new Intent(testator_details.this,Executors.class);
                startActivity(in);
                //Toast.makeText(getApplication(), "1111", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                int selectedId = radioSexGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);
                Toast.makeText(testator_details.this, radioButton.getText(), Toast.LENGTH_SHORT).show();
                Toast.makeText(testator_details.this, error + "", Toast.LENGTH_SHORT).show();

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
                String fnm =fn.getText().toString();
                String mdnm =mn.getText().toString();
                String srnm = sn.getText().toString();
                String ad1 = ad.getText().toString();
                String pst = ps.getText().toString();

                String tl1 = tl.getText().toString();
                String em1 = em.getText().toString();
                String dt1 =dt.getText().toString();
                String rv = ((RadioButton) findViewById(radioSexGroup.getCheckedRadioButtonId())).getText().toString();


//                String uid = getIntent().getExtras().getString("u_id");
//                intent.putExtra("u_id",uid);

                params.put("name", fnm);
                params.put("mdname", mdnm);
                params.put("srname", srnm);
                params.put("adres", ad1);
                params.put("pst-code", pst);
                params.put("telephone", tl1);
                params.put("email", em1);
                params.put("dob", dt1);
                params.put("gndr", rv);
                params.put("uid",uid);

                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);


    }
    //--button Inner Function--//
    private void intent_executer(String uid) {


        Intent intent = new Intent(testator_details.this, Executors.class);

        intent.putExtra("fname", fn.getText().toString());
        intent.putExtra("midname", mn.getText().toString());
        intent.putExtra("surname", sn.getText().toString());
        intent.putExtra("address", ad.getText().toString());
        intent.putExtra("post", ps.getText().toString());
        intent.putExtra("tell", tl.getText().toString());
        intent.putExtra("email", em.getText().toString());
        intent.putExtra("date", dt.getText().toString());

        intent.putExtras(getIntent());
        //String uid = getIntent().getExtras().getString("u_id");
        intent.putExtra("u_id", uid);
        Toast.makeText(this, uid, Toast.LENGTH_SHORT).show();
        //--Radiogroup--//

        String rv = ((RadioButton) findViewById(radioSexGroup.getCheckedRadioButtonId())).getText().toString();
        intent.putExtra("Radiogroup", rv);

        //--Radiogroup--//
        startActivity(intent);
        finish();

    }
    //--Button Inner function--//

    private void Intent_spouns(String uid){
        Intent intent = new Intent(testator_details.this, Spouse.class);
        intent.putExtra("fname",fn.getText().toString());
        intent.putExtra("midname", mn.getText().toString());
        intent.putExtra("surname", sn.getText().toString());
        intent.putExtra("address", ad.getText().toString());
        intent.putExtra("post", ps.getText().toString());
        intent.putExtra("tell", tl.getText().toString());
        intent.putExtra("email", em.getText().toString());
        intent.putExtra("date", dt.getText().toString());

        intent.putExtras(getIntent());
       // String uid = getIntent().getExtras().getString("u_id");
        intent.putExtra("u_id",uid);

        //--Radiogroup--//

        String rv = ((RadioButton)findViewById(radioSexGroup.getCheckedRadioButtonId())).getText().toString();
        intent.putExtra("Radiogroup",rv);

        //--Radiogroup--//
        startActivity(intent);

    }




    //--calender Dialog Box--//
    public void datepicker(View view) {

        DatePickerFregment fragment = new DatePickerFregment();
        fragment.show(getSupportFragmentManager(), "date");
    }

    private void setDate(final Calendar calendar) {

        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        ((TextView) findViewById(R.id.showdate)).setText(dateFormat.format(calendar.getTime()));
    }

    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, month, day);
        setDate(cal);
    }


    public static class DatePickerFregment extends DialogFragment {


        public Dialog onCreateDialog(Bundle savedInstancestate) {

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(),
                    (DatePickerDialog.OnDateSetListener)
                            getActivity(), year, month, day);

        }

    }
    //--calender Dialog Box--//
}
