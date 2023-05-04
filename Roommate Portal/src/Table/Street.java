package Table;
public class Street {
    // Stores values of tblStreet
    String street_id;
    String street_name;
    public Street(){
        // Empty constructor
    }

    public Street(String strID, String strName){
        this.street_id = strID;
        this.street_name = strName;
    }

    public String getStreetID(){
        return this.street_id;
    }

    public String getStreetName(){
        return this.street_name;
    }

}
