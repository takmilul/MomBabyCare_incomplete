package com.hackathon.appsoul.mombabycare.child.dashboard;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hackathon.appsoul.mombabycare.R;
import com.hackathon.appsoul.mombabycare.activity.DoctorHelpFragment;
import com.hackathon.appsoul.mombabycare.classes.GradientBackgroundPainter;
import com.hackathon.appsoul.mombabycare.fragments.CheckupChildRecyclerViewFragment;
import com.hackathon.appsoul.mombabycare.fragments.ChildDetailsFragment;
import com.hackathon.appsoul.mombabycare.fragments.VaccineFragment;
import com.hackathon.appsoul.mombabycare.model.Event;

/*
   lines added by TONMOY: line: 51, 61-67, 94-95, 170-174.
 */

public class DashBoardChildActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
   
   public static final String DEBUG_TAG = "MOM_BABY_CARE";
   
   public static final String REQUEST_MODE = "mode";
   public static final String REQUEST_MODE_NEW = "new";
   public static final String REQUEST_MODE_EDIT = "edit";
   public static final String REQUEST_PRIMARY_KEY = "pk";
   
   public static final int REQUEST_CODE_DIARY_ENTRY = 1;
   private static String KEY_DIALOG_TITLE = "DIALOG_TITLE";
   private static final int DIALOG_ID_CONFIRM = 0;
   
   private Integer eventId;
   private boolean dialogVisible;
   
   //SharedPreference
   public static final String PREFERENCES = "preferences";
   Fragment fragment;
   
   GradientBackgroundPainter gradientBackgroundPainter;
   boolean checkup;
   TextView txtMomName;
   String momId = "";
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_dash_board_child);
      
      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);
      toolbar.setTitleTextColor(getResources().getColor(R.color.white_70_transparent));
      CoordinatorLayout background = (CoordinatorLayout) findViewById(R.id.colorFlow);
      gradientBackgroundPainter = new GradientBackgroundPainter(background);
      gradientBackgroundPainter.start();
      
      /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
      fab.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
         }
      }); */
      
      DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
      ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
      drawer.setDrawerListener(toggle);
      toggle.syncState();
      
      NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
      navigationView.setNavigationItemSelectedListener(this);
      
      fragment = new ChildDetailsFragment();
      getFragmentManager().beginTransaction().add(R.id.childDetailFragment, fragment).commit();
   }
   
   @Override
   public void onBackPressed() {
      DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
      if (drawer.isDrawerOpen(GravityCompat.START)) {
         drawer.closeDrawer(GravityCompat.START);
      } else {
//         super.onBackPressed();
         finish();
      }
   }
   
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.dash_board_child, menu);
      return true;
   }
   
   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      // Handle action bar item clicks here. The action bar will
      // automatically handle clicks on the Home/Up button, so long
      // as you specify a parent activity in AndroidManifest.xml.
      int id = item.getItemId();
      
      //noinspection SimplifiableIfStatement
      if (id == R.id.action_settings) {
         return true;
      }
      
      return super.onOptionsItemSelected(item);
   }
   
   @SuppressWarnings("StatementWithEmptyBody")
   @Override
   public boolean onNavigationItemSelected(MenuItem item) {
      // Handle navigation view item clicks here.
      int id = item.getItemId();
      View view;
      if (id == R.id.nav_details_child) {
         fragment = new ChildDetailsFragment();
         
      } else if (id == R.id.nav_checkup_child) {
         // Handle the camera action
         fragment = new CheckupChildRecyclerViewFragment();
         getFragmentManager().beginTransaction().replace(R.id.childDetailFragment, fragment).commit();
         checkup = true;
      } else if (id == R.id.nav_vaccine_child) {
         fragment = new VaccineFragment();
         getFragmentManager().beginTransaction().replace(R.id.childDetailFragment, fragment).commit();
         checkup = true;
      } else if (id == R.id.nav_weight_child) {
         
      } else if (id == R.id.nav_diet_child) {
         
      } else if (id == R.id.nav_phc_child) {
         
      } else if (id == R.id.nav_drHelp_child) {
         fragment = new DoctorHelpFragment();
      } else if (id == R.id.nav_survey_child) {
         
      }
      FragmentManager manager = getFragmentManager();
      FragmentTransaction transaction = manager.beginTransaction();
      transaction.replace(R.id.childDetailFragment, fragment);
      transaction.addToBackStack(null);
      transaction.commit();
      
      DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
      drawer.closeDrawer(GravityCompat.START);
      return true;
   }
   
   void showConfirmationDialog(int dialogTitleId, Event event) {
      this.eventId = event.getId();
      
      Bundle params = new Bundle();
      params.putString(KEY_DIALOG_TITLE, getString(dialogTitleId));
      dialogVisible = true;
      showDialog(DIALOG_ID_CONFIRM, params);
   }
   
   @Override
   protected void onDestroy() {
      gradientBackgroundPainter.stop();
      super.onDestroy();
   }
}
