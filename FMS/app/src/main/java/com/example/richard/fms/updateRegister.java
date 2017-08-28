package com.example.richard.fms;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class updateRegister extends AppCompatActivity {

    EditText dob,name,address,street,zipcode,pass;
    AutoCompleteTextView type,gender,state,city;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    String phno;
    DatabaseHelper dh=new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_register);

        //========================================

        dob = (EditText) findViewById(R.id.u_dob);
        name = (EditText) findViewById(R.id.u_name);
        address = (EditText) findViewById(R.id.u_address);
        street = (EditText) findViewById(R.id.u_street);
        city = (AutoCompleteTextView) findViewById(R.id.u_city);
        state = (AutoCompleteTextView) findViewById(R.id.u_state);
        zipcode = (EditText) findViewById(R.id.u_zipcode);
        pass = (EditText) findViewById(R.id.u_password);
        type=(AutoCompleteTextView) findViewById(R.id.u_type);
        gender=(AutoCompleteTextView) findViewById(R.id.u_gender);

        //==================Auto text for gender=====================================
        String[] gen={"Male","Female","Others"};
        ArrayAdapter<String>adpgen=new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,gen);
        gender.setThreshold(1);
        gender.setAdapter(adpgen);
        //========================================================

        //==================Auto text for type=====================================
        String[] tp={"admin","user"};
        ArrayAdapter<String>adptp=new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,tp);
        type.setThreshold(1);
        type.setAdapter(adptp);
        //========================================================

        //==================Auto text for city=====================================
        String[] ct={"Chennai","Coimbatore","Madurai","Tiruchirappalli","Tiruppur","Salem","Erode","Vellore","Tirunelveli","Thoothukkudi",
                "Nagercoil","Thanjavur","Dindigul","Cuddalore","Kanchipuram","Tiruvannamalai","Kumbakonam","Rajapalayam","Pudukottai",
                "Hosur","Ambur","Karaikkudi","Neyveli","Nagapattinam",};
        ArrayAdapter<String>adpct=new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,ct);
        city.setThreshold(1);
        city.setAdapter(adpct);
        //========================================================

        //==================Auto text for state=====================================
        String[] sat={"Tamil Nadu"};
        ArrayAdapter<String>adpsat=new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,sat);
        state.setThreshold(1);
        state.setAdapter(adpsat);
        //========================================================


        dob.setFocusable(false);
        dob.setClickable(true);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int yy=cal.get(Calendar.YEAR);
                int mm=cal.get(Calendar.MONTH);
                int dd=cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd=new DatePickerDialog(updateRegister.this, mDateSetListener,yy,mm,dd);
                dpd.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                dob.setText(date);
            }
        };
        //=====================

        Bundle si=getIntent().getExtras();
        phno=si.getString("");

        //=======================

        Cursor uedit=dh.updateedit(phno);
        while(uedit.moveToNext()) {
            name.append(uedit.getString(0).toString());
            dob.append(uedit.getString(1).toString());
            address.append(uedit.getString(2).toString());
            street.append(uedit.getString(3).toString());
            city.append(uedit.getString(4).toString());
            state.append(uedit.getString(5).toString());
            zipcode.append(uedit.getString(6).toString());
            gender.append(uedit.getString(7).toString());
            pass.append(uedit.getString(8).toString());
            type.append(uedit.getString(9).toString());
        }

        //===============================
    }

    public void toUpdate(View v)
    {
        name = (EditText) findViewById(R.id.u_name);
        address = (EditText) findViewById(R.id.u_address);
        street = (EditText) findViewById(R.id.u_street);
        city = (AutoCompleteTextView) findViewById(R.id.u_city);
        state = (AutoCompleteTextView) findViewById(R.id.u_state);
        zipcode = (EditText) findViewById(R.id.u_zipcode);
        pass = (EditText) findViewById(R.id.u_password);
        type=(AutoCompleteTextView) findViewById(R.id.u_type);
        gender =(AutoCompleteTextView) findViewById(R.id.u_gender);

        String namestr = name.getText().toString();
        String dobstr= dob.getText().toString();
        String addressstr = address.getText().toString();
        String streetstr = street.getText().toString();
        String citystr = city.getText().toString();
        String statestr = state.getText().toString();
        String zipcodestr = zipcode.getText().toString().trim();
        String passstr = pass.getText().toString().trim();
        String typestr=type.getText().toString();
        String genderstr=gender.getText().toString();

        boolean val = dh.updateRegister(phno, namestr, dobstr, addressstr, streetstr, citystr, statestr, zipcodestr,
                genderstr, passstr, typestr);

        if (val) {
            Toast.makeText(this, "Update Successfull", Toast.LENGTH_SHORT).show();
            Intent show = new Intent(this, viewRegistered.class);
            startActivity(show);
            finish();
        }
        else
            Toast.makeText(this, "Update UnSuccessfull", Toast.LENGTH_SHORT).show();
    }
}
