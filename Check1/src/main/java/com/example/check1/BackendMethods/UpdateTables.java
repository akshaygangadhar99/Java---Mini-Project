package com.example.check1.BackendMethods;
import java.sql.*;
import java.time.LocalTime;
import java.time.LocalDate;

public class UpdateTables {
    /*
    The objective of the methods developed as part of this class is to update all the relevant tables in our
    sql database based on any user-action in the front-end GUI
     */

    /*
    -> One set of methods will pertain to Villa-related tables
    -> Another set of methods will pertain to Apartment and Building-related tables
     */

    private static final String url = "jdbc:mysql://localhost:3306/";
    private static final String user = "root";
    private static final String password = "root";

    /*
    First, we will focus on the Villa-related methods.
    *********************VILLA*******************
    Two inputs:
        (1) bed_id - varchar(10)
        (2) user_id - varchar(10)

    Required outputs:
        (1) Update tblBookingDetails:
            -> Add a new record with following details:
                -> Generate new booking_id - varchar(10)
                -> Add data to - user_id, booking_date, booking_time, villa_bed_id
        (2) Update tblVillaBooking:
            -> For given bed_id, update the "user_id" and "availability" attributes
        (3) Update tblVilla:
            -> From tblVillaBooking, get villa_id for given bed_id
            -> Update the following fields for given villa_id and corresponding bed_id:
                -> room_1/room_2/room_3 availability: decrement by 1
                -> availability: if all rooms are booked
     */

    /*
    (1) updateVillaTables(String bed_id, String user_id):
        -> updateVillaBooking(bed_id, user_id)
        -> updateVilla(villa_id,bed_id)
        -> updateBookingDetails(user_id,bed_id,1)

    (2) updateApartmentTables(String bed_id, String user_id)
        -> updateAptBooking(bed_id, user_id)
        -> updateApartment(apt_id,bed_id)
        -> updateBookingDetails(user_id, bed_id, 2)
     */

    public static Boolean updateVillaTables(String bed_id, String user_id){

        // Update tblVillaBooking
        String villa_id = updateVillaBooking(bed_id, user_id);

        if(villa_id==null){
            System.out.println("updateVillaTables -> null Villa ID Line 57");
            return false;
        } else{
            // Update tblVilla
            if(updateVilla(villa_id,bed_id)){
                // Update tblBookingDetails
                if(updateBookingDetails(user_id,bed_id,1)){
                    return true;
                } else{
                    System.out.println("updateBookingDetails -> Line 66");
                    return false;
                }
            } else{
                System.out.println("updateVilla -> Line 64)");
                return false;
            }
        }
    }

    public static String updateVillaBooking(String bed_id, String user_id){
        /*
        This method updates tblVillaBooking and returns the value of the villa_id attribute for
        given bed_id
         */

        String villa_id = "";

        try{
            String dbURL = url + "dbPortal";
            Connection connection = DriverManager.getConnection(dbURL,user,password);
            Statement stmt = connection.createStatement();

            // Update relevant attribute (user_id, availability) for given bed_id
            String insertAttribute = "UPDATE tblVillaBooking " +
                    "SET user_id = '"+user_id+"'," +
                    "availability = 0 " +
                    "WHERE bed_id = '"+bed_id+"'";
            stmt.executeUpdate(insertAttribute);

            // Retrieve villa_id
            String retrieveRecord = "SELECT * FROM tblVillaBooking " +
                    "WHERE bed_id = '"+bed_id+"'";

            ResultSet rs = stmt.executeQuery(retrieveRecord);
            while(rs.next()){
                villa_id = rs.getString("villa_id");
            }

            return villa_id;

        } catch(SQLException e){
            e.printStackTrace();
        }

        // If process fails, return NULL
        return null;
    }

    public static Boolean updateVilla(String villa_id,String bed_id){
        /*
        This method updates the relevant attributes of tblVilla, namely:
            -> room_1/room_2/room_3
            -> availability
            -> three_sharing_avail/four_sharing_avail
         */

        String room = "";
        try{
            String dbURL = url + "dbPortal";
            Connection connection = DriverManager.getConnection(dbURL,user,password);
            Statement stmt = connection.createStatement();

            int bedNo = Integer.parseInt(bed_id)%10;
            if (bedNo==0){
                bedNo=10;
            }

            if(bedNo<=4){
                room = "room_1";
            } else if(bedNo>4 && bedNo<=7){
                room = "room_2";
            } else{
                room = "room_3";
            }

            String decrementRoom = "UPDATE tblVilla " +
                    "SET "+room+" = "+room+"-1 " +
                    "WHERE villa_id = "+villa_id;

            // Set availability of villa to 0 if all rooms are occupied
            String updateAvailability = "UPDATE tblVilla " +
                    "SET availability = 0 WHERE " +
                    "room_1=0 and room_2=0 and room_3=0 and villa_id = '"+villa_id+"'";

            if(stmt.executeUpdate(decrementRoom)>0){
                return true;
            } else{
                return false;
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static Boolean updateBookingDetails(String user_id, String bed_id, int housingType){
        /*
        If housingType = 1:
            -> VILLA
        else:
            -> APARTMENT
         */

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        try{
            String dbURL = url + "dbPortal";
            Connection connection = DriverManager.getConnection(dbURL,user,password);


            String query = "";
            if (housingType==1){
                query = "INSERT INTO tblBookingDetails " +
                        "(user_id,booking_date,booking_time,villa_bed_id) VALUES " +
                        "(?,?,?,?)";
            } else{
                query = "INSERT INTO tblBookingDetails " +
                        "(user_id,booking_date,booking_time,apt_bed_id) VALUES " +
                        "(?,?,?,?)";
            }

            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setObject(1,user_id);
            stmt.setObject(2,currentDate);
            stmt.setObject(3,currentTime);
            stmt.setObject(4,bed_id);

            // Execute update
            if(stmt.executeUpdate()>0){
                return true;
            } else{
                return false;
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    public static Boolean updateApartmentTables(String bed_id, String user_id){
        // First we will update tblApartmentBooking
        String apt_id = updateAptBooking(bed_id, user_id);

        // Update tblApartment, tblBuilding
        if(updateApartment(apt_id,bed_id)){
            // Update tblBookingDetails
            return updateBookingDetails(user_id, bed_id, 2);
        } else{
            return false;
        }
    }

    public static String updateAptBooking(String bed_id, String user_id){
        /*
        -> This method updates tblAptBooking - by inserting user_id and setting
        availability to 0 for given bed_id
        -> The method returns apt_id as a string
         */
        String apt_id = "";

        try{
            String dbURL = url + "dbPortal";
            Connection connection = DriverManager.getConnection(dbURL,user,password);
            Statement stmt = connection.createStatement();

            // Update relevant attribute (user_id, availability) for given bed_id
            String insertAttribute = "UPDATE tblAptBooking " +
                    "SET user_id = '"+user_id+"'," +
                    "availability = 0 " +
                    "WHERE bed_id = '"+bed_id+"'";
            stmt.executeUpdate(insertAttribute);

            // Retrieve villa_id
            String retrieveRecord = "SELECT * FROM tblAptBooking " +
                    "WHERE bed_id = '"+bed_id+"'";

            ResultSet rs = stmt.executeQuery(retrieveRecord);
            while(rs.next()){
                apt_id = rs.getString("apt_id");
            }

            return apt_id;

        } catch(SQLException e){
            e.printStackTrace();
        }

        // If process fails, return NULL
        return null;

    }

    public static Boolean updateApartment(String apt_id, String bed_id){
        /*

         */

        String room = "";
        try{
            String dbURL = url + "dbPortal";
            Connection connection = DriverManager.getConnection(dbURL,user,password);
            Statement stmt = connection.createStatement();

            int bedNo = Integer.parseInt(bed_id)%10;

            // bedNo = 0 corresponds to bed 10 for each flat
            if(bedNo==0){
                bedNo=10;
            }

            if(bedNo<=4){
                room = "room_1";
            } else if(bedNo>4 && bedNo<=7){
                room = "room_2";
            } else{
                room = "room_3";
            }

            String decrementRoom = "UPDATE tblApartment " +
                    "SET "+room+" = "+room+"-1 " +
                    "WHERE apt_id = "+apt_id;

            // Set availability of apartment to 0 if all rooms are occupied
            String updateAvailability = "UPDATE tblApartment " +
                    "SET availability = 0 WHERE " +
                    "room_1=0 and room_2=0 and room_3=0 and apt_id = '"+apt_id+"'";

            if(stmt.executeUpdate(decrementRoom)>0){
                // Retrieve attribute bld_id
                String retrieveBldID = "SELECT bld_id FROM tblApartment WHERE apt_id = '"+apt_id+"'";
                ResultSet rs = stmt.executeQuery(retrieveBldID);

                if(rs != null){
                    String bld_id = "";
                    while(rs.next()){
                        bld_id = rs.getString("bld_id");
                    }

                    int[] stmtExecCheck = {0,0,0};
                    int[] availabilityCheck = {0,0,0};

                    // Update three_sharing_avail for given building in tblBuilding
                    Boolean checkThree = checkApartmentAvailability(bld_id,2);
                    String updateThreeAvail = "";
                    if(checkThree == null){
                        return false; // This implies that the checkApartmentAvailability() method has failed
                    } else if (!checkThree) {
                        updateThreeAvail = "UPDATE tblBuilding " +
                                "SET three_sharing_avail = 0 WHERE" +
                                "bld_id = '"+bld_id+"'";
                        availabilityCheck[0] = 1;
                        stmtExecCheck[0] = stmt.executeUpdate(updateThreeAvail);
                    }

                    // Update four_sharing_avail for given building in tblBuilding
                    Boolean checkFour = checkApartmentAvailability(bld_id,3);
                    String updateFourAvail = "";
                    if(checkFour==null){
                        return false; // This implies that the checkApartmentAvailability() method has failed
                    } else if(!checkFour){
                        updateFourAvail = "UPDATE tblBuilding " +
                                "SET four_sharing_avail = 0 WHERE" +
                                "bld_id = '"+bld_id+"'";
                        availabilityCheck[1] = 1;
                        stmtExecCheck[1] = stmt.executeUpdate(updateFourAvail);
                    }

                    // Update availability of corresponding building in tblBuilding
                    String updateBldAvail = "";
                    Boolean checkApt = checkApartmentAvailability(bld_id,1);
                    if(checkApt == null){
                        return false; // This implies that the checkApartmentAvailability() method has failed
                    } else if(!checkApt){
                        updateBldAvail = "UPDATE tblBuilding " +
                                "SET availability = 0 WHERE " +
                                "bld_id = '"+bld_id+"'";
                        availabilityCheck[2] = 1;
                        stmtExecCheck[2] = stmt.executeUpdate(updateBldAvail);
                    }

                    int executeSuccess = 1;
                    for(int i=0; i<3; i++){
                        if(availabilityCheck[i]==1){
                            if(stmtExecCheck[i]==0){
                                executeSuccess = 0;
                                break;
                            }
                        }
                    }

                    if(executeSuccess==0){
                        return false;
                    } else {
                        return true;
                    }

                } else{
                    return false;
                }

            } else{
                return false;
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static Boolean checkApartmentAvailability(String bld_id, int checkType){
        /*
        Returns true if apartments are available in tblApartment for given bld_id.
        There are three types of checks:
            (1) checkType = 1: Checks for apartment availability
            (2) checkType = 2: Checks for three-sharing availability
            (3) checkType = 3: Checks for four-sharing availability
         */

        try{
            String dbURL = url + "dbPortal";
            Connection connection = DriverManager.getConnection(dbURL,user,password);
            Statement stmt = connection.createStatement();

            if(checkType==1){
                // Check for apartment availability
                String aptAvailability = "SELECT availability FROM tblApartment WHERE " +
                        "bld_id = '"+bld_id+"'";

                ResultSet rs = stmt.executeQuery(aptAvailability);


                while(rs.next()){
                    if(rs.getInt("availability")==1){
                        return true;
                    }
                }
                return false;

            } else if(checkType==2){
                // Check for three-sharing availability
                String threeShareAvail = "SELECT room_2, room_3 from tblApartment " +
                        "WHERE bld_id = '"+bld_id+"'";
                ResultSet rs = stmt.executeQuery(threeShareAvail);

                while(rs.next()){
                    if(rs.getInt("room_2")!=0 || rs.getInt("room_3")!=0){
                        return true;
                    }
                }
                return false;

            } else{
                // Check for four-sharing availability
                String fourShareAvail = "SELECT room_1 from tblApartment " +
                        "WHERE bld_id = '"+bld_id+"'";
                ResultSet rs = stmt.executeQuery(fourShareAvail);

                while(rs.next()){
                    if(rs.getInt("room_1")!=0){
                        return true;
                    }
                }
                return false;
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        // Return NULL if process fails
        return null;
    }

    public static Boolean updateTables(String user_id, String bed_id, String houseType){
        if(houseType.equalsIgnoreCase("villa")){
            return updateVillaTables(bed_id, user_id);
        } else{
            return updateApartmentTables(bed_id,user_id);
        }
    }

    public static void main(String[] args) {
        System.out.println(updateTables("170933068","125","Villa"));
    }
}
