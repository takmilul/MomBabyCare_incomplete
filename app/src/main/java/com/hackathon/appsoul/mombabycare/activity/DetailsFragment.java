package com.hackathon.appsoul.mombabycare.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hackathon.appsoul.mombabycare.R;
import com.hackathon.appsoul.mombabycare.SharedPreference;
import com.hackathon.appsoul.mombabycare.adapters.ChildProfileListAdapter;
import com.hackathon.appsoul.mombabycare.child.dashboard.DashBoardChildActivity;
import com.hackathon.appsoul.mombabycare.model.ChildModel;
import com.hackathon.appsoul.mombabycare.model.PregnantMother;
import com.hackathon.appsoul.mombabycare.recyclerview.OnItemClickListener.ClickListener;
import com.hackathon.appsoul.mombabycare.recyclerview.OnItemClickListener.RecyclerTouchListener;
import com.hackathon.appsoul.mombabycare.server.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DetailsFragment extends Fragment {
   // TODO: Rename parameter arguments, choose names that match
   // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
   //    public static final String STATUS = "status";
   //    public static final String NAME = "name";
   //    public static final String ADDRESS = "address";
   //    public static final String MOBILE_NO = "mobile_no";
   //    public static final String GUARDIAN_MOBILE = "guardian_mobile";
   //    public static final String INCOME = "income";
   //    public static final String DOB = "dob";
   //    public static final String BLOOD_GROUP = "blood_group";
   //    public static final String WEIGHT = "weight";
   //    public static final String HEIGHT = "height";
   //    public static final String PREGNENCY_PERIOD = "pregnency_period";
   //
   //    // TODO: Rename and change types of parameters
   //    private String detailsStatus;
   //    private String detailsName;
   //    private String detailsAddress;
   //    private String detailsMobileNo;
   //    private String detailsGuardianMobileNo;
   //    private String detailsMonthlyIncome;
   //    private String detailsDateOfBirth;
   //    private String detailsBloodGroup;
   //    private String detailsWeight;
   //    private String detailsHeight;
   //    private String detailsPregnencyPeriod;
   //
   //    private OnFragmentInteractionListener mListener;

   TextView detailsStatusTV;
   TextView detailsNameTV;
   TextView detailsAddressTV;
   TextView detailsMobileNoTV;
   TextView detailsGuardianMobileNoTV;
   TextView detailsMonthlyIncomeTV;
   TextView detailsDateOfBirthTV;
   TextView detailsBloodGroupTV;
   TextView detailsWeightTV;
   TextView detailsHeightTV;
   TextView detailsPregnencyPeriodTV;

   ProgressBar pbbar;
   RecyclerView lstChildProfile;
   String prfileidMom;
   SQLConnection connectionClass;

   SharedPreference sharedPreference;
   ArrayList<ChildModel> childArrayList;
   PregnantMother pregnantMother;
   ChildModel childModel;
   ChildProfileListAdapter childProfileListAdapter;

   public DetailsFragment() {

      // Required empty public constructor
   }

   //    /**
   //     * Use this factory method to create a new instance of
   //     * this fragment using the provided parameters.
   //     *
   //     * @param param1 Parameter 1.
   //     * @param param2 Parameter 2.
   //     * @return A new instance of fragment DetailsFragment.
   //     */
   // TODO: Rename and change types and number of parameters
   //    public static DetailsFragment newInstance(String param1, String param2) {
   //        DetailsFragment fragment = new DetailsFragment();
   //        Bundle args = new Bundle();
   //        args.putString(ARG_PARAM1, param1);
   //        args.putString(ARG_PARAM2, param2);
   //        fragment.setArguments(args);
   //        return fragment;
   //    }

   public static Fragment newInstance(String dummy1, String dummy2) {
      return null;
   }

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      sharedPreference = new SharedPreference(getActivity());
      if (getArguments() != null) {
         //            detailsStatus = getArguments().getString(STATUS);
         //            detailsName = getArguments().getString(NAME);
         //            detailsAddress = getArguments().getString(ADDRESS);
         //            detailsMobileNo = getArguments().getString(MOBILE_NO);
         //            detailsGuardianMobileNo = getArguments().getString(GUARDIAN_MOBILE);
         //            detailsMonthlyIncome = getArguments().getString(INCOME);
         //            detailsDateOfBirth = getArguments().getString(DOB);
         //            detailsBloodGroup = getArguments().getString(BLOOD_GROUP);
         //            detailsWeight = getArguments().getString(WEIGHT);
         //            detailsHeight = getArguments().getString(HEIGHT);
         //            detailsPregnencyPeriod = getArguments().getString(PREGNENCY_PERIOD);
      }
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      // Inflate the layout for this fragment
      return inflater.inflate(R.layout.fragment_details, container, false);
   }

   @Override
   public void onActivityCreated(Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);

      /*detailsStatusTV = (TextView) getActivity().findViewById(R.id.detailsStatusMomTV);
      detailsAddressTV = (TextView) getActivity().findViewById(R.id.detailsAddressMomTV);
      detailsMobileNoTV = (TextView) getActivity().findViewById(R.id.detailsMobileNoMomTV);
      detailsGuardianMobileNoTV = (TextView) getActivity().findViewById(R.id.detailsGurdianMobileNoMomTV);
      detailsMonthlyIncomeTV = (TextView) getActivity().findViewById(R.id.detailsMonthlyIncomeMomTV);*/
      detailsNameTV = (TextView) getActivity().findViewById(R.id.detailsNameMomTV);
      detailsDateOfBirthTV = (TextView) getActivity().findViewById(R.id.detailsDateOfBirthMomTV);
      detailsBloodGroupTV = (TextView) getActivity().findViewById(R.id.detailsBloodGroupMomTV);
      detailsWeightTV = (TextView) getActivity().findViewById(R.id.detailsWeightMomTV);
      detailsHeightTV = (TextView) getActivity().findViewById(R.id.detailsHeightMomTV);
      detailsPregnencyPeriodTV = (TextView) getActivity().findViewById(R.id.detailsPregnencyPeriodMomTV);

      //SharedPreference sharedPreference;
      sharedPreference = new SharedPreference(getActivity());
      //String id = getArguments().getString("ID", null);
      //pregnantMother = new PregnantMother();
      pregnantMother = sharedPreference.getMotherInfo();
      String id = sharedPreference.getMomId();

      detailsNameTV.setText(pregnantMother.getName());
      detailsDateOfBirthTV.setText(pregnantMother.getDateOfBirth());
      detailsBloodGroupTV.setText(pregnantMother.getBloodGroup());
      detailsWeightTV.setText(pregnantMother.getWeightBFPrag());
      detailsHeightTV.setText(pregnantMother.getHeight());
      detailsPregnencyPeriodTV.setText(pregnantMother.getPragnantDueDate());

      connectionClass = new SQLConnection();

      pbbar = (ProgressBar) getActivity().findViewById(R.id.pbbarChildProfile);
      pbbar.setVisibility(View.GONE);

      //lstChildProfile = (ListView) getActivity().findViewById(R.id.lstChildProfile);
      prfileidMom="";
      
      childArrayList = new ArrayList<>();
      lstChildProfile = (RecyclerView) getActivity().findViewById(R.id.lstChildProfile);

      FillChildList fillChildList = new FillChildList();
      fillChildList.execute("");


      //        detailsStatusTV.setText(detailsStatus);
      //        detailsNameTV.setText(detailsName);
      //        detailsAddressTV.setText(detailsAddress);
      //        detailsMobileNoChildTV.setText(detailsMobileNo);
      //        detailsGuardianMobileNoTV.setText(detailsGuardianMobileNo);
      //        detailsMonthlyIncomeTV.setText(detailsMonthlyIncome);
      //        detailsDateOfBirthTV.setText(detailsDateOfBirth);
      //        detailsBloodGroupTV.setText(detailsBloodGroup);
      //        detailsWeightTV.setText(detailsWeight);
      //        detailsHeightTV.setText(detailsHeight);
      //        detailsPregnencyPeriodTV.setText(detailsPregnencyPeriod);
   }

   @Override
   public void onResume() {
      super.onResume();
   }
   
   @Override
   public void onPause() {
      super.onPause();
   }
   
   public interface OnFragmentInteractionListener {
   }
   
   public class AddPro extends AsyncTask<String, String, String> {

      String z = "";
      Boolean isSuccess = false;

      //copy your data in String object before sending them into query
      //        String babyName = babyNameET.getText().toString();
      //        String  dateOfBirth = babyDateOfBirthET.getText().toString();
      //        String   bloodGroupspnr = babyBloodGroupSpnr.getSelectedItem().toString();
      //        //String babyGender=babyBloodGroupSpnr
      //        String newbabyGender = babyGender;
      //        String height = babyHeightET.getText().toString();
      //        String weight = babyWeightET.getText().toString();
      //        String deliveryStatus=babyDeliveryStatusET.getText().toString();
      //        String bloodGroup=babyBloodGroupSpnr.getSelectedItem().toString();
      //        String birthDefect=babyBirthDefectET.getText().toString();

      //        String hwId="12001";
      //        String InputByUser="HW";

      @Override
      protected void onPreExecute() {

      }

      @Override
      protected void onPostExecute(String r) {

         Toast.makeText(getContext(), r, Toast.LENGTH_SHORT).show();
         if (isSuccess == true) {
            // FillList fillList = new FillList();
            // fillList.execute("");
         }
      }

      @Override
      protected String doInBackground(String... params) {
          /*  if (proname.trim().equals("") || prodesc.trim().equals(""))
                z = "Please enter User Id and Password";
            else {*/
         try {
            Connection con = connectionClass.CONN();
            if (con == null) {
               z = "Error in connection with SQL server";
            }
            else {

               String dates = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).format(Calendar.getInstance().getTime());
               //String query = "insert into tbl_BabyProfile values (" + momId + ",'" + babyName + "','" + dateOfBirth + "','" + newbabyGender + "', '"+ height+"', '"+ weight +"', '"+ deliveryStatus +"', '"+ bloodGroup +"', '"+ birthDefect +"', '"+ hwId +"',getdate(),'HW','A','Client')";
               //PreparedStatement preparedStatement = con.prepareStatement(query);
               //preparedStatement.executeUpdate();
               z = "Added Child Successfully";
               isSuccess = true;
            }
         } catch (Exception ex) {
            isSuccess = false;
            z = "Exceptions";
         }
           /* }*/
         return z;
      }
   }

   // TODO: Rename method, update argument and hook method into UI event
   //    public void onFragmentInteraction(Uri uri) {
   //        if (mListener != null) {
   //            mListener.onFragmentInteraction(uri);
   //        }
   //    }

   //    @Override
   //    public void onAttach(Context context) {
   //        super.onAttach(context);
   //        if (context instanceof OnFragmentInteractionListener) {
   //            mListener = (OnFragmentInteractionListener) context;
   //        } else {
   //            throw new RuntimeException(context.toString()
   //                    + " must implement OnFragmentInteractionListener");
   //        }
   //    }
   //
   //    @Override
   //    public void onDetach() {
   //        super.onDetach();
   //        mListener = null;
   //    }
   //
   //    /**
   //     * This interface must be implemented by activities that contain this
   //     * fragment to allow an interaction in this fragment to be communicated
   //     * to the activity and potentially other fragments contained in that
   //     * activity.
   //     * <p/>
   //     * See the Android Training lesson <a href=
   //     * "http://developer.android.com/training/basics/fragments/communicating.html"
   //     * >Communicating with Other Fragments</a> for more information.
   //     */
   //    public interface OnFragmentInteractionListener {
   //        // TODO: Update argument type and name
   //        //type does not required to be Uri
   //        void onFragmentInteraction(Uri uri);
   //    }


   public class FillChildList extends AsyncTask<String, String, String> {
      String z = "";

      List<Map<String, String>> prolist  = new ArrayList<Map<String, String>>();

      @Override
      protected void onPreExecute() {

         pbbar.setVisibility(View.VISIBLE);
      }

      @Override
      protected void onPostExecute(String r) {

         pbbar.setVisibility(View.GONE);

         childProfileListAdapter = new ChildProfileListAdapter(getActivity(), childArrayList);
         LayoutManager layoutManager = new LinearLayoutManager(getActivity());
         lstChildProfile.setLayoutManager(layoutManager);
         lstChildProfile.setAdapter(childProfileListAdapter);

         lstChildProfile.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), lstChildProfile, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
               Intent intent = new Intent(getActivity(), DashBoardChildActivity.class);
               //sharedPreference.setChildId(childProfileListAdapter.getItem(position).getId());
               sharedPreference.setChildInfo(childArrayList.get(position));
               startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
         }));


         /*Toast.makeText(getActivity(), r, Toast.LENGTH_SHORT).show();

         String[] from = { "A", "B", "C" };

         int[] views = { R.id.lblProfileId, R.id.lblProfileName,R.id.lblMomStatus};
         final SimpleAdapter ADA = new SimpleAdapter(getActivity(),
                                                    prolist, R.layout.profile_list_template_for_mom, from,views);
         lstChildProfile.setAdapter(ADA);

         lstChildProfile.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {
               HashMap<String, Object> obj = (HashMap<String, Object>) ADA
                                                                       .getItem(arg2);
               prfileidMom = (String) obj.get("A");
               String profileNameMom = (String) obj.get("B");
               String momDateOfBirth = (String) obj.get("C");

               Intent intent=new Intent(getActivity(), DashBoardChildActivity.class);
               intent.putExtra("MOM_NAME",profileNameMom);
               intent.putExtra("MOM_ID",prfileidMom);

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
            } else {
               int momId = Integer.parseInt(sharedPreference.getMomId());
               String momName = sharedPreference.getMomName();
               String query = "EXEC [dbo].[BabyProfile_SP] " + momId;
               PreparedStatement ps = con.prepareStatement(query);
               ResultSet rs = ps.executeQuery();

               ArrayList<String> data1 = new ArrayList<String>();
               while (rs.next()) {
                  /*Map<String, String> datanum = new HashMap<String, String>();
                  datanum.put("A", rs.getString("Id"));
                  datanum.put("B", rs.getString("Name"));
                  datanum.put("C", rs.getString("DateOfBirth"));
                  prolist.add(datanum);*/

                  childModel = new ChildModel(rs.getString("Id"), rs.getString("Mother_ID"), rs.getString("BabyName"), momName, rs.getString("Gender"), rs.getString("Height"), rs.getString("Weight"), rs.getString("BloodGroup"), rs.getString("BirthDate"), rs.getString("AgeOfBaby"));
                  childArrayList.add(childModel);
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
