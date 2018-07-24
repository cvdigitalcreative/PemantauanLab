package com.rosalina.pemantauanlab.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rosalina.pemantauanlab.Boundary.ListLaporan_frag;
import com.rosalina.pemantauanlab.Model.Model;
import com.rosalina.pemantauanlab.R;

import java.util.List;

public class Kasi_RcAdapter extends RecyclerView.Adapter<Kasi_RcAdapter.ViewHolder> {
    private List<Model> list;
    DatabaseReference myRef;
    ListLaporan_frag context;

    public Kasi_RcAdapter(List<Model> modelList, ListLaporan_frag context) {
        list = modelList;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        Kasi_RcAdapter.ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Model model = list.get(position);
        View view = null;
        holder.nama.setText(model.getNama());
        holder.namabarang.setText(model.getNama_barang());
        holder.tanggals.setText(model.getTanggal());
        holder.bulan.setText(model.getBulan());

        if (model.getStatus_done().equals("done")){
            holder.kelas.setText("Selesai");

        }else if(model.getTerima().equals("tidak")){
            holder.kelas.setText("Laporan Baru");

        }else if(model.getTerima().equals("ya")){
            holder.kelas.setText("Laporan Sedang DiKerjakan");

        }else{
            Toast.makeText(view.getContext(), "Error, Please Restart Application", Toast.LENGTH_LONG).show();
        }
        holder.uid.setText(model.getUid());
        holder.terima.setText(model.getTerima());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setReadValue(holder.uid.getText().toString());
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                alertDialog.setCancelable(true);
                LayoutInflater inflater = ((AppCompatActivity)v.getContext()).getLayoutInflater();
                View dialogview = inflater.inflate(R.layout.dialogalert, null);
                alertDialog.setView(dialogview);

                TextView namalist =  dialogview.findViewById(R.id.listlaporan_nama);
                TextView kelas =  dialogview.findViewById(R.id.listlaporan_kelas);
                TextView nama_barang =  dialogview.findViewById(R.id.listlaporan_barang);
                TextView no_unit =  dialogview.findViewById(R.id.listlaporan_nounit);
                TextView lokasi =  dialogview.findViewById(R.id.listlaporan_lokasi);
                TextView jumlah =  dialogview.findViewById(R.id.listlaporan_jumlah);
                TextView uraian =  dialogview.findViewById(R.id.listlaporan_uraian);
                TextView status =  dialogview.findViewById(R.id.listlaporan_status);

                //setAlert dialog
                alertDialog.setTitle("Detail Laporan");
                namalist.setText(model.getNama());
                status.setText(model.getStatus_pelapor());
                System.out.println(model.getStatus_pelapor());
                kelas.setText(model.getKelas());
                nama_barang.setText(model.getNama_barang());
                no_unit.setText(model.getNo_unit());
                lokasi.setText(model.getLokasi());
                jumlah.setText(model.getJumlah());
                uraian.setText(model.getUraian_kerusakan());

//                        .setMessage("Nama : "+model.getNama()+"\n"
//                                +"Kelas : "+model.getKelas()+"\n"
//                                +"Nama Barang : "+model.getNama_barang()+"\n"
//                                +"Nomor Unit : "+model.getNo_unit()+"\n"
//                                +"Lokasi : "+model.getLokasi()+"\n"
//                                +"Jumlah : "+model.getJumlah()+"\n"
//                                +"Uraian Kerusakan : "+model.getUraian_kerusakan()+"\n

                    button_dialogcostum(alertDialog, holder, holder.terima.getText().toString(), v, holder.kelas.getText().toString());
            }
        });
    }

    private void button_dialogcostum(final AlertDialog.Builder alertDialog, final ViewHolder holders, String terima, final View view, String status) {
        final AppCompatActivity activity = (AppCompatActivity) view.getContext();
        System.out.println(status);
//        if (status.equals("Selesai")) {
//            alertDialog.setPositiveButton("Selesai", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    //Executelist(dialog, activity, which);
//                    setHapuslaporan(holders.uid.getText().toString());
//                }
//            });
//        } else if (status.equals("Laporan Sedang DiKerjakan")) {
//            alertDialog.setPositiveButton("Sedang Dikerjakan", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    setSelesai(holders.uid.getText().toString());
//                    Executelist(dialog, activity, which);
//                }
//            });
//        } else {
            if (terima.equals("tidak")) {
                alertDialog.setPositiveButton("Terima", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, final int which) {
                        setTerimaValue(holders.uid.getText().toString(), "ya");
                        //holders.kelas.setText("Laporan Diterima");
                        Executelist(dialog, activity, which);
                    }
                });
                alertDialog.setNegativeButton("Tolak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, final int which) {
                        setTerimaValue(holders.uid.getText().toString(), "tolak");
                        // holders.kelas.setText("Laporan Ditolak");
                        Executelist(dialog, activity, which);
                        setHapuslaporan(holders.uid.getText().toString());
                    }
                });
            } else if (status.equals("Selesai")) {
                alertDialog.setPositiveButton("Hapus Laporan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Executelist(dialog, activity, which);
                        setHapuslaporan(holders.uid.getText().toString());
                    }
                });
            }else if (status.equals("Laporan Sedang DiKerjakan")) {
                alertDialog.setPositiveButton("Sedang Di Kerjakan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setReadValue(holders.uid.getText().toString());
                        Executelist(dialog, activity, which);
                    }
                });
//            } else if (terima.equals("batal")) {
//                alertDialog.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Executelist(dialog, activity, which);
//                        setHapuslaporan(holders.uid.getText().toString());
//                    }
//                });
//                alertDialog.setNegativeButton("Terima", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        setTerimaValue(holders.uid.getText().toString(), "selesai");
//                        Executelist(dialog, activity, which);
//                    }
//                });
        } else {
                Toast.makeText(view.getContext(), "Error, Please Restart Application", Toast.LENGTH_LONG).show();
            }

            final AlertDialog dialogs = alertDialog.create();
            dialogs.show();

        }
    private void Executelist(DialogInterface dialog, AppCompatActivity activity, int which) {
        ((AlertDialog) dialog).getButton(which).setVisibility(View.INVISIBLE);
        ListLaporan_frag fragment = new ListLaporan_frag();
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();

        Bundle bundle = new Bundle();
        int getvalue = 1;
        bundle.putInt("keystatus", getvalue);
        fragment.setArguments(bundle);

    }

    private void setHapuslaporan(String uid){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        myRef.child("Laporan").child(uid).removeValue();
    }

    private void setSelesai(String uid) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        myRef.child("Laporan").child(uid).child("status_done");
    }

    private void setTerimaValue(String uid, String decision) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        myRef.child("Laporan").child(uid).child("terima").setValue(decision);
    }

    private void setReadValue(String uid) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        myRef.child("Laporan").child(uid).child("status_read").setValue("read");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView terima, uid, nama, tanggals, bulan, kelas, namabarang;

        public ViewHolder(View itemView) {
            super(itemView);

            tanggals = itemView.findViewById(R.id.tanggal_fo);
            bulan = itemView.findViewById(R.id.bulans);
            nama = itemView.findViewById(R.id.nama_pelapor);
            kelas = itemView.findViewById(R.id.kelas_pelapor);
            namabarang = itemView.findViewById(R.id.nama_barang);

            //alertdialog

            terima = itemView.findViewById(R.id.terima);
            uid = itemView.findViewById(R.id.Uid);
        }
    }
}
