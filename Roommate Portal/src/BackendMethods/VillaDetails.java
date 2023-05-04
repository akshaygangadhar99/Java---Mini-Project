package BackendMethods;

import Table.Villa;

public class VillaDetails {
    /*
    This class contains methods that will return details pertaining to available villas
    based on user-specified choice.

    1 Required Field: Room Type -> 3-sharing / 4-sharing
     */

    private static final String url = "jdbc:mysql://localhost:3306/";
    private static final String user = "root";
    private static final String password = "0123456789";

    static int roomType;

    public VillaDetails(){
        // Empty constructor
    }

    public VillaDetails(int roomType){
        // Constructor
        VillaDetails.roomType = roomType;
    }

    public Villa[] getVillaObj(){
        /*
        Returns array of objects of class Villa
         */

        String[][] villaDet = MinorMethods.getVillaList(roomType);
        Villa[] VillaObj = new Villa[villaDet.length];

        String stName;
        String villaNo;
        float distToUni;

        for(int i=0; i<villaDet.length; i++){
            VillaObj[i] = new Villa(villaDet[i][0],villaDet[i][1],Float.parseFloat(villaDet[i][2]));
        }

        return VillaObj;
    }
}




