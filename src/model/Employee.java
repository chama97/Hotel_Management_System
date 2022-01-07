package model;

public class Employee {
    private String id;
    private String name;
    private String Address;
    private String status;
    private int contact;
    private String mail;
    private double salary;

    public Employee() {
    }

    public Employee(String id, String name, String address, String status, int contact, String mail, double salary) {
        this.setId(id);
        this.setName(name);
        setAddress(address);
        this.setStatus(status);
        this.setContact(contact);
        this.setMail(mail);
        this.setSalary(salary);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String
    toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", Address='" + Address + '\'' +
                ", status='" + status + '\'' +
                ", contact=" + contact +
                ", mail='" + mail + '\'' +
                ", salary=" + salary +
                '}';
    }
}
