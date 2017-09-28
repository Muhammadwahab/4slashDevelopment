package com.example.smart_will;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

public class Guardians extends AppCompatActivity  {
    private TextView mDisplayDate,showdate1;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

//        public void reg(View view) {
//            Intent intern = new Intent(this, Gifts_and_legacy.class);
//            startActivity(intern);
//        }
String url_guardians ="http://128.199.50.69/guardians";

    SessionManager session;
     EditText grdn1,grdn2,grdn3,grdn4,grdn5,grdn7;
     TextView grdn6;
     Button Grd_skip,guar_add;
    ImageButton but;
     Button btn_Executor;
static int count1;
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
            setContentView(R.layout.activity_guardians);
         // Find the toolbar view inside the activity layout
         Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
         // Sets the Toolbar to act as the ActionBar for this Activity window.
         // Make sure the toolbar exists in the activity and is not null
         setSupportActionBar(toolbar);
         getSupportActionBar().setDisplayShowTitleEnabled(false);
         session = new SessionManager(getApplicationContext());
         HashMap<String, String> user = session.getUserDetails();
         final String uid = user.get(SessionManager.KEY_NAME);
         //--Radiogroup--//
         mDisplayDate = (TextView) findViewById(R.id.picdate);
         showdate1 = (TextView) findViewById(R.id.showdate_guar);

         mDisplayDate.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Calendar cal = Calendar.getInstance();
                 int year = cal.get(Calendar.YEAR);
                 int month = cal.get(Calendar.MONTH);
                 int day = cal.get(Calendar.DAY_OF_MONTH);

                 DatePickerDialog dialog = new DatePickerDialog(
                         Guardians.this,
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

         SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
         SharedPreferences.Editor editor = sp.edit();
         editor.putInt("your_int_key", count1);
         editor.commit();



         grdn1 = (EditText)findViewById(R.id.Grd1);
         grdn2 = (EditText)findViewById(R.id.Grd2);
         grdn3 = (EditText)findViewById(R.id.Grd3);
         grdn4 = (EditText)findViewById(R.id.Grd4);
         grdn5 = (EditText)findViewById(R.id.Grd5);
         grdn6 = (TextView)findViewById(R.id.showdate_guar);
         grdn7 = (EditText)findViewById(R.id.Grd7);
         //Toast.makeText(this, getIntent().getExtras().getString("u_id") , Toast.LENGTH_SHORT).show();

         Grd_skip=(Button)findViewById(R.id.grdSkip);
         guar_add=(Button)findViewById(R.id.grdGrdn);

         but=(ImageButton)findViewById(R.id.back);
         but.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(Guardians.this,Executors.class));
             }
         });
         Grd_skip.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 Intent intent = new Intent(Guardians.this, Gifts_and_legacy.class);
                 startActivity(intent);

             }
         });


         guar_add.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 SharedPreferences sp1 = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
                 int myIntValue = sp1.getInt("your_int_key", -1);
                 count1++;
                 String a=Integer.toString(myIntValue);

                 if(grdn1.length()==0)
                 {
                     grdn1.requestFocus();
                     grdn1.setError("Please enter first name");
                 }

                 else if(grdn3.length()==0)
                 {
                     grdn3.requestFocus();
                     grdn3.setError("Please enter surname");
                 }
                 else if(grdn4.length()==0)
                 {
                     grdn4.requestFocus();
                     grdn4.setError("Please enter address");
                 }
                 else if(grdn5.length()==0)
                 {
                     grdn5.requestFocus();
                     grdn5.setError("Please enter Post Code");
                 }
                 else if(grdn6.length()==0)
                 {
                     grdn6.requestFocus();
                     grdn6.setError("Please Select date");
                 }
                 else if(grdn7.length()==0)
                 {
                     grdn7.requestFocus();
                     grdn7.setError("Please enter Your Relation");
                 }

//                else if(rg.getCheckedRadioButtonId()==0)
//                {
//                    rg.requestFocus();
//                    Toast.makeText(testator_details.this, "please select male Female", Toast.LENGTH_SHORT).show();
//                }
                 else if(a.equals("1"))
                 {
                     //Toast.makeText(Guardians.this, "You can't add more than 2 Guardians" , Toast.LENGTH_SHORT).show();
                     Insert_guardians(uid);
                     Intent i = new Intent(Guardians.this, Gifts_and_legacy.class);
                     startActivity(i);

                 }
                 else
                 {
                     Insert_guardians(uid);
                     //guardiansData(uid);
                     Intent i = new Intent(Guardians.this, Guardians.class);
                     startActivity(i);
                 }

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
            Intent in=new Intent(Guardians.this,logina1.class);
            startActivity(in);
            return(true);

    }
        return(super.onOptionsItemSelected(item));
    }


    private void guardiansData(final String uid){

            Intent intent = new Intent(this, Gifts_and_legacy.class);
            intent.putExtra("gar1",grdn1.getText().toString());
            intent.putExtra("gar2",grdn2.getText().toString());
            intent.putExtra("gar3",grdn3.getText().toString());
            intent.putExtra("gar4",grdn4.getText().toString());
            intent.putExtra("gar5",grdn5.getText().toString());
            intent.putExtra("gar6",grdn6.getText().toString());
            intent.putExtra("gar7",grdn7.getText().toString());

           // String uid = getIntent().getExtras().getString("u_id");
            intent.putExtra("u_id",uid);


            intent.putExtras(getIntent());
            startActivity(intent);

        }
    public void Insert_guardians(final String uid){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_guardians, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                //Toast.makeText(getApplication(), "44444", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(Guardians.this, error + "", Toast.LENGTH_SHORT).show();

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
                String gr1 =grdn1.getText().toString();
                String gr2=grdn2.getText().toString();
                String gr3 = grdn3.getText().toString();
                String gr4 = grdn4.getText().toString();
                String gr5 = grdn5.getText().toString();


                String gr6 =grdn6.getText().toString();
                String gr7 = grdn7.getText().toString();


                intent.putExtra("u_id",uid);


                params.put("name", gr1);
                params.put("mdname", gr2);
                params.put("srname", gr3);
                params.put("adres", gr4);
                params.put("pst-code", gr5);
                params.put("dob", gr6);
                params.put("relation", gr7);
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


        public void datepicker(View view) {

            com.example.smart_will.Executors.DatePickerFregment fragment = new com.example.smart_will.Executors.DatePickerFregment();
            fragment.show(getSupportFragmentManager(), "date");
        }

        private void setDate(final Calendar calendar) {

            final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
            ((TextView) findViewById(R.id.Grd6)).setText(dateFormat.format(calendar.getTime()));
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
