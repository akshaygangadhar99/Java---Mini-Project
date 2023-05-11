package Table;

public class Apartment {
    String apt_id;
    String bld_id;
    String apt_no;
    int room_1;
    int room_2;
    int room_3;
    int availability;

    public Apartment(){
        // Empty constructor
    }

    public Apartment(String aptID, String bldID, String aptNo, int r1, int r2, int r3, int avail){
        // Constructor
        this.apt_id = aptID;
        this.bld_id = bldID;
        this.apt_no = aptNo;
        this.room_1 = r1;
        this.room_2 = r2;
        this.room_3 = r3;
        this.availability = avail;
    }

    public String getAptID(){
        return this.apt_id;
    }

    public String getBldID(){
        return this.bld_id;
    }

    public String getAptNo(){
        return this.apt_no;
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
}
