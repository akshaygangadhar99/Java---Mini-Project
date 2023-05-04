package BackendMethods;
import Table.Building;
public class BuildingDetails {
    /*
    This class contains methods that will return details pertaining to available buildings
    based on user-specified choice.

    1 Required Field: Room Type -> 3-sharing / 4-sharing
     */

    static int roomType;

    public BuildingDetails(){
        // Empty constructor
    }

    public BuildingDetails(int roomType){
        // Constructor
        BuildingDetails.roomType = roomType;
    }

    public Building[] getBldObj(){
        /*
        Returns an array of objects of class Building
         */

        String[][] bldDet = MinorMethods.getBldList(roomType);

        Building[] bldObj = new Building[bldDet.length];

        for(int i=0; i<bldDet.length; i++){
            bldObj[i] = new Building(bldDet[i][0],bldDet[i][1],Float.parseFloat(bldDet[i][2]));
        }

        return bldObj;

    }



}
