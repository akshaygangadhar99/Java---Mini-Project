package com.example.check1.BackendMethods;
import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import com.example.check1.BackendMethods.UpdateTables.*;
import com.example.check1.BackendMethods.MinorMethods.*;
public class AdminMethods {
    private static final String url = "jdbc:mysql://localhost:3306/";
    private static final String user = "root";
    private static final String password = "0123456789";

    /*
    This class contains methods that will update relevant tables based on admin-action, which basically involves
    modifying availability of a:
        -> building/apartment/room
        -> villa/room
     */

    public static Boolean changeBedAvailability(String housing_id, int availability, int houseType){
        /*
        This method changes the availability of all beds of a given apartment to corresponding availability: 0 or 1.
            -> 1 - Villa =====> housing_id : villa_id
            -> 2 - Apartment =====> housing_id : apt_id

        METHOD IS CURRENTLY INCOMPLETE
         */

        try{
            String dbURL = url + "dbPortal";
            Connection connection = DriverManager.getConnection(dbURL,user,password);
            Statement stmt = connection.createStatement();

            if(houseType==1){
                // Villa
                String updateVillaBooking = "UPDATE tblVillaBooking " +
                        "SET availability = "+availability+" " +
                        "WHERE villa_id = '"+housing_id+"'";
                if(stmt.executeUpdate(updateVillaBooking)>0){
                    return true;
                } else{
                    return false;
                }
            } else{
                // Apartment
                String updateAptBooking = "UPDATE tblAptBooking " +
                        "SET availability = "+availability+" " +
                        "WHERE apt_id = '"+housing_id+"'";
                if(stmt.executeUpdate(updateAptBooking)>0){
                    return true;
                } else{
                    return false;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        // If process fails, i.e., failure in establishing SQL connection, return false
        return false;
    }

    public static Boolean changeAptRoomAvailability(String apt_id, int roomNo, int availability){
        /*
        This method changes the availability of beds of a given room to admin-specified value
        METHOD IS CURRENTLY INCOMPLETE
         */

        String room = "";
        try{
            String dbURL = url + "dbPortal";
            Connection connection = DriverManager.getConnection(dbURL,user,password);
            Statement stmt = connection.createStatement();

            if(roomNo==1){
                room = "room_1";
            } else if (roomNo==2){
                room = "room_2";
            } else{
                room = "room_3";
            }
            String modifyAvailability = "UPDATE tblApartment " +
                    "SET "+room+" = "+availability+" " +
                    "WHERE apt_id = "+apt_id;
            int check = stmt.executeUpdate(modifyAvailability);
            System.out.println(check);
            if(check>0){
                return true;
            } else {
                return false;
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;

    }

    public static Boolean changeApartmentAvailability(String apt_id, int availability){
        /*
        This method modifies the availability of a given apartment for given apt_id in tblApartment and subsequently
        the rooms, and beds as well in tblApartment and tblAptBooking, and tblBuilding

        Affected tables:
            (1) tblApartment
                -> availability
                -> room_1
                -> room_2
                -> room_3
            (2) tblAptBooking
                -> availability
            (4) tblBuilding
                -> availability
                -> three_share_avail
                -> four_share_avail

         */

        try{
            String dbURL = url + "dbPortal";
            Connection connection = DriverManager.getConnection(dbURL,user,password);
            Statement stmt = connection.createStatement();

            String updateApartment = "UPDATE tblApartment " +
                    "SET availability =  "+availability+" " +
                    "WHERE apt_id = '"+apt_id+"'";

            if(stmt.executeUpdate(updateApartment)>0){
                // Now we update rooms of given apartment
                String updateRooms = "";
                if(availability==1){
                    updateRooms = "UPDATE tblApartment " +
                            "SET room_1=4, room_2=3, room_3=3 " +
                            "WHERE apt_id='"+apt_id+"'";
                } else{
                    updateRooms = "UPDATE tblApartment " +
                            "SET room_1=0, room_2=0, room_3=0 " +
                            "WHERE apt_id='"+apt_id+"'";
                }
                if(stmt.executeUpdate(updateRooms)>0){
                    // Now we update availability of beds of specified apt_id
                    String updateBeds = "UPDATE tblAptBooking " +
                            "SET availability = "+availability+", " +
                            "user_id = NULL " +
                            "WHERE apt_id = '"+apt_id+"'";

                    if(stmt.executeUpdate(updateBeds)>0){
                        // Now, we that tblApartment and tblAptBooking are updated, we move onto tblBuilding -> availability, three_sharing_avail, four_sharing_avail
                        // First we retrieve the bld_id
                        String getBedID = "SELECT bld_id FROM tblApartment WHERE apt_id = '"+apt_id+"'";
                        ResultSet rs = stmt.executeQuery(getBedID);
                        String bld_id = "";
                        if(rs!=null){
                            while(rs.next()){
                                bld_id = rs.getString("bld_id");
                            }
                            String updateAvailability = "";
                            String updateThreeShare = "";
                            String updateFourShare = "";

                            if(UpdateTables.checkApartmentAvailability(bld_id,1)){
                                // Set availability to 1
                                updateAvailability = "UPDATE tblBuilding SET availability = 1 WHERE bld_id = '"+bld_id+"'";
                            } else{
                                // Set availability to 0
                                updateAvailability = "UPDATE tblBuilding SET availability = 0 WHERE bld_id = '"+bld_id+"'";
                            }

                            if(UpdateTables.checkApartmentAvailability(bld_id,2)){
                                // Set three_sharing_avail to 1
                                updateThreeShare = "UPDATE tblBuilding SET three_sharing_avail = 1 WHERE bld_id = '"+bld_id+"'";
                            } else{
                                // Set three_sharing_avail to 0
                                updateThreeShare = "UPDATE tblBuilding SET three_sharing_avail = 0 WHERE bld_id = '"+bld_id+"'";
                            }

                            if(UpdateTables.checkApartmentAvailability(bld_id,3)){
                                // Set four_sharing_avail to 1
                                updateFourShare = "UPDATE tblBuilding SET four_sharing_avail = 1 WHERE bld_id = '"+bld_id+"'";
                            } else{
                                // Set four_sharing_avail to 0
                                updateFourShare = "UPDATE tblBuilding SET four_sharing_avail = 0 WHERE bld_id = '"+bld_id+"'";
                            }

                            int a = stmt.executeUpdate(updateAvailability);
                            int b = stmt.executeUpdate(updateThreeShare);
                            int c = stmt.executeUpdate(updateFourShare);

                            if((a>0) && (b>0) && (c>0)){
                                // Our tasks are completed, return true
                                return true;
                            } else{
                                return false;
                            }

                        } else{
                            return false;
                        }
                    } else{
                        return false;
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

    public static Boolean changeBuildingAvailability(String bld_id, int bldAvail){
        /*
        This method updates the availability of a building with given bld_id to admin-specified choice.
         */

        /*
        There are two paths to be followed here:
            (1) Setting bldAvail to 0:
                -> Set the corresponding three_share_avail and four_share_avail to 0: tblBuilding
                -> Set the corresponding apartment availability, room_1, room_2 and room_3 to 0: tblApartment
                -> Set the corresponding bed availability to 0: tblAptBooking
            (2) Setting bldAvail to 1:
                -> Set the corresponding three_share_avail and four_share_avail to 1: tblBuilding
                -> Set the corresponding apartment availability to 1; room_1 to 4; room_2 and room_3 to 3: tblApartment
                -> Set the corresponding bed availability to 1: tblAptBooking
         */

        try{
            String dbURL = url + "dbPortal";
            Connection connection = DriverManager.getConnection(dbURL,user,password);
            Statement stmt = connection.createStatement();

            String updateAvail = "UPDATE tblBuilding " +
                    "SET availability = "+bldAvail+"," +
                    "three_sharing_avail = "+bldAvail+", " +
                    "four_sharing_avail = "+bldAvail+" " +
                    "WHERE bld_id = '"+bld_id+"'";

            // ***************** NEW CODE ******************* //

            if(stmt.executeUpdate(updateAvail)>0){
                // If tblBuilding is updated successfully, we move on to tblApartment
                // In tblApartment, we first set availability, room_1, room_2, room_3 attributes to specified values --> Part 1
                // and then,
                // we retrieve list of apt_id's whose bed availabilities need to be updated in tblAptBooking --> Part 2

                String updateApartment = "";
                if(bldAvail==0){
                    updateApartment = "UPDATE tblApartment " +
                            "SET availability = 0, " +
                            "room_1 = 0, " +
                            "room_2 = 0, " +
                            "room_3 = 0 " +
                            "WHERE bld_id = '"+bld_id+"'";
                } else{
                    updateApartment = "UPDATE tblApartment " +
                            "SET availability = 1, " +
                            "room_1 = 4, " +
                            "room_2 = 3, " +
                            "room_3 = 3 " +
                            "WHERE bld_id = '"+bld_id+"'";
                }

                if(stmt.executeUpdate(updateApartment)>0){
                    String selectAptID = "SELECT apt_id FROM tblApartment WHERE bld_id = '"+bld_id+"'";
                    ResultSet rs = stmt.executeQuery(selectAptID);
                    String[] apt = new String[100];
                    int row = 0;

                    if(rs != null){
                        while(rs.next()){
                            apt[row] = rs.getString("apt_id");
                            row += 1;
                            // System.out.println(apt[row-1]);
                        }

                        apt = MinorMethods.cropArray1D(row,apt);
                        int check = 1;
                        for(String apt_id : apt){
                            String updateBedID = "UPDATE tblAptBooking " +
                                    "SET availability = "+bldAvail+" " +
                                    "WHERE apt_id = '"+apt_id+"'";
                            if(stmt.executeUpdate(updateBedID)==0){
                                check = 0;
                            }
                        }
                        if(check==1){
                            return true;
                        } else{
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static Boolean changeVillaAvailability(String villa_id, int availability){
        /*
        This method modifies the availability of a specific villa along with corresponding attributes and tables
        in tblVilla and tblVillaBooking
         */

        try{
            String dbURL = url + "dbPortal";
            Connection connection = DriverManager.getConnection(dbURL,user,password);
            Statement stmt = connection.createStatement();

            String updateVilla = "";
            if(availability==0){
                updateVilla = "UPDATE tblVilla " +
                        "SET availability = 0, " +
                        "room_1 = 0, " +
                        "room_2 = 0, " +
                        "room_3 = 0 " +
                        "WHERE villa_id = '"+villa_id+"'";
            } else{
                updateVilla = "UPDATE tblVilla " +
                        "SET availability = 1, " +
                        "room_1 = 4, " +
                        "room_2 = 3, " +
                        "room_3 = 3 " +
                        "WHERE villa_id = '"+villa_id+"'";
            }

            if(stmt.executeUpdate(updateVilla)>0){
                // Update bed availabilities in tblVillaBooking
                String updateVillaBooking = "UPDATE tblVillaBooking " +
                        "SET availability = "+availability+"," +
                        "user_id = NULL " +
                        "WHERE villa_id = '"+villa_id+"'";
                if(stmt.executeUpdate(updateVillaBooking)>0){
                    return true;
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

    public static Boolean deleteUserBooking(String user_id){
        /*
        Assuming a user has booked a particular bed, this method deletes this booking and subsequently updates the
        villa/apartment tables for the same
         */

        try{
            String dbURL = url + "dbPortal";
            Connection connection = DriverManager.getConnection(dbURL,user,password);
            Statement stmt = connection.createStatement();

            // First we retrieve the bed_id booked by the user -STILL NEED TO DELETE USER FROM TBLBOOKING DETAILS
            String getBedID = "SELECT villa_bed_id, apt_bed_id " +
                    "FROM tblBookingDetails " +
                    "WHERE user_id = '"+user_id+"'";

            ResultSet rs = stmt.executeQuery(getBedID);

            String villa_bed_id = "";
            String apt_bed_id = "";
            if(rs != null){
                while(rs.next()){
                    villa_bed_id = rs.getString("villa_bed_id");
                    apt_bed_id = rs.getString("apt_bed_id");
                }
                if(villa_bed_id!=null){
                    // We update villa tables -> tblVillaBooking & tblVilla
                    String updateVillaBooking = "UPDATE tblVillaBooking " +
                            "SET availability = 1, " +
                            "user_id = NULL " +
                            "WHERE bed_id = '"+villa_bed_id+"'";

                    if(stmt.executeUpdate(updateVillaBooking)>0){
                        String getVillaID = "SELECT villa_id FROM tblVillaBooking WHERE bed_id = '"+villa_bed_id+"'";
                        ResultSet rs2 = stmt.executeQuery(getVillaID);
                        if(rs2 != null){
                            String villa_id = "";
                            while(rs2.next()){
                                villa_id = rs2.getString("villa_id");
                            }
                            // Now we modify corresponding availability & room_1/room_2/room_3 attributes in tblVilla
                            int roomNo = Integer.parseInt(villa_bed_id)%10;
                            if (roomNo==0){
                                roomNo = 10;
                            }

                            String room = "";
                            if(roomNo<=4){
                                room = "room_1";
                            } else if (roomNo>4 && roomNo<=7) {
                                room = "room_2";
                            } else{
                                room = "room_3";
                            }

                            String updateVilla = "UPDATE tblVilla " +
                                    "SET availability = 1, " +
                                    room+" = "+room+" + 1 " +
                                    "WHERE villa_id = '"+villa_id+"'";

                            if(stmt.executeUpdate(updateVilla)>0){
                                // Finally, we can delete the user booking details from tblBookingDetails
                                String deleteUserBooking = "DELETE FROM tblBookingDetails WHERE user_id = '"+user_id+"'";
                                if(stmt.executeUpdate(deleteUserBooking)>0){
                                    return true;
                                } else {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        } else{
                            return false;
                        }
                    } else{
                        return false;
                    }
                } else{
                    /*
                    We update apartment tables:
                        -> tblAptBooking
                        -> tblApartment
                        -> tblBuilding
                     */

                    // Let's first retrieve the apt_id from tblAptBooking
                    String getAptID = "SELECT apt_id FROM tblAptBooking WHERE bed_id = '"+apt_bed_id+"'";

                    ResultSet rs3 = stmt.executeQuery(getAptID);

                    if(rs3 != null){
                        String apt_id = "";
                        while(rs3.next()){
                            apt_id = rs3.getString("apt_id");
                        }

                        // We modify the availability of bed to 1, remove user_id and move on to the next stage
                        String updateAptBooking = "UPDATE tblAptBooking " +
                                "SET availability = 1, " +
                                "user_id = NULL " +
                                "WHERE bed_id = '"+apt_bed_id+"'";

                        if(stmt.executeUpdate(updateAptBooking)>0){
                            // Now, we move on to updating tblApartment
                            // First we retrieve bld_id
                            String getBldID = "SELECT bld_id FROM tblApartment WHERE apt_id = '"+apt_id+"'";
                            ResultSet rs4 = stmt.executeQuery(getBldID);
                            if(rs4 != null){
                                String bld_id = "";
                                while(rs4.next()){
                                    bld_id = rs4.getString("bld_id");
                                }

                                int bedNo = Integer.parseInt(apt_bed_id)%10;

                                if(bedNo==0){
                                    bedNo = 10;
                                }

                                if(bedNo<=4){
                                    // Four-sharing: room_1
                                    // Update tblApartment
                                    String updateApartment = "UPDATE tblApartment " +
                                            "SET availability = 1, " +
                                            "room_1 = room_1 + 1 " +
                                            "WHERE apt_id = '"+apt_id+"'";

                                    if(stmt.executeUpdate(updateApartment)>0){
                                        // modify tblBuilding
                                        String updateBuilding = "UPDATE tblBuilding " +
                                                "SET four_sharing_avail = 1, " +
                                                "availability = 1 " +
                                                "WHERE bld_id = '"+bld_id+"'";
                                        if(stmt.executeUpdate(updateBuilding)>0){
                                            return true;
                                        } else{
                                            return false;
                                        }
                                    } else{
                                        return false;
                                    }
                                } else{
                                    // First update tblBuilding: three_sharing_avail and availability
                                    String updateBuilding = "UPDATE tblBuilding " +
                                            "SET availability = 1, " +
                                            "three_sharing_avail = 1 " +
                                            "WHERE bld_id = '"+bld_id+"'";
                                    if(stmt.executeUpdate(updateBuilding)>0){
                                        // Now update tblApartment room_2/room_3
                                        String updateApartment = "";
                                        if(bedNo<=7){
                                            // room_2
                                            updateApartment = "UPDATE tblApartment " +
                                                    "SET room_2 = room_2 + 1, " +
                                                    "availability = 1 " +
                                                    "WHERE apt_id = '"+apt_id+"'";
                                        } else{
                                            // room_3
                                            updateApartment = "UPDATE tblApartment " +
                                                    "SET room_3 = room_3 + 1, " +
                                                    "availability = 1 " +
                                                    "WHERE apt_id = '"+apt_id+"'";
                                        }

                                        if(stmt.executeUpdate(updateApartment)>0){
                                            // Now that all required modifications are done, we can delete user booking
                                            // from tblBookingDetails
                                            String deleteUserBooking = "DELETE FROM tblBookingDetails WHERE user_id = '"+user_id+"'";
                                            if(stmt.executeUpdate(deleteUserBooking)>0){
                                                return true;
                                            } else {
                                                return false;
                                            }
                                        } else{
                                            return false;
                                        }
                                    } else{
                                        return false;
                                    }
                                }

                            } else{
                                return false;
                            }
                        } else{
                            return false;
                        }
                    } else{
                        return false;
                    }
                }
            } else{
                return false;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        // System.out.println(changeAptRoomAvailability("1",4,1));
        // System.out.println(changeApartmentAvailability("1",1)); // WORKS PERFECTLY!!!
        // System.out.println(changeBuildingAvailability("2",0)); // WORKS PERFECTLY!!!
        // System.out.println(changeVillaAvailability("1",1)); // WORKS PERFECTLY!!!
        // System.out.println(deleteUserBooking("170933068")); // WORKS PERFECTLY!!!
    }
}
