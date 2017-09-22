package com.hackathon.appsoul.mombabycare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hackathon.appsoul.mombabycare.R;
import com.hackathon.appsoul.mombabycare.SharedPreference;

public class MainActivity extends AppCompatActivity {

   SharedPreference sharedPreference;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      sharedPreference = new SharedPreference(this);
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

   public void login(View view) {
      Intent intent = new Intent(this, ProfileListForMomActivity.class);
      startActivity(intent);
   }
}
