package com.hackathon.appsoul.mombabycare.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hackathon.appsoul.mombabycare.R;
import com.hackathon.appsoul.mombabycare.SharedPreference;
import com.hackathon.appsoul.mombabycare.classes.GradientBackgroundPainter;
import com.hackathon.appsoul.mombabycare.server.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/*
   lines added by TONMOY: line: 44, 134, 55, 62-69, 113-114, 208-212.
 */

public class CreateChildProfileActivity extends AppCompatActivity implements TextWatcher, OnFocusChangeListener {
   private EditText babyNameET;
   private EditText babyWeightET;
   private EditText babyHeightET;
   private TextView babyDateOfBirthET;
   private Spinner babyBloodGroupSpnr;
   private EditText babyDeliveryStatusET;
   private EditText babyBirthDefectET;
   private RadioButton babyMaleRBtn;
   private RadioButton babyFemaleRBtn;
   private TextView saveButton;
   
   private TextView nameErrorTV;
   private TextView dateOfBirthErrorTV;
   private TextView weightErrorTV;
   private TextView heightErrorTV;
   private TextView deliveryStatusErrorTV;
   private TextView birthDefectErrorTV;
   
   private View underlineViewName;
   private View underlineViewDob;
   private View underlineViewWeight;
   private View underlineViewHeight;
   private View underlineViewDeliveryStatus;
   private View underlineViewBirthDefect;
   
   private View secondUnderlineViewName;
   private View secondUnderlineViewDob;
   private View secondUnderlineViewWeight;
   private View secondUnderlineViewHeight;
   private View secondUnderlineViewDeliveryStatus;
   private View secondUnderlineViewBirthDefect;
   
   private Animation anim;
   
   private boolean isNameError = true;
   private boolean isDateOfBirthError = true;
   private boolean isWeightError = true;
   private boolean isHeightError = true;
   private boolean isDeliveryStatusError = true;
   private boolean isBirthDefectError = true;
   
   private boolean redMarkAnim;
   
