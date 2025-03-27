package com.aboc.safetyNet.model;

public class Firestation {
    private String address;
    private Integer station;

    //Constructeur
    public Firestation(){}

    public Firestation(String address, Integer station){
        this.address = address;
        this.station = station;
    }

    //GETTER ET SETTER
    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}

    public Integer getStation() {return station;}
    public void setStation(Integer station) {this.station = station;}

    @Override
    public String toString() {
        return "Firestation{" +
                "address='" + address + '\'' +
                ", station=" + station +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Firestation that = (Firestation) o;
        return address.equals(that.address);
    }
}
