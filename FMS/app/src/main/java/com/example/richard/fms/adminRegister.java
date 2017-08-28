package com.example.richard.fms;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;

public class adminRegister extends AppCompatActivity {

    EditText dob;
    AutoCompleteTextView city,state;
    DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_register);
        dob = (EditText) findViewById(R.id.u_dob);
        dob.setFocusable(false);
        dob.setClickable(true);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int yy=cal.get(Calendar.YEAR);
                int mm=cal.get(Calendar.MONTH);
                int dd=cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd=new DatePickerDialog(adminRegister.this, mDateSetListener,yy,mm,dd);
                dpd.show();
                dpd.getDatePicker().setMaxDate(cal.getTimeInMillis());
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                if(year>2010){
                    dob.setError("Date of birth Year cannot be above 2010");
                    dob.setFocusableInTouchMode(true);
                    dob.setCursorVisible(false);
                }
                else{
                    dob.setError(null);
                    dob.setFocusable(false);
                }
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                dob.setText(date);
            }
        };
        city = (AutoCompleteTextView) findViewById(R.id.u_city);
        state = (AutoCompleteTextView) findViewById(R.id.u_state);

        //==================Auto text for city=====================================
        String[] ct={"Chennai","Coimbatore","Madurai","Tiruchirappalli","Tiruppur","Salem","Erode","Vellore","Tirunelveli","Thoothukkudi",
                "Nagercoil","Thanjavur","Dindigul","Cuddalore","Kanchipuram","Tiruvannamalai","Kumbakonam","Rajapalayam","Pudukottai",
                "Hosur","Ambur","Karaikkudi","Neyveli","Nagapattinam",};
        ArrayAdapter<String> adpct=new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,ct);
        city.setThreshold(1);
        city.setAdapter(adpct);
        //========================================================

        //==================Auto text for state=====================================
        String[] sat={"Tamil Nadu"};
        ArrayAdapter<String>adpsat=new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,sat);
        state.setThreshold(1);
        state.setAdapter(adpsat);
        //========================================================
    }
    DatabaseHelper dh=new DatabaseHelper(this);

    public void ToRegister(View v) {

        EditText name = (EditText) findViewById(R.id.u_name);
        EditText phno = (EditText) findViewById(R.id.u_phno);
        EditText address = (EditText) findViewById(R.id.u_address);
        EditText street = (EditText) findViewById(R.id.u_street);
        city = (AutoCompleteTextView) findViewById(R.id.u_city);
        state = (AutoCompleteTextView) findViewById(R.id.u_state);
        EditText zipcode = (EditText) findViewById(R.id.u_zipcode);
        EditText pass = (EditText) findViewById(R.id.u_password);
        EditText cpass = (EditText) findViewById(R.id.u_cpassword);
        RadioGroup gender = (RadioGroup) findViewById(R.id.u_gender);


        String namestr = name.getText().toString().toUpperCase();
        String phnostr = phno.getText().toString().trim();
        String dobstr= dob.getText().toString();
        String addressstr = address.getText().toString().trim();
        String streetstr = street.getText().toString().toUpperCase();
        String citystr = city.getText().toString().toUpperCase();
        String statestr = state.getText().toString().toUpperCase();
        String zipcodestr = zipcode.getText().toString().trim();
        String passstr = pass.getText().toString().trim();
        String cpassstr = cpass.getText().toString().trim();
        String genderstr;
        if(namestr.equals(""))
            name.setError("Name Required");
        else  if(phnostr.equals(""))
            phno.setError("Phone Number Required");
        else if((!phnostr.matches("[789][0-9]{9}"))||(phnostr.length()<10))
            phno.setError("Incorrect Phone Number Format");
        else if(dob.getError()!=null)
            Toast.makeText(this,"Date of birth Year cannot be bellow 2010", Toast.LENGTH_SHORT).show();
        else if(addressstr.equals(""))
            address.setError("Address Required");
        else if(streetstr.equals(""))
            street.setError("Street Required");
        else if(citystr.equals(""))
            city.setError("City Required");
        else if(statestr.equals(""))
            state.setError("State Required");
        else if(zipcodestr.equals(""))
            zipcode.setError("Zip Code Required");
        else if(passstr.equals(""))
            pass.setError("Password Required");
        else if(passstr.length()<6)
            pass.setError("Password length must be moe than 6 character");
        else if(cpassstr.equals(""))
            cpass.setError("Confirm Password Required");
        else if(cpassstr.length()<6)
            cpass.setError("Confirm Password length must be moe than 6 character");
        else if(gender.getCheckedRadioButtonId()==-1)
            Toast.makeText(this, "Gender Required", Toast.LENGTH_SHORT).show();
        else {
            genderstr = ((RadioButton) findViewById(gender.getCheckedRadioButtonId())).getText().toString();
            if (!passstr.equals(cpassstr))
                Toast.makeText(this, "Passwords Do Not Match", Toast.LENGTH_SHORT).show();
            else {
                boolean val = dh.insertadmin(namestr,phnostr,dobstr,addressstr,streetstr,citystr,statestr,zipcodestr,
                        genderstr,passstr);
                if (val) {
                    Toast.makeText(this, "Inserted Successfull", Toast.LENGTH_SHORT).show();
                    Intent show=new Intent(this,adminView.class);
                    startActivity(show);
                    finish();
                }
                else
                    Toast.makeText(this, "Insertion UnSuccessfull", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
