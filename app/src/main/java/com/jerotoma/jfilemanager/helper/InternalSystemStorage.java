package com.jerotoma.jfilemanager.helper;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.File;
import java.util.Formatter;
import java.util.Locale;

/**
 * Created by Otoman on 02/04/2017.
 */

public class InternalSystemStorage {

    private static InternalSystemStorage systemStorage;

    public InternalSystemStorage(){
        systemStorage = this;
    }
    public static InternalSystemStorage getInstance(){
        return  systemStorage;
    }


    public static String getInternalTotalSpace(){
        String size = "";
        File external_storage = new File(getInternalStoragePath());
        if (external_storage.exists()) {
            size = totalSize(external_storage);
        }
        return size;
    }
    public static String getInternalFreeSpace(){

        float storage = 0;
        File external_storage = new File(getInternalStoragePath());
        if (external_storage.exists()) {
            storage = external_storage.getFreeSpace();
        }
        return formatSize(storage);
    }
    public static File[] getInternalFiles(){
        File root = new File(getInternalStoragePath());

        return root.listFiles();
    }


    private  static String getInternalStoragePath() {
        File storage = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        String external_storage_path = "";
        String path = "";

        if(storage.exists()) {
            File[] files = storage.listFiles();

            for (File file : files) {
                if (file.exists()) {
                    try {
                        if (Environment.isExternalStorageEmulated(file)) {
                            //Storage is removable
                            external_storage_path = file.getAbsolutePath();

                            break;
                        }
                    }catch(Exception e) {
                        Log.e("TAG", e.toString());
                    }
                }
            }
        }

        if (!external_storage_path.isEmpty()) {

            path = external_storage_path;
        }
        return path;
    }
    private static String totalSize(File file) {
        StatFs stat = new StatFs(file.getPath());
        long blockSize, totalBlocks;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();
            totalBlocks = stat.getBlockCountLong();
        } else {
            blockSize = stat.getBlockSize();
            totalBlocks = stat.getBlockCount();
        }

        return formatSize(totalBlocks * blockSize);
    }

    private static String formatSize(float size) {
        String suffix = null;

        if (size >= 1024) {
            suffix = "KB";
            size /= 1024;
            if (size >= 1024) {
                suffix = "MB";
                size /= 1024;
            }
            if (size >= 1024) {
                suffix = "GB";
                size /= 1024;
            }

        }

        StringBuilder resultBuilder = new StringBuilder();

        // Send all output to the Appendable object sb
        Formatter formatter = new Formatter(resultBuilder, Locale.US);
        formatter.format("%.2f", size);

        if (suffix != null)resultBuilder.append(suffix);

        return resultBuilder.toString();
    }

    public static int getSpaceUsageRatio(){


        float storageFreeSapce      = 0;
        float storageTotalSapce  = 0;
        File external_storage = new File(getInternalStoragePath());
        if (external_storage.exists()) {
            storageFreeSapce = external_storage.getFreeSpace();
            storageTotalSapce = external_storage.getTotalSpace();
        }
      float ratio = ((storageTotalSapce- storageFreeSapce)/storageTotalSapce) * 100;
    return (int)ratio;
    }
}
