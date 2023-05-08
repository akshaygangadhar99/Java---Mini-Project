package BackendMethods;

import java.sql.*;

public class MinorMethods {
    /*
    This class contains reusable methods used within other classes of this package
     */

    private static final String url = "jdbc:mysql://localhost:3306/";
    private static final String user = "root";
    private static final String password = "0123456789";

    public static String getStName(String strID){
        /*
        This method returns street name for a given street id from tblStreet
         */
        try{
            String dbURL = url + "dbPortal";
            Connection connection = DriverManager.getConnection(dbURL,user,password);
            Statement stmt = connection.createStatement();

            String getStrName = "SELECT street_name FROM tblStreet WHERE " +
                    "street_id = "+strID;

            ResultSet rs = stmt.executeQuery(getStrName);

            String strName = "";

            while(rs.next()){
                strName = rs.getString("street_name");
            }
            return strName;

        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public static String[][] cropArray(int index, String[][] oldArr){
        /*
        This method returns new array containing first "index" rows of oldArr
         */

        String[][] newArr = new String[index][oldArr[0].length];
        for(int i=0; i<index; i++){
            for(int j=0; j<oldArr[0].length; j++){
                newArr[i][j] = oldArr[i][j];
            }
        }

        return newArr;
    }

    public static String[][] getVillaList(int roomType){
        /*
        This method returns a 2D String array containing information pertaining to available villas
        based on room-availability. Details are stored as follows:
        -> Column 1: Street Name
        -> Column 2: Villa No.
        -> Column 3: Distance to University (km)
         */

        String[][] villaDet = new String[95][3];
        int row = 0;
        String[] street = {"1","2","3","6"};
        // Now we extract data based on type of room-sharing
        if(roomType==1){
            // 3-sharing
            try{
                String dbURL = url + "dbPortal";
                Connection connection = DriverManager.getConnection(dbURL,user,password);
                Statement stmt = connection.createStatement();

                String distToUni;
                String villaNo;
                String streetName;

                for(String streetID : street){
                    streetName = MinorMethods.getStName(streetID);

                    String retrieveDetails = "SELECT villa_no,uni_distance FROM tblVilla WHERE " +
                            "availability=1 and street_id="+streetID+" and (room_2 <> 0 or room_3 <> 0)";
                    ResultSet rs = stmt.executeQuery(retrieveDetails);
                    while(rs.next()){
                        villaNo = rs.getString("villa_no");
                        distToUni = Float.toString(rs.getFloat("uni_distance"));

                        villaDet[row][0] = streetName;
                        villaDet[row][1] = villaNo;
                        villaDet[row][2] = distToUni;
                        row += 1;
                    }
                }
                villaDet = MinorMethods.cropArray(row,villaDet);
                return villaDet;
            } catch (SQLException e){
                e.printStackTrace();
            }
        } else{
            // 4-sharing
            try{
                String dbURL = url + "dbPortal";
                Connection connection = DriverManager.getConnection(dbURL,user,password);
                Statement stmt = connection.createStatement();

                String distToUni;
                String villaNo;
                String streetName;


                for(String streetID : street){
                    streetName = MinorMethods.getStName(streetID);

                    String retrieveDetails = "SELECT villa_no,uni_distance FROM tblVilla WHERE " +
                            "availability=1 and street_id="+streetID+" and room_1<>0";
                    ResultSet rs = stmt.executeQuery(retrieveDetails);
                    while(rs.next()){
                        villaNo = rs.getString("villa_no");
                        distToUni = Float.toString(rs.getFloat("uni_distance"));

                        villaDet[row][0] = streetName;
                        villaDet[row][1] = villaNo;
                        villaDet[row][2] = distToUni;

                        row += 1;
                    }
                }

                villaDet = MinorMethods.cropArray(row,villaDet);

                return villaDet;

            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String[][] getBldList(int roomType){
        /*
        This method returns a 2D String array containing information pertaining to available buildings
        based on room-availability. Details are stored as follows:
        -> Column 0: Building ID (UNIQUE)
        -> Column 1: Street Name
        -> Column 2: Building Name
        -> Column 3: Distance to University (km)
         */

        String[] street = {"4"};
        int row = 0;
        // Since there are 18 buildings
        String[][] bldDetails = new String[18][4];

        if(roomType==1){
            // 3-sharing
            try{
                String dbURL = url + "dbPortal";
                Connection connection = DriverManager.getConnection(dbURL,user,password);
                Statement stmt = connection.createStatement();

                String distToUni;
                String bldID;
                String bldNo;
                String streetName;

                for(String streetID : street){
                    streetName = getStName(streetID);

                    String retrieveDetails = "SELECT bld_id,bld_name,uni_distance FROM tblBuilding WHERE " +
                            "availability=1 and street_id="+streetID+" and three_sharing_avail=1";
                    ResultSet rs = stmt.executeQuery(retrieveDetails);
                    while(rs.next()){
                        bldID = rs.getString("bld_id");
                        bldNo = rs.getString("bld_name");
                        distToUni = Float.toString(rs.getFloat("uni_distance"));

                        bldDetails[row][0] = bldID;
                        bldDetails[row][1] = streetName;
                        bldDetails[row][2] = bldNo;
                        bldDetails[row][3] = distToUni;

                        row += 1;
                    }

                    bldDetails = cropArray(row,bldDetails);
                    return bldDetails;
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        } else {
            // 4-sharing
            try{
                String dbURL = url + "dbPortal";
                Connection connection = DriverManager.getConnection(dbURL,user,password);
                Statement stmt = connection.createStatement();

                String distToUni;
                String bldID;
                String bldNo;
                String streetName;

                for(String streetID : street){
                    streetName = getStName(streetID);

                    String retrieveDetails = "SELECT bld_id,bld_name,uni_distance FROM tblBuilding WHERE " +
                            "availability=1 and street_id="+streetID+" and four_sharing_avail=1";
                    ResultSet rs = stmt.executeQuery(retrieveDetails);
                    while(rs.next()){
                        bldID = rs.getString("bld_id");
                        bldNo = rs.getString("bld_name");
                        distToUni = Float.toString(rs.getFloat("uni_distance"));

                        bldDetails[row][0] = bldID;
                        bldDetails[row][1] = streetName;
                        bldDetails[row][2] = bldNo;
                        bldDetails[row][3] = distToUni;

                        row += 1;
                    }

                    bldDetails = cropArray(row,bldDetails);
                    return bldDetails;
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

}
