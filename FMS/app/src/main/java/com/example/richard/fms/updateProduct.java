package com.example.richard.fms;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class updateProduct extends AppCompatActivity {

    EditText pid,pname,pquantity,pprice,padddate,location,shopid;
    AutoCompleteTextView ptype;
    String pnme;
    DatabaseHelper dh=new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_product);

        //===============================================================
        Bundle si=getIntent().getExtras();
        pnme=si.getString("");
        //===============================================================


        pid=(EditText) findViewById(R.id.prd_id);
        pname=(EditText) findViewById(R.id.prd_name);
        location=(EditText) findViewById(R.id.location);
        shopid=(EditText) findViewById(R.id.shop_id);
        ptype=(AutoCompleteTextView) findViewById(R.id.prd_type);
        pquantity=(EditText) findViewById(R.id.prd_quantity);
        pprice=(EditText) findViewById(R.id.prd_price);
        padddate=(EditText) findViewById(R.id.prd_adddate);
        //==================Auto text for state=====================================
        String[] type={"Seed","Fertilizer"};
        ArrayAdapter<String> adptype=new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,type);
        ptype.setThreshold(1);
        ptype.setAdapter(adptype);
        //========================================================


        Cursor uedit=dh.updatePrdEdit(pnme);
        while(uedit.moveToNext()) {
            pid.append(uedit.getString(0).toString());
            pname.append(uedit.getString(1).toString());
            location.append(uedit.getString(2).toString());
            shopid.append(uedit.getString(3).toString());
            ptype.append(uedit.getString(4).toString());
            pquantity.append(uedit.getString(5).toString());
            pprice.append(uedit.getString(6).toString());
            padddate.append(uedit.getString(7).toString());
        }
        pid.setEnabled(false);
        pname.setEnabled(false);
        location.setEnabled(false);
        shopid.setEnabled(false);
        padddate.setEnabled(false);
    }

    public void addProd(View v)
    {
        pid=(EditText) findViewById(R.id.prd_id);
        pname=(EditText) findViewById(R.id.prd_name);
        location=(EditText) findViewById(R.id.location);
        shopid=(EditText) findViewById(R.id.shop_id);
        ptype=(AutoCompleteTextView) findViewById(R.id.prd_type);
        pquantity=(EditText) findViewById(R.id.prd_quantity);
        pprice=(EditText) findViewById(R.id.prd_price);
        padddate=(EditText) findViewById(R.id.prd_adddate);

        String ptypestr=ptype.getText().toString();
        String pquantitystr=pquantity.getText().toString();
        String ppricestr=pprice.getText().toString();


        boolean res=dh.UpdateProduct(pnme,ptypestr,pquantitystr,ppricestr);
        if (res) {
            Toast.makeText(this, "Updated Successfull", Toast.LENGTH_SHORT).show();
            Intent adminview=new Intent(this,viewProduct.class);
            startActivity(adminview);
            finish();
        }
        else
            Toast.makeText(this, "Updated UnSuccessfull", Toast.LENGTH_SHORT).show();
    }
}
