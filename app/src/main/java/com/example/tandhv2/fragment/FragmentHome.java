package com.example.tandhv2.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.tandhv2.MainActivity;
import com.example.tandhv2.R;
import com.example.tandhv2.adapter.RecycleViewAdapter;
import com.example.tandhv2.database.SensorDatabase;
import com.example.tandhv2.model.Sensor;
import com.example.tandhv2.utils.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FragmentHome extends Fragment implements View.OnClickListener,RecycleViewAdapter.ItemListener {

    private RecyclerView recyclerView;
    private RecycleViewAdapter adapter;
    private TextView signal;
    private Button btReload;
    private List<Sensor> sensors;
    private ProgressDialog progressDialog;

    private Session session;
    int pos;
    private Handler mHandler;

    //private Runnable m_Runnable;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        sensors = SensorDatabase.getInstance(getActivity()).sensorDAO().getAll();
        //sensors = new ArrayList<>();
        //fakeData();

        adapter = new RecycleViewAdapter(getActivity());
        adapter.setSensors(sensors);
        LinearLayoutManager manager =new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false);

        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        btReload.setOnClickListener(this);

        adapter.setItemListener(this);



        this.mHandler = new Handler();

        //this.mHandler.postDelayed(m_Runnable,500);
        m_Runnable.run();


    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycleView);
        btReload = view.findViewById(R.id.reload);
        btReload.setVisibility(View.GONE);
        progressDialog = new ProgressDialog(getActivity());
        session = new Session(getActivity());
        signal = view.findViewById(R.id.signal);
        //((MainActivity) getActivity()).setActionBarTitle("T and H Plus Plus");

    }
    private void fakeData() {
//        sensors.add(new Sensor("1","22/05/2022","25","95"));
//        sensors.add(new Sensor("2","23/05/2022","27","93"));
//        sensors.add(new Sensor("3","24/05/2022","24","92"));
//        sensors.add(new Sensor("4","25/05/2022","29","90"));

    }

    @Override
    public void onClick(View view) {
        if(view == btReload){

            //fakeData();
//            try {
//                String str_result=new ReadJson().execute().get();
//            } catch (ExecutionException | InterruptedException e) {
//                e.printStackTrace();
//            }
            //progressBar.setVisibility(View.INVISIBLE);

        }


    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(this, sensors.get(position).getTemperature(), Toast.LENGTH_SHORT).show();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Delete?");
        alertDialog.setIcon(R.drawable.ic_delete);
        alertDialog.setMessage("Delete this data?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                new deleteData().execute(sensors.get(position).getObjectId());
                //sensors.remove(position);
                //adapter.notifyDataSetChanged();
                pos = position;
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alertDialog.show();


    }






    private  class ReadJson extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            //progressDialog.show();
            //super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
           // String url = "https://data.mongodb-api.com/app/application-0-nvpfc/endpoint/sensor/get";
            String url;
            if(session.getUrlGet().equals("")){
                url = session.getDefaultUrlGet();
            }else{
                url = session.getUrlGet();
            }
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            signal.setTextColor(getActivity().getResources().getColor(R.color.green));
                            SensorDatabase.getInstance(getActivity()).sensorDAO().deleteAll();
                            //Toast.makeText(getActivity(), "Successfully", Toast.LENGTH_SHORT).show();
                            Log.e("AAA",response.length()+"");
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject object = response.getJSONObject(i);
                                    String dateString = object.getString("date");
                                    String date = dateString.substring(0,10);
                                    String time = dateString.substring(11,19);
                                    time = formatTime(time);
                                    Sensor sensor = new Sensor(
                                            object.getString("_id"),
                                            date,
                                            object.getString("temperature"),
                                            object.getString("humidity"),
                                            time);
                                    //Log.e("Date",dateString+ " " + time + " " + date);

                                    Sensor sensorCheck = SensorDatabase.getInstance(getActivity()).sensorDAO().getSensorByObjectId(sensor.getObjectId());
                                    if(sensorCheck == null){
                                        SensorDatabase.getInstance(getActivity()).sensorDAO().insertSensor(sensor);
                                    }



                                    // sensors.add(new Sensor("*","99/99/2099","99","99"));
                                } catch (JSONException | ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            sensors = SensorDatabase.getInstance(getActivity()).sensorDAO().getAll();
                            adapter.setSensors(sensors);
                            for(Sensor sensor:sensors){
                                Log.e("AAA",sensor.toString());
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            signal.setTextColor(getActivity().getResources().getColor(R.color.red));
                            //Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
            requestQueue.add(jsonArrayRequest);
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {

            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            //progressDialog.dismiss();
        }
    }

    private String formatTime(String time) throws ParseException {

        int h = Integer.parseInt(time.substring(0,2));
            String s ="";
            if(h < 3){
                s = "0" + Integer.toString(h+7) + time.substring(2,8);
            }else if(h > 2 && h <17){
                s = Integer.toString(h+7) + time.substring(2,8);
            }else if(h > 16){
                s = "0" + Integer.toString(h+7-24) + time.substring(2,8);
            }
            Log.e("Date", time + " " + h + " " + s);

            return s;

    }

    private class deleteData extends AsyncTask<String, Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            //String url = "https://data.mongodb-api.com/app/application-0-nvpfc/endpoint/sensor/delete?id=";
            String url;
            if(session.getUrlDelete().equals("")){
                url = session.getDefaultUrlDelete() +"?id=";
            }else{
                url = session.getUrlDelete() +"?id=";
            }

            String id = strings[0];
            Log.e("BBB", url+id);
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.DELETE, url+id, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
//                            sensors.remove(pos);
//                            adapter.notifyDataSetChanged();
                            Sensor sensor = sensors.get(pos);
                            SensorDatabase.getInstance(getActivity()).sensorDAO().delete(sensor);
                            sensors = SensorDatabase.getInstance(getActivity()).sensorDAO().getAll();
                            adapter.setSensors(sensors);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity(), "Không thể kết nối tới sever", Toast.LENGTH_SHORT).show();
                        }
                    });
            requestQueue.add(jsonArrayRequest);
            return null;
        }
    }

    private final Runnable m_Runnable = new Runnable(){
        public void run() {
            //Toast.makeText(getActivity(),"in runnable",Toast.LENGTH_SHORT).show();
            //SensorDatabase.getInstance(getActivity()).sensorDAO().deleteAll();

            new ReadJson().execute();

            mHandler.postDelayed(m_Runnable, 2000);
        }

    };

}
