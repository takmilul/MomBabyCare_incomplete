package com.hackathon.appsoul.mombabycare.activity;

/**
 * Created by A. A. M. Sharif on 28-Apr-16.
 */

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FTPService extends IntentService {

    public FTPService(){
        super("FTPService");
    }


    public static String uploadFolder = "/upload/";
    public static int port = 21;
    public static String username = "ftpuser";
    public static String password = "dsk@credit123";
    public static String host = "115.127.19.148";

    @Override
    protected void onHandleIntent(Intent intent) {

        while(!ChatDoctorActivity.isNetworkConnected()){
            SystemClock.sleep(5000);
        }

        String path = intent.getStringExtra("path");
        if(intent.getStringExtra("task").equals("upload")) {
            //Upload files to server
            boolean result = Ftp.upload(host, port, uploadFolder, username, password, path);
            ChatDoctorActivity.setUploadResult(result);
            String name = path.substring(path.lastIndexOf("/") + 1);
            if (result) {
                ChatDoctorActivity.showFileUploaded(name);
            }
            ChatDoctorActivity.onPostExecute(name);
        } else if(intent.getStringExtra("task").equals("download")){
            //Download files from server
            String remoteFilePath = intent.getStringExtra("remote_file");
            int status = Ftp.download(host, port, username, password, remoteFilePath, path);
            if(status == 0)
                Log.d("** FTPService **", "Downloaded");
            else if(status == 1)
                Log.d("** FTPService **", "File does not exists in server");
            else
                Log.d("** FTPService **", "Downloaded Failed");
        }

    }
}
