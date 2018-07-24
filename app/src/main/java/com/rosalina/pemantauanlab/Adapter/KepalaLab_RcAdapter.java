package com.rosalina.pemantauanlab.Adapter;

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

public class KepalaLab_RcAdapter extends RecyclerView.Adapter<KepalaLab_RcAdapter.ViewHolder> {
    private List<Model> listmodel;
    DatabaseReference myRef;
    ListLaporan_frag context;


    public KepalaLab_RcAdapter(List<Model> modelList, ListLaporan_frag context) {
        listmodel = modelList;
        this.context = context;
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
    View view = null;
    holder.nama.setText(model.getNama());
    holder.namabarang.setText(model.getNama_barang());
    holder.uid.setText(model.getUid());
        System.out.println("coyyyaa");
        System.out.println(model.getTanggal());
        System.out.println(model.getBulan());
    holder.tanggals.setText(model.getTanggal());
    holder.bulan.setText(model.getBulan());
    //holder.ongoing.setText(model.status_ongoing);
        if (model.status_done.equals("undone")){
            holder.kelas.setText("Laporan Diterima");
        } else if (model.status_done.equals("ongoingd")){
            holder.kelas.setText("Sedang Dikerjakan");
        } else if (model.status_done.equals("done")){
            holder.kelas.setText("Selesai");
        } else{
            Toast.makeText(view.getContext(), "Error, Please Restart Application", Toast.LENGTH_LONG).show();
        }
    holder.done.setText(model.status_done);

    //itemclicked
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

                button_dialogcostum(alertDialog, holder, holder.done.getText().toString(), v);
            }
        });
    }

    private void button_dialogcostum(final AlertDialog.Builder alertDialog, final ViewHolder holders, String done, final View view) {
        final AppCompatActivity activity = (AppCompatActivity) view.getContext();
        System.out.println(done);
        if (done.equals("undone")) {
            alertDialog.setNegativeButton("Kerjakan", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, final int which) {
                    setDoneValue(holders.uid.getText().toString(), "ongoingd");
                    //holders.kelas.setText("Sedang Dikerjakan");
                    Executelist(dialog, activity, which);
                }
            });
                } else if (done.equals("ongoingd")){
                  alertDialog.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                    setDoneValue(holders.uid.getText().toString(), "undone");
                    //holders.kelas.setText("Laporan Diterima");
                    Executelist(dialog, activity, which);
                    }
                  });
                  alertDialog.setPositiveButton("Selesai", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                            setDoneValue(holders.uid.getText().toString(), "done");
                            //holders.kelas.setText("Laporan Selesai");
                            Executelist(dialog, activity, which);
                        }
                    });

                    } else if (done.equals("done")){
                    alertDialog.setNeutralButton("Laporan Telah Di Selesaikan", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setReadValue(holders.uid.getText().toString());
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
        public TextView tanggals, done, uid, nama, bulan, kelas, namabarang;

        public ViewHolder(View itemview){
            super(itemview);

            tanggals = itemView.findViewById(R.id.tanggal_fo);
            bulan = itemView.findViewById(R.id.bulans);
            nama = itemview.findViewById(R.id.nama_pelapor);
            kelas = itemview.findViewById(R.id.kelas_pelapor);
            namabarang = itemview.findViewById(R.id.nama_barang);

//            //alertdialog
//
            done = itemview.findViewById(R.id.done);
            uid = itemview.findViewById(R.id.Uid);
//
        }
    }
}
