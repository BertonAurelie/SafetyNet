package model;

public class Person {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private int zip;
    private String phone;
    private String email;

    //GETTER ET SETTER
    public String getFirstName(){return firstName;}
    public String getLastName(){return lastName;}
    public String getAddress() {return address;}
    public String getCity() {return city;}
    public int getZip() {return zip;}
    public String getPhone() {return phone;}
    public String getEmail() {return email;}

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", zip=" + zip +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

