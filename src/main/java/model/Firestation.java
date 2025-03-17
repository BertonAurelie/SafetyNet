package model;

public class Firestation {
    private String address;
    private int station;

    //Constructeur
    public Firestation(){}

    public Firestation(String address, int station){
        this.address = address;
        this.station = station;
    }

    //GETTER ET SETTER
    public String getAddress() {return address;}
    public int getStation() {return station;}

    public void setAddress(String address) {this.address = address;}
    public void setStation(int station) {this.station = station;}

    @Override
    public String toString() {
        return "Firestation{" +
                "address='" + address + '\'' +
                ", station=" + station +
                '}';
    }
}
