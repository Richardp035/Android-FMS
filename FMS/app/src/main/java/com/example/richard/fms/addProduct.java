package com.example.richard.fms;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class addProduct extends AppCompatActivity {

    EditText pid,pname,pquantity,pprice,padddate;
    AutoCompleteTextView ptype;
    String uniqueID;
    Spinner location,shopid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);

        pid=(EditText) findViewById(R.id.prd_id);
        ptype=(AutoCompleteTextView) findViewById(R.id.prd_type);
        //==================Auto text for state=====================================
        String[] type={"Seed","Fertilizer"};
        ArrayAdapter<String> adptype=new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,type);
        ptype.setThreshold(1);
        ptype.setAdapter(adptype);
        //========================================================

        //========Product id autoGenerate==========
        uniqueID = "";
        double d;
        for (int i = 1; i <= 4; i++)
        {
            d = Math.random() * 10;
            uniqueID = uniqueID + ((int)d);
        }
        //==========================================
        pid.setText("PDRID"+uniqueID);
        pid.setEnabled(false);
        //==========================================

        //==========================================
        padddate = (EditText) findViewById(R.id.prd_adddate);
        padddate.setEnabled(false);
        SimpleDateFormat SDF=new SimpleDateFormat("dd/MM/yyyy");
        padddate.setText(SDF.format(new Date()));
        //==================================================

        //=======================Spinner=====================
        location=(Spinner)findViewById(R.id.location);
        shopid=(Spinner)findViewById(R.id.shop_id);
        ArrayAdapter adploc = ArrayAdapter.createFromResource(this, R.array.location, android.R.layout.simple_spinner_item);
        adploc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location.setAdapter(adploc);
        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){
                    shopid.setEnabled(false);
                }else if(i==1){
                    shopid.setEnabled(true);
                    ArrayAdapter adpshop = ArrayAdapter.createFromResource(getApplicationContext(), R.array.chennai, android.R.layout.simple_spinner_item);
                    adpshop.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    shopid.setAdapter(adpshop);
                }else if(i==2){
                    shopid.setEnabled(true);
                    ArrayAdapter adpshop = ArrayAdapter.createFromResource(getApplicationContext(), R.array.coimbatore, android.R.layout.simple_spinner_item);
                    adpshop.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    shopid.setAdapter(adpshop);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //===================================================
    }

    DatabaseHelper pdh=new DatabaseHelper(this);

    public void addProd(View v)
    {
        pid=(EditText) findViewById(R.id.prd_id);
        pname=(EditText) findViewById(R.id.prd_name);
        location=(Spinner) findViewById(R.id.location);
        shopid=(Spinner) findViewById(R.id.shop_id);
        ptype=(AutoCompleteTextView) findViewById(R.id.prd_type);
        pquantity=(EditText) findViewById(R.id.prd_quantity);
        pprice=(EditText) findViewById(R.id.prd_price);
        padddate=(EditText) findViewById(R.id.prd_adddate);

        String pidstr=pid.getText().toString();;
        String pnamestr=pname.getText().toString();
        String locationstr=location.getSelectedItem().toString();
        String shopidstr=shopid.getSelectedItem().toString();
        String ptypestr=ptype.getText().toString();
        String pquantitystr=pquantity.getText().toString();
        String ppricestr=pprice.getText().toString();
        String padddatestr=padddate.getText().toString();

        if(pnamestr.equals(""))
            pname.setError("Product Name Needed");
        //else if(shopidstr.equals(""))
            //shopid.setError("");
        else if(ptypestr.equals(""))
            ptype.setError("");
        else if(pquantitystr.equals(""))
            pquantity.setError("");
        else if(ppricestr.equals(""))
            pprice.setError("");
        else
        {
            boolean res=pdh.insertProduct(pidstr,pnamestr,locationstr,shopidstr,ptypestr,pquantitystr,ppricestr,padddatestr);
            if (res) {
                Toast.makeText(this, "Inserted Successfull", Toast.LENGTH_SHORT).show();
                Intent adminview=new Intent(this,adminView.class);
                startActivity(adminview);
                finish();
            }
            else
                Toast.makeText(this, "Insertion UnSuccessfull", Toast.LENGTH_SHORT).show();
        }
    }
}
