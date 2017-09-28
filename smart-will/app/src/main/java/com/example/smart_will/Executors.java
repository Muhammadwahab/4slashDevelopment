package com.example.smart_will;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ParseException;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
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

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class Executors extends AppCompatActivity {

    //    public void reg(View view) {
//        Intent intern = new Intent(this, Guardians.class);
//        startActivity(intern);
//    }
    String url_executor ="http://128.199.50.69/executor";

    EditText et1, et2, et3, et4, et5, et7;
    TextView et6;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextView mDisplayDate,showdate1;
    Button exenext, exeadd;
    ImageButton but;
    Button btn_Executor;
    static int count;
SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executors);
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("your_int_key", count);
        editor.commit();

//--Executor data getting--//
        et1 = (EditText) findViewById(R.id.Ex_t1);
        et2 = (EditText) findViewById(R.id.Ex_t2);
        et3 = (EditText) findViewById(R.id.Ex_t3);
        et4 = (EditText) findViewById(R.id.Ex_t4);
        et5 = (EditText) findViewById(R.id.Ex_t5);
        et6 = (TextView) findViewById(R.id.showdate_ex);
        et7 = (EditText) findViewById(R.id.Ex_t7);
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        final String uid = user.get(SessionManager.KEY_NAME);
//        final String uid = getIntent().getExtras().getString("u_id");
//--Executor data getting--//
        mDisplayDate = (TextView) findViewById(R.id.picdate);
        showdate1 = (TextView) findViewById(R.id.showdate_ex);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Executors.this,
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
                startActivity(new Intent(Executors.this,testator_details.class));
            }
        });
        exenext = (Button) findViewById(R.id.Executor_next);
        exenext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et1.length() == 0) {
                    et1.requestFocus();
                    et1.setError("Please enter first name");
                } else if (et3.length() == 0) {
                    et3.requestFocus();
                    et3.setError("Please enter surname");
                } else if (et4.length() == 0) {
                    et4.requestFocus();
                    et4.setError("Please enter address");
                } else if (et5.length() == 0) {
                    et5.requestFocus();
                    et5.setError("Please enter Post Code");
                } else if (et6.length() == 0) {
                    et6.requestFocus();
                    et6.setError("Please Select date");
                } else if (et7.length() == 0) {
                    et7.requestFocus();
                    et7.setError("Please enter Your Relation");
                }

//                else if(rg.getCheckedRadioButtonId()==0)
//                {
//                    rg.requestFocus();
//                    Toast.makeText(testator_details.this, "please select male Female", Toast.LENGTH_SHORT).show();
//                }
                else {
//                    Toast.makeText(testator_details.this,"Validation Successful",Toast.LENGTH_LONG).show();
                    Insert_executor(uid);
                   // Exec_intent_func(uid);
                }


            }
        });

        exeadd = (Button) findViewById(R.id.exeadd);
        exeadd.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                SharedPreferences sp1 = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
                int myIntValue = sp1.getInt("your_int_key", -1);
                count++;
                String a = Integer.toString(myIntValue);
                if (et1.length() == 0) {
                    et1.requestFocus();
                    et1.setError("Please enter first name");
                }  else if (et3.length() == 0) {
                    et3.requestFocus();
                    et3.setError("Please enter surname");
                } else if (et4.length() == 0) {
                    et4.requestFocus();
                    et4.setError("Please enter address");
                } else if (et5.length() == 0) {
                    et5.requestFocus();
                    et5.setError("Please enter Post Code");
                } else if (et6.length() == 0) {
                    et6.requestFocus();
                    et6.setError("Please Select date");
                } else if (et7.length() == 0) {
                    et7.requestFocus();
                    et7.setError("Please enter Your Relation");
                } else if (a.equals("2")) {
                    //Toast.makeText(Executors.this, "You can't add more than 3 executor", Toast.LENGTH_SHORT).show();
                    Insert_executor_add(uid);
                    //Exec_intent_func(uid);
                    Intent in=new Intent(Executors.this,Guardians.class);
                    startActivity(in);

                } else {
                    //Toast.makeText(Executors.this, a, Toast.LENGTH_SHORT).show();
                    Insert_executor_add(uid);
                    //Exec_intent_func(uid);
                    Intent i = new Intent(Executors.this, Executors.class);
                    startActivity(i);
                }

            }
        });

    }

    private void Insert_executor_add(final String uid) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_executor, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                //Toast.makeText(getApplication(), "3333", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(Executors.this, error + "", Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> params = new HashMap<String, String>();

