package com.hackathon.appsoul.mombabycare.model;

public class VaccineModel {
   private String id;
   private boolean isChecked;
   private String vaccine;
   private String date;

   public VaccineModel(String id, boolean isChecked, String name, String date) {
      this.id = id;
      this.isChecked = isChecked;
      this.vaccine = name;
      this.date = date;
   }

   public VaccineModel(boolean isChecked, String name, String date) {
      this.isChecked = isChecked;
      this.vaccine = name;
      this.date = date;
   }

   public VaccineModel(String id, boolean isChecked, String date) {
      this.id = id;
      this.isChecked = isChecked;
      this.date = date;
   }

   public VaccineModel(String name, String date) {
      this.vaccine = name;
      this.date = date;
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public void setChecked(boolean checked) {
      isChecked = checked;
   }

   public void setVaccine(String vaccine) {
      this.vaccine = vaccine;
   }

   public void setDate(String date) {
      this.date = date;
   }

   public String getVaccine() {
      return vaccine;
   }

   public String getDate() {
      return date;
   }

   public boolean isChecked() {
      return isChecked;
   }
}
