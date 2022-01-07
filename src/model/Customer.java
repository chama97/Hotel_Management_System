package model;

public class Customer {
    private String CId;
    private String CName;
    private String CAddress;
    private String CNationality;
    private String CCity;
    private String CMail;
    private int CContact;

    public Customer() {
    }

    public Customer(String CId, String CName, String CAddress, String CNationality, String CCity, String CMail, int CContact) {
        this.setCId(CId);
        this.setCName(CName);
        this.setCAddress(CAddress);
        this.setCNationality(CNationality);
        this.setCCity(CCity);
        this.setCMail(CMail);
        this.setCContact(CContact);
    }

    public String getCId() {
        return CId;
    }

    public void setCId(String CId) {
        this.CId = CId;
    }

    public String getCName() {
        return CName;
    }

    public void setCName(String CName) {
        this.CName = CName;
    }

    public String getCAddress() {
        return CAddress;
    }

    public void setCAddress(String CAddress) {
        this.CAddress = CAddress;
    }

    public String getCNationality() {
        return CNationality;
    }

    public void setCNationality(String CNationality) {
        this.CNationality = CNationality;
    }

    public String getCCity() {
        return CCity;
    }

    public void setCCity(String CCity) {
        this.CCity = CCity;
    }

    public String getCMail() {
        return CMail;
    }

    public void setCMail(String CMail) {
        this.CMail = CMail;
    }

    public int getCContact() {
        return CContact;
    }

    public void setCContact(int CContact) {
        this.CContact = CContact;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "CId='" + CId + '\'' +
                ", CName='" + CName + '\'' +
                ", CAddress='" + CAddress + '\'' +
                ", CNationality='" + CNationality + '\'' +
                ", CCity='" + CCity + '\'' +
                ", CMail='" + CMail + '\'' +
                ", CContact=" + CContact +
                '}';
    }
}
