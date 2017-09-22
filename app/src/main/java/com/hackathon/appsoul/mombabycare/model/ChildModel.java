package com.hackathon.appsoul.mombabycare.model;//

public class ChildModel {
   private String id;
   private String motherId;
   private String babyName;
   private String gender;
   private String height;
   private String weight;
   private String bloodGroup;
   private String birthDate;
   private String age;
   private String mothersName;

   public ChildModel(String id, String motherId, String babyName, String mothersName, String gender, String height, String weight, String bloodGroup, String birthDate, String age) {
      this.id = id;
      this.motherId = motherId;
      this.babyName = babyName;
      this.mothersName = mothersName;
      this.gender = gender;
      this.height = height;
      this.weight = weight;
      this.bloodGroup = bloodGroup;
      this.birthDate = birthDate;
      this.age = age;
   }

   public String getId() {
      return id;
   }

   public String getMothersName() {
      return mothersName;
   }

   public String getMotherId() {
      return motherId;
   }

   public String getBabyName() {
      return babyName;
   }

   public String getGender() {
      return gender;
   }

   public String getHeight() {
      return height;
   }

   public String getWeight() {
      return weight;
   }

   public String getBloodGroup() {
      return bloodGroup;
   }

   public String getBirthDate() {
      return birthDate;
   }

   public String getAge() {
      return age;
   }
}
