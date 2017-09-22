package com.hackathon.appsoul.mombabycare.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hackathon.appsoul.mombabycare.R;
import com.hackathon.appsoul.mombabycare.SharedPreference;
import com.hackathon.appsoul.mombabycare.adapters.VaccineAdapter;
import com.hackathon.appsoul.mombabycare.model.VaccineModel;
import com.hackathon.appsoul.mombabycare.recyclerview.DividerItemDecoration;
import com.hackathon.appsoul.mombabycare.server.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class VaccineFragment extends Fragment {

   ArrayList<VaccineModel> VaccineArrayList;
   ArrayList<VaccineModel> VaccineArrayListForDB;
   SQLConnection connectionClass;
   ProgressBar pbbar;
   String vaccineSave = "";
   Connection con;
   int isCheckVaccine;
   private String dates;
   private RecyclerView recyclerView;
   private VaccineAdapter vaccineAdapter;
   private VaccineModel vaccineModel;
   private VaccineModel vaccineModelForDB;

   public VaccineFragment() {
   }

   @Override
   public void onCreate(@Nullable Bundle savedInstanceState) {
      setHasOptionsMenu(true);
      super.onCreate(savedInstanceState);
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      return inflater.inflate(R.layout.fragment_vaccine, container, false);
   }

   // TODO: Rename method, update argument and hook method into UI event
   public void onButtonPressed(Uri uri) {
   }

   @Override
   public void onAttach(Context context) {
      context = getActivity();
      super.onAttach(context);
   }

   @Override
   public void onDetach() {
      super.onDetach();
   }

   @Override
   public void onActivityCreated(@Nullable Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);
      recyclerView = (RecyclerView) getActivity().findViewById(R.id.vaccineRv);

      connectionClass = new SQLConnection();

      pbbar = (ProgressBar) getActivity().findViewById(R.id.pbbarVaccineCheckup);
      pbbar.setVisibility(View.GONE);

      VaccineArrayList = new ArrayList<>();
      VaccineArrayListForDB = new ArrayList<>();

      vaccineSave = "";

      FillList fillList = new FillList();
      try {
         fillList.execute("").get();
      } catch (InterruptedException e) {
         e.printStackTrace();
      } catch (ExecutionException e) {
         e.printStackTrace();
      }

      vaccineAdapter = new VaccineAdapter(getActivity(), VaccineArrayList);
      RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
      recyclerView.setLayoutManager(mLayoutManager);
      recyclerView.setItemAnimator(new DefaultItemAnimator());
      recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
      recyclerView.setAdapter(vaccineAdapter);
   }

   @Override
   public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
      menu.clear();
      getActivity().getMenuInflater().inflate(R.menu.checkup_menu, menu);
      super.onCreateOptionsMenu(menu, inflater);
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      VaccineArrayListForDB = vaccineAdapter.getVaccineModelArrayList();
      vaccineSave = "Save";

      for (VaccineModel c : this.VaccineArrayListForDB) {
         vaccineModel = c;
         /*FillList fillList = new FillList();
         try {
            fillList.execute("").get();
         } catch (InterruptedException e) {
            e.printStackTrace();
         } catch (ExecutionException e) {
            e.printStackTrace();
         }*/
         updateVaccine();
      }
      Toast.makeText(getActivity(), "Data Updated Successfully", Toast.LENGTH_LONG).show();
      vaccineSave = "";
      return super.onOptionsItemSelected(item);
   }

   public void updateVaccine(){
      int i = 5;
      getActivity().runOnUiThread(
      new Runnable(){
         @Override
         public void run() {
            con = connectionClass.CONN();
            if (vaccineModel.isChecked()) {
               isCheckVaccine = 1;
            } else {
               isCheckVaccine = 0;
            }
            String query = "Exec CHILDVACCINE_Update_SP " + vaccineModel.getId() + "," + isCheckVaccine + ",'" + dates + "'";

            PreparedStatement ps = null;
            try {
               ps = con.prepareStatement(query);
            } catch (SQLException e) {
               e.printStackTrace();
            }
            try {
               ResultSet rs = ps.executeQuery();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
      });
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
         Toast.makeText(getActivity().getApplicationContext(), r, Toast.LENGTH_SHORT).show();
      }

      @Override
      protected String doInBackground(String... params) {

         try {
            con = connectionClass.CONN();
            if (con == null) {
               z = "Error in connection with SQL server";
            } else {
               SharedPreference sharedPreference = new SharedPreference(getActivity());
               int childId = Integer.parseInt(sharedPreference.getChildId());
               String server_Client = "S";
               String checkupType = "FM";
               String query = "";

               dates = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH).format(Calendar.getInstance().getTime());

               /*if (vaccineSave.equals("Save")) {
                  isCheckVaccine = 0;

                  if (vaccineModel.isChecked()) {
                     isCheckVaccine = 1;
                  } else {
                     isCheckVaccine = 0;
                     dates = "NULL";
                  }

                  query = "Exec CHILDVACCINE_Update_SP " + vaccineModel.getId() + "," + isCheckVaccine + ",'" + dates + "'";

                  PreparedStatement ps = con.prepareStatement(query);
                  ResultSet rs = ps.executeQuery();
               }*/ //else {
                  query = "Exec CHILDVACCINE_SP " + "" + childId + "";

                  PreparedStatement ps = con.prepareStatement(query);
                  ResultSet rs = ps.executeQuery();
                  int i = 0;
                  while (rs.next()) {
                     i++;
                     vaccineModel = new VaccineModel(rs.getString("ID"), rs.getBoolean("istaken"), rs.getString("VACCINENAME_TYPE"), rs.getString("DUEDATE"));
                     VaccineArrayList.add(vaccineModel);
                  }
               //}
               z = "Success";
            }
         } catch (Exception ex) {
            z = "Error retrieving data from table";
         }
         return z;
      }
   }

}
