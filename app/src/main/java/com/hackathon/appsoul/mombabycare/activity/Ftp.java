package com.hackathon.appsoul.mombabycare.activity;

/**
 * Created by A. A. M. Sharif on 27-Apr-16.
 */
import android.util.Log;


import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class Ftp {
    private static FTPClient ftpClient;

    static public boolean upload(String host, int port, String folder, String userName, String password, String path){
        ftpClient = new FTPClient();

        try {

            ftpClient.connect(host, port);

            if (ftpClient.login(userName, password))
            {
                ftpClient.enterLocalPassiveMode(); // important!
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

                String name = path.substring(path.lastIndexOf("/") + 1);

                FileInputStream in = new FileInputStream(new File(path));

                boolean result = ftpClient.storeFile(folder + name, in);

                in.close();
                int code = ftpClient.getReplyCode();
                boolean succeed = (result && 226==code);
                if (succeed) {

                    ftpClient.sendSiteCommand("chmod " + "755 " + folder + name);

                    Log.d("*** Ftp ***", "Uploaded");
                }else{
                    Log.d("*** Ftp ***", "Upload failed");
                }

                ftpClient.logout();
                ftpClient.disconnect();
                ftpClient = null;

                return succeed;
            } else { // login failed
                return false;
            }
        } catch (IOException e) {
            Log.d("*** Ftp ***", e.getMessage(), e);
            ftpClient = null;
            return false;
        }
    }

    //returns 0 if downloaded successfully otherwise returns 1 when file does not exists in server or -1 if fails to download
    static public int download(String host, int port, String user, String pwd, String remoteFilePath, String localFilePath) {
        ftpClient = new FTPClient();

        FileOutputStream fos = null;
        try {
            int reply;
            ftpClient.connect(host, port);
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                ftpClient = null;
                return -1; //download failed because FTP Server denied to connect (reply code is not OK)
            }
            if(ftpClient.login(user, pwd)) {
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
            } else {
                ftpClient.disconnect();
                ftpClient = null;
                return -1; //download failed because of login failure to FTP Server
            }

            //downloads file and opens local file for writing the downloaded file
            File file = new File(localFilePath);
            if(file.createNewFile() && file.exists()) {
                fos = new FileOutputStream(file);
                //checks if the file exists in the server and then downloads
                if(!ftpClient.retrieveFile(remoteFilePath, fos)) {
                    Log.d("*** Ftp ***", "File does not exists in Server");
                    return 1;
                }
            } else {
                Log.d("*** Ftp ***", "Failed to create file for saving");
            }

        } catch (IOException e) {
            Log.d("*** Ftp ***", e.getMessage(), e);
            ftpClient = null;
            return -1;
        } finally {
            if(fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    Log.d("*** Ftp ***", "Failed to close file", e);
                }
            }
        }

        //disconnect from FTP server after download
        if (ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();

            } catch (IOException f) {
                // do nothing as file is already downloaded from FTP server
            }
        }

        ftpClient = null; // frees memory from ftpClient reference
        return 0;
    }
}
