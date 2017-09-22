package com.hackathon.appsoul.mombabycare.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hackathon.appsoul.mombabycare.R;
import com.hackathon.appsoul.mombabycare.SharedPreference;
import com.hackathon.appsoul.mombabycare.asynctask.GetMomInfo;
import com.hackathon.appsoul.mombabycare.classes.GradientBackgroundPainter;
import com.hackathon.appsoul.mombabycare.server.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {
   
   View view_password;
   View view_username;
   Animation anim;
   
   SharedPreference sharedPreference;
   ProgressBar pbbar;
   SQLConnection connectionClass;
   
   EditText userMobileNoET;
   EditText userPasswordET;
   private GradientBackgroundPainter gradientBackgroundPainter;
   String userType, userMobileNo, userPassword, userId, userMobileNoChk, userPasswordChk;
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_login);
      
      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);
      toolbar.setTitleTextColor(getResources().getColor(R.color.white_70_transparent));
      toolbar.setNavigationIcon(R.drawable.back_button1);
      toolbar.setNavigationOnClickListener(new OnClickListener() {
         @Override
         public void onClick(View v) {
            onBackPressed();
         }
      });
      
      userMobileNoET = (EditText) findViewById(R.id.userMobileNoET);
      userPasswordET = (EditText) findViewById(R.id.userPasswordET);
      
      anim = AnimationUtils.loadAnimation(this, R.anim.scale);
      
      view_password = findViewById(R.id.underline_view_password);
      view_username = findViewById(R.id.underline_view_username);
      
      userMobileNoET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
         @Override
         public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
               view_username.setVisibility(View.VISIBLE);
               view_username.startAnimation(anim);
            } else {
               view_username.setVisibility(View.INVISIBLE);
            }
         }
      });
      
      userPasswordET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
         @Override
         public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
               view_password.setVisibility(View.VISIBLE);
               view_password.startAnimation(anim);
            } else {
               view_password.setVisibility(View.INVISIBLE);
            }
         }
      });
      
      
      
      sharedPreference = new SharedPreference(this);
      
      userMobileNoChk = "";
      userType = "";
      userPassword = "";
      userId = "";
      userPasswordChk = "";
      
      connectionClass = new SQLConnection();
      
      pbbar = (ProgressBar) findViewById(R.id.pbbarLoginActvity);
      
      pbbar.setVisibility(View.GONE);
      
      LinearLayout backgroundImage = (LinearLayout) findViewById(R.id.root_view);
      gradientBackgroundPainter = new GradientBackgroundPainter(backgroundImage);
      gradientBackgroundPainter.start();
   }
   
   @Override
   protected void onDestroy() {
      super.onDestroy();
      gradientBackgroundPainter.stop();
   }
   
   public void onClickProfile(View view) {
      
      Intent intent = new Intent(getApplicationContext(), CreateMotherProfileActivity.class);
      startActivity(intent);
   }
   
   public void onClickProfileView(View view) {
      
      Intent intent = new Intent(getApplicationContext(), ProfileListForMomActivity.class);
      startActivity(intent);
   }
   
   public void createProfile(View view) {
      Intent intent = new Intent(this, CreateMotherProfileActivity.class);
      startActivity(intent);
   }
   
   public void login(View view) throws ExecutionException, InterruptedException {
      
      userMobileNo = userMobileNoET.getText().toString();
      
      userPassword = userPasswordET.getText().toString();
      
      FillList fillList = new FillList();
      try {
         fillList.execute("").get();
      } catch (InterruptedException e) {
         e.printStackTrace();
      } catch (ExecutionException e) {
         e.printStackTrace();
      }
      
      //Toast.makeText(getApplicationContext(), userId, Toast.LENGTH_LONG).show();
      if (!userMobileNoChk.equals("")) {
         if (userType.equals("HW")) {
            Intent intent = new Intent(this, ProfileListForMomActivity.class);
            sharedPreference.setHWId(userId);
            startActivity(intent);
         } else {
            Intent intent = new Intent(this, DashBoardMomActivity.class);
            sharedPreference.setMomId(userId);
            GetMomInfo momInfo = new GetMomInfo(getBaseContext());
            momInfo.execute("");
            startActivity(intent);
         }
      } else {
         Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_LONG).show();
      }
      
   }
   
   public class FillList extends AsyncTask<String, String, String> {
      String z = "";
      
      @Override
      protected void onPreExecute() {
         
         pbbar.setVisibility(View.VISIBLE);
      }
      
      @Override
      protected void onPostExecute(String r) {
         
         pbbar.setVisibility(View.GONE);
         Toast.makeText(getApplicationContext(), r, Toast.LENGTH_SHORT).show();
         
      }
      
      @Override
      protected String doInBackground(String... params) {
         
         try {
            Connection con = connectionClass.CONN();
            if (con == null) {
               z = "Error in connection with SQL server";
            } else {
               //               String isActive="A";
               //               String server_Client="S";
               //               String checkupType="FM";
               
               // String query = "SELECT CheckupName, CheckupNormalValue,Remarks from tbl_CheckupMaster";
               
               String query = "Exec UserLogin_SP " + "'" + userMobileNo + "'" + "," + "'" + userPassword + "'";
               PreparedStatement ps = con.prepareStatement(query);
               ResultSet rs = ps.executeQuery();
               
               int i = 0;
               while (rs.next()) {
                  i++;
                  userMobileNoChk = rs.getString("UserMobileNo");
                  userPasswordChk = rs.getString("Password");
                  userType = rs.getString("UserType");
                  userId = rs.getString("UserId");
               }
               
               z = "Success";
            }
         } catch (Exception ex) {
            z = "Error retrieving data from table";
         }
         return z;
      }
   }
}
