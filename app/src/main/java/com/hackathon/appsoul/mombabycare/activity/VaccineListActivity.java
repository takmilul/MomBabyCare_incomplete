package com.hackathon.appsoul.mombabycare.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.hackathon.appsoul.mombabycare.R;
import com.hackathon.appsoul.mombabycare.server.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VaccineListActivity extends AppCompatActivity {
   
   ProgressBar pbbar;
   ListView lstMomProfile;
   
   SQLConnection connectionClass;
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_vaccine_list);
      
      connectionClass = new SQLConnection();
      pbbar = (ProgressBar) findViewById(R.id.pbbarProfile);
      pbbar.setVisibility(View.GONE);
      
      lstMomProfile = (ListView) findViewById(R.id.lstMomProfile);
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
         Toast.makeText(VaccineListActivity.this, r, Toast.LENGTH_SHORT).show();
         
         String[] from = {"A", "B", "C"};
         
         int[] views = {R.id.lblProfileId, R.id.lblProfileName, R.id.lblMomStatus};
         final SimpleAdapter ADA = new SimpleAdapter(VaccineListActivity.this,
               prolist, R.layout.profile_list_template_for_mom, from, views);
         lstMomProfile.setAdapter(ADA);
         
         lstMomProfile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {
               HashMap<String, Object> obj = (HashMap<String, Object>) ADA
                     .getItem(arg2);
               String prfileidMom = (String) obj.get("A");
               String profileNameMom = (String) obj.get("B");
               String momDateOfBirth = (String) obj.get("C");
               
               Intent intent = new Intent(VaccineListActivity.this, DashBoardMomActivity.class);
               intent.putExtra("MOM_NAME", profileNameMom);
               intent.putExtra("MOM_ID", prfileidMom);
               startActivity(intent);
            }
         });
         
      }
      
      @Override
      protected String doInBackground(String... params) {
         
         try {
            Connection con = connectionClass.CONN();
            if (con == null) {
               z = "Error in connection with SQL server";
            } else {
               String query = "select Id,Name,[dbo].fn_GetAge(DateOfBirth,getdate()) DateOfBirth from tbl_ProfileNew_PragnantMother";
               PreparedStatement ps = con.prepareStatement(query);
               ResultSet rs = ps.executeQuery();
               
               ArrayList<String> data1 = new ArrayList<String>();
               while (rs.next()) {
                  Map<String, String> datanum = new HashMap<String, String>();
                  datanum.put("A", rs.getString("Id"));
                  datanum.put("B", rs.getString("Name"));
                  datanum.put("C", rs.getString("DateOfBirth"));
                  prolist.add(datanum);
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
