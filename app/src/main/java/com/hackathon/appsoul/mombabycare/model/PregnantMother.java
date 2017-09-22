package com.hackathon.appsoul.mombabycare.model;

public class PregnantMother {
    private String id;
    private String Name;
    private String Address;
    private String Division;
    private String District;
    private String Upazila_Thana;
    private String Union_Ward;
    private String MobileNo;
    private String Password;
    private String DateOfBirth;
    private String BloodGroup;
    private String MajorDiseases;
    private String GurdianMobileNo;
    private String MonthlyIncome;
    private String PragnantDueDate;
    private String WeightBFPrag;
    private String Height;
    private String NewMother_Pragnant;
    private String HW_ID;
    private String CreateDate;
    private String isActive;
    private String ServerClient;
    private String InputByUser;

    public PregnantMother() {
    }

   public PregnantMother(String id, String name, String newMother_Pragnant) {
      this.id = id;
      Name = name;
      NewMother_Pragnant = newMother_Pragnant;
   }

   public PregnantMother(String name, String address, String division, String district, String upazila_Thana, String union_Ward, String mobileNo, String password, String dateOfBirth, String bloodGroup, String majorDiseases, String gurdianMobileNo, String monthlyIncome, String pragnantDueDate, String weightBFPrag, String height, String newMother_Pragnant, String HW_ID, String createDate, String isActive, String serverClient, String inputByUser) {
        Name = name;
        Address = address;
        Division = division;
        District = district;
        Upazila_Thana = upazila_Thana;
        Union_Ward = union_Ward;
        MobileNo = mobileNo;
        Password = password;
        DateOfBirth = dateOfBirth;
        BloodGroup = bloodGroup;
        MajorDiseases = majorDiseases;
        GurdianMobileNo = gurdianMobileNo;
        MonthlyIncome = monthlyIncome;
        PragnantDueDate = pragnantDueDate;
        WeightBFPrag = weightBFPrag;
        Height = height;
        NewMother_Pragnant = newMother_Pragnant;
        this.HW_ID = HW_ID;
        CreateDate = createDate;
        this.isActive = isActive;
        ServerClient = serverClient;
        InputByUser = inputByUser;
    }

    public PregnantMother(String id, String name, String address, String division, String district, String upazila_Thana, String union_Ward, String mobileNo, String password, String dateOfBirth, String bloodGroup, String majorDiseases, String gurdianMobileNo, String monthlyIncome, String pragnantDueDate, String weightBFPrag, String height, String newMother_Pragnant, String HW_ID, String createDate, String isActive, String serverClient, String inputByUser) {
        this.id = id;
        Name = name;
        Address = address;
        Division = division;
        District = district;
        Upazila_Thana = upazila_Thana;
        Union_Ward = union_Ward;
        MobileNo = mobileNo;
        Password = password;
        DateOfBirth = dateOfBirth;
        BloodGroup = bloodGroup;
        MajorDiseases = majorDiseases;
        GurdianMobileNo = gurdianMobileNo;
        MonthlyIncome = monthlyIncome;
        PragnantDueDate = pragnantDueDate;
        WeightBFPrag = weightBFPrag;
        Height = height;
        NewMother_Pragnant = newMother_Pragnant;
        this.HW_ID = HW_ID;
        CreateDate = createDate;
        this.isActive = isActive;
        ServerClient = serverClient;
        InputByUser = inputByUser;
    }

   public PregnantMother(String id, String name, String dateOfBirth, String bloodGroup, String weightBFPrag, String height, String newMother_Pragnant) {
      this.id = id;
      Name = name;
      DateOfBirth = dateOfBirth;
      BloodGroup = bloodGroup;
      WeightBFPrag = weightBFPrag;
      Height = height;
      NewMother_Pragnant = newMother_Pragnant;
   }

   public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDivision() {
        return Division;
    }

    public void setDivision(String division) {
        Division = division;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getUpazila_Thana() {
        return Upazila_Thana;
    }

    public void setUpazila_Thana(String upazila_Thana) {
        Upazila_Thana = upazila_Thana;
    }

    public String getUnion_Ward() {
        return Union_Ward;
    }

    public void setUnion_Ward(String union_Ward) {
        Union_Ward = union_Ward;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getBloodGroup() {
        return BloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        BloodGroup = bloodGroup;
    }

    public String getMajorDiseases() {
        return MajorDiseases;
    }

    public void setMajorDiseases(String majorDiseases) {
        MajorDiseases = majorDiseases;
    }

    public String getGurdianMobileNo() {
        return GurdianMobileNo;
    }

    public void setGurdianMobileNo(String gurdianMobileNo) {
        GurdianMobileNo = gurdianMobileNo;
    }

    public String getMonthlyIncome() {
        return MonthlyIncome;
    }

    public void setMonthlyIncome(String monthlyIncome) {
        MonthlyIncome = monthlyIncome;
    }

    public String getPragnantDueDate() {
        return PragnantDueDate;
    }

    public void setPragnantDueDate(String pragnantDueDate) {
        PragnantDueDate = pragnantDueDate;
    }

    public String getWeightBFPrag() {
        return WeightBFPrag;
    }

    public void setWeightBFPrag(String weightBFPrag) {
        WeightBFPrag = weightBFPrag;
    }

    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = height;
    }

    public String getNewMother_Pragnant() {
        return NewMother_Pragnant;
    }

    public void setNewMother_Pragnant(String newMother_Pragnant) {
        NewMother_Pragnant = newMother_Pragnant;
    }

    public String getHW_ID() {
        return HW_ID;
    }

    public void setHW_ID(String HW_ID) {
        this.HW_ID = HW_ID;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getServerClient() {
        return ServerClient;
    }

    public void setServerClient(String serverClient) {
        ServerClient = serverClient;
    }

    public String getInputByUser() {
        return InputByUser;
    }

    public void setInputByUser(String inputByUser) {
        InputByUser = inputByUser;
    }
}
