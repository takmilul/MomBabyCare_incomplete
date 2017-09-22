package com.hackathon.appsoul.mombabycare.asynctask;//

import android.content.Context;
import android.os.AsyncTask;

import com.hackathon.appsoul.mombabycare.SharedPreference;
import com.hackathon.appsoul.mombabycare.model.PregnantMother;
import com.hackathon.appsoul.mombabycare.server.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GetMomInfo extends AsyncTask<String, String, String> {

   Context context;
   SQLConnection connectionClass;
   String z = "";

   public GetMomInfo(Context context) {
      this.context = context;
   }

   @Override
   protected void onPreExecute() {
   }

   @Override
   protected void onPostExecute(String r) {
   }

   @Override
   protected String doInBackground(String... params) {
      try {
         connectionClass = new SQLConnection();
         Connection con = connectionClass.CONN();
         if (con == null) {
            z = "Error in connection with SQL server";
         }
         else {
            SharedPreference sharedPreference = new SharedPreference(context);
            int momId = Integer.parseInt(sharedPreference.getMomId());
            String query = "EXEC [SignleMotherProfile_SP] " + momId;
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
               sharedPreference.setMotherInfo(new PregnantMother(rs.getString("Id"), rs.getString("Name"), rs.getString("DateOfBirth"), rs.getString("BloodGroup"), rs.getString("WeightBFPrag"), rs.getString("Height"), rs.getString("NewMother_Pragnant")));
            }
            z = "Success";
         }
      } catch (Exception ex) {
         z = "Error retrieving data from table";
      }
      return z;
   }
}

