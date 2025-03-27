package com.aboc.safetyNet.model;

public class Person {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private Integer zip;
    private String phone;
    private String email;

    public Person() {}

    //Constructeur
    public Person(String firstName, String lastName, String address, String city, Integer zip, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
    }

    //GETTER ET SETTER
    public String getFirstName(){return firstName;}
    public String getLastName(){return lastName;}
    public String getAddress() {return address;}
    public String getCity() {return city;}
    public Integer getZip() {return zip;}
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
    public void setZip(Integer zip) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return firstName.equals(person.getFirstName()) && lastName.equals(person.getLastName());
    }
}

