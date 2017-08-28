package com.example.richard.fms;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order extends AppCompatActivity {

    ListView lsve;
    double amt,qunt,tot,orgqnt;
    ArrayAdapter<String> dataAdapter;
    Context con=this;
    DatabaseHelper  userdb;
    SQLiteDatabase sqldb;
    String phno,uniqueID,orderid,pid,orderdate,qunstr;
    Intent in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);

        //=========================================
        Bundle si=getIntent().getExtras();
        phno=si.getString("");
        //=========================================
        SimpleDateFormat SDF=new SimpleDateFormat("dd/MM/yyyy");
        orderdate=SDF.format(new Date());
        //========Product id autoGenerate==========
        uniqueID = "";
        double d;
        for (int i = 1; i <= 4; i++)
        {
            d = Math.random() * 10;
            uniqueID = uniqueID + ((int)d);
        }
        //==========================================


        lsve = (ListView) findViewById(R.id.listing);
        final List<String> list = new ArrayList<String>();
        userdb=new DatabaseHelper(con);
        sqldb=userdb.getWritableDatabase();
        Cursor cs=userdb.Orderprdview(sqldb);
        if(cs.moveToFirst()){
            do{
                list.add(cs.getString(0));
            }while(cs.moveToNext());
        }
        else{
            list.add("Not Found");
        }
        dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list);
        lsve.setAdapter(dataAdapter);
        lsve.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parant, final View view, int position, long id) {
            final String selected_list = list.get(position);
            final Cursor vReg = userdb.viewPrdbyName(selected_list, sqldb);
            StringBuffer buff = new StringBuffer();
            while (vReg.moveToNext()) {
                buff.append("Product Quantity\t:\t" + vReg.getString(0) + " Kg\n");
                buff.append("Product Price\t:\t" + vReg.getString(1) + " Per/kg\n");
                qunt = Double.parseDouble(vReg.getString(0));
                amt = Double.parseDouble(vReg.getString(1));
                pid=""+vReg.getString(2);
                //Alert Dialog=======================================
                final AlertDialog builder = new AlertDialog.Builder(Order.this).create();
                builder.setCancelable(false);
                builder.setCanceledOnTouchOutside(false);
                builder.setTitle("Order");
                builder.setMessage(buff.toString());
                //==================Icluding layout===========================
                LayoutInflater li = LayoutInflater.from(Order.this);
                final View lay = li.inflate(R.layout.quninput, null);
                builder.setView(lay);
                final EditText qun = (EditText) lay.findViewById(R.id.quantity);
                final TextView qtv = (TextView) lay.findViewById(R.id.total);
                final TextView orgqtv = (TextView) lay.findViewById(R.id.orgqun);
                qun.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void afterTextChanged(Editable e) {
                        double v1 = 0.0;
                        if (!qun.getText().toString().equals(""))
                            v1 = Double.parseDouble("0" + qun.getText().toString());
                        tot = amt * v1;
                        orgqnt = qunt - v1;
                        int len=qun.getText().toString().length();
                        if (qunt==orgqnt)
                            builder.getButton(AlertDialog.BUTTON_NEUTRAL).setEnabled(false);
                        else if (orgqnt < 0) {
                            qun.setFilters(new InputFilter[] {new InputFilter.LengthFilter(len-1)});
                            builder.getButton(AlertDialog.BUTTON_NEUTRAL).setEnabled(false);
                            qun.setError("Should not exists the Quantity");
                        } else {
                            builder.getButton(AlertDialog.BUTTON_NEUTRAL).setEnabled(true);
                            orgqtv.setText("" + orgqnt);
                        }
                        qtv.setText("" + tot);
                    }
                });
                //=====================================
                builder.setButton(AlertDialog.BUTTON_POSITIVE,"Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                builder.setButton(AlertDialog.BUTTON_NEUTRAL,"Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        orderid="ORDERID"+uniqueID;
                        qunstr=qun.getText().toString();
                        String totstr=""+tot;

                        boolean val=userdb.orderprdupdate(selected_list,orgqnt);
                        /*if (val)
                            Toast.makeText(getApplicationContext(), "Product update Successfull", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), "Product update UnSuccessfull", Toast.LENGTH_SHORT).show();*/

                        boolean res=userdb.orderprd(orderid,phno,pid,selected_list,qunstr,totstr,orderdate);
                        if (res) {
                            Toast.makeText(getApplicationContext(), "Order Successfull", Toast.LENGTH_SHORT).show();
                            Intent intent = getIntent();
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Order UnSuccessfull", Toast.LENGTH_SHORT).show();
                        //Intent intent = getIntent();
                        //startActivity(intent);
                    }
                });
                builder.show();
                builder.getButton(AlertDialog.BUTTON_NEUTRAL).setEnabled(false);
            }
            //userdb.close();
            }
        });
    }
}