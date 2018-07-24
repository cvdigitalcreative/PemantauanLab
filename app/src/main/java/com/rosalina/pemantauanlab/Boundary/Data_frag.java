package com.rosalina.pemantauanlab.Boundary;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rosalina.pemantauanlab.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A simple {@link Fragment} subclass.
 */
public class Data_frag extends Fragment {
    ListView listView;
    int[] dirFile ={R.raw.file_lab1, R.raw.file_lab2, R.raw.file_lab3,R.raw.file_lab4,R.raw.file_lab5,
            R.raw.file_lab6,R.raw.file_lab7,R.raw.file_lab8,R.raw.file_lab9, R.raw.file_lab10};
    String[] name_file={"file1.xls","file_lab1.xls", "file_lab2.xls", "file_lab3.xls", "file_lab4.xls", "file_lab5.xls", "file_lab6.xls", "file_lab7.xls",
            "file_lab9.xls", "file_lab10.xls"};
    public Data_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data_frag, container, false);
        //Toolbar
        android.support.v7.widget.Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity action = (AppCompatActivity)getActivity();
        action.setSupportActionBar(toolbar);
        action.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        action.setTitle("Data Lab");

        listView = view.findViewById(R.id.listview);
        listView.setAdapter(new dataListAdapter(name_file, dirFile));

        //File file = new File(Environment.getExternalStorageDirectory()+"/excelfie/" +filename)
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                System.out.println("asd");
//                System.out.println(Environment.getExternalStorageDirectory());
//               // InputStream in = getResources().openRawResource(Integer.parseInt("/assets/file_lab.xls"));
//                //intent.setDataAndType(Uri.fromFile(in));
//                String path = "android.resource://" + view.getContext().getPackageName() + "/" + R.raw.file_lab1;
//                intent.setDataAndType(Uri.parse(path), "application/vnd.ms-excel");
//
////                intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/Download/" + "data1.xls")), "application/vnd.ms-excel");
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//
//                try {
//                    startActivity(intent);
//                }
//                catch (ActivityNotFoundException e) {
//                    System.out.println("coy "+e.getMessage());
//                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
                System.out.println("coy "+name_file[position]);
                CopyReadAssets(name_file[position]);
            }
        });
        //Reading excel file
//        File fileDirectory = new File (Environment.getDataDirectory()+ "/excelfile/");
//        dirFile = fileDirectory.listFiles();
        return view;
    }

    private void CopyReadAssets(String filename)
    {
        AssetManager assetManager = getActivity().getAssets();

        InputStream in = null;
        OutputStream out = null;
        File file = new File(getActivity().getFilesDir(), filename);
        try
        {
            in = assetManager.open(filename);
            out = getActivity().openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);

            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e)
        {
            Log.e("tag", e.getMessage());
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(
                Uri.parse("file://" + getActivity().getFilesDir() + "/"+filename),
                "application/vnd.ms-excel");

        startActivity(intent);
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException
    {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1)
        {
            out.write(buffer, 0, read);
        }
    }

    class dataListAdapter extends BaseAdapter {
        String[] Detail;
        int[] dirFile;

        public dataListAdapter(String[] detail, int[] dirFile) {
            super();
            this.Detail = detail;
            this.dirFile = dirFile;
        }

        public int getCount() {
            // TODO Auto-generated method stub
            return Detail.length;
        }

        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View row;
            row = inflater.inflate(R.layout.listview, parent, false);

            TextView detail;

            detail = row.findViewById(R.id.nama_file);
            detail.setText(Detail[position]);

            return (row);
        }
    }
}
