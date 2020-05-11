package com.example.myclass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateData extends AppCompatActivity {
    String kode, matkul, dosen, hari;
    Button update;
    EditText ukodes, umatkuls, udosen, uhari;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        update  = (Button)findViewById(R.id.btnUpdate);
        ukodes  = (EditText)findViewById(R.id.ukode);
        umatkuls= (EditText)findViewById(R.id.umatakuliah);
        udosen  = (EditText)findViewById(R.id.udosen);
        uhari   = (EditText)findViewById(R.id.uhari);

        AndroidNetworking.initialize(this);

        Intent data = getIntent();
        kode   = data.getStringExtra("kode");
        matkul = data.getStringExtra("mk");
        dosen  = data.getStringExtra("dosen");
        hari   = data.getStringExtra("hari");

        ukodes.setText(kode);
        umatkuls.setText(matkul);
        udosen.setText(dosen);
        uhari.setText(hari);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ukodes.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Data tidak boleh kosong...", Toast.LENGTH_LONG).show();
                }else {
                    update();
                }
            }
        });

    }

    public  void update(){
        AndroidNetworking.post("http://192.168.100.2/bit/update.php")
                .addBodyParameter("kode", ukodes.getText().toString())
                .addBodyParameter("matkul", umatkuls.getText().toString())
                .addBodyParameter("dosen", udosen.getText().toString())
                .addBodyParameter("hari", uhari.getText().toString())
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        try {
                            if(response.getString("status").equals("berhasil") ){
                                Toast.makeText(getApplicationContext(), "Data berhasil di update..."
                                        ,Toast.LENGTH_LONG).show();

                            }else {
                                Toast.makeText(getApplicationContext(), "Data gagal di update!"
                                        ,Toast.LENGTH_LONG).show();
                            }
                        }catch (JSONException e){
                            Toast.makeText(getApplicationContext(), "Kesalahan update, Kode 1"
                                    ,Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(getApplicationContext(), "Kesalahan update, Kode 2"
                                ,Toast.LENGTH_LONG).show();
                    }
                });
    }
}
