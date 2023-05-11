    package BackendMethods;

    import java.sql.*;

    import BackendMethods.MinorMethods.*;

    public class VisualMethods {
        /*
        This class contains methods which will generate the required data for the set of visualizations that will be provided
        as part of our application interface
         */

        private static final String url = "jdbc:mysql://localhost:3306/";
        private static final String user = "root";
        private static final String password = "0123456789";

        public static float getBuildingDistance(String bld_id){
            /*
            This method returns the distance of a given building from the university
             */

            float dist = 0.0f;

            try{
                String dbURL = url + "dbPortal";
                Connection connection = DriverManager.getConnection(dbURL,user,password);
                Statement stmt = connection.createStatement();

                String getDist = "select uni_distance from tblBuilding where bld_id = '"+bld_id+"'";

                ResultSet resultSet = stmt.executeQuery(getDist);

                while(resultSet.next()){
                    dist = resultSet.getFloat("uni_distance");
                }

            } catch (SQLException e){
                e.printStackTrace();
            }

            return dist;
        }

        public static int[] getAptCount(){

            String[] aptIDs = new String[3600];
            int row = 0;
            String bldID = "";
            float dist = 0.0f;

            int[] count = {0,0,0};

            /*
            count[0] -> count of booked apartments within 1 km [0,1)
            count[1] -> count of booked apartments between 1 and 2 km [1,2)
            count[2] -> count of booked apartments more than 2 km away
             */

            try{
                String dbURL = url + "dbPortal";
                Connection connection = DriverManager.getConnection(dbURL,user,password);
                Statement stmt = connection.createStatement();

                // First we need to extract list of apartment id's which are booked -> this can be either done using
                // tblBookingDetails or tblAptBooking

                String getAptID = "SELECT apt_id from tblAptBooking where availability = 0";

                ResultSet rs = stmt.executeQuery(getAptID);
                while(rs.next()){
                    aptIDs[row] = rs.getString("apt_id");
                    row += 1;
                }

                aptIDs = MinorMethods.cropArray1D(row,aptIDs);

                for(String ID: aptIDs){
                    String getBldID = "SELECT bld_id FROM tblApartment WHERE apt_id = '"+ID+"'";
                    ResultSet rs2 = stmt.executeQuery(getBldID);
                    while(rs2.next()){
                        bldID = rs2.getString("bld_id");
                        dist = getBuildingDistance(bldID);
                        if(dist<1.0){
                            count[0] += 1;
                        } else if(dist>=1.0 && dist<2.0){
                            count[1] += 1;
                        } else{
                            count[2] += 1;
                        }
                    }
                }
            } catch (SQLException e){
                e.printStackTrace();
            }

            return count;

        }

        public static float getVillaDistance(String villaID){

            float dist = 0.0f;

            try{
                String dbURL = url + "dbPortal";
                Connection connection = DriverManager.getConnection(dbURL,user,password);
                Statement stmt = connection.createStatement();

                String getDist = "SELECT uni_distance FROM tblVilla WHERE villa_id = '"+villaID+"'";

                ResultSet resultSet = stmt.executeQuery(getDist);

                while(resultSet.next()){
                    dist = resultSet.getFloat("uni_distance");
                }

            } catch (SQLException e){
                e.printStackTrace();
            }

            return dist;
        }

        public static int[] getVillaCount(){
            float dist = 0.0f;
            String villaID = "";
            int[] count = {0,0,0};

            try{
                String dbURL = url + "dbPortal";
                Connection connection = DriverManager.getConnection(dbURL,user,password);
                Statement stmt = connection.createStatement();

                String getVillaID = "SELECT villa_id FROM tblVillaBooking WHERE availability = 0";

                ResultSet resultSet = stmt.executeQuery(getVillaID);

                while(resultSet.next()){
                    villaID = resultSet.getString("villa_id");
                    dist = getVillaDistance(villaID);
                    if(dist<1.0){
                        count[0] += 1;
                    } else if(dist>=1.0 && dist<2.0){
                        count[1] += 1;
                    } else{
                        count[2] += 1;
                    }
                }

            } catch (SQLException e){
                e.printStackTrace();
            }
            return count;
        }

        public static int[] getDistanceWiseCount(){
            /*
            -> We will be offering a visual bar-chart depicting distance-wise no. of bookings.
            -> For this, the building/villa distance will be split into intervals as: < 1 km, 1-2 km, > 2 km,
               i.e., [0,1),[1,2),[2,...)
             */

            String[] aptIDs = new String[3600];
            int row = 0;
            int[] aptCount = new int[3];
            int[] villaCount = new int[3];

            try{
                String dbURL = url + "dbPortal";
                Connection connection = DriverManager.getConnection(dbURL,user,password);
                Statement stmt = connection.createStatement();

                // First we need to get count of distance-wise bookings for apartments
                aptCount = getAptCount();

                // Now we retrieve count of distance-wise bookings for villas
                villaCount = getVillaCount();

            } catch (SQLException e){
                e.printStackTrace();
            }

    //        System.out.println("Total bookings within a 1 km radius: "+(aptCount[0]+villaCount[0]));
    //        System.out.println("Total bookings between 1 and 2 km: "+(aptCount[1]+villaCount[1]));
    //        System.out.println("Total bookings farther than a 1 km radius: "+(aptCount[2]+villaCount[2]));

            int[] count = {0,0,0};
            for(int i=0; i<3; i++){
                count[i] = aptCount[i] + villaCount[i];
            }
            return count;
        }

        public static int[] getStrWiseReligionCount(String streetID){
            /*
            StreetID    StreetName
                1     Thicket Crescent
                2        Thicket
                3         Dasve
                4       Portofino --> Apartment
                5       Club View
                6       Hill View
             */

            String[] userID = new String[2500];
            String[] villaIDs = new String[950];
            int row = 0;
            String getReligion = "";
            String religion = "";
            String getUsers = "";
            int[] count = {0,0,0,0,0,0};

            try{
                String dbURL = url + "dbPortal";
                Connection connection = DriverManager.getConnection(dbURL,user,password);
                Statement stmt = connection.createStatement();

                String getVillaIDS = "SELECT villa_id from tblVilla WHERE street_id = '"+streetID+"'";
                ResultSet resultSet = stmt.executeQuery(getVillaIDS);
                while(resultSet.next()){
                    villaIDs[row]  = resultSet.getString("villa_id");
                    row += 1;
                }

                villaIDs = MinorMethods.cropArray1D(row,villaIDs);

                row = 0;

                // Now we retrieve user ids for given street
                for(String ID : villaIDs){
                     getUsers = "SELECT user_id FROM tblVillaBooking WHERE villa_id = '"+ID+"'";
                     ResultSet resultSet1 = stmt.executeQuery(getUsers);
                     while(resultSet1.next()){
                         userID[row] = resultSet1.getString("user_id");
                         row += 1;
                     }
                }

                userID = MinorMethods.cropArray1D(row,userID);
                row = 0;

                // Now we retrieve religion of each user and increment count accordingly
                /*
                    -> Hindu: 0.45
                    -> Christian: 0.3
                    -> Islam: 0.24
                    -> Atheist: 0.2
                    -> Buddhism: 0.05
                    -> Sikh: 0.01
                 */
                for(String ID : userID){
                    getReligion = "SELECT user_religion FROM tblUser WHERE user_id = '"+ID+"'";
                    ResultSet resultSet1 = stmt.executeQuery(getReligion);
                    while(resultSet1.next()){
                        religion = resultSet1.getString("user_religion");
                        if(religion.equalsIgnoreCase("Hindu")){
                            count[0] += 1;
                        } else if (religion.equalsIgnoreCase("Christian")) {
                            count[1] += 1;
                        } else if (religion.equalsIgnoreCase("Islam")) {
                            count[2] += 1;
                        } else if (religion.equalsIgnoreCase("Atheist")) {
                            count[3] += 1;
                        } else if (religion.equalsIgnoreCase("Buddhism")) {
                            count[4] += 1;
                        } else {
                            // Sikh
                            count[5] += 1;
                        }
                    }
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
            return count;
        }

        public static void main(String[] args) {
            //System.out.println(getVillaDistance("1"));
            //System.out.println(getDistanceWiseCount());

            // does not work for 4,5 --> need to enter suitable values for #5
            int[] count = getStrWiseReligionCount("6");
            System.out.println("Hindu: "+count[0]);
            System.out.println("Christian: "+count[1]);
            System.out.println("Islam: "+count[2]);
            System.out.println("Atheist: "+count[3]);
            System.out.println("Buddhism: "+count[4]);
            System.out.println("Sikh: "+count[5]);

        }

    }
