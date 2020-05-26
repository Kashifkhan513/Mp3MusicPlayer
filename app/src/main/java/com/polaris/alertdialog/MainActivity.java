package com.polaris.alertdialog;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
ListView listView;
ArrayList<String>arrayList;
ArrayAdapter<String>arrayAdapter;
ArrayList<File>fileArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listView);
        arrayList=new ArrayList<>();


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
        else {

            getFileUponPermission();
        }

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent=new Intent(MainActivity.this,Main2Activity.class);
               intent.putExtra("POS",position);
               intent.putExtra("FILE",fileArrayList);
               startActivity(intent);
           }
       });

    }

    private ArrayList<File> getAllAudioFile(File externalStorageDirectory) {
    ArrayList<File>allAudioFiles=new ArrayList<>();

    File[]files=externalStorageDirectory.listFiles();

    for (File file:files)
    {
        if (file.isDirectory())
        {

            allAudioFiles.addAll(getAllAudioFile(file));
        }
        else {

            if (file.getName().endsWith(".mp3"))
            {
                allAudioFiles.add(file);
            }
        }
    }






    return allAudioFiles;

    }


    public void getFileUponPermission()
    {



        fileArrayList=getAllAudioFile(Environment.getExternalStorageDirectory());

        for (File file:fileArrayList)
        {
            arrayList.add(file.getName()) ;
        }

        arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arrayList);

        listView.setAdapter(arrayAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1)
        {
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                getFileUponPermission();
            }
            else {
                Toast.makeText(this, "Permission Denied..", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
