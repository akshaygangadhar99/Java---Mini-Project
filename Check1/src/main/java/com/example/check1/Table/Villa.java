package com.example.check1.Table;
import com.example.check1.BackendMethods.MinorMethods;
public class Villa {
    private String villa_id;
    private String street_id;
    private String villa_no;
    private int room_1;
    private int room_2;
    private int room_3;
    private float uni_distance;
    private int availability;
    private String street_name;

    public Villa(){
        // Empty constructor
    }

    public Villa(String streetName, String villaNo, float uniDist){
        // Constructor
        this.street_name = streetName;
        this.villa_no = villaNo;
        this.uni_distance = uniDist;
    }

    public Villa(String vID, String strID, String vNo, int r1, int r2, int r3, float uniDist, int avail){
        // Constructor
        this.villa_id = vID;
        this.street_id = strID;
        this.villa_no = vNo;
        this.room_1 = r1;
        this.room_2 = r2;
        this.room_3 = r3;
        this.uni_distance = uniDist;
        this.availability = avail;
    }

    public String getVillaID(){
        return this.villa_id;
    }

    public String getStreetID(){
        return this.street_id;
    }

    public String getVillaNo(){
        return this.villa_no;
    }

    public int getRoom_1(){
        return this.room_1;
    }

    public int getRoom_2(){
        return this.room_2;
    }

    public int getRoom_3(){
        return this.room_3;
    }

    public int getAvailability(){
        return this.availability;
    }

    public String getStreetName(){
        return this.street_name;
    }

}
