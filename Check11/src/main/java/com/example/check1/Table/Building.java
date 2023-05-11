package Table;

public class Building {
    private String bld_id;
    private String street_id;
    private String bld_name;
    private float uni_distance;
    private int availability;
    private int three_sharing_avail;
    private int four_sharing_avail;
    private String street_name;

    public Building(){
        // Empty constructor
    }

    public Building(String bldID, String strID, String bldName, float uniDist, int avail, int threeSharing,
                    int fourSharing){
        // Constructor
        this.bld_id = bldID;
        this.street_id = strID;
        this.bld_name = bldName;
        this.uni_distance = uniDist;
        this.availability = avail;
        this.three_sharing_avail = threeSharing;
        this.four_sharing_avail = fourSharing;
    }

    public Building(String bldID, String streetName, String bldName, float uniDist){
        // Constructor
        this.bld_id = bldID;
        this.street_name = streetName;
        this.bld_name = bldName;
        this.uni_distance = uniDist;
    }

    public String getBldID(){
        return this.bld_id;
    }

    public String getStrID(){
        return this.street_id;
    }

    public String bldName(){
        return this.bld_name;
    }

    public float getUniDistance(){
        return this.uni_distance;
    }

    public int getAvailability(){
        return this.availability;
    }

    public int getThree_sharing_avail(){
        return this.three_sharing_avail;
    }

    public int getFour_sharing_avail(){
        return this.four_sharing_avail;
    }

    public String getStreetName(){
        return this.street_name;
    }
}
