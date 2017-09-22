package com.hackathon.appsoul.mombabycare.activity;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.hackathon.appsoul.mombabycare.R;
import com.hackathon.appsoul.mombabycare.classes.GradientBackgroundPainter;
import com.hackathon.appsoul.mombabycare.datastorage.DataBaseHelper;
import com.hackathon.appsoul.mombabycare.datastorage.DataSource;
import com.hackathon.appsoul.mombabycare.fragments.CheckupMomRecyclerViewFragment;
import com.hackathon.appsoul.mombabycare.fragments.VaccineFragment;
import com.hackathon.appsoul.mombabycare.model.Event;

import java.io.File;

/*
   lines added by TONMOY: line: 56, 66-71, 113-114, 260-264.
 */

public class DashBoardMomActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
   public static final String DEBUG_TAG = "MOM_BABY_CARE";
   
   private DataBaseHelper helper;
   private DataSource dataSource;
   
   private static String KEY_DIALOG_TITLE = "DIALOG_TITLE";
   private static final int DIALOG_ID_CONFIRM = 0;
   
   private Integer eventId;
   private String eventAudioFile;
   private String eventPhotoFile;
   private boolean dialogVisible;
   
   Fragment fragment;
   
   boolean checkup;
   TextView txtMomName;
   String momId = "";
   GradientBackgroundPainter gradientBackgroundPainter;
   private DoctorHelpFragment.ChatHistoryListViewAdapter chatHistoryListViewAdapter;
   static DashBoardMomActivity thisActivity = null;
   
   void setChatHistoryListViewAdapter(DoctorHelpFragment.ChatHistoryListViewAdapter chatHistoryListViewAdapter) {
      this.chatHistoryListViewAdapter = chatHistoryListViewAdapter;
   }
   
   DoctorHelpFragment.ChatHistoryListViewAdapter getChatHistoryListViewAdapter(){
      return this.chatHistoryListViewAdapter;
   }
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_dash_board_mom);
      thisActivity = this;
      
      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);
      toolbar.setTitleTextColor(getResources().getColor(R.color.white_70_transparent));
      CoordinatorLayout background = (CoordinatorLayout) findViewById(R.id.contentWrapperLayout);
      gradientBackgroundPainter = new GradientBackgroundPainter(background);
      gradientBackgroundPainter.start();
      
      dataSource = new DataSource(this);


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
      DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
      ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
      drawer.setDrawerListener(toggle);
      toggle.syncState();
      
      NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
      navigationView.setNavigationItemSelectedListener(this);

     /*   txtMomName= (TextView) findViewById(R.id.txtMomName);

        Intent intent=getIntent();

        momId=intent.getStringExtra("MOM_ID");

        txtMomName.setText(intent.getStringExtra("MOM_NAME"));
    */
      
      fragment = new DetailsFragment();
      getFragmentManager().beginTransaction().add(R.id.contentFragment, fragment).commit();
      
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
      
      getMenuInflater().inflate(R.menu.dash_board_mom, menu);
      //menu.getItem(1).setVisible(false);
      return true;
   }
   
   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      // Handle action bar item clicks here. The action bar will
      // automatically handle clicks on the Home/Up button, so long
      // as you specify a parent activity in AndroidManifest.xml.
      int id = item.getItemId();
      
      //noinspection SimplifiableIfStatement
      if (id == R.id.addChild) {
         
         Intent intent = new Intent(DashBoardMomActivity.this, CreateChildProfileActivity.class);
         intent.putExtra("MOM_ID", momId);
         startActivity(intent);
         return true;
      }
      
      return super.onOptionsItemSelected(item);
   }
   
   @SuppressWarnings("StatementWithEmptyBody")
   @Override
   public boolean onNavigationItemSelected(MenuItem item) {
      // Handle navigation view item clicks here.
      int id = item.getItemId();
      if (id == R.id.nav_details) {
         fragment = new DetailsFragment();
      } else if (id == R.id.nav_checkup) {
         // Handle the camera action
         fragment = new CheckupMomRecyclerViewFragment();
         checkup = true;
      } else if (id == R.id.nav_vaccine) {
         fragment = new VaccineFragment();
         checkup = true;
      } else if (id == R.id.nav_weight) {
         
      } else if (id == R.id.nav_drHelp) {
         fragment = new DoctorHelpFragment();
      } else if (id == R.id.nav_survey) {
         
      }
      FragmentManager manager = getFragmentManager();
      FragmentTransaction transaction = manager.beginTransaction();
      transaction.replace(R.id.contentFragment, fragment);
      transaction.addToBackStack(null);
      transaction.commit();
      
      DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
      drawer.closeDrawer(GravityCompat.START);
      return true;
   }
   
   void showConfirmationDialog(int dialogTitleId, Event event) {
      this.eventId = event.getId();

//      Bundle params = new Bundle();
//      params.putString(KEY_DIALOG_TITLE, getString(dialogTitleId));
//      dialogVisible = true;
//      showDialog(DIALOG_ID_CONFIRM, params);
      
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setTitle(getString(dialogTitleId));
      builder.setMessage(getString(R.string.dlg_prompt));
      builder.setPositiveButton(getString(R.string.dlg_yes), new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int which) {
            Event event = dataSource.getEvent(eventId);
            //Deletes from Database
            boolean deleted=dataSource.delete(eventId);
            if (deleted){
               Toast.makeText(getApplicationContext(),"Deleted from DB",Toast.LENGTH_LONG).show();
            }
            else {
               Toast.makeText(getApplicationContext(), "Deleted from DB Failed", Toast.LENGTH_LONG).show();
            }
            //Delete audio and image files
            deleteMediaFile(event.getAudioFile());
            deleteMediaFile(event.getPhotoFile());
            thisActivity.chatHistoryListViewAdapter.refresh(DoctorHelpFragment.RefreshType.ON_DELETE);
         }
      });
      builder.setNegativeButton(getString(R.string.dlg_no), new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
         }
      });
      
      builder.create().show();
   }
   
   // Deletes the file
   private void deleteMediaFile(String fileName){
      if (fileName != null && fileName.length() > 0){
         File file = new File(fileName);
         if (!file.delete()){
            Toast.makeText(getBaseContext(), "can't delete " + fileName, Toast.LENGTH_LONG).show();
         }
      }
   }
   
   /**
    * Called by the system when the app calls showDialog
    */
