package com.rosalina.pemantauanlab.Adapter;

import android.app.AlertDialog;
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

public class KepalaLab_RcAdapter extends RecyclerView.Adapter<KepalaLab_RcAdapter.ViewHolder> {
    private List<Model> listmodel;
    DatabaseReference myRef;


    public KepalaLab_RcAdapter(List<Model> modelList) {
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
    holder.ongoing.setText(model.status_ongoing);
    holder.done.setText(model.status_done);

    //itemclicked
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setReadValue(holder.uid.getText().toString());

                System.out.println("asdasdsa");
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

                button_dialogcostum(alertDialog, holder, holder.done.getText().toString(),holder.ongoing.getText().toString(), v);
            }
        });
    }

    private void button_dialogcostum(final AlertDialog.Builder alertDialog, final ViewHolder holders, String done, String og, final View view) {
        final AppCompatActivity activity = (AppCompatActivity) view.getContext();
        if (og.equals("notgoing")) {
            alertDialog.setNegativeButton("Kerjakan", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, final int which) {
                    setOngoingValue(holders.uid.getText().toString(), "ongoing");
                    Executelist(dialog, activity, which);
                }
            });
                } else if (og.equals("ongoing")){
                  alertDialog.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                    setOngoingValue(holders.uid.getText().toString(), "notgoing");
                    Executelist(dialog, activity, which);
                    }
                  });
                  alertDialog.setPositiveButton("Selesai", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                            setDoneValue(holders.uid.getText().toString(), "selesai");
                            Executelist(dialog, activity, which);
                        }
                    });

                    } else if (done.equals("selesai")){
                alertDialog.setNeutralButton("Laporan Telah Di Selesaikan", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Executelist(dialog, activity, which);
                    Toast.makeText(view.getContext(), "Terima Kasih, Laporan Sudah Terlaksanakan", Toast.LENGTH_LONG).show();
                }
            });
        }
        else {
            Toast.makeText(view.getContext(), "Error, Please Restart Application", Toast.LENGTH_LONG).show();
        }

        final AlertDialog dialogs = alertDialog.create();
        dialogs.show();

    }
    private void Executelist(DialogInterface dialog, AppCompatActivity activity, int which) {
        ((AlertDialog) dialog).getButton(which).setVisibility(View.INVISIBLE);
        ListLaporan_frag fragment = new ListLaporan_frag();
        Bundle bundle = new Bundle();
        int getvalue = 0;
        bundle.putInt("keystatus", getvalue);
        fragment.setArguments(bundle);

        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void setDoneValue(String uid, String decision) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        myRef.child("Laporan").child(uid).child("status_done").setValue(decision);
    }

    private void setOngoingValue(String uid, String decision) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        myRef.child("Laporan").child(uid).child("status_ongoing").setValue(decision);
    }

    private void setReadValue(String uid) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        myRef.child("Laporan").child(uid).child("status_read").setValue("read");
    }

    @Override
    public int getItemCount() {
        return listmodel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView ongoing, done, uid, nama, namalist,kelaslist, namabaranglist, kelas, namabarang, nounit, lokasi, jumlah, uraiankerusakan;

        public ViewHolder(View itemview){
            super(itemview);
            nama = itemview.findViewById(R.id.nama_pelapor);
            kelas = itemview.findViewById(R.id.kelas_pelapor);
            namabarang = itemview.findViewById(R.id.nama_barang);

            //alertdialog

            ongoing = itemview.findViewById(R.id.ongoing);
            done = itemview.findViewById(R.id.done);
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
