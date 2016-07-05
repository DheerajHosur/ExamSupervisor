package com.example.v7.examinarsallotment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.HashMap;

public class AddStaff extends AppCompatActivity  implements AsyncResponse {
    private Button  button2, button3, button4;
    public EditText staffID,staffname,phno;
    Context context=this;
    private Spinner staff;
    Integer flag;
    //private TextView resultText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_staff);
        staffID = (EditText)findViewById(R.id.StaffID);
        staffname = (EditText)findViewById(R.id.StaffName);
        phno = (EditText)findViewById(R.id.phNo);
        staff=(Spinner)findViewById(R.id.stafftype);
//final String str=staffID.getText().toString().trim();
        // components from main.xml
        //resultText = (TextView) findViewById(R.id.result)
        staff.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    flag = 1;

                } else if (position == 2) {
                    flag = 2;

                } else if (position == 3) {
                    flag = 3;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button3=(Button)findViewById(R.id.submit);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String str=staffID.getText().toString().trim();
                final String str1=staffname.getText().toString().trim();
                final String str2=phno.getText().toString().trim();
                if(str.equals("")||str1.equals("")||str2.equals("")||flag==0){
                    Toast.makeText(getApplicationContext(),"Enter all details", Toast.LENGTH_LONG).show();

            }
                else{
                    add();
                }}
        });
        button4=(Button)findViewById(R.id.more);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                staffID.setText("");
                staffname.setText("");
                phno.setText("");
                staff.setSelection(0);
            }
        });

    }

    @Override
    public void processFinish(String output)
    {
        Toast.makeText(this, output, Toast.LENGTH_LONG).show();
        if(output.equals("New staff added successfully"))
        {   Toast.makeText(this, "Staff added successfully", Toast.LENGTH_LONG).show();
            String messageText = "Welcome to Exam duty!! /n Your Login Details: UserID :"+ staffID.getText().toString()+" Password :123." ;
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phno.getText().toString(), null, messageText + " NOTE: Do not forget to Change you Password!!", null, null);
            //Toast.makeText(getApplicationContext(), "Notification Sent", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this, "Couldn't Add Staff", Toast.LENGTH_LONG).show();

            //Intent i=new Intent(getApplicationContext(),Sample.class);
            //startActivity(i);
        }
    }
    public void add(){
        HashMap postData = new HashMap();
        postData.put("button3", "Login");
        postData.put("mobile", "android");
        postData.put("username", staffname.getText().toString());
        postData.put("staffid", staffID.getText().toString());
        postData.put("stafftype",staff.getSelectedItem().toString());
        postData.put("phoneno", phno.getText().toString());
        PostResponseAsyncTask loginTask = new PostResponseAsyncTask(AddStaff.this, postData);
        loginTask.execute(Config.URL_ADD);
    }
}

