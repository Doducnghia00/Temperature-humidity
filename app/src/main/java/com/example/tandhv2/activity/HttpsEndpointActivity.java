package com.example.tandhv2.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.tandhv2.R;
import com.example.tandhv2.database.SensorDatabase;
import com.example.tandhv2.model.Sensor;
import com.example.tandhv2.utils.Session;

import org.json.JSONArray;

public class HttpsEndpointActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText get,delete;
    private Button add, reset, test;
    private Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_https_endpoint);
        initView();
        add.setOnClickListener(this);
        reset.setOnClickListener(this);

        getSupportActionBar().setTitle("HTTPS Endpoints");
    }

    private void initView() {
        session = new Session(HttpsEndpointActivity.this);
        get = findViewById(R.id.urlGet);
        delete = findViewById(R.id.urlDelete);
        add = findViewById(R.id.addEndpoint);
        reset = findViewById(R.id.resetEndpoint);
        if(session.getUrlGet().equals("")){
            get.setText(session.getDefaultUrlGet());
            delete.setText(session.getDefaultUrlDelete());
        }else{
            get.setText(session.getUrlGet());
            delete.setText(session.getUrlDelete());
        }



        test = findViewById(R.id.bt_test);
        test.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == add){
            String getUrl = get.getText().toString().trim();
            String deleteUrl = delete.getText().toString().trim();
            if(getUrl.equals("") || deleteUrl.equals("") ){
                Toast.makeText(this, "Nhập đủ các trường", Toast.LENGTH_SHORT).show();
            }else{
                session.setUrlGet(getUrl);
                session.setUrlDelete(deleteUrl);
                Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        if(view == reset){
            resetSession();
        }
        if(view == test){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Delete all?");
            alertDialog.setIcon(R.drawable.ic_refresh);
            alertDialog.setMessage("Delete all data?");
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                   new deleteAllData().execute();
                    finish();
                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            alertDialog.show();
        }
    }

    private void resetSession() {
        //Toast.makeText(this, sensors.get(position).getTemperature(), Toast.LENGTH_SHORT).show();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Reset?");
        alertDialog.setIcon(R.drawable.ic_refresh);
        alertDialog.setMessage("Reset Https Endpoint?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                session.removeUrl();
                finish();
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alertDialog.show();
    }

    private class deleteAllData extends AsyncTask<String, Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            RequestQueue requestQueue = Volley.newRequestQueue(HttpsEndpointActivity.this);
            //String url = "https://data.mongodb-api.com/app/application-0-nvpfc/endpoint/sensor/delete?id=";
            String url;
            if(session.getUrlDelete().equals("")){
                url = session.getDefaultUrlDelete() +"?name=1";
            }else{
                url = session.getUrlDelete() +"?name=1";
            }

            //String id = strings[0];
            //Log.e("BBB", url+id);
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.DELETE, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Toast.makeText(HttpsEndpointActivity.this, "Gửi yêu cầu thành công", Toast.LENGTH_SHORT).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(HttpsEndpointActivity.this, "Kết nối thất bại", Toast.LENGTH_SHORT).show();
                        }
                    });
            requestQueue.add(jsonArrayRequest);
            return null;
        }
    }
}