package com.hackathon.appsoul.mombabycare.model;


public class CheckupModel {
   private int id;
   private boolean checked;
   private String name;
   private String value;
   private String remarks;

   public CheckupModel(){}
   public CheckupModel(boolean checked, String name, String value, String remarks) {
      this.checked = checked;
      this.name = name;
      this.value = value;
      this.remarks = remarks;
   }

   public CheckupModel(String name, String value, String remarks) {
      this.name = name;
      this.value = value;
      this.remarks = remarks;
   }

   public CheckupModel(int id, boolean checked, String name, String value, String remarks) {
      this.id = id;
      this.checked = checked;
      this.name = name;
      this.value = value;
      this.remarks = remarks;
   }

   public void setChecked(boolean checked) {
      this.checked = checked;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public void setRemarks(String remarks) {
      this.remarks = remarks;
   }

   public int getId() {
      return id;
   }

   public boolean isChecked() {
      return checked;
   }

   public String getName() {
      return name;
   }

   public String getValue() {
      return value;
   }

   public String getRemarks() {
      return remarks;
   }

   public class MyViewHolder {
   }
}
