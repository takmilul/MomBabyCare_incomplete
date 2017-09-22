package com.hackathon.appsoul.mombabycare.fragments;

import android.app.Fragment;
import android.content.Context;
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
import com.hackathon.appsoul.mombabycare.adapters.CheckupRecyclerViewAdapter;
import com.hackathon.appsoul.mombabycare.model.CheckupModel;
import com.hackathon.appsoul.mombabycare.recyclerview.DividerItemDecoration;
import com.hackathon.appsoul.mombabycare.server.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CheckupChildRecyclerViewFragment extends Fragment {

   ArrayList<CheckupModel> checkupArrayList;
   ArrayList<CheckupModel> checkupArrayListForDB;
   SQLConnection connectionClass;
   ProgressBar pbbar;
   private RecyclerView recyclerView;
   private CheckupRecyclerViewAdapter checkupRecyclerViewAdapter;
   private CheckupModel checkupModel;
   private CheckupModel checkupModelForDB;

   public CheckupChildRecyclerViewFragment() {
   }

   @Override
   public void onCreate(@Nullable Bundle savedInstanceState) {
      setHasOptionsMenu(true);
      super.onCreate(savedInstanceState);

   }

   @Override
   public void onActivityCreated(@Nullable Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);

      recyclerView = (RecyclerView) getActivity().findViewById(R.id.checkupLv);

      connectionClass = new SQLConnection();

      pbbar = (ProgressBar) getActivity().findViewById(R.id.pbbarProfileCheckup);
      pbbar.setVisibility(View.GONE);

      checkupArrayList = new ArrayList<>();
      checkupArrayListForDB = new ArrayList<>();

      FillList fillList = new FillList();
      try {
         fillList.execute("").get();
      } catch (InterruptedException e) {
         e.printStackTrace();
      } catch (ExecutionException e) {
         e.printStackTrace();
      }

      checkupRecyclerViewAdapter = new CheckupRecyclerViewAdapter(getActivity(), checkupArrayList);
      RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
      recyclerView.setLayoutManager(mLayoutManager);
      recyclerView.setItemAnimator(new DefaultItemAnimator());
      recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
      recyclerView.setAdapter(checkupRecyclerViewAdapter);
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      //android.app.ActionBar actionBar = getActivity().getActionBar();
      return inflater.inflate(R.layout.fragment_checkup_mom, container, false);
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
   public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
      menu.clear();
      getActivity().getMenuInflater().inflate(R.menu.checkup_menu, menu);
      super.onCreateOptionsMenu(menu, inflater);
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      checkupArrayListForDB = checkupRecyclerViewAdapter.getCheckupModelArrayList();
      return super.onOptionsItemSelected(item);
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
            Connection con = connectionClass.CONN();
            if (con == null) {
               z = "Error in connection with SQL server";
            }
            else {
               String isActive = "A";
               String server_Client = "S";
               String checkupType = "FM";

               // String query = "SELECT CheckupName, CheckupNormalValue,Remarks from tbl_CheckupMaster";

               String query = "Exec CheckupMaster_SP " + "'" + isActive + "'" + "," + "'" + server_Client + "'" + "," + "'" + checkupType + "'";
               PreparedStatement ps = con.prepareStatement(query);
               ResultSet rs = ps.executeQuery();

               int i = 0;
               while (rs.next()) {
                  i++;
                  checkupModel = new CheckupModel(rs.getString("CheckupName"), rs.getString("CheckupNormalValue"), rs.getString("Remarks"));
                  checkupArrayList.add(checkupModel);
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