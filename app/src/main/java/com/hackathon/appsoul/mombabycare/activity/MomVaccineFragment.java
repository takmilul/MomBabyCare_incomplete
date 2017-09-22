/*
package com.hackathon.appsoul.mombabycare.activity;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.hackathon.appsoul.mombabycare.R;
import com.hackathon.appsoul.mombabycare.adapters.VaccineAdapter;
import com.hackathon.appsoul.mombabycare.model.VaccineModel;
import com.hackathon.appsoul.mombabycare.server.SQLConnection;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

//*/
/**
// * A simple {@link Fragment} subclass.
// * Use the {@link MomVaccineFragment#newInstance} factory method to
// * create an instance of this fragment.
// *//*

public class MomVaccineFragment extends Fragment {
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    SQLConnection connectionClass;

    ListView motherVaccineLV;
    Button save;


    public MomVaccineFragment() {
        // Required empty public constructor
    }

//    */
/**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment MomVaccineFragment.
//     *//*

//    // TODO: Rename and change types and number of parameters
//    public static MomVaccineFragment newInstance(String param1, String param2) {
//        MomVaccineFragment fragment = new MomVaccineFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mom_vaccine, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        */
/*layout = (ViewGroup) getActivity().findViewById(R.id.momVaccineLayout);

        LinearLayout vaccineItem = new LinearLayout(getActivity());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        vaccineItem.setLayoutParams(params);
        vaccineItem.setOrientation(LinearLayout.HORIZONTAL);

        CheckBox chk = new CheckBox(getActivity());
        LinearLayout.LayoutParams chkParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 65.0f);
        chk.setLayoutParams(chkParams);
        chk.setText("Vaccine1");

        TextView date = new TextView(getActivity());
        LinearLayout.LayoutParams dateParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 35.0f);
        date.setLayoutParams(dateParams);
        date.setText("12.04.2016");

        vaccineItem.addView(chk);
        vaccineItem.addView(date);

        layout.addView(vaccineItem);


        vaccineItem = new LinearLayout(getActivity());
        vaccineItem.setLayoutParams(params);
        vaccineItem.setOrientation(LinearLayout.HORIZONTAL);

        chk = new CheckBox(getActivity());
        chk.setLayoutParams(chkParams);
        chk.setText("Vaccine2");

        date = new TextView(getActivity());
        date.setLayoutParams(dateParams);
        date.setText("13.04.2016");

        vaccineItem.addView(chk);
        vaccineItem.addView(date);

        layout.addView(vaccineItem);*//*

        save = (Button) getActivity().findViewById(R.id.motherVaccineButton);
        motherVaccineLV = (ListView) getActivity().findViewById(R.id.motherVaccineLV);

        boolean[] dataBase = {true, false};
        String[] vaccineNames = {"Vaccine1", "Vaccine2"};
        String[] dates = {"12.03.2016", "15.03.2016"};
        ArrayList<VaccineModel> vaccines = new ArrayList<>();
        for(int i = 0; i < 2; i++){
            vaccines.add(new VaccineModel(dataBase[i], vaccineNames[i], dates[i]));
        }
        VaccineAdapter customAdapter = new VaccineAdapter(getActivity(), vaccines);
        motherVaccineLV.setAdapter(customAdapter);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < 2; i++){
                    CheckBox chk = (CheckBox) motherVaccineLV.findViewById(i);
                    Toast.makeText(getActivity(), String.valueOf(chk.isChecked()), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void save(View view){

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
            if(isSuccess==true) {
                // FillList fillList = new FillList();
                // fillList.execute("");
            }
        }

        @Override
        protected String doInBackground(String... params) {
          */
/*  if (proname.trim().equals("") || prodesc.trim().equals(""))
                z = "Please enter User Id and Password";
            else {*//*

            try {
                Connection con = connectionClass.CONN();
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {

                    String dates = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
                            .format(Calendar.getInstance().getTime());
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
           */
/* }*//*

            return z;
        }
    }
}
*/
