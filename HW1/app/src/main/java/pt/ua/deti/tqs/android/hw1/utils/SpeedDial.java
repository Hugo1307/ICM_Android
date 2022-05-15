package pt.ua.deti.tqs.android.hw1.utils;

public class SpeedDial {

    private String name;
    private String number;

    public SpeedDial(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public SpeedDial() {
        this.name = "-";
        this.number = "";
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}
