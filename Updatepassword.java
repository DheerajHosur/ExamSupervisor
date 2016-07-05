package com.example.v7.examinarsallotment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.HashMap;

public class Updatepassword extends AppCompatActivity implements View.OnClickListener ,AsyncResponse {

    private EditText currernt ,newpass,confirm;


    private Button buttonUpdate;

    private String type,code,name,desg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatepassword);

        Intent intent = getIntent();

        type = intent.getStringExtra("type");
        if(type.equals("s"))
            code="Faculty";
        else if(type.equals("p"))
            code="Prinicipal";
        else if(type.equals("c"))
            code="Clerk";
        else if(type.equals("h"))
            code="HOD";
        currernt = (EditText) findViewById(R.id.editText3);
        newpass = (EditText) findViewById(R.id.editText4);
        confirm = (EditText) findViewById(R.id.editText5);
        name = currernt.getText().toString().trim();
        desg =newpass.getText().toString().trim();

        buttonUpdate = (Button) findViewById(R.id.button2);

        buttonUpdate.setOnClickListener(this);


        // currernt.setText(id);

        //getEmployee();
    }



    private void updateEmployee(){
        final String name = currernt.getText().toString().trim();
        final String desg =newpass.getText().toString().trim();
        // final String conf=confirm.getText().toString().trim();

        class UpdateEmployee extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Updatepassword.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(Updatepassword.this, s, Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Config.KEY_EMP_current,name);
                hashMap.put(Config.KEY_EMP_NAME,desg);
                hashMap.put("id",code);


                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Config.URL_UPDATE_pass,hashMap);

                return s;
            }
        }

        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();
    }



    @Override
    public void onClick(View v) {

        if(v == buttonUpdate){
            if(!(newpass.getText().toString().equals(""))&&!(confirm.getText().toString().equals(""))){
                if(newpass.getText().toString().equals(confirm.getText().toString())){
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put(Config.KEY_EMP_current,name);
                    hashMap.put(Config.KEY_EMP_NAME,desg);
                    hashMap.put("id",code);


                    Toast.makeText(getApplicationContext(),hashMap.toString(),Toast.LENGTH_LONG).show();
                    PostResponseAsyncTask loginTask =
                            new PostResponseAsyncTask(Updatepassword.this, hashMap);
                    loginTask.execute(Config.URL_UPDATE_pass);}
                else {
                    Toast.makeText(getApplicationContext(),"ConfirmPassword Didn't Match!!",Toast.LENGTH_LONG).show();
                }}
            else {
                Toast.makeText(getApplicationContext(),"Fill all Fields",Toast.LENGTH_LONG).show();
            }

        }
    }
    @Override
    public void processFinish(String output)
    {
        Toast.makeText(getApplicationContext(),output, Toast.LENGTH_LONG).show();
        if(output.equals("LoginOK")){
            if(confirm.getText().toString().equals(newpass.getText().toString())){
                Toast.makeText(getApplicationContext(),"Password Updated!", Toast.LENGTH_LONG).show();
                // updateEmployee();}
            }
            else{
                Toast.makeText(getApplicationContext(),"Enter your Current Password Correctly!!", Toast.LENGTH_LONG).show();
            }
        }
    }}
