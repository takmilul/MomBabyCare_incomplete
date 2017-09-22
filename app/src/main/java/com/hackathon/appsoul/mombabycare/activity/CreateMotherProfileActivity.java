package com.hackathon.appsoul.mombabycare.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hackathon.appsoul.mombabycare.R;
import com.hackathon.appsoul.mombabycare.classes.GradientBackgroundPainter;
import com.hackathon.appsoul.mombabycare.model.PregnantMother;
import com.hackathon.appsoul.mombabycare.server.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CreateMotherProfileActivity extends AppCompatActivity implements TextWatcher, OnFocusChangeListener {
   
   private SQLConnection connectionClass;
   private PregnantMother pregnantMother;
   private RadioButton newMotherRBtn;
   private RadioButton pregnentRBtn;
   private ProgressBar pbbar;
   
   private Spinner divisionSpnr;
   private Spinner districtSpnr;
   private Spinner thanaSpnr;
   private Spinner unionSpnr;
   private Spinner bloodGroupSpnr;
   
   private EditText nameET;
   private EditText passwordET;
   private EditText retypeET;
   private EditText addressET;
   private EditText mobileNoET;
   private EditText guardianMobileNoET;
   private EditText monthlyIncomeET;
   private TextView dateOfBirthET;
   private EditText weightET;
   private EditText heightET;
   private EditText majorDiseaseET;
   private EditText pregnancyPeriodET;
   
   private TextView nameErrorTV;
   private TextView passwordErrorTV;
   private TextView retypeErrorTV;
   private TextView addressErrorTV;
   private TextView mobileNoErrorTV;
   private TextView guardianMobileNoErrorTV;
   private TextView monthlyIncomeErrorTV;
   private TextView dateOfBirthErrorTV;
   private TextView weightErrorTV;
   private TextView heightErrorTV;
   private TextView majorDiseaseErrorTV;
   private TextView pregnancyPeriodErrorTV;
   
   private TextView SaveBtn;
   
   private View underlineViewName;
   private View underlineViewPassword;
   private View underlineViewRetypePassword;
   private View underlineViewAddress;
   private View underlineViewMobileNo;
   private View underlineViewGuardiansMobileNo;
   private View underlineViewMonthlyIncome;
   private View underlineViewDob;
   private View underlineViewWeight;
   private View underlineViewHeight;
   private View underlineViewMajorDisease;
   private View underlineViewPregnancyPeriod;
   
   private View secondUnderlineViewName;
   private View secondUnderlineViewPassword;
   private View secondUnderlineViewRetypePassword;
   private View secondUnderlineViewAddress;
   private View secondUnderlineViewMobileNo;
   private View secondUnderlineViewGuardiansMobileNo;
   private View secondUnderlineViewMonthlyIncome;
   private View secondUnderlineViewDob;
   private View secondUnderlineViewWeight;
   private View secondUnderlineViewHeight;
   private View secondUnderlineViewMajorDisease;
   private View secondUnderlineViewPregnancyPeriod;
   
   private Animation anim;
   
   private boolean isNameError = true;
   private boolean isPasswordError = true;
   private boolean isRetypeError = true;
   private boolean isAddressError = true;
   private boolean isMobileNoError = true;
   private boolean isGuardianMobileNoError = true;
   private boolean isMonthlyIncomeError = true;
   private boolean isDateOfBirthError = true;
   private boolean isWeightError = true;
   private boolean isHeightError = true;
   private boolean isMajorDiseaseError = true;
   private boolean isPregnancyPeriodError = true;
   
   private boolean redMarkAnim;
   
   private LinearLayout pregnancyPeriodEnableDisable;
   private String[] bloodGroups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
   /*private String[] divisions = {"Dhaka", "Rajshahi"};
   private String[] districts = {"Dhaka", "Manikgonj", "Tanggail", "Mymensingho", "Kishorgonj"};
   private String[] thanas = {"Dhamrai", "Mirpur", "Jatrabari"};
   private String[] unions = {"Kushura", "Balia", "Atulla", "Bengroi", "Haluapara"};*/

   HashMap<String,String> address;
   String division_Code;
   String district_Code;
   String upazila_Code;
   String union_Code;
   String village_Code;
   String areaName;

   //String[] majorDiseases= {"Diabates","Hypertension","Heart Disease", "Other"};
   private GradientBackgroundPainter gradientBackgroundPainter;
   private String motherType = "New Mother";
   private boolean dateSelected;
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_create_mother_profile);
      initializer();
      
      // start of newly added code 
      
      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);
      toolbar.setTitleTextColor(getResources().getColor(R.color.white_70_transparent));
      toolbar.setNavigationIcon(R.drawable.back_button1);
      toolbar.setNavigationOnClickListener(new OnClickListener() {
         @Override
         public void onClick(View v) {
            onBackPressed();
         }
      });
      
      //added code next 3 lines for gradient background.
      LinearLayout backgroundImage = (LinearLayout) findViewById(R.id.createMother);
      gradientBackgroundPainter = new GradientBackgroundPainter(backgroundImage);
      gradientBackgroundPainter.start();
      
      //end of newly added code
      
      anim = AnimationUtils.loadAnimation(this, R.anim.scale);
      
      nameET.setOnFocusChangeListener(this);
      passwordET.setOnFocusChangeListener(this);
      retypeET.setOnFocusChangeListener(this);
      addressET.setOnFocusChangeListener(this);
      mobileNoET.setOnFocusChangeListener(this);
      guardianMobileNoET.setOnFocusChangeListener(this);
      monthlyIncomeET.setOnFocusChangeListener(this);
      dateOfBirthET.setOnFocusChangeListener(this);
      weightET.setOnFocusChangeListener(this);
      heightET.setOnFocusChangeListener(this);
      majorDiseaseET.setOnFocusChangeListener(this);
      pregnancyPeriodET.setOnFocusChangeListener(this);
      
      nameET.addTextChangedListener(this);
      passwordET.addTextChangedListener(this);
      retypeET.addTextChangedListener(this);
      addressET.addTextChangedListener(this);
      mobileNoET.addTextChangedListener(this);
      guardianMobileNoET.addTextChangedListener(this);
      monthlyIncomeET.addTextChangedListener(this);
      dateOfBirthET.addTextChangedListener(this);
      weightET.addTextChangedListener(this);
      heightET.addTextChangedListener(this);
      majorDiseaseET.addTextChangedListener(this);
      pregnancyPeriodET.addTextChangedListener(this);
      
      connectionClass = new SQLConnection();
      pregnantMother = new PregnantMother();
      
      pbbar.setVisibility(View.GONE);
      ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, bloodGroups);
      arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      bloodGroupSpnr.setAdapter(arrayAdapter);
      bloodGroupSpnr.setOnItemSelectedListener(new MyOnItemSelectedListener());
      
      /*ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, districts);
      arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      districtSpnr.setAdapter(arrayAdapter2);
      districtSpnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            
         }
         
         @Override
         public void onNothingSelected(AdapterView<?> parent) {
            
         }
      });
      
      ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, thanas);
      arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      thanaSpnr.setAdapter(arrayAdapter3);
      thanaSpnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            
         }
         
         @Override
         public void onNothingSelected(AdapterView<?> parent) {
            
         }
      });
      
      ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, unions);
      arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      unionSpnr.setAdapter(arrayAdapter4);
      unionSpnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            
         }
         
         @Override
         public void onNothingSelected(AdapterView<?> parent) {
            
         }
      });*/





      getDivisionAddress("select Division_Code,AreaName from [GEO_CODE_BANG] where  Zila_Code  is null and Upazila_Thana_Code  is null and PSA  is null and Union_Code  is null and Mouza_MohollaCode  is null and RMO  is null and VillageCode  is null");


      divisionSpnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

         @Override

         public void onItemSelected(AdapterView<?> parent, View view,

                                    int position, long id) {

            String name = divisionSpnr.getSelectedItem().toString();

            String code = address.get(name);
//district
            getDistrictAddress("select Zila_Code as Division_Code,AreaName from [GEO_CODE_BANG] where Division_Code='"+code+"' and Upazila_Thana_Code  is null and PSA  is null and Union_Code  is null and Mouza_MohollaCode  is null and RMO  is null and VillageCode  is null and Zila_Code  is not null");
         }

         @Override

         public void onNothingSelected(AdapterView<?> parent) {

         }

      });


      districtSpnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

         @Override

         public void onItemSelected(AdapterView<?> parent, View view,

                                    int position, long id) {

            String name = districtSpnr.getSelectedItem().toString();

            String code = address.get(name);

            Toast.makeText(CreateMotherProfileActivity.this, name + "-"+ code, Toast.LENGTH_SHORT)

                    .show();
         }

         @Override

         public void onNothingSelected(AdapterView<?> parent) {

         }

      });

      /*
      ArrayAdapter<String> arrayAdapter5 = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, divisions);
      arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      
      divisionSpnr.setAdapter(arrayAdapter5);
      
      divisionSpnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            
         }
         
         @Override
         public void onNothingSelected(AdapterView<?> parent) {
            
         }
      });*/

       /* ArrayAdapter<String> arrayAdapter6 = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_item,majorDiseases);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        majorDiseaseSpnr.setAdapter(arrayAdapter6);
        majorDiseaseSpnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        */
      newMotherRBtn.setChecked(true);
      pregnancyPeriodEnableDisable.setVisibility(View.GONE);
      pregnancyPeriodErrorTV.setVisibility(View.GONE);
      
      newMotherRBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (!newMotherRBtn.isChecked()) {
               pregnancyPeriodEnableDisable.setVisibility(View.VISIBLE);
               pregnancyPeriodErrorTV.setVisibility(View.VISIBLE);
               motherType = "Pregnant";
            } else {
               pregnancyPeriodEnableDisable.setVisibility(View.GONE);
               pregnancyPeriodErrorTV.setVisibility(View.GONE);
               motherType = "New Mother";
            }
         }
      });
      
      SaveBtn.setOnClickListener(new OnClickListener() {
         @Override
         public void onClick(View v) {
            AddPro addPro = new AddPro();
            addPro.execute("");
         }
      });
   }
   
   @Override
   protected void onDestroy() {
      gradientBackgroundPainter.stop();
      super.onDestroy();
   }
   
   @Override
   public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      
   }
   
   @Override
   public void onTextChanged(CharSequence s, int start, int before, int count) {
      
   }
   
   @Override
   public void afterTextChanged(Editable s) {
      
      View view = getCurrentFocus();
      
      if (view == nameET) {
         validateName();
      } else if (view == passwordET) {
         validatePassword();
      } else if (view == retypeET) {
         validateRetypePassword();
      } else if (view == addressET) {
         validateAddress();
      } else if (view == mobileNoET) {
         validateMobileNo();
      } else if (view == guardianMobileNoET) {
         validateGuardianMobileNo();
      } else if (view == monthlyIncomeET) {
         validateMonthlyIncome();
      } else if (view == dateOfBirthET) {
         validateDateOfBirth();
      } else if (view == weightET) {
         validateWeight();
      } else if (view == heightET) {
         validateHeight();
      } else if (view == majorDiseaseET) {
         validateMajorDisease();
      } else if (view == pregnancyPeriodET) {
         validatePregnancyPeriod();
      }
   }
   
   @Override
   public void onFocusChange(View v, boolean hasFocus) {
      
      if (v == nameET) {
         if (hasFocus) {
            redMarkAnim = false;
            underlineViewName.setVisibility(View.VISIBLE);
            underlineViewName.startAnimation(anim);
         } else {
            underlineViewName.setVisibility(View.INVISIBLE);
         }
      } else if (v == passwordET) {
         if (hasFocus) {
            redMarkAnim = false;
            underlineViewPassword.setVisibility(View.VISIBLE);
            underlineViewPassword.startAnimation(anim);
         } else {
            underlineViewPassword.setVisibility(View.INVISIBLE);
         }
      } else if (v == retypeET) {
         if (hasFocus) {
            redMarkAnim = false;
            underlineViewRetypePassword.setVisibility(View.VISIBLE);
            underlineViewRetypePassword.startAnimation(anim);
         } else {
            underlineViewRetypePassword.setVisibility(View.INVISIBLE);
         }
      } else if (v == addressET) {
         if (hasFocus) {
            redMarkAnim = false;
            underlineViewAddress.setVisibility(View.VISIBLE);
            underlineViewAddress.startAnimation(anim);
         } else {
            underlineViewAddress.setVisibility(View.INVISIBLE);
         }
      } else if (v == mobileNoET) {
         if (hasFocus) {
            redMarkAnim = false;
            underlineViewMobileNo.setVisibility(View.VISIBLE);
            underlineViewMobileNo.startAnimation(anim);
         } else {
            underlineViewMobileNo.setVisibility(View.INVISIBLE);
         }
      } else if (v == guardianMobileNoET) {
         if (hasFocus) {
            redMarkAnim = false;
            underlineViewGuardiansMobileNo.setVisibility(View.VISIBLE);
            underlineViewGuardiansMobileNo.startAnimation(anim);
         } else {
            underlineViewGuardiansMobileNo.setVisibility(View.INVISIBLE);
         }
      } else if (v == monthlyIncomeET) {
         if (hasFocus) {
            redMarkAnim = false;
            underlineViewMonthlyIncome.setVisibility(View.VISIBLE);
            underlineViewMonthlyIncome.startAnimation(anim);
         } else {
            underlineViewMonthlyIncome.setVisibility(View.INVISIBLE);
         }
      } else if (v == dateOfBirthET) {
         if (hasFocus) {
            redMarkAnim = false;
            underlineViewDob.setVisibility(View.VISIBLE);
            underlineViewDob.startAnimation(anim);
            datePicker(v);
         } else {
            underlineViewDob.setVisibility(View.INVISIBLE);
         }
      } else if (v == weightET) {
         if (hasFocus) {
            redMarkAnim = false;
            underlineViewWeight.setVisibility(View.VISIBLE);
            underlineViewWeight.startAnimation(anim);
         } else {
            underlineViewWeight.setVisibility(View.INVISIBLE);
         }
      } else if (v == heightET) {
         if (hasFocus) {
            redMarkAnim = false;
            underlineViewHeight.setVisibility(View.VISIBLE);
            underlineViewHeight.startAnimation(anim);
         } else {
            underlineViewHeight.setVisibility(View.INVISIBLE);
         }
      } else if (v == majorDiseaseET) {
         if (hasFocus) {
            redMarkAnim = false;
            underlineViewMajorDisease.setVisibility(View.VISIBLE);
            underlineViewMajorDisease.startAnimation(anim);
         } else {
            underlineViewMajorDisease.setVisibility(View.INVISIBLE);
         }
      } else if (v == pregnancyPeriodET) {
         if (hasFocus) {
            redMarkAnim = false;
            underlineViewPregnancyPeriod.setVisibility(View.VISIBLE);
            underlineViewPregnancyPeriod.startAnimation(anim);
         } else {
            underlineViewPregnancyPeriod.setVisibility(View.INVISIBLE);
         }
      }
   }
   
   public void datePicker(View view) {
      DatePickerFragment datePickerFragment = new DatePickerFragment(dateOfBirthET);
      datePickerFragment.show(getFragmentManager(), "date");
   }
   
   private static class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
   }
   
   public class AddPro extends AsyncTask<String, String, String> {
      
      String z = "";
      Boolean isSuccess = false;
      
      String name = nameET.getText().toString();
      String address = addressET.getText().toString();
      String divisionspnr = divisionSpnr.getSelectedItem().toString();
      String districtspnr = districtSpnr.getSelectedItem().toString();
      String thanaspnr = thanaSpnr.getSelectedItem().toString();
      String unionspnr = unionSpnr.getSelectedItem().toString();
      String mobileNo = mobileNoET.getText().toString();
      String password = passwordET.getText().toString();
      String dateOfBirth = dateOfBirthET.getText().toString();
      String bloodGroupspnr = bloodGroupSpnr.getSelectedItem().toString();
      
      String majorDisease = majorDiseaseET.getText().toString();
      String guardianMobileNo = guardianMobileNoET.getText().toString();
      String monthlyIncome = monthlyIncomeET.getText().toString();
      String pregnencyPeriod = pregnancyPeriodET.getText().toString();
      String weight = weightET.getText().toString();
      String height = heightET.getText().toString();
      String hwId = "12001";
      String newMotherrBtn = motherType;
      
      @Override
      protected void onPreExecute() {
         pbbar.setVisibility(View.VISIBLE);
      }
      
      @Override
      protected void onPostExecute(String r) {
         pbbar.setVisibility(View.GONE);
         Toast.makeText(CreateMotherProfileActivity.this, r, Toast.LENGTH_SHORT).show();
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
            } else {
               
               String dates = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
                                    .format(Calendar.getInstance().getTime());
               
               //this Value will get current Date time of system
               String dates1 = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
                                     .format(Calendar.getInstance().getTime());
               
               //if(majorDiseasespnr.equals("Other")) majorDiseasespnr = majorDisease;
               String query = "EXEC ProfileNew_PragnantMother_InsertSP  '" + name + "','" + address + "','" + divisionspnr + "','" + districtspnr + "', '" + thanaspnr + "', '" + unionspnr + "', '" + mobileNo + "', '" + password + "', '" + dateOfBirth + "', '" + bloodGroupspnr + "', '" + majorDisease + "', '" + guardianMobileNo + "', " + Integer.valueOf(monthlyIncome) + ",  '" + pregnencyPeriod + "', '" + weight + "', '" + height + "', '" + newMotherrBtn + "', '" + hwId + "','" + dates1 + "','A','C','Client'";
               PreparedStatement preparedStatement = con.prepareStatement(query);
               preparedStatement.executeUpdate();
               z = "Added Mother Successfully";
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
   
   private void validateName() {
      
   }
   
   private void validatePassword() {
      
   }
   
   private void validateRetypePassword() {
      
   }
   
   private void validateAddress() {
      
   }
   
   private void validateMobileNo() {
      
   }
   
   private void validateGuardianMobileNo() {
      
   }
   
   private void validateMonthlyIncome() {
      String weight = monthlyIncomeET.getText().toString().replaceAll("( )+", "").trim();
      MarginLayoutParams params = (MarginLayoutParams) monthlyIncomeErrorTV.getLayoutParams();
      
      if (weight.length() > 0) {
         if (Integer.parseInt(weight) <= 0) {
            if (!redMarkAnim) {
               redMarkAnim = true;
               secondUnderlineViewMonthlyIncome.startAnimation(anim);
            }
            isMonthlyIncomeError = true;
            secondUnderlineViewMonthlyIncome.setVisibility(View.VISIBLE);
            float scale = getResources().getDisplayMetrics().density;
            int marginLeft = (int) (12 * scale + 0.5f);
            int marginBottom = (int) (16 * scale + 0.5f);
            params.setMargins(marginLeft, 0, 0, marginBottom);
            monthlyIncomeErrorTV.setLayoutParams(params);
            monthlyIncomeErrorTV.setText("'Monthly Income' must be greater than 0");
            monthlyIncomeErrorTV.setTextColor(getResources().getColor(R.color.red_5));
            monthlyIncomeET.setTextColor(getResources().getColor(R.color.red_5));
         } else {
            secondUnderlineViewMonthlyIncome.setVisibility(View.INVISIBLE);
            monthlyIncomeET.setTextColor(getResources().getColor(R.color.white_70_transparent));
            params.setMargins(0, 0, 0, 0);
            monthlyIncomeErrorTV.setLayoutParams(params);
            monthlyIncomeErrorTV.setText("");
            if (redMarkAnim) {
               redMarkAnim = false;
               underlineViewMonthlyIncome.startAnimation(anim);
            }
            isMonthlyIncomeError = false;
         }
      } else {
         secondUnderlineViewMonthlyIncome.setVisibility(View.INVISIBLE);
         monthlyIncomeErrorTV.setText("");
         monthlyIncomeET.setTextColor(getResources().getColor(R.color.white_70_transparent));
         params.setMargins(0, 0, 0, 0);
         monthlyIncomeErrorTV.setLayoutParams(params);
         redMarkAnim = false;
         if (isMonthlyIncomeError) {
            underlineViewMonthlyIncome.startAnimation(anim);
         }
      }
   }
   
   private void validateDateOfBirth() {
      
      long time = System.currentTimeMillis();
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      String date = dateFormat.format(time);
      
      MarginLayoutParams params = (MarginLayoutParams) dateOfBirthErrorTV.getLayoutParams();
      /*LinearLayout.LayoutParams params1 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
      params1.setMargins(16,0,0,16);*/
      
      String bd = dateOfBirthET.getText().toString();
      if (bd.compareTo(date) > 0) {
         redMarkAnim = false;
         isDateOfBirthError = true;
         dateOfBirthErrorTV.setText("'Date of Birth' can't be greater than current date");
         dateOfBirthErrorTV.setTextColor(getResources().getColor(R.color.red_5));
         float scale = getResources().getDisplayMetrics().density;
         int marginLeft = (int) (12 * scale + 0.5f);
         int marginBottom = (int) (16 * scale + 0.5f);
         params.setMargins(marginLeft, 0, 0, marginBottom);
         dateOfBirthErrorTV.setLayoutParams(params);
         secondUnderlineViewDob.setVisibility(View.VISIBLE);
         dateOfBirthET.setTextColor(getResources().getColor(R.color.red_5));
         if (isDateOfBirthError) {
            secondUnderlineViewDob.startAnimation(anim);
         }
//         bdError.setTypeface(null, Typeface.BOLD);
//         dateOfBirthET.getBackground().setColorFilter(getResources().getColor(R.color.red_dark), PorterDuff.Mode.SRC_ATOP);
      } else {
         dateOfBirthErrorTV.setText("");
         params.setMargins(0, 0, 0, 0);
         dateOfBirthErrorTV.setLayoutParams(params);
         dateOfBirthET.setTextColor(getResources().getColor(R.color.white_70_transparent));
         secondUnderlineViewDob.setVisibility(View.INVISIBLE);
         if (isDateOfBirthError) {
            underlineViewDob.startAnimation(anim);
         }
         isDateOfBirthError = false;
      }
   }
   
   private void validateWeight() {
      String weight = weightET.getText().toString().replaceAll("( )+", "").trim();
      MarginLayoutParams params = (MarginLayoutParams) weightErrorTV.getLayoutParams();
      
      if (weight.length() > 0) {
         if (Integer.parseInt(weight) <= 0) {
            if (!redMarkAnim) {
               redMarkAnim = true;
               secondUnderlineViewWeight.startAnimation(anim);
            }
            isWeightError = true;
            secondUnderlineViewWeight.setVisibility(View.VISIBLE);
            float scale = getResources().getDisplayMetrics().density;
            int marginLeft = (int) (12 * scale + 0.5f);
            int marginBottom = (int) (16 * scale + 0.5f);
            params.setMargins(marginLeft, 0, 0, marginBottom);
            weightErrorTV.setLayoutParams(params);
            weightErrorTV.setText("'Weight' must be greater than 0");
            weightErrorTV.setTextColor(getResources().getColor(R.color.red_5));
            weightET.setTextColor(getResources().getColor(R.color.red_5));
         } else {
            secondUnderlineViewWeight.setVisibility(View.INVISIBLE);
            weightET.setTextColor(getResources().getColor(R.color.white_70_transparent));
            params.setMargins(0, 0, 0, 0);
            weightErrorTV.setLayoutParams(params);
            weightErrorTV.setText("");
            isWeightError = false;
            if (redMarkAnim) {
               redMarkAnim = false;
               underlineViewWeight.startAnimation(anim);
            }
         }
      } else {
         secondUnderlineViewWeight.setVisibility(View.INVISIBLE);
         weightErrorTV.setText("");
         weightET.setTextColor(getResources().getColor(R.color.white_70_transparent));
         params.setMargins(0, 0, 0, 0);
         weightErrorTV.setLayoutParams(params);
         redMarkAnim = false;
         if (isWeightError) {
            underlineViewWeight.startAnimation(anim);
         }
      }
   }
   
   private void validateHeight() {
      String height = heightET.getText().toString().replaceAll("( )+", "").trim();
      MarginLayoutParams params = (MarginLayoutParams) heightErrorTV.getLayoutParams();
      
      if (height.length() > 0) {
         if (Integer.parseInt(height) <= 0) {
            if (!redMarkAnim) {
               redMarkAnim = true;
               secondUnderlineViewHeight.startAnimation(anim);
            }
            isHeightError = true;
            secondUnderlineViewHeight.setVisibility(View.VISIBLE);
            float scale = getResources().getDisplayMetrics().density;
            int marginLeft = (int) (12 * scale + 0.5f);
            int marginBottom = (int) (16 * scale + 0.5f);
            params.setMargins(marginLeft, 0, 0, marginBottom);
            heightErrorTV.setLayoutParams(params);
            heightErrorTV.setText("'Height' must be greater than 0");
            heightErrorTV.setTextColor(getResources().getColor(R.color.red_5));
            heightET.setTextColor(getResources().getColor(R.color.red_5));
         } else {
            secondUnderlineViewHeight.setVisibility(View.INVISIBLE);
            heightET.setTextColor(getResources().getColor(R.color.white_70_transparent));
            params.setMargins(0, 0, 0, 0);
            heightErrorTV.setLayoutParams(params);
            heightErrorTV.setText("");
            isHeightError = false;
            if (redMarkAnim) {
               redMarkAnim = false;
               underlineViewHeight.startAnimation(anim);
            }
         }
      } else {
         secondUnderlineViewHeight.setVisibility(View.INVISIBLE);
         heightErrorTV.setText("");
         heightET.setTextColor(getResources().getColor(R.color.white_70_transparent));
         params.setMargins(0, 0, 0, 0);
         heightErrorTV.setLayoutParams(params);
         redMarkAnim = false;
         if (isHeightError) {
            underlineViewHeight.startAnimation(anim);
         }
      }
   }
   
   private void validateMajorDisease() {
      
   }
   
   private void validatePregnancyPeriod() {
      
   }
   
   /*
    public class UpdatePro extends AsyncTask<String, String, String> {



        String z = "";
        Boolean isSuccess = false;


        String proname = edtproname.getText().toString();
        String prodesc = edtprodesc.getText().toString();


        @Override
        protected void onPreExecute() {
            pbbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            pbbar.setVisibility(View.GONE);
            Toast.makeText(AddProducts.this, r, Toast.LENGTH_SHORT).show();
            if(isSuccess==true) {
                FillList fillList = new FillList();
                fillList.execute("");
            }
        }

        @Override
        protected String doInBackground(String... params) {
            if (proname.trim().equals("") || prodesc.trim().equals(""))
                z = "Please enter User Id and Password";
            else {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {

                        String dates = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
                                .format(Calendar.getInstance().getTime());

                        String query = "Update Producttbl set ProName='"+proname+"',ProDesc='"+prodesc+"' , OnDate='"+dates+"' where Id="+proid;
                        PreparedStatement preparedStatement = con.prepareStatement(query);
                        preparedStatement.executeUpdate();
                        z = "Updated Successfully";

                        isSuccess = true;
                    }
                } catch (Exception ex) {
                    isSuccess = false;
                    z = "Exceptions";
                }
            }
            return z;
        }
    }*/

/*

    public class DeletePro extends AsyncTask<String, String, String> {

        String z = "";
        Boolean isSuccess = false;

        String proname = edtproname.getText().toString();
        String prodesc = edtprodesc.getText().toString();

        @Override
        protected void onPreExecute() {
            pbbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            pbbar.setVisibility(View.GONE);
            Toast.makeText(AddProducts.this, r, Toast.LENGTH_SHORT).show();
            if(isSuccess==true) {
                FillList fillList = new FillList();
                fillList.execute("");
            }
        }

        @Override
        protected String doInBackground(String... params) {
            if (proname.trim().equals("") || prodesc.trim().equals(""))
                z = "Please enter User Id and Password";
            else {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {

                        String dates = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
                                .format(Calendar.getInstance().getTime());

                        String query = "delete from Producttbl where Id="+proid;
                        PreparedStatement preparedStatement = con.prepareStatement(query);
                        preparedStatement.executeUpdate();
                        z = "Deleted Successfully";
                        isSuccess = true;
                    }
                } catch (Exception ex) {
                    isSuccess = false;
                    z = "Exceptions";
                }
            }
            return z;
        }
    }
*/


   public class FillAddressList extends AsyncTask<String, String, String> {
      String z = "";

      List<Map<String, String>> prolist  = new ArrayList<Map<String, String>>();

      @Override
      protected void onPreExecute() {

         pbbar.setVisibility(View.VISIBLE);
      }

      @Override
      protected void onPostExecute(String r) {

         pbbar.setVisibility(View.GONE);
         Toast.makeText(CreateMotherProfileActivity.this, r, Toast.LENGTH_SHORT).show();


         String[] from = { "A", "B", "C" };


         int[] views = { R.id.lblProfileId, R.id.lblProfileName,R.id.lblMomStatus};
         final SimpleAdapter ADA = new SimpleAdapter(CreateMotherProfileActivity.this,
                 prolist, R.layout.profile_list_template_for_mom, from,views);
         districtSpnr.setAdapter(ADA);


         districtSpnr.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {
               HashMap<String, Object> obj = (HashMap<String, Object>) ADA
                       .getItem(arg2);
               String divisionCode = (String) obj.get("A");
               String divisionName = (String) obj.get("B");
              // String momDateOfBirth = (String) obj.get("C");


               Intent intent=new Intent(CreateMotherProfileActivity.this,DashBoardMomActivity.class);
               intent.putExtra("MOM_NAME",divisionCode);
               intent.putExtra("MOM_ID",divisionName);

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
               String query = "select Division_Code,AreaName from [GEO_CODE_BANG] where  Zila_Code  is null and Upazila_Thana_Code  is null and PSA  is null and Union_Code  is null and Mouza_MohollaCode  is null and RMO  is null and VillageCode  is null";
               PreparedStatement ps = con.prepareStatement(query);
               ResultSet rs = ps.executeQuery();

               ArrayList<String> data1 = new ArrayList<String>();
               while (rs.next()) {
                  Map<String, String> datanum = new HashMap<String, String>();
                  datanum.put("A", rs.getString("Division_Code"));
                  datanum.put("B", rs.getString("AreaName"));
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
   
   private void initializer() {
      nameET = (EditText) findViewById(R.id.nameET);
      addressET = (EditText) findViewById(R.id.adressET);
      divisionSpnr = (Spinner) findViewById(R.id.divisionSpnr);
      districtSpnr = (Spinner) findViewById(R.id.districtSpnr);
      thanaSpnr = (Spinner) findViewById(R.id.thanaSpnr);
      unionSpnr = (Spinner) findViewById(R.id.unionSpnr);
      mobileNoET = (EditText) findViewById(R.id.mobileNoET);
      passwordET = (EditText) findViewById(R.id.passwordET);
      retypeET = (EditText) findViewById(R.id.retypeET);
      dateOfBirthET = (TextView) findViewById(R.id.dateOfBirthET);
      bloodGroupSpnr = (Spinner) findViewById(R.id.bloodGroupSpnr);
      majorDiseaseET = (EditText) findViewById(R.id.majorDiseaseET);
      guardianMobileNoET = (EditText) findViewById(R.id.guardianMobileNoET);
      monthlyIncomeET = (EditText) findViewById(R.id.monthlyIncomeET);
      pregnancyPeriodEnableDisable = (LinearLayout) findViewById(R.id.pregnancyPeriodEnableDisable);
      pregnancyPeriodET = (EditText) findViewById(R.id.pregnencyPeriodET);
      weightET = (EditText) findViewById(R.id.weightET);
      heightET = (EditText) findViewById(R.id.heightET);
      newMotherRBtn = (RadioButton) findViewById(R.id.newMotherRBtn);
      pregnentRBtn = (RadioButton) findViewById(R.id.pregnentRBtn);
      pbbar = (ProgressBar) findViewById(R.id.pbbar);
      SaveBtn = (TextView) findViewById(R.id.saveButton);
      
      nameErrorTV = (TextView) findViewById(R.id.nameErrorTV);
      passwordErrorTV = (TextView) findViewById(R.id.passwordErrorTV);
      retypeErrorTV = (TextView) findViewById(R.id.retypePasswordErrorTV);
      addressErrorTV = (TextView) findViewById(R.id.addressErrorTV);
      mobileNoErrorTV = (TextView) findViewById(R.id.mobileErrorTV);
      guardianMobileNoErrorTV = (TextView) findViewById(R.id.guardiantsMobileErrorTV);
      dateOfBirthErrorTV = (TextView) findViewById(R.id.dateOfBirthErrorTV);
      monthlyIncomeErrorTV = (TextView) findViewById(R.id.monthlyIncomeErrorTV);
      weightErrorTV = (TextView) findViewById(R.id.weightErrorTV);
      heightErrorTV = (TextView) findViewById(R.id.heightErrorTV);
      majorDiseaseErrorTV = (TextView) findViewById(R.id.majorDiseaseErrorTV);
      pregnancyPeriodErrorTV = (TextView) findViewById(R.id.pregnancyPeriodErrorTV);
      
      underlineViewName = findViewById(R.id.underline_view_name);
      underlineViewPassword = findViewById(R.id.underline_view_password);
      underlineViewRetypePassword = findViewById(R.id.underline_view_retype_password);
      underlineViewAddress = findViewById(R.id.underline_view_address);
      underlineViewMobileNo = findViewById(R.id.underline_view_mobile_no);
      underlineViewGuardiansMobileNo = findViewById(R.id.underline_view_guardian_mobile_no);
      underlineViewMonthlyIncome = findViewById(R.id.underline_view_monthly_income);
      underlineViewDob = findViewById(R.id.underline_view_dob);
      underlineViewWeight = findViewById(R.id.underline_view_weight);
      underlineViewHeight = findViewById(R.id.underline_view_height);
      underlineViewMajorDisease = findViewById(R.id.underline_view_major_disease);
      underlineViewPregnancyPeriod = findViewById(R.id.underline_view_pregnancy_period);
      
      secondUnderlineViewName = findViewById(R.id.second_underline_view_name);
      secondUnderlineViewPassword = findViewById(R.id.second_underline_view_password);
      secondUnderlineViewRetypePassword = findViewById(R.id.second_underline_view_retype_password);
      secondUnderlineViewAddress = findViewById(R.id.second_underline_view_address);
      secondUnderlineViewMobileNo = findViewById(R.id.second_underline_view_mobile_no);
      secondUnderlineViewGuardiansMobileNo = findViewById(R.id.second_underline_view_guardian_mobile_no);
      secondUnderlineViewMonthlyIncome = findViewById(R.id.second_underline_view_monthly_income);
      secondUnderlineViewDob = findViewById(R.id.second_underline_view_dob);
      secondUnderlineViewWeight = findViewById(R.id.second_underline_view_weight);
      secondUnderlineViewHeight = findViewById(R.id.second_underline_view_height);
      secondUnderlineViewMajorDisease = findViewById(R.id.second_underline_view_major_disease);
      secondUnderlineViewPregnancyPeriod = findViewById(R.id.second_underline_view_pregnancy_period);
   }


   private void getDivisionAddress(String query){

      address = new HashMap<>();

      try {
         Connection con = connectionClass.CONN();
         if (con == null) {
           // z = "Error in connection with SQL server";
         } else {

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

               division_Code = rs.getString("Division_Code");
               areaName = rs.getString("AreaName");

               //address.put(division_Code, areaName);
               address.put(areaName, division_Code);
              // data.add(division_Code);
               data.add(areaName);

            }

            String[] array = data.toArray(new String[0]);

            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,

                    android.R.layout.simple_list_item_1, data);

            divisionSpnr.setAdapter(NoCoreAdapter);

            // z = "Success";
         }
      } catch (Exception ex) {
         //z = "Error retrieving data from table";

      }




   }


   private void getDistrictAddress(String query){

      final HashMap<String,String> address = new HashMap<>();

      try {
         Connection con = connectionClass.CONN();
         if (con == null) {
            // z = "Error in connection with SQL server";
         } else {
            //String query = "select Zila_Code,AreaName from [GEO_CODE_BANG] where Division_Code='"+division_Code+"' and Upazila_Thana_Code  is null and PSA  is null and Union_Code  is null and Mouza_MohollaCode  is null and RMO  is null and VillageCode  is null and Zila_Code  is not null";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

               division_Code = rs.getString("Division_Code");
               areaName = rs.getString("AreaName");

               //address.put(division_Code, areaName);
               address.put(areaName, division_Code);
               // data.add(division_Code);
               data.add(areaName);

            }

            String[] array = data.toArray(new String[0]);

            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,

                    android.R.layout.simple_list_item_1, data);

            districtSpnr.setAdapter(NoCoreAdapter);

            // z = "Success";
         }
      } catch (Exception ex) {
         //z = "Error retrieving data from table";

      }




   }

}