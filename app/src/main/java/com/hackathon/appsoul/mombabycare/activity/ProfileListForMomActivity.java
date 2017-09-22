package com.hackathon.appsoul.mombabycare.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hackathon.appsoul.mombabycare.R;
import com.hackathon.appsoul.mombabycare.SharedPreference;
import com.hackathon.appsoul.mombabycare.adapters.MomProfileListAdapter;
import com.hackathon.appsoul.mombabycare.classes.GradientBackgroundPainter;
import com.hackathon.appsoul.mombabycare.model.PregnantMother;
import com.hackathon.appsoul.mombabycare.recyclerview.OnItemClickListener.ClickListener;
import com.hackathon.appsoul.mombabycare.recyclerview.OnItemClickListener.RecyclerTouchListener;
import com.hackathon.appsoul.mombabycare.server.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
   lines added by TONMOY: line: 47, 54-60, 185-189.
 */

public class ProfileListForMomActivity extends AppCompatActivity {

   SQLConnection connectionClass;
   ProgressBar pbbar;
   //ListView lstMomProfile;
   SharedPreference sharedPreference;
   String prfileidMom;
   RecyclerView lstMomProfile;
   ArrayList<PregnantMother> motherArrayList;
   PregnantMother pregnantMother;
   MomProfileListAdapter momProfileListAdapter;
   GradientBackgroundPainter gradientBackgroundPainter;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_profile_list_for_mom);
   
      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);
      getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button1);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      LinearLayout background = (LinearLayout) findViewById(R.id.momProfileList);
      gradientBackgroundPainter = new GradientBackgroundPainter(background);
      gradientBackgroundPainter.start();
      
      connectionClass = new SQLConnection();
      motherArrayList = new ArrayList<>();
      sharedPreference = new SharedPreference(getBaseContext());
      pbbar = (ProgressBar) findViewById(R.id.pbbarProfile);
      pbbar.setVisibility(View.GONE);

      //lstMomProfile = (ListView) findViewById(R.id.lstMomProfile);
      lstMomProfile = (RecyclerView) findViewById(R.id.lstMomProfile);
      prfileidMom = "";

      FillList fillList = new FillList();
      fillList.execute("");

       /*List<String> list = Arrays.asList("tonmoy","sayed","sharif","tipu");
       MomProfileListAdapter adapter = new MomProfileListAdapter(getBaseContext(), list);
       lstMomProfile.setAdapter(adapter);*/
   }

   public class FillList extends AsyncTask<String, String, String> {
      String z = "";

      List<Map<String, String>> prolist = new ArrayList<Map<String, String>>();

      @Override
      protected void onPreExecute() {
         pbbar.setVisibility(View.VISIBLE);
      }

      @Override
      protected void onPostExecute(String r) {

         pbbar.setVisibility(View.GONE);
         Toast.makeText(ProfileListForMomActivity.this, r, Toast.LENGTH_SHORT).show();

         /*String[] from = {"A", "B", "C"};

         int[] views = {R.id.lblProfileId, R.id.lblProfileName, R.id.lblMomAge};
         final SimpleAdapter ADA = new SimpleAdapter(ProfileListForMomActivity.this, prolist, R.layout.profile_list_template_for_mom, from, views);
         lstMomProfile.setAdapter(ADA);*/

         momProfileListAdapter = new MomProfileListAdapter(getBaseContext(), motherArrayList);
         LayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
         lstMomProfile.setLayoutManager(layoutManager);
         lstMomProfile.setAdapter(momProfileListAdapter);

         lstMomProfile.addOnItemTouchListener(new RecyclerTouchListener(getBaseContext(), lstMomProfile, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
               Intent intent = new Intent(ProfileListForMomActivity.this, DashBoardMomActivity.class);
               //sharedPreference.setMomId(momProfileListAdapter.getItem(position).getName(), momProfileListAdapter.getItem(position).getId());
               sharedPreference.setMotherInfo(motherArrayList.get(position));

               startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
         }));

         /*lstMomProfile.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
               HashMap<String, Object> obj = (HashMap<String, Object>) ADA.getItem(arg2);
               prfileidMom = (String) obj.get("A");
               String profileNameMom = (String) obj.get("B");
               String momDateOfBirth = (String) obj.get("C");

               Intent intent = new Intent(ProfileListForMomActivity.this, DashBoardMomActivity.class);
               intent.putExtra("MOM_NAME", profileNameMom);
               intent.putExtra("MOM_ID", prfileidMom);

               startActivity(intent);

            }
         });*/

      }

      @Override
      protected String doInBackground(String... params) {

         try {
            Connection con = connectionClass.CONN();
            if (con == null) {
               z = "Error in connection with SQL server";
            }
            else {
               //String query = "SELECT Id,Name,NewMother_Pragnant FROM tbl_ProfileNew_PragnantMother";
               //String query = "SELECT Id,Name,DateOfBirth,BloodGroup,PragnantDueDate,WeightBFPrag,Height FROM tbl_ProfileNew_PragnantMother" ;

               /*String query =  "SELECT m.Id, m.Name, m.Address, m.MobileNo, m.DateOfBirth, m.BloodGroup, m.PragnantDueDate, m.WeightBFPrag, m.Height, m.NewMother_Pragnant, m.HW_ID,org.Org_Staff_Name, org.Org_Staff_MobileNo, org.Org_ContactNo FROM tbl_ProfileNew_PragnantMother AS m INNER JOIN tbl_OrgHealthStaff AS org ON m.HW_ID = org.Org_Staff_ID WHERE (m.Id = 1)" ;*/

               int HWId = Integer.parseInt(sharedPreference.getHwId());
               String query = "EXEC [HWForMotherProfile_SP]" + HWId;
               PreparedStatement ps = con.prepareStatement(query);
               ResultSet rs = ps.executeQuery();

               ArrayList<String> data1 = new ArrayList<String>();
               while (rs.next()) {
                  /*Map<String, String> datanum = new HashMap<String, String>();
                  datanum.put("A", rs.getString("Id"));
                  datanum.put("B", rs.getString("Name"));
                  datanum.put("C", rs.getString("NewMother_Pragnant"));
                  prolist.add(datanum);*/

                  pregnantMother = new PregnantMother(rs.getString("Id"), rs.getString("Name"), rs.getString("DateOfBirth"), rs.getString("BloodGroup"), rs.getString("WeightBFPrag"), rs.getString("Height"), rs.getString("NewMother_Pragnant"));

                  /*pregnantMother = new PregnantMother(rs.getString("Name"),rs.getString("DateOfBirth"),rs.getString("BloodGroup"),rs.getString("WeightBFPrag"),rs.getString("Height"),rs.getString("PragnantDueDate"));*/

                  motherArrayList.add(pregnantMother);
               }

               z = "Success";
            }
         } catch (Exception ex) {
            z = "Error retrieving data from table";
         }
         return z;
      }
   }
   
   @Override
   protected void onDestroy() {
      gradientBackgroundPainter.stop();
      super.onDestroy();
   }
}
