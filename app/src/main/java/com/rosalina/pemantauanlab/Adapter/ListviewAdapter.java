package com.rosalina.pemantauanlab.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AlertDialogLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rosalina.pemantauanlab.Fragment.ListLaporan_frag;
import com.rosalina.pemantauanlab.Model;
import com.rosalina.pemantauanlab.R;

import java.util.HashMap;
import java.util.List;

public class ListviewAdapter extends RecyclerView.Adapter<ListviewAdapter.ViewHolder> {
    private List<Model> listmodel;
    DatabaseReference myRef;


    public ListviewAdapter(List<Model> modelList) {
        listmodel = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
    final Model model = listmodel.get(position);
    holder.nama.setText(model.getNama());
    holder.namabarang.setText(model.getNama_barang());
    holder.kelas.setText(model.getKelas());
    holder.uid.setText(model.getUid());

    //itemclicked
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               setReadValue(holder.uid.getText().toString());

                System.out.println("asdasdsa");
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Detail Laporan")
                        .setMessage("Nama : "+model.getNama()+"\n"
                                +"Kelas : "+model.getKelas()+"\n"
                                +"Nama Barang : "+model.getNama_barang()+"\n"
                                +"Nomor Unit : "+model.getNo_unit()+"\n"
                                +"Lokasi : "+model.getLokasi()+"\n"
                                +"Jumlah : "+model.getJumlah()+"\n"
                                +"Uraian Kerusakan : "+model.getUraian_kerusakan()+"\n"
                        ).show();
            }
        });
    }

    private void setReadValue(String uid) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        myRef.child("Laporan").child(uid).child("status_laporan").setValue("Read");
    }

    @Override
    public int getItemCount() {
        return listmodel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView uid, nama, namalist,kelaslist, namabaranglist, kelas, namabarang, nounit, lokasi, jumlah, uraiankerusakan;

        public ViewHolder(View itemview){
            super(itemview);
            nama = itemview.findViewById(R.id.nama_pelapor);
            kelas = itemview.findViewById(R.id.kelas_pelapor);
            namabarang = itemview.findViewById(R.id.nama_barang);

            //alertdialog
            uid = itemview.findViewById(R.id.Uid);
            namalist = itemview.findViewById(R.id.listlaporan_nama);
            kelaslist = itemview.findViewById(R.id.listlaporan_kelas);
            namabaranglist = itemview.findViewById(R.id.listlaporan_barang);
            jumlah = itemview.findViewById(R.id.listlaporan_jumlah);
            nounit = itemview.findViewById(R.id.listlaporan_nounit);
            lokasi = itemview.findViewById(R.id.listlaporan_lokasi);
            uraiankerusakan = itemview.findViewById(R.id.listlaporan_uraian);
        }
    }
}