//   @Override
//   protected Dialog onCreateDialog(int id, Bundle params) {
//      AlertDialog.Builder builder = new AlertDialog.Builder(this);
//      builder.setTitle(params.getString(KEY_DIALOG_TITLE));
//
//      if (id == DIALOG_ID_CONFIRM) {
//         // If title and message not set here, no title or message area will be generated
//         builder.setMessage(getString(R.string.dlg_prompt));
//         builder.setPositiveButton(getString(R.string.dlg_yes), new DialogButtonListener(DIALOG_ID_CONFIRM));
//         builder.setNegativeButton(getString(R.string.dlg_no), new DialogButtonListener(DIALOG_ID_CONFIRM));
//      }
//
//      // Create the AlertDialog object and return it
//      return builder.create();
//   }
   
   /**
    * A listener for dialog button clicks. It stores the id of the dialog to
    * allow its use by multiple dialog types
    */
//   private class DialogButtonListener implements DialogInterface.OnClickListener {
//      private int dialogId;
//
//      DialogButtonListener(int dialogId) {
//         this.dialogId = dialogId;
//      }
//
//      @Override
//      public void onClick(DialogInterface dialog, int which) {
//         if (dialogId == DIALOG_ID_CONFIRM) {
//            if (which == DialogInterface.BUTTON_POSITIVE) {
//               Event event = dataSource.getEvent(eventId);
//               //TODO Delete from Database
//               boolean deleted = dataSource.delete(eventId);
//               if (deleted) {
//                  Toast.makeText(getApplicationContext(), "Deleted from DB", Toast.LENGTH_LONG).show();
//               } else {
//                  Toast.makeText(getApplicationContext(), "Deleted from DB Failed", Toast.LENGTH_LONG).show();
//               }
//               //Delete audio and image files
//               deleteMediaFile(event.getAudioFile());
//               deleteMediaFile(event.getPhotoFile());
//               thisActivity.chatHistoryListViewAdapter.refresh(DoctorHelpFragment.RefreshType.ON_DELETE);
//
//            }
//         }
//         dialogVisible = false;
//      }
//
//      private void deleteMediaFile(String fileName) {
//         if (fileName != null && fileName.length() > 0) {
//            File file = new File(fileName);
//            if (!file.delete()) {
//               Toast.makeText(getBaseContext(), "can't delete " + fileName, Toast.LENGTH_LONG).show();
//            }
//         }
//      }
//   }
   
   /**
    * Called by the system; associated with showDialog
    */
//   @Override
//   protected void onPrepareDialog(int id, final Dialog dialog, Bundle params) {
//      if (id == DIALOG_ID_CONFIRM) {
//         ((AlertDialog) dialog).setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dlg_yes), new DialogButtonListener(DIALOG_ID_CONFIRM));
//         ((AlertDialog) dialog).setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.dlg_no), new DialogButtonListener(DIALOG_ID_CONFIRM));
//         ((AlertDialog) dialog).setTitle(params.getString(KEY_DIALOG_TITLE));
//      }
//   }
   
   
   @Override
   protected void onDestroy() {
      gradientBackgroundPainter.stop();
      super.onDestroy();
   }
}
