package com.example.myclass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.myclass.adapter.adapter;
import com.example.myclass.model.dataMatkulModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button tambah;
    private final static  String STATUS = "Data Matakuliah";
    public ArrayList<dataMatkulModel> dataModel;
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tambah       = (Button) findViewById(R.id.tambah);
        recyclerView = (RecyclerView)findViewById(R.id.recy);

        recyclerView.setHasFixedSize(true);
        dataModel = new ArrayList<>();
        AndroidNetworking.initialize(this);

        loadData();

        layoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new adapter(this, dataModel);
        recyclerView.setAdapter(adapter);

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent form = new Intent(getApplicationContext(), TambahData.class);
                startActivity(form);
            }
        });

    }

    public void loadData(){
        AndroidNetworking.post("http://192.168.100.2/bit/read.php")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(STATUS, "response : "+response.toString());
                        // do anything with response
                        try {
                            if(response.getString("status").equals("berhasil")){
                                JSONArray jsonArray = response.getJSONArray("matakuliah");
                                for (int i =0; i < jsonArray.length(); i++){
                                    JSONObject data = jsonArray.getJSONObject(i);
                                    dataMatkulModel item = new dataMatkulModel(
                                            data.getString("kode"),
                                            data.getString("matkul"),
                                            data.getString("dosen"),
                                            data.getString("hari")
                                    );
                                    dataModel.add(item);
                                }
                            }else {
                                Toast.makeText(getApplicationContext(), "Data gagal di load!"
                                        ,Toast.LENGTH_LONG).show();
                            }
                        }catch (JSONException e){
                            Toast.makeText(getApplicationContext(), "Error load 1"
                                    ,Toast.LENGTH_LONG).show();
                        }

                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(getApplicationContext(), "Error load 2"
                                ,Toast.LENGTH_LONG).show();
                    }
                });
    }

}