   String[] bloodGroups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};

   SharedPreference sharedPreference;
   int momId;
   String babyGender = "Male";

   SQLConnection connectionClass;

   ProgressBar pbbar;
   GradientBackgroundPainter gradientBackgroundPainter;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_create_child_profile);
      initializer();
      
      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);
      toolbar.setTitleTextColor(getResources().getColor(R.color.white_70_transparent));
      getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button1);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      LinearLayout background = (LinearLayout) findViewById(R.id.colorFlow);
      gradientBackgroundPainter = new GradientBackgroundPainter(background);
      gradientBackgroundPainter.start();
   
   
      anim = AnimationUtils.loadAnimation(this, R.anim.scale);
   
      babyNameET.setOnFocusChangeListener(this);
      babyDateOfBirthET.setOnFocusChangeListener(this);
      babyWeightET.setOnFocusChangeListener(this);
      babyHeightET.setOnFocusChangeListener(this);
      babyDeliveryStatusET.setOnFocusChangeListener(this);
      babyBirthDefectET.setOnFocusChangeListener(this);
   
      babyNameET.addTextChangedListener(this);
      babyDateOfBirthET.addTextChangedListener(this);
      babyWeightET.addTextChangedListener(this);
      babyHeightET.addTextChangedListener(this);
      babyDeliveryStatusET.addTextChangedListener(this);
      babyBirthDefectET.addTextChangedListener(this);
      
      
      pbbar = (ProgressBar) findViewById(R.id.pbbar);
      pbbar.setVisibility(View.GONE);
      sharedPreference = new SharedPreference(getBaseContext());
      

      ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, bloodGroups);
      arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      babyBloodGroupSpnr.setAdapter(arrayAdapter);
      babyBloodGroupSpnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

         }

         @Override
         public void onNothingSelected(AdapterView<?> parent) {

         }
      });

      //momId = Integer.parseInt(sharedPreference.getMomId());
      //momId = (sharedPreference.getMomId());

      connectionClass = new SQLConnection();

      babyMaleRBtn.setChecked(true);

      babyMaleRBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (!babyMaleRBtn.isChecked()) {

               babyGender = "Female";
            }
            else {
               babyGender = "Male";
            }

         }
      });

      saveButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            AddPro addPro = new AddPro();
            addPro.execute("");
         }
      });
   }

   private void initializer() {
      babyNameET = (EditText) findViewById(R.id.babyNameET);
      babyWeightET = (EditText) findViewById(R.id.babyWeightET);
      babyHeightET = (EditText) findViewById(R.id.babyHeightET);
      babyDateOfBirthET = (TextView) findViewById(R.id.babyDateOfBirthET);
      babyBloodGroupSpnr = (Spinner) findViewById(R.id.babyBloodGroupSpnr);
      babyDeliveryStatusET = (EditText) findViewById(R.id.babyDeliveryStatusET);
      babyBirthDefectET = (EditText) findViewById(R.id.babyBirthDefectET);

      babyMaleRBtn = (RadioButton) findViewById(R.id.babyMaleRBtn);
      babyFemaleRBtn = (RadioButton) findViewById(R.id.babyFemaleRBtn);

      saveButton = (TextView) findViewById(R.id.saveButton);
   
      nameErrorTV = (TextView) findViewById(R.id.nameErrorTV);
      dateOfBirthErrorTV = (TextView) findViewById(R.id.dateOfBirthErrorTV);
      weightErrorTV = (TextView) findViewById(R.id.weightErrorTV);
      heightErrorTV = (TextView) findViewById(R.id.heightErrorTV);
      deliveryStatusErrorTV = (TextView) findViewById(R.id.deliveryErrorTV);
      birthDefectErrorTV = (TextView) findViewById(R.id.birthDefectErrorTV);
   
      underlineViewName = findViewById(R.id.underline_view_name);
      underlineViewDob = findViewById(R.id.underline_view_dob);
      underlineViewWeight = findViewById(R.id.underline_view_weight);
      underlineViewHeight = findViewById(R.id.underline_view_height);
      underlineViewDeliveryStatus = findViewById(R.id.underline_view_delivery_status);
      underlineViewBirthDefect = findViewById(R.id.underline_view_birth_defect);
   
      secondUnderlineViewName = findViewById(R.id.second_underline_view_name);
      secondUnderlineViewDob = findViewById(R.id.second_underline_view_dob);
      secondUnderlineViewWeight = findViewById(R.id.second_underline_view_weight);
      secondUnderlineViewHeight = findViewById(R.id.second_underline_view_height);
      secondUnderlineViewDeliveryStatus = findViewById(R.id.second_underline_view_delivery_status);
      secondUnderlineViewBirthDefect = findViewById(R.id.second_underline_view_birth_defect);
   }

   public void datePicker(View view) {
      DatePickerFragment datePickerFragment = new DatePickerFragment(babyDateOfBirthET);
      datePickerFragment.show(getFragmentManager(), "date");
   }

   public class AddPro extends AsyncTask<String, String, String> {

      String z = "";
      Boolean isSuccess = false;

      String babyName = babyNameET.getText().toString();
      String dateOfBirth = babyDateOfBirthET.getText().toString();
      String bloodGroupspnr = babyBloodGroupSpnr.getSelectedItem().toString();
      //String babyGender=babyBloodGroupSpnr
      String newbabyGender = babyGender;
      String height = babyHeightET.getText().toString();
      String weight = babyWeightET.getText().toString();
      String deliveryStatus = babyDeliveryStatusET.getText().toString();
      String bloodGroup = babyBloodGroupSpnr.getSelectedItem().toString();
      String birthDefect = babyBirthDefectET.getText().toString();
      int momId = Integer.parseInt(sharedPreference.getMomId());
      String hwId = "12001";
      String InputByUser = "HW";

      @Override
      protected void onPreExecute() {
         pbbar.setVisibility(View.VISIBLE);
      }

      @Override
      protected void onPostExecute(String r) {
         pbbar.setVisibility(View.GONE);
         Toast.makeText(CreateChildProfileActivity.this, r, Toast.LENGTH_SHORT).show();
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

               String dates = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH).format(Calendar.getInstance().getTime());
               String vacineYear = "0-5";
               String inputbyUser="HW";
               sharedPreference = new SharedPreference(getBaseContext());
               //momId = sharedPreference.getMomId();
               String query = "EXEC ProfileNew_Baby_InsertSP " + momId + ",'" + babyName + "','" + dateOfBirth + "','" + newbabyGender + "', '" + height + "', '" + weight + "', '" + deliveryStatus + "', '" + bloodGroup + "', '" + birthDefect + "', '" + hwId + "','" + dates + "','" + inputbyUser + "','A','Client','" + vacineYear + "'";
               PreparedStatement preparedStatement = con.prepareStatement(query);
               preparedStatement.executeUpdate();
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
      
      View view = getCurrentFocus();
      
      
      if (view == babyNameET) {
         validateName();
      }
      
      else if (view == babyDateOfBirthET) {
         validateDateOfBirth();
      }
      
      else if (view == babyWeightET) {
         validateWeight();
      }
      
      else if (view == babyHeightET) {
         validateHeight();
      }
      
      else if (view == babyBirthDefectET) {
         validateDeliveryStatus();
      }
      
      else if (view == babyDeliveryStatusET) {
         validateBirthDefect();
      }
   }
   
   @Override
   public void afterTextChanged(Editable s) {
      
   }
   
   
   @Override
   public void onFocusChange(View v, boolean hasFocus) {
      
      if (v == babyNameET) {
         if (hasFocus) {
            underlineViewName.setVisibility(View.VISIBLE);
            underlineViewName.startAnimation(anim);
         } else {
            underlineViewName.setVisibility(View.INVISIBLE);
         }
      }
      
      else if (v == babyDateOfBirthET) {
         if (hasFocus) {
            underlineViewDob.setVisibility(View.VISIBLE);
            underlineViewDob.startAnimation(anim);
            datePicker(v);
            Toast.makeText(this, "gain focus", Toast.LENGTH_SHORT).show();
         } else {
            underlineViewDob.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "loses focus", Toast.LENGTH_SHORT).show();
         }
      }
      
      else if (v == babyWeightET) {
         if (hasFocus) {
            underlineViewWeight.setVisibility(View.VISIBLE);
            underlineViewWeight.startAnimation(anim);
         } else {
            underlineViewWeight.setVisibility(View.INVISIBLE);
         }
      }
      
      else if (v == babyHeightET) {
         if (hasFocus) {
            underlineViewHeight.setVisibility(View.VISIBLE);
            underlineViewHeight.startAnimation(anim);
         } else {
            underlineViewHeight.setVisibility(View.INVISIBLE);
         }
      }
      
      else if (v == babyBirthDefectET) {
         if (hasFocus) {
            underlineViewBirthDefect.setVisibility(View.VISIBLE);
            underlineViewBirthDefect.startAnimation(anim);
         } else {
            underlineViewBirthDefect.setVisibility(View.INVISIBLE);
         }
      }
      
      else if (v == babyDeliveryStatusET) {
         if (hasFocus) {
            underlineViewDeliveryStatus.setVisibility(View.VISIBLE);
            underlineViewDeliveryStatus.startAnimation(anim);
         } else {
            underlineViewDeliveryStatus.setVisibility(View.INVISIBLE);
         }
      }
   }
   
   
   public void validateName() {
      
   }
   
   public void validateDateOfBirth() {
   
      long time = System.currentTimeMillis();
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      String date = dateFormat.format(time);
   
      TextView bdError = (TextView) findViewById(R.id.dateOfBirthErrorTV);
      assert bdError != null;
      MarginLayoutParams params = (MarginLayoutParams) bdError.getLayoutParams();
      /*LinearLayout.LayoutParams params1 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
      params1.setMargins(16,0,0,16);*/
   
      String bd = babyDateOfBirthET.getText().toString();
      if (bd.compareTo(date) > 0) {
         bdError.setText("'Date of Birth' can't be greater than current date");
         bdError.setTextColor(getResources().getColor(R.color.red_5));
         float scale = getResources().getDisplayMetrics().density;
         int marginLeft = (int) (12 * scale + 0.5f);
         int marginBottom = (int) (16 * scale + 0.5f);
         params.setMargins(marginLeft, 0, 0, marginBottom);
         bdError.setLayoutParams(params);
         secondUnderlineViewDob.setVisibility(View.VISIBLE);
         secondUnderlineViewDob.startAnimation(anim);
         babyDateOfBirthET.setTextColor(getResources().getColor(R.color.red_5));

//         bdError.setTypeface(null, Typeface.BOLD);
//         dateOfBirthET.getBackground().setColorFilter(getResources().getColor(R.color.red_dark), PorterDuff.Mode.SRC_ATOP);
      } else {
         bdError.setText("");
         params.setMargins(0, 0, 0, 0);
         bdError.setLayoutParams(params);
         babyDateOfBirthET.setTextColor(getResources().getColor(R.color.white_70_transparent));
         secondUnderlineViewDob.setVisibility(View.INVISIBLE);
//         underlineViewDob.setVisibility(View.VISIBLE);
         underlineViewDob.startAnimation(anim);
      }
   }
   
   public void validateWeight() {
      
   }
   
   public void validateHeight() {
      
   }
   
   public void validateDeliveryStatus() {
      
   }
   
   public void validateBirthDefect() {
      
   }
}
