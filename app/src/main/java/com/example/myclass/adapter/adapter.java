package com.example.myclass.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.myclass.R;
import com.example.myclass.UpdateData;
import com.example.myclass.model.dataMatkulModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class adapter extends RecyclerView.Adapter<adapter.Holder> {
    private ArrayList<dataMatkulModel> dataModel;
    private Activity activity;


    public adapter(Activity activity, ArrayList<dataMatkulModel> dataModel){
        this.dataModel = dataModel;
        this.activity  = activity;
    }

    @NonNull
    @Override
    public adapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapterdata, parent, false);
        Holder holder = new Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final adapter.Holder holder, final int position) {
        dataMatkulModel model = dataModel.get(position);

        holder.namaMk.setText(model.getMatkul());
        holder.kodeMk.setText(model.getKode());
        holder.namaDs.setText(model.getDosen());
        holder.hariMk.setText(model.getHari());

        holder.hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.hapusData();
                dataModel.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, dataModel.size());
                notifyDataSetChanged();

            }
        });

        holder.model = model;

    }

    @Override
    public int getItemCount() {
        return dataModel.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView namaMk, kodeMk, namaDs, hariMk;
        ImageView hapus;
        dataMatkulModel model;

        public Holder(@NonNull View v) {
            super(v);
            kodeMk = (TextView)v.findViewById(R.id.kodeMk);
            namaMk = (TextView)v.findViewById(R.id.namaMk);
            namaDs = (TextView)v.findViewById(R.id.namaDs);
            hariMk = (TextView)v.findViewById(R.id.hariMk);
            hapus  = (ImageView)v.findViewById(R.id.hapus);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent lempar = new Intent(activity, UpdateData.class);
                    lempar.putExtra("kode", model.getKode());
                    lempar.putExtra("mk", model.getMatkul());
                    lempar.putExtra("dosen", model.getDosen());
                    lempar.putExtra("hari", model.getHari());
                    activity.startActivity(lempar);

                }
            });

        }//


        public void hapusData(){
            AndroidNetworking.post("http://192.168.100.2/bit/delete.php")
                    .addBodyParameter("kode", model.getKode())
                    .setTag("test")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // do anything with response
                            try {
                                if(response.getString("status").equals("berhasil") ){
                                    Toast.makeText(activity, "Data berhasil dihapus..."
                                            ,Toast.LENGTH_LONG).show();

                                }else {
                                    Toast.makeText(activity, "Data gagal dihapus!"
                                            ,Toast.LENGTH_LONG).show();
                                }
                            }catch (JSONException e){
                                Toast.makeText(activity, "Kesalahan hapus, Kode 1"
                                        ,Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onError(ANError error) {
                            // handle error
                            Toast.makeText(activity, "Kesalahan hapus, Kode 2"
                                    ,Toast.LENGTH_LONG).show();
                        }
                    });
        }//

    }
}
