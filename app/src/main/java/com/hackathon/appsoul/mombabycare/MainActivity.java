package com.hackathon.appsoul.mombabycare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.hackathon.appsoul.mombabycare.activity.SignupLoginActivity;
import com.hackathon.appsoul.mombabycare.classes.GradientBackgroundPainter;

public class MainActivity extends AppCompatActivity {
   
   private GradientBackgroundPainter gradientBackgroundPainter;
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      
      LinearLayout backgroundImage = (LinearLayout) findViewById(R.id.activity_main);
      gradientBackgroundPainter = new GradientBackgroundPainter(backgroundImage);
      gradientBackgroundPainter.start();
   }
   
   @Override
   protected void onDestroy() {
      super.onDestroy();
      gradientBackgroundPainter.stop();
   }
   
   public void signup(View view) {
      startActivity(new Intent(this, SignupLoginActivity.class).putExtra("type", "signup"));
   }
   
   public void loginNew(View view) {
      startActivity(new Intent(this, SignupLoginActivity.class).putExtra("type", "login"));
   }
}
