package com.hackathon.appsoul.mombabycare;

import android.content.Context;
import android.content.SharedPreferences;

import com.hackathon.appsoul.mombabycare.model.ChildModel;
import com.hackathon.appsoul.mombabycare.model.PregnantMother;

public class SharedPreference {
   public static final String PASSWORD = "Password";
   public static final String MOM_ID = "Mom_Id";
   public static final String MOM_NAME = "Mom_Name";
   public static final String MOM_DOB = "Mom_dob";
   public static final String MOM_WEIGHT = "Mom_Weight";
   public static final String MOM_HEIGHT = "Mom_Height";
   public static final String MOM_BLOOD_GROUP = "Mom_Blood_Group";
   public static final String MOM_STATUS = "Mom_Status";

   public static final String BABY_ID = "Baby_Id";
   public static final String BABY_NAME = "Baby_Name";
   public static final String BABY_DOB = "Baby_dob";
   public static final String BABY_WEIGHT = "Baby_Weight";
   public static final String BABY_HEIGHT = "Baby_Height";
   public static final String BABY_BLOOD_GROUP = "Baby_Blood_Group";
   public static final String BABY_GENDER = "Baby_Gender";
   public static final String BABY_AGE = "Baby_Age";

   public static final String HW_ID = "HW_id";

   Context context;
   SharedPreferences.Editor editor;
   private SharedPreferences sharedPreferences;
   private String name;
   private PregnantMother mother;

   public SharedPreference(Context context) {
      this.context = context;
      sharedPreferences = context.getSharedPreferences("mom&baby", Context.MODE_PRIVATE);
      editor = sharedPreferences.edit();
   }

   public void setLoginInfo(String name, String password) {
      editor.putString(MOM_NAME, name);
      editor.putString(PASSWORD, password);
      editor.commit();
   }

   public void setMomId(String id) {
      editor.putString(MOM_ID, id);
      editor.commit();
   }

   public void setHWId(String id){
      editor.putString(HW_ID, id);
      editor.commit();
   }

   public String getHwId(){
      return sharedPreferences.getString(HW_ID, null);
   }

   public String[] getLoginInfo() {
      return new String[]{sharedPreferences.getString(MOM_NAME, null), sharedPreferences.getString(PASSWORD, null)};
   }

   public String getMomId() {
      return sharedPreferences.getString(MOM_ID, null);
   }

   public String getMomName(){
      return sharedPreferences.getString(MOM_NAME, null);
   }
   public String getChildId() {
      return sharedPreferences.getString(BABY_ID, null);
   }

   public void setChildId(String id) {
      editor.putString(BABY_ID, id);
      editor.commit();
   }
   
   public void setMotherInfo(PregnantMother mother) {
      editor.putString(MOM_ID, mother.getId());
      editor.putString(MOM_NAME, mother.getName());
      editor.putString(MOM_DOB, mother.getDateOfBirth());
      editor.putString(MOM_WEIGHT, mother.getWeightBFPrag());
      editor.putString(MOM_HEIGHT, mother.getHeight());
      editor.putString(MOM_BLOOD_GROUP, mother.getBloodGroup());
      editor.commit();
   }

   public void setChildInfo(ChildModel child) {
      editor.putString(BABY_ID, child.getId());
      editor.putString(MOM_ID, child.getMotherId());
      editor.putString(BABY_NAME, child.getBabyName());
      editor.putString(MOM_NAME, child.getMothersName());
      editor.putString(BABY_DOB, child.getBirthDate());
      editor.putString(BABY_GENDER, child.getGender());
      editor.putString(BABY_WEIGHT, child.getWeight());
      editor.putString(BABY_HEIGHT, child.getHeight());
      editor.putString(BABY_BLOOD_GROUP, child.getBloodGroup());
      editor.putString(BABY_AGE, child.getAge());
      editor.commit();
   }


   public PregnantMother getMotherInfo(){
      return new PregnantMother(sharedPreferences.getString(MOM_ID, null), sharedPreferences.getString(MOM_NAME, null), sharedPreferences.getString(MOM_DOB, null),sharedPreferences.getString(MOM_BLOOD_GROUP, null), sharedPreferences.getString(MOM_WEIGHT, null), sharedPreferences.getString(MOM_HEIGHT, null), sharedPreferences.getString(MOM_STATUS, null));
   }

   public ChildModel getChildInfo(){
      return new ChildModel(sharedPreferences.getString(BABY_ID, null), sharedPreferences.getString(MOM_ID, null), sharedPreferences.getString(BABY_NAME, null), sharedPreferences.getString(MOM_NAME, null), sharedPreferences.getString(BABY_GENDER, null), sharedPreferences.getString(BABY_HEIGHT, null),sharedPreferences.getString(BABY_WEIGHT, null), sharedPreferences.getString(BABY_BLOOD_GROUP, null), sharedPreferences.getString(BABY_DOB, null), sharedPreferences.getString(BABY_AGE, null));
   }
}
