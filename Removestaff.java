package com.example.v7.examinarsallotment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Removestaff extends AppCompatActivity implements ListView.OnItemClickListener,View.OnClickListener {

private ListView listView;
private Button buttonDelete;
private String JSON_STRING;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_removestaff);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        buttonDelete = (Button) findViewById(R.id.delete_all);


        buttonDelete.setOnClickListener(this);
        getJSON();
        }


        private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
        jsonObject = new JSONObject(JSON_STRING);
        JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

        for(int i = 0; i<result.length(); i++){
        JSONObject jo = result.getJSONObject(i);
        String id = jo.getString(Config.TAG_ID);
        String name = jo.getString(Config.TAG_NAME);

        HashMap<String,String> employees = new HashMap<>();
        employees.put(Config.TAG_ID,id);
        employees.put(Config.TAG_NAME,name);
        list.add(employees);
        }

        } catch (JSONException e) {
        e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
        Removestaff.this, list, R.layout.list_item,
        new String[]{Config.TAG_ID,Config.TAG_NAME},
        new int[]{R.id.id, R.id.name});

        listView.setAdapter(adapter);
        }

private void getJSON(){
class GetJSON extends AsyncTask<Void,Void,String> {

    ProgressDialog loading;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loading = ProgressDialog.show(Removestaff.this,"Fetching Data","Wait...",false,false);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        loading.dismiss();
        JSON_STRING = s;
        showEmployee();
    }

    @Override
    protected String doInBackground(Void... params) {
        RequestHandler rh = new RequestHandler();
        String s = rh.sendGetRequest(Config.URL_GET_ALL);
        return s;
    }
}
GetJSON gj = new GetJSON();
gj.execute();
        }

@Override
public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, SelectedStaff.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String empId = map.get(Config.TAG_ID).toString();
        intent.putExtra(Config.EMP_ID, empId);
        startActivity(intent);
        }
private void deleteEmployee(){
class DeleteEmployee extends AsyncTask<Void,Void,String> {
    ProgressDialog loading;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loading = ProgressDialog.show(Removestaff.this, "Updating...", "Wait...", false, false);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        loading.dismiss();
        Toast.makeText(Removestaff.this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    protected String doInBackground(Void... params) {
        RequestHandler rh = new RequestHandler();
        String s = rh.sendGetRequest(Config.URL_DELETE_ALL);
        return s;
    }
}

DeleteEmployee de = new DeleteEmployee();
de.execute();
        }

private void confirmDeleteEmployee(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to delete this employee?");

        alertDialogBuilder.setPositiveButton("Yes",
        new DialogInterface.OnClickListener() {
@Override
public void onClick(DialogInterface arg0, int arg1) {
        deleteEmployee();
        startActivity(new Intent(Removestaff.this,Clerk.class));
        }
        });

        alertDialogBuilder.setNegativeButton("No",
        new DialogInterface.OnClickListener() {
@Override
public void onClick(DialogInterface arg0, int arg1) {

        }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        }

@Override
public void onClick(View v) {


        if(v == buttonDelete){
        confirmDeleteEmployee();
        }
        }


        }
