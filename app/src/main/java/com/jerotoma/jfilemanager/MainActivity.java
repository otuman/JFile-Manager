package com.jerotoma.jfilemanager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.jerotoma.jfilemanager.Model.Drive;
import com.jerotoma.jfilemanager.adapter.LocalDriveRecyclerViewAdapter;
import com.jerotoma.jfilemanager.helper.ExternalSDCardSystemStorage;
import com.jerotoma.jfilemanager.helper.InternalSystemStorage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private String TAG  = this.getClass().getName();
    private int progressStatus = 0;
    private static final  int MY_PERMISSIONS_REQUEST = 1;
    private ArrayList<Drive> drives;

    @BindView(R.id.drive_category_list)RecyclerView drive_internal_external;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupView();
    }
    protected void setupView(){

         // Here, thisActivity is the current activity
         if ( (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)    != PackageManager.PERMISSION_GRANTED )&&
                 (ContextCompat.checkSelfPermission( this,Manifest.permission.WRITE_EXTERNAL_STORAGE )  != PackageManager.PERMISSION_GRANTED )  ) {

             // Should we show an explanation?
             if ( ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE) &&
                  ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ) {

                 // Show an explanation to the user *asynchronously* -- don't block
                 // this thread waiting for the user's response! After the user
                 // sees the explanation, try again to request the permission.
                 ActivityCompat.requestPermissions(
                         this,
                         new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE },
                         MY_PERMISSIONS_REQUEST );
             } else {

                 // No explanation needed, we can request the permission.
                 ActivityCompat.requestPermissions(
                         this,
                         new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                         MY_PERMISSIONS_REQUEST  );

                 // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                 // app-defined int constant. The callback method gets the
                 // result of the request.
             }
         }else {
             ListDrive();

         }

     }

     private void ListDrive() {

         drives = new ArrayList<>();
         Drive   drive0 = new Drive("Internal storage", "",
                                    InternalSystemStorage.getInternalFreeSpace()+ " free of "+
                                    InternalSystemStorage.getInternalTotalSpace(),
                                    InternalSystemStorage.getSpaceUsageRatio());
         drives.add(drive0);
         Drive   drive1 = new Drive("SD Card", "",
                 getSDCardFreeSpace()+ " free of "+ getSDCardTotalSpace(), getSDCardSpaceUsageRatio());
         drives.add(drive1);

         // use this setting to improve performance if you know that changes
         // in content do not change the layout size of the RecyclerView
         drive_internal_external.setHasFixedSize(true);

         // use a linear layout manager
         LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
         drive_internal_external.setLayoutManager(mLayoutManager);

         // specify an adapter (see also next example)
         LocalDriveRecyclerViewAdapter mAdapter = new LocalDriveRecyclerViewAdapter(drives);
         drive_internal_external.setAdapter(mAdapter);


         Log.d(TAG, "Total as free " + InternalSystemStorage.getInternalFreeSpace());
         Log.d(TAG, "Total internal space  " + InternalSystemStorage.getInternalTotalSpace() );



     }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {

            switch (requestCode) {
                case MY_PERMISSIONS_REQUEST: {
                    // If request is cancelled, the result arrays are empty.
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // permission was granted, yay! Do the
                        // contacts-related task you need to do.
                        Toast.makeText(this,"Permission granted", Toast.LENGTH_LONG).show();
                        ListDrive();

                    }else{
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


    protected String getSDCardTotalSpace(){
      return ExternalSDCardSystemStorage.getExternalSDCardTotalSpace();
    }
    protected int getSDCardSpaceUsageRatio(){
      return ExternalSDCardSystemStorage.getSpaceUsageRatio();
    }

    protected String getSDCardFreeSpace(){
        return ExternalSDCardSystemStorage.getExternalSDCardFreeSpace();
    }
    protected  File[] getSDCardFiles(){
        return ExternalSDCardSystemStorage.getExternalSDCardFiles();
    }
}