//                String newString = newString.getText().toString();
//                String email = mail.getText().toString();
//                String passwrd = pass.getText().toString();
                Intent intent = new Intent();
                intent.putExtras(getIntent());
                String fnm1 = et1.getText().toString();
                String mdnm1 = et2.getText().toString();
                String srnm1 = et3.getText().toString();
                String ad1 = et4.getText().toString();
                String pst1 = et5.getText().toString();


                String db1 = et6.getText().toString();
                String rl1 = et7.getText().toString();

//                String uid1 = getIntent().getExtras().getString("u_id");
                intent.putExtra("u_id", uid);


                params.put("name", fnm1);
                params.put("mdname", mdnm1);
                params.put("srname", srnm1);
                params.put("adres", ad1);
                params.put("pst-code", pst1);
                params.put("dob", db1);
                params.put("relation", rl1);
                params.put("uid", uid);


                return params;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }


    public void Insert_executor(final String uid) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_executor, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(Executors.this, Guardians.class);
                startActivity(intent);
                //Toast.makeText(getApplication(), "3333", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(Executors.this, error + "", Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> params = new HashMap<String, String>();

//                String newString = newString.getText().toString();
//                String email = mail.getText().toString();
//                String passwrd = pass.getText().toString();
                Intent intent = new Intent();
                intent.putExtras(getIntent());
                String fnm1 = et1.getText().toString();
                String mdnm1 = et2.getText().toString();
                String srnm1 = et3.getText().toString();
                String ad1 = et4.getText().toString();
                String pst1 = et5.getText().toString();


                String db1 = et6.getText().toString();
                String rl1 = et7.getText().toString();

//                String uid1 = getIntent().getExtras().getString("u_id");
                intent.putExtra("u_id", uid);


                params.put("name", fnm1);
                params.put("mdname", mdnm1);
                params.put("srname", srnm1);
                params.put("adres", ad1);
                params.put("pst-code", pst1);
                params.put("dob", db1);
                params.put("relation", rl1);
                params.put("uid", uid);


                return params;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
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
            Intent in=new Intent(Executors.this,logina1.class);
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

    private void Exec_intent_func(final String uid) {

        Intent intent = new Intent(Executors.this, Guardians.class);
        intent.putExtra("grdn_fn", et1.getText().toString());
        intent.putExtra("grdn_md", et2.getText().toString());
        intent.putExtra("grdn_sr", et3.getText().toString());
        intent.putExtra("grdn_ad", et4.getText().toString());
        intent.putExtra("grdn_ps", et5.getText().toString());
        intent.putExtra("grdn_db", et6.getText().toString());
        intent.putExtra("grdn_rel", et7.getText().toString());


        //String uid = getIntent().getExtras().getString("u_id");
        intent.putExtra("u_id", uid);
        intent.putExtras(getIntent());
        Toast.makeText(this, uid, Toast.LENGTH_SHORT).show();


        startActivity(intent);


    }


    public void datepicker(View view) {

        DatePickerFregment fragment = new DatePickerFregment();
        fragment.show(getSupportFragmentManager(), "date");
    }

    private void setDate(final Calendar calendar) {

        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        ((TextView) findViewById(R.id.Ex_t6)).setText(dateFormat.format(calendar.getTime()));
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



}