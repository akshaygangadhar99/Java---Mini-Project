package BackendMethods;

import java.sql.*;
public class BackendMethods {
    /*
    The primary objective of this class is to develop the methods that will connect the front-end GUI to
    the back-end database, and perform modification operations on the same.
     */

    private static final String url = "jdbc:mysql://localhost:3306/";
    private static final String user = "root";
    private static final String password = "0123456789";

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

    /*
    METHOD 1
    Objective: Display housing details sorted based on (i) Street and (ii) Distance (closest to farthest)
    Return: 2D array containing street_name, villa_no and dist_to_university details
     */

    public static String[][] getHouseList(int houseType, int roomType){
        /*
        houseType:
            1 - villa
            2 - apartment
        roomType:
            1 - 3-sharing
            2 - 4-sharing
         */

        if(houseType==1){
            // Villas are available in 4 streets 1,2,3 and 6
            // Display: Street - Villa No. - Distance from University -> 2D array with 3 subsequent columns

            // Since there are 95 villas in total
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
                        streetName = getStName(streetID);

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
                    villaDet = cropArray(row,villaDet);
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
                        streetName = getStName(streetID);

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

                    villaDet = cropArray(row,villaDet);

                    return villaDet;

                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        } else{
            // APARTMENT DETAILS - WE WILL GIVE DETAILS PERTAINING TO BUILDING
            String[] street = {"4"};
            int row = 0;
            // Since there are 18 buildings
            String[][] bldDetails = new String[18][3];

            if(roomType==1){
                // 3-sharing
                try{
                    String dbURL = url + "dbPortal";
                    Connection connection = DriverManager.getConnection(dbURL,user,password);
                    Statement stmt = connection.createStatement();

                    String distToUni;
                    String bldNo;
                    String streetName;

                    for(String streetID : street){
                        streetName = getStName(streetID);

                        String retrieveDetails = "SELECT bld_name,uni_distance FROM tblBuilding WHERE " +
                                "availability=1 and street_id="+streetID+" and three_sharing_avail=1";
                        ResultSet rs = stmt.executeQuery(retrieveDetails);
                        while(rs.next()){
                            bldNo = rs.getString("bld_name");
                            distToUni = Float.toString(rs.getFloat("uni_distance"));

                            bldDetails[row][0] = streetName;
                            bldDetails[row][1] = bldNo;
                            bldDetails[row][2] = distToUni;

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
                    String bldNo;
                    String streetName;

                    for(String streetID : street){
                        streetName = getStName(streetID);

                        String retrieveDetails = "SELECT bld_name,uni_distance FROM tblBuilding WHERE " +
                                "availability=1 and street_id="+streetID+" and four_sharing_avail=1";
                        ResultSet rs = stmt.executeQuery(retrieveDetails);
                        while(rs.next()){
                            bldNo = rs.getString("bld_name");
                            distToUni = Float.toString(rs.getFloat("uni_distance"));

                            bldDetails[row][0] = streetName;
                            bldDetails[row][1] = bldNo;
                            bldDetails[row][2] = distToUni;

                            row += 1;
                        }

                        bldDetails = cropArray(row,bldDetails);
                        return bldDetails;
                    }
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String[][] threeShareVilla = getHouseList(1,1);
        for(int i=0; i< threeShareVilla.length; i++){
            for(int j=0; j<threeShareVilla[0].length; j++){
                System.out.print("| "+threeShareVilla[i][j]+" |");
            }
            System.out.println("");
        }
    }
}
