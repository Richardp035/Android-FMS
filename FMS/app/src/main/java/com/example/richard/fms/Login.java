package com.example.richard.fms;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    DatabaseHelper dh=new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    public void LoginClick(View v){

        if(v.getId()==R.id.loginbtn)
        {
            EditText lphno=(EditText)findViewById(R.id.l_phno);
            String lphnostr=lphno.getText().toString().trim();
            EditText lpass=(EditText)findViewById(R.id.l_pass);
            String lpassstr=lpass.getText().toString().trim();
            if(lphnostr.equals(""))
                lphno.setError("Login Phone Number Required");
            else if((!lphnostr.matches("[789][0-9]{9}"))||(lphnostr.length()<10))
                lphno.setError("Login Phone Number Should Contain Minimium 10 Digit (or) Incorrect Format");
            else if(lpassstr.equals(""))
                lpass.setError("Password Required");

            else {
                String type = dh.loginsearch(lphnostr,lpassstr);
                if(lphnostr.equals("9940630028") && lpassstr.equals("richard"))
                {
                    Toast.makeText(getApplicationContext(), "Administrator success ",Toast.LENGTH_SHORT).show();
                    Intent show=new Intent(this,adminView.class);
                    startActivity(show);
                    finish();
                }
                else if(type.equals("user")) {
                    Toast.makeText(getApplicationContext(), " User success ",Toast.LENGTH_SHORT).show();
                    Intent phno=new Intent(getBaseContext(),userView.class);
                    phno.putExtra("",lphnostr);
                    startActivity(phno);
                    finish();
                }
                else if(type.equals("admin")) {
                    Toast.makeText(getApplicationContext(), "Administrator success ",Toast.LENGTH_SHORT).show();
                    Intent show=new Intent(this,adminView.class);
                    startActivity(show);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Failed ",Toast.LENGTH_SHORT).show();
                }
            }
        }
        if(v.getId()==R.id.registerbtn)
        {
            Intent reg=new Intent(this,UserRegister.class);
            startActivity(reg);
            finish();
        }
    }
}
