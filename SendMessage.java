package com.example.v7.examinarsallotment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SendMessage extends AppCompatActivity implements View.OnClickListener {

    public EditText editTextId;
    private EditText editTextName;
    private EditText num;
    private String PhoneNo;
public TextView Message;
    private Button buttonSend;
    private Button buttonDelete;

    public String name,hid,date1,time1,hid1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        Intent intent = getIntent();

         name= intent.getStringExtra("name");
        hid= intent.getStringExtra("hid");
        date1= intent.getStringExtra("date1");
        time1= intent.getStringExtra("time1");
        Integer i=Integer.parseInt(hid);
        i=i+1;
       hid1=Integer.toString(i);
        editTextId = (EditText) findViewById(R.id.editTextId);
        editTextName = (EditText) findViewById(R.id.editTextName);
        Message=(TextView)findViewById(R.id.textView9);
        num = (EditText) findViewById(R.id.num);


        buttonSend = (Button) findViewById(R.id.button7);
        //buttonDelete = (Button) findViewById(R.id.buttonDelete);

        buttonSend.setOnClickListener(this);
       // buttonDelete.setOnClickListener(this);

       //editTextId.setText(id);

        getEmployee();
    }

    private void getEmployee(){
        class GetEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SendMessage.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showEmployee(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_Phone,name);
                return s;
            }
        }
        GetEmployee ge = new GetEmployee();
        ge.execute();
    }

    private void showEmployee(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String desg = c.getString("phone");
            PhoneNo=desg;
            //Message.setText("Hi Hello");
            Message.setText("Dear Sir/Madam,"+
                    "You Prof. "+name+", has been allotted for Supervision Duty to Block No: "+hid1+
                    ", on Date: "+date1+
                    ", at Time : "+time1+
                    ", ThankYou!!");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    @Override
    public void onClick(View v) {
        if(v == buttonSend){
            String messageText = Message.getText().toString();
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(PhoneNo, null, messageText, null, null);
                Toast.makeText(getApplicationContext(), "Message Sent"+PhoneNo, Toast.LENGTH_LONG).show();

           /* SmsManager sm = SmsManager.getDefault();
            sm.sendTextMessage(PhoneNo, null, messageText, null, null);
            Toast.makeText(getApplicationContext(),"Message sent to :"+PhoneNo,Toast.LENGTH_LONG).show();*/
        }

        if(v == buttonDelete){
            //confirmDeleteEmployee();
        }
    }
}
