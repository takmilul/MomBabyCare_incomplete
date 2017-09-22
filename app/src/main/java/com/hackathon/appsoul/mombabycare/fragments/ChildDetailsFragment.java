package com.hackathon.appsoul.mombabycare.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hackathon.appsoul.mombabycare.R;
import com.hackathon.appsoul.mombabycare.SharedPreference;
import com.hackathon.appsoul.mombabycare.model.ChildModel;

public class ChildDetailsFragment extends Fragment {

   String prfileidMom;
   TextView childDetailsGenderTV;
   TextView childDetailsNameTV;
   TextView childDetailsAgeTV;
   TextView childDetailsMothersNameTV;
   TextView childDetailsGuardianMobileNoTV;
   TextView childDetailsDateOfBirthTV;
   TextView childDetailsBloodGroupTV;
   TextView childDetailsWeightTV;
   TextView childDetailsHeightTV;

   ChildModel childModel;
   SharedPreference sharedPreference;

   public ChildDetailsFragment() {
   }

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
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
      return inflater.inflate(R.layout.fragment_child_details, container, false);
   }

   @Override
   public void onActivityCreated(Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);

      childDetailsGenderTV = (TextView) getActivity().findViewById(R.id.detailsGenderChildTV);
      childDetailsNameTV = (TextView) getActivity().findViewById(R.id.detailsNameChildTV);
      childDetailsAgeTV = (TextView) getActivity().findViewById(R.id.detailsAgeChildTV);
      childDetailsMothersNameTV = (TextView) getActivity().findViewById(R.id.detailsMothersNameChildTV);
      childDetailsGuardianMobileNoTV = (TextView) getActivity().findViewById(R.id.detailsGurdianMobileNoChildTV);
      childDetailsDateOfBirthTV = (TextView) getActivity().findViewById(R.id.detailsDateOfBirthChildTV);
      childDetailsBloodGroupTV = (TextView) getActivity().findViewById(R.id.detailsBloodGroupChildTV);
      childDetailsWeightTV = (TextView) getActivity().findViewById(R.id.detailsWeightChildTV);
      childDetailsHeightTV = (TextView) getActivity().findViewById(R.id.detailsHeightChildTV);

      sharedPreference = new SharedPreference(getActivity());
      childModel = sharedPreference.getChildInfo();

      //childDetailsGenderTV.setText();
      childDetailsGenderTV.setText(childModel.getGender());
      childDetailsNameTV.setText(childModel.getBabyName());
      childDetailsAgeTV.setText(childModel.getAge());
      childDetailsMothersNameTV.setText(childModel.getMothersName());
      //childDetailsGuardianMobileNoTV.setText();
      childDetailsDateOfBirthTV.setText(childModel.getBirthDate());
      childDetailsBloodGroupTV.setText(childModel.getBloodGroup());
      childDetailsWeightTV.setText(childModel.getWeight());
      childDetailsHeightTV.setText(childModel.getHeight());
      childDetailsGuardianMobileNoTV.setText(sharedPreference.getMotherInfo().getGurdianMobileNo());
   }
}
