package com.hackathon.appsoul.mombabycare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.hackathon.appsoul.mombabycare.R;
import com.hackathon.appsoul.mombabycare.classes.GradientBackgroundPainter;

public class SignupLoginActivity extends AppCompatActivity implements OnItemClickListener {
   
   private boolean signup;
   private ListView signupLoginLv;
   private LinearLayout signupLogin;
   private GradientBackgroundPainter gradientBackgroundPainter;
   private String[] signupItem = {"I Am A Pregnant Woman", "I Am A New Mother", "I Am A Guest"};
   private String[] loginItem = {"I Am A Pregnant Woman", "I Am A New Mother", "I Am A Doctor", "I Am A Health Worker", "I Am A Guest"};
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_signup_login);
      
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

//      getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);//or add in style.xml
   
      /*ColorDrawable newColor = new ColorDrawable(getResources().getColor(R.color.white_30_transparent));//your color from res
      newColor.setAlpha(128);//from 0(0%) to 256(100%)
      actionBar.setBackgroundDrawable(newColor);*/
      
      signupLoginLv = (ListView) findViewById(R.id.signupLoginLv);
      
      signupLogin = (LinearLayout) findViewById(R.id.activity_signup_login);
      gradientBackgroundPainter = new GradientBackgroundPainter(signupLogin);
      gradientBackgroundPainter.start();
      
      String type[];
      if (getIntent().getStringExtra("type").equals("signup")) {
         type = signupItem;
         signup = true;
      } else {
         type = loginItem;
         signup = false;
      }
      signupLoginLv.setAdapter(new ArrayAdapter<String>(this, R.layout.signup_login_row, R.id.signupLoginTypeListItem, type));
      signupLoginLv.setOnItemClickListener(this);
      
      
      
   }
   
   @Override
   protected void onDestroy() {
      gradientBackgroundPainter.stop();
      super.onDestroy();
   }
   
   @Override
   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      if (signup) {
         startActivity(new Intent(this, CreateMotherProfileActivity.class));
      } else {
         startActivity(new Intent(this, LoginActivity.class));
      }
   }
   
   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      
      return super.onOptionsItemSelected(item);
   }
}
