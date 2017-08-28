package com.example.richard.fms;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class viewProduct extends AppCompatActivity {

    ListView lsve;
    ArrayAdapter<String> dataAdapter;
    Context con=this;
    DatabaseHelper  userdb;
    SQLiteDatabase sqldb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_product);

        lsve = (ListView) findViewById(R.id.listing);
        final List<String> list = new ArrayList<String>();
        userdb=new DatabaseHelper(con);
        sqldb=userdb.getWritableDatabase();
        Cursor cs=userdb.prdview(sqldb);
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
            public void onItemClick(AdapterView<?> parant, final View view, int position, long id){
                final String selected_list=list.get(position);
                Cursor  vReg=userdb.viewPrd(selected_list, sqldb);
                StringBuffer buff=new StringBuffer();
                while(vReg.moveToNext()){
                    buff.append("Product Id\t:\t"+vReg.getString(0)+"\n");
                    buff.append("Product Name\t:\t"+vReg.getString(1)+"\n");
                    buff.append("Shop Id\t:\t"+vReg.getString(2)+"\n");
                    buff.append("Product Type\t:\t"+vReg.getString(3)+"\n");
                    buff.append("Product Quantity\t:\t"+vReg.getString(4)+"\n");
                    buff.append("Product Price\t:\t"+vReg.getString(5)+"\n");
                    buff.append("Product Added Date\t:\t"+vReg.getString(6)+"\n");

                    //Alert Dialog
                    final AlertDialog.Builder builder=new AlertDialog.Builder(viewProduct.this);
                    builder.setCancelable(true);
                    builder.setTitle("Product Details");
                    builder.setMessage(buff.toString());
                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            boolean ret=userdb.deleteregistered(selected_list);
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                            if(ret==false)
                                Toast.makeText(getApplicationContext(),"Deleting Failed",Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getApplicationContext(),"Deleting Success",Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //AlertDialog.Builder update=new AlertDialog.Builder(viewRegistered.this);
                            //View upview=getLayoutInflater().inflate(R.layout.update_register,null);
                            Intent pnme=new Intent(getApplicationContext(),updateProduct.class);
                            pnme.putExtra("",selected_list);
                            startActivity(pnme);
                            finish();
                        }
                    });
                    builder.show();
                }
                userdb.close();
            }
        });
    }
}
