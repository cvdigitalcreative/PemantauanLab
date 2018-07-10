package com.rosalina.pemantauanlab.Adapter;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ListFragment;
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
import com.rosalina.pemantauanlab.Fragment.ListLaporan_frag;
import com.rosalina.pemantauanlab.Model.Model;
import com.rosalina.pemantauanlab.R;

import java.util.List;

public class Kasi_RcAdapter extends RecyclerView.Adapter<Kasi_RcAdapter.ViewHolder> {
    private List<Model> list;
    DatabaseReference myRef;

    public Kasi_RcAdapter(List<Model> modelList) {
        list = modelList;
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
        holder.nama.setText(model.getNama());
        holder.namabarang.setText(model.getNama_barang());
        holder.kelas.setText(model.getKelas());
        holder.uid.setText(model.getUid());
        holder.terima.setText(model.getTerima());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setReadValue(holder.uid.getText().toString());
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());

                    alertDialog.setTitle("Detail Laporan")
                        .setMessage("Nama : "+model.getNama()+"\n"
                                +"Kelas : "+model.getKelas()+"\n"
                                +"Nama Barang : "+model.getNama_barang()+"\n"
                                +"Nomor Unit : "+model.getNo_unit()+"\n"
                                +"Lokasi : "+model.getLokasi()+"\n"
                                +"Jumlah : "+model.getJumlah()+"\n"
                                +"Uraian Kerusakan : "+model.getUraian_kerusakan()+"\n"
                        );

                    button_dialogcostum(alertDialog, holder, holder.terima.getText().toString(), v);
            }
        });
    }

    private void button_dialogcostum(final AlertDialog.Builder alertDialog, final Kasi_RcAdapter.ViewHolder holders, String terima, final View view) {
        final AppCompatActivity activity = (AppCompatActivity) view.getContext();
        if (terima.equals("tidak")) {
            alertDialog.setPositiveButton("Terima", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, final int which) {
                    setTerimaValue(holders.uid.getText().toString(), "ya");
                    Executelist(dialog, activity, which);
                }
            });
            alertDialog.setNegativeButton("Tolak", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, final int which) {
                    setTerimaValue(holders.uid.getText().toString(), "tolak");
                    Executelist(dialog, activity, which);
                }
            });
        } else if (terima.equals("tolak")) {
            alertDialog.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Executelist(dialog, activity, which);
                    setHapuslaporan(holders.uid.getText().toString());
                }
            });

            alertDialog.setPositiveButton("Terima", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setTerimaValue(holders.uid.getText().toString(), "ya");
                    Executelist(dialog, activity, which);
                }
            });
        } else if (terima.equals("ya")) {
            alertDialog.setPositiveButton("Batalkan", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setTerimaValue(holders.uid.getText().toString(), "batal");
                    Executelist(dialog, activity, which);
                }
            });
        }  else if (terima.equals("batal")) {
            alertDialog.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Executelist(dialog, activity, which);
                    setHapuslaporan(holders.uid.getText().toString());
                }
            });
            alertDialog.setNegativeButton("Terima", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setTerimaValue(holders.uid.getText().toString(), "ya");
                    Executelist(dialog, activity, which);
                }
            });
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
        public TextView terima, uid, nama, namalist,kelaslist, namabaranglist, kelas, namabarang, nounit, lokasi, jumlah, uraiankerusakan;

        public ViewHolder(View itemView) {
            super(itemView);

            nama = itemView.findViewById(R.id.nama_pelapor);
            kelas = itemView.findViewById(R.id.kelas_pelapor);
            namabarang = itemView.findViewById(R.id.nama_barang);

            //alertdialog

            terima = itemView.findViewById(R.id.terima);
            uid = itemView.findViewById(R.id.Uid);
            namalist = itemView.findViewById(R.id.listlaporan_nama);
            kelaslist = itemView.findViewById(R.id.listlaporan_kelas);
            namabaranglist = itemView.findViewById(R.id.listlaporan_barang);
            jumlah = itemView.findViewById(R.id.listlaporan_jumlah);
            nounit = itemView.findViewById(R.id.listlaporan_nounit);
            lokasi = itemView.findViewById(R.id.listlaporan_lokasi);
            uraiankerusakan = itemView.findViewById(R.id.listlaporan_uraian);
        }
    }
}
