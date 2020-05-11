package com.example.myclass;

import androidx.appcompat.app.AppCompatActivity;
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

public class TambahData extends AppCompatActivity {
    public EditText kode, matkul, dosen, hari;
    public Button simpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);

        kode   = (EditText)findViewById(R.id.kode);
        matkul = (EditText)findViewById(R.id.matakuliah);
        dosen  = (EditText)findViewById(R.id.dosen);
        hari   = (EditText)findViewById(R.id.hari);
        simpan = (Button) findViewById(R.id.btnAksi);

        AndroidNetworking.initialize(this);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpan();
            }
        });
    }

    public void simpan(){
        AndroidNetworking.post("http://192.168.100.2/bit/insert.php")
                .addBodyParameter("kode", kode.getText().toString())
                .addBodyParameter("matkul", matkul.getText().toString())
                .addBodyParameter("dosen", dosen.getText().toString())
                .addBodyParameter("hari", hari.getText().toString())
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        try {
                            if(response.getString("status").equals("berhasil") ){
                                Toast.makeText(getApplicationContext(), "Data berhasil disimpan..."
                                        ,Toast.LENGTH_LONG).show();

                            }else {
                                Toast.makeText(getApplicationContext(), "Data gagal disimpan!"
                                        ,Toast.LENGTH_LONG).show();
                            }
                        }catch (JSONException e){
                            Toast.makeText(getApplicationContext(), "Kesalahan Kode 1"
                                    ,Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(getApplicationContext(), "Kesalahan Kode 2"
                                ,Toast.LENGTH_LONG).show();
                    }
                });
    }
}
