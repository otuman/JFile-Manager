package com.jerotoma.jfilemanager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String TAG  = this.getClass().getName();
    private static final  int MY_PERMISSIONS_REQUEST = 1;
    private List<String> fileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Here, thisActivity is the current activity
        if ( (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)    != PackageManager.PERMISSION_GRANTED )&&
             (ContextCompat.checkSelfPermission( this,Manifest.permission.WRITE_EXTERNAL_STORAGE )  != PackageManager.PERMISSION_GRANTED )  ) {

            // Should we show an explanation?
            if ( ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE) &&
                 ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST);


            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }




    }

    private void ListDir() {

           File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
           Log.d(TAG,  root.listFiles().length + " Otoman");


           File[] files  =  root.listFiles();
           fileList = new ArrayList<>();
           for(File file: files){
               fileList.add(file.getPath());
           }
           for (int i=0; i < fileList.size(); i++){
                Log.d(TAG, "Asante" + fileList.get(i));
            }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {

            switch (requestCode) {
                case MY_PERMISSIONS_REQUEST: {
                    // If request is cancelled, the result arrays are empty.
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                        // permission was granted, yay! Do the
                        // contacts-related task you need to do.
                        Toast.makeText(this,"Permission granted", Toast.LENGTH_LONG).show();
                        ListDir();

                    } else {
                        Toast.makeText(this,"Permission not granted", Toast.LENGTH_LONG).show();
                        // permission denied, boo! Disable the
                        // functionality that depends on this permission.
                    }
                   break;
                }

                // other 'case' lines to check for other
                // permissions this app might request
            }
    }
}
