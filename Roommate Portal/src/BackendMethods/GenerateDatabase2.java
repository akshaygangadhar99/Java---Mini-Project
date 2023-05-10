package BackendMethods;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;
import java.sql.*;
import java.io.*;
import java.util.*;

import BackendMethods.MinorMethods.*;
import BackendMethods.UpdateTables.*;

public class GenerateDatabase2 {

    private static final String url = "jdbc:mysql://localhost:3306/";
    private static final String user = "root";
    private static final String password = "0123456789";

    public static String[][] readFile() {
        // This method reads the names_genders.csv file and returns the content in the form of a 2D String array

        String line = "";
        int row = 0;
        String[][] nameGender = new String[3000][2];

        int maleCount = 0;
        int femaleCount = 0;
        try{
            FileReader fileReader = new FileReader("D:/Work/Data Science/Christ University/Academics/Semester 2/Java/" +
                    "22122103-MDL273L-JAVA/Java---Mini-Project/Roommate Portal/src/names_genders.csv");
            BufferedReader br = new BufferedReader(fileReader);
            while((line = br.readLine()) != null){
                String[] value = line.split(",");
                for(int i=0; i<value.length; i++){
                    nameGender[row][i] = value[i];
                }
                if(nameGender[row][1].equalsIgnoreCase("M")){
                    maleCount += 1;
                } else{
                    femaleCount += 1;
                }
                row += 1;
            }

        } catch (IOException e){
            e.printStackTrace();
        }

//        System.out.println("Males: "+maleCount);
//        System.out.println("Females: "+femaleCount);

        return MinorMethods.cropArray(row,nameGender);
    }

    public static void createTables(){
        /*
        List of tables:
            -> tblStreet
            -> tblCourse
            -> tblUser
            -> tblPreferences
            -> tblBuilding
            -> tblApartment
            -> tblAptBooking
            -> tblVilla
            -> tblVillaBooking
            -> tblBookingDetails
         */

        String createStreet = "CREATE TABLE IF NOT EXISTS tblStreet (street_id varchar(10)," +
                "street_name varchar(50)," +
                "CONSTRAINT PRIMARY KEY (street_id))";

        String createCourse = "CREATE TABLE IF NOT EXISTS tblCourse (course_id varchar(10)," +
                "course_name varchar(100)," +
                "CONSTRAINT PRIMARY KEY (course_id))";

        String createUser = "CREATE TABLE IF NOT EXISTS tblUser (user_id varchar(10)," +
                "user_fname varchar(100), user_lname varchar(100), user_dob date," +
                "user_gender int, user_course_id varchar(10), user_city varchar(100)," +
                "user_state varchar(100), user_country varchar(100), user_email varchar(150), " +
                "user_image varchar(250), user_religion varchar(50), user_type int, user_status int," +
                "user_password varchar(100)," +
                "CONSTRAINT PRIMARY KEY (user_id), " +
                "CONSTRAINT FOREIGN KEY (user_course_id) REFERENCES tblCourse(course_id))";

        String createPreferences = "CREATE TABLE IF NOT EXISTS tblPreferences (user_id varchar(10) PRIMARY KEY," +
                "user_food_preference int, user_bio varchar(1000), user_personality int, user_cooking_ability int," +
                "user_smoker int, user_alcohol int, user_language varchar(250)," +
                "CONSTRAINT FOREIGN KEY (user_id) REFERENCES tblUser(user_id))";

        String createBuilding = "CREATE TABLE IF NOT EXISTS tblBuilding (bld_id varchar(10)," +
                "street_id varchar(10), bld_name varchar(100), uni_distance float, availability int, " +
                "three_sharing_avail int, four_sharing_avail int," +
                "CONSTRAINT PRIMARY KEY (bld_id)," +
                "CONSTRAINT FOREIGN KEY (street_id) REFERENCES tblStreet(street_id))";

        String createApartment = "CREATE TABLE IF NOT EXISTS tblApartment (apt_id varchar(10), bld_id varchar(10)," +
                "apt_no varchar(10), room_1 int, room_2 int, room_3 int, availability int," +
                "CONSTRAINT PRIMARY KEY (apt_id)," +
                "CONSTRAINT FOREIGN KEY (bld_id) REFERENCES tblBuilding(bld_id))";

        String createAptBooking = "CREATE TABLE IF NOT EXISTS tblAptBooking (bed_id varchar(10)," +
                "apt_id varchar(10), user_id varchar(10), availability int," +
                "CONSTRAINT PRIMARY KEY (bed_id)," +
                "CONSTRAINT FOREIGN KEY (apt_id) REFERENCES tblApartment(apt_id)," +
                "CONSTRAINT FOREIGN KEY (user_id) REFERENCES tblUser(user_id))";

        String createVilla = "CREATE TABLE IF NOT EXISTS tblVilla (villa_id varchar(10)," +
                "street_id varchar(10), villa_no varchar(10), room_1 int, room_2 int, room_3 int," +
                "uni_distance float, availability int," +
                "CONSTRAINT PRIMARY KEY (villa_id), " +
                "CONSTRAINT FOREIGN KEY (street_id) REFERENCES tblStreet(street_id))";

        String createVillaBooking = "CREATE TABLE IF NOT EXISTS tblVillaBooking (bed_id varchar(10)," +
                "villa_id varchar(10), user_id varchar(10), availability int," +
                "CONSTRAINT PRIMARY KEY (bed_id)," +
                "CONSTRAINT FOREIGN KEY (villa_id) REFERENCES tblVilla(villa_id)," +
                "CONSTRAINT FOREIGN KEY (user_id) REFERENCES tblUser(user_id))";

        String createBookingDetails = "CREATE TABLE IF NOT EXISTS tblBookingDetails (booking_id int NOT NULL AUTO_INCREMENT," +
                "user_id varchar(10), booking_date date, booking_time time," +
                "villa_bed_id varchar(10), apt_bed_id varchar(10)," +
                "CONSTRAINT PRIMARY KEY (booking_id)," +
                "CONSTRAINT FOREIGN KEY (user_id) REFERENCES tblUser(user_id)," +
                "CONSTRAINT FOREIGN KEY (villa_bed_id) REFERENCES tblVillaBooking(bed_id)," +
                "CONSTRAINT FOREIGN KEY (apt_bed_id) REFERENCES tblAptBooking(bed_id))";

        try{
            String dbURL = url + "dbPortal";
            Connection connection = DriverManager.getConnection(dbURL,user,password);
            Statement stmt = connection.createStatement();

            // Execute the table creation queries
            stmt.executeUpdate(createStreet); // CHECK
            stmt.executeUpdate(createCourse); // CHECK
            stmt.executeUpdate(createUser); // ****Only add Admin details for now****
            stmt.executeUpdate(createPreferences); // Not required currently
            stmt.executeUpdate(createBuilding); // CHECK - need to add Club View buildings
            stmt.executeUpdate(createApartment); // CHECK - need to add Club View apartments
            stmt.executeUpdate(createAptBooking); // CHECK - need to add Club View apartments
            stmt.executeUpdate(createVilla); // CHECK
            stmt.executeUpdate(createVillaBooking); // CHECK
            stmt.executeUpdate(createBookingDetails); // Not required currently

        } catch(SQLException e){
            e.printStackTrace();
        }

    }

    public static void addStrCourse(){
        // This method inserts default values into tblStreet and tblCourse

        // tblStreet
        String insertStreet = "INSERT INTO tblStreet (street_id, street_name) VALUES" +
                "('1','Thicket Crescent'), " +
                "('2','Thicket'), " +
                "('3','Dasve'), " +
                "('4','Portofino'), " +
                "('5','Club View'), " +
                "('6','Hill View')";

        String insertCourse = "INSERT INTO tblCourse (course_id,course_name) VALUES " +
                "('1','BSc'), " +
                "('2','BBA'), " +
                "('3','BA LLB'), " +
                "('4','BBA LLB'), " +
                "('5','BCom'), " +
                "('6','MSc'), " +
                "('7','MBA'), " +
                "('8','LLM')";

        try{
            String dbURL = url + "dbPortal";
            Connection connection = DriverManager.getConnection(dbURL,user,password);
            Statement stmt = connection.createStatement();

            stmt.executeUpdate(insertStreet);
            stmt.executeUpdate(insertCourse);

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /*
        Our hostel database contains the following information:
            -> There are 6 streets, namely:
                (i) Thicket Crescent (25 x villas): 250 capacity
                (ii) Thicket (25 x villas): 250 capacity
                (iii) Dasve (10 x villas): 100 capacity
                (iv) Portofino (9 blocks x 2 buildings per block; 5 floors (G+4) x 4 apartments per floor): ((18*20)=360)*10 = 3600 capacity
                (v) Club View (5 x villas; 2 x buildings : 5 floors (G+4) x 4 apartments per floor): 50 + 400 = 450 capacity
                (vi) Hill View (35 x villas): 350 capacity

            -> Total housing capacity = 250 + 250 + 100 + 3600 + 450 + 350 = 5000

        For our initial database, the entire street of Portofino encompasses of apartments.
        The buildings are named as: A1, A2, B1, B2, ... , I1, I2 -> Which results in a total of 18 buildings
    */

    public static void addBuilding(){
        /*
        In this method, we insert default Portofino database values into tblBuilding
         */

        String[] block = {"A","B","C","D","E","F","G","H","I"};
        float distToUni = 0.6f; // km
        // This is our estimated starting distance from CUL campus to the hypothetical PF A-1
        // Average distance between two buildings is taken to be 50 m, i.e., 0.05 km

        int availability  = 1; // set availability of housing in each building to 1 (initially)

        int PK = 1;


        try{
            String dbURL = url + "dbPortal";
            Connection connection = DriverManager.getConnection(dbURL,user,password);
            Statement stmt = connection.createStatement();
            for(int i=0; i<9; i++) {
                int index = i;
                for (int j = 1; j <= 2; j++) {
                    // bld_id = i+1
                    // street_id = 4 : PORTOFINO
                    String bldName = block[index] + Integer.toString(j);
                    distToUni += 0.05; // Add 0.05 km for each successive building
                    // availability = availability

                    String insertBuilding = "INSERT INTO tblBuilding " +
                            "(bld_id, street_id, bld_name, uni_distance, availability,three_sharing_avail," +
                            "four_sharing_avail) VALUES (" +
                            "'" + Integer.toString(PK) + "','4','" + bldName + "'," + Float.toString(distToUni) + ",1,1,1" +
                            ")";

                    stmt.executeUpdate(insertBuilding);
                    PK += 1;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void addVilla(){
        /*
        -> This method inserts default values into tblVilla.
        -> For our hypothetical case, we are assuming there are 4 streets with 25 villas each. These are:
            (1) Thicket Crescent
            (2) Thicket
            (3) Dasve
            (4) Hill View
         */

        //String[] streets = {"Thicket Crescent","Thicket","Dasve","Hill View"};
        String[] streetID = {"1","2","3","6"};

        float[] distToUni = {1.0f,1.8f,2.1f,0.5f};

        int[] numOfVillas = {25,25,10,35};

        int PK = 1;

        try{
            String dbURL = url + "dbPortal";
            Connection connection = DriverManager.getConnection(dbURL,user,password);
            Statement stmt = connection.createStatement();
            for(int i=0; i< streetID.length; i++){
                float dist = distToUni[i];
                for(int j=1; j<=numOfVillas[i]; j++){
                    String insertVilla = "INSERT INTO tblVilla " +
                            "(villa_id,street_id,villa_no,room_1,room_2,room_3,uni_distance,availability)" +
                            "VALUES (" +
                            "'"+Integer.toString(PK)+"','"+streetID[i]+"','" +
                            Integer.toString(j) + "',4,3,3,"+ Float.toString(dist)+",1)";
                    PK += 1;
                    dist += 0.03;
                    stmt.executeUpdate(insertVilla);
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    public static void addApartment(){
        /*
        -> This method adds initial list of apartments to the apartment table.
        -> Currently there are 18 buildings in Portofino.
         */

        int PK = 1;

        try{
            String dbURL = url + "dbPortal";
            Connection connection = DriverManager.getConnection(dbURL,user,password);
            Statement stmt = connection.createStatement();

            for(int bldID = 1; bldID <= 18; bldID++){
                int[] apt = {101,201,301,401,501};
                for(int aptNo: apt){
                    for(int i=0; i<4; i++){
                        String insertApt =  "INSERT INTO tblApartment " +
                                "(apt_id,bld_id,apt_no,room_1,room_2,room_3,availability)" +
                                "VALUES (" +
                                "'"+Integer.toString(PK)+"','"+Integer.toString(bldID)+"','" +
                                Integer.toString(aptNo+i)+"',4,3,3,1" +
                                ")";
                        PK += 1;
                        stmt.executeUpdate(insertApt);
                    }
                }

            }


        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void addAdmin(){
        /*
        -> This method adds the admin details for two admins, namely
            (1) Akshay Gangadhar (2212103)
            (2) Karan Punjabi (22122140)
            to the user table (tbluser)
         */

        /*
        NOTE THE FOLLOWING RULES:
            (1) Gender:
                -> 1 - Male
                -> 2 - Female
                -> 3 - Others
            (2) User_Type:
                -> 1 - Student
                -> 2 - Admin
            (3) User_Status:
                -> 1 - Active
                -> 2 - Inactive
         */

        try{
            String dbURL = url + "dbPortal";
            Connection connection = DriverManager.getConnection(dbURL,user,password);
            Statement stmt = connection.createStatement();

            String insertAdmin1 = "INSERT INTO tblUser " +
                    "(user_id,user_fname,user_lname,user_dob,user_gender," +
                    "user_city,user_state,user_country,user_email,user_religion,user_type," +
                    "user_status,user_password) VALUES (" +
                    "'1','Akshay','Gangadhar','1999-08-24',1,'Mumbai','Maharashtra','India'," +
                    "'akshaygangadhar99@gmail.com','Hindu',2,1,'0123456789')";

            String insertAdmin2 = "INSERT INTO tblUser " +
                    "(user_id,user_fname,user_lname,user_dob,user_gender," +
                    "user_city,user_state,user_country,user_email,user_religion,user_type," +
                    "user_status,user_password) VALUES (" +
                    "'2','Karan','Punjabi','2000-04-09',1,'Ahmedabad','Gujarat','India'," +
                    "'karanpunjabi660@gmail.com','Hindu',2,1,'0123456789')";

            stmt.executeUpdate(insertAdmin1);
            stmt.executeUpdate(insertAdmin2);

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /*
    NEED TO ADD DATA TO FOLLOWING TABLES:
        -> tblUser
        -> tblPreferences
        -> tblAptBooking
        -> tblVillaBooking
        -> tblBookingDetails
    IN THE GIVEN LISTED ORDER
     */

    public static String generateReligion(){
        /*
        Generates religion based on certain set of conditions
         */

        String[] religion = {"Hindu","Christian","Atheist","Islam","Buddhism","Sikh"};

        /*
        Proportion of each religion in our database:
            -> Hindu: 0.45
            -> Christian: 0.3
            -> Islam: 0.24
            -> Atheist: 0.2
            -> Buddhism: 0.05
            -> Sikh: 0.01

        Total: 45 + 30 + 24 + 20 + 5 + 1 = 125
         */

        Random random = new Random();
        int randomInt = random.nextInt(125);

        if(randomInt<45){
            return religion[0];
        } else if(randomInt>=45 && randomInt<75){
            return religion[1];
        } else if(randomInt>=75 && randomInt<99){
            return religion[2];
        } else if(randomInt>=99 && randomInt<119){
            return religion[3];
        } else if(randomInt>=119 && randomInt<124){
            return religion[4];
        } else{
            return religion[5];
        }
    }

    public static LocalDate getDate(){
        /*
        This method generates a random date of Date datatype
         */

        LocalDate startDate = LocalDate.of(1998,1,1);
        LocalDate endDate = LocalDate.of(2004,12,31);

        // Generate random date between start and end dates
        long days = startDate.until(endDate, ChronoUnit.DAYS);
        LocalDate randomDate = startDate.plusDays(ThreadLocalRandom.current().nextLong(days + 1));

        return randomDate;
    }
    public static void addUsersAndPreferences(String[][] nameGender){
        /*
        This method adds a list of 2500 users to the database, along with their preferences
        to the corresponding tables -> tblUser, tblPreferences
         */

        int regNo = 170933001;
        int index = 0;
        String name = "";
        int gender = 0;
        Random random = new Random();

        try{
            String dbURL = url + "dbPortal";
            Connection connection = DriverManager.getConnection(dbURL,user,password);
            Statement stmt = connection.createStatement();

            /*
            user_id (Reg No) - READY
            user_fname (Name) - READY
            user_dob - getDate()
            user_gender - READY
            user_course_id - Generate int in range(0,9) --> 9 - exclusive
            user_email - name+regNo@gmail.com
            user_religion - generateReligion()
            user_type - 1
            user_status - 1
            user_password - NAME
             */

            String user_id = "";
            String firstName = "";
            LocalDate DOB;
            String course_id = "";
            String email = "";
            String religion = "";
            String password = "";

            String updateUser = "";
            String updatePreferences = "";

            int food;
            int personality;
            int cookingAbility;
            int smoker, alcohol;

            // Statement for tblUser
            for(int i=0; i<2500; i++){
                user_id = Integer.toString(regNo);
                firstName = nameGender[i][0];
                DOB = getDate();

                if(nameGender[i][1].equalsIgnoreCase("M")){
                    gender = 1;
                } else{
                    gender = 2;
                }

                course_id = Integer.toString(random.nextInt(8)+1);
                email = firstName + "_" + user_id + "@gmail.com";
                religion = generateReligion();
                password = firstName;

                updateUser = updateUser = "INSERT INTO tblUser " +
                        "(user_id,user_fname,user_dob,user_gender,user_course_id,user_email," +
                        "user_religion,user_type,user_status,user_password) VALUES " +
                        "(?,?,?,?,?,?,?,1,1,?)";

                PreparedStatement preparedStatement = connection.prepareStatement(updateUser);

                preparedStatement.setString(1,user_id);
                preparedStatement.setString(2,firstName);
                preparedStatement.setDate(3, Date.valueOf(DOB));
                preparedStatement.setInt(4,gender);
                preparedStatement.setString(5,course_id);
                preparedStatement.setString(6,email);
                preparedStatement.setString(7,religion);
                preparedStatement.setString(8,password);

                preparedStatement.executeUpdate();

                food = random.nextInt(3) + 1; // range of values is (1,2,3)
                personality = random.nextInt(11);
                cookingAbility = random.nextInt(3) + 1;
                smoker = random.nextInt(3) + 1;
                alcohol = random.nextInt(3) + 1;

                updatePreferences = "INSERT INTO tblPreferences " +
                        "(user_id,user_food_preference,user_personality,user_cooking_ability," +
                        "user_smoker,user_alcohol) VALUES (?,?,?,?,?,?)";

                PreparedStatement preparedStatement1 = connection.prepareStatement(updatePreferences);

                preparedStatement1.setString(1,user_id);
                preparedStatement1.setInt(2,food);
                preparedStatement1.setInt(3,personality);
                preparedStatement1.setInt(4,cookingAbility);
                preparedStatement1.setInt(5,smoker);
                preparedStatement1.setInt(6,alcohol);

                preparedStatement1.executeUpdate();

                /*
                At this stage, pending tables are:
                    -> tblVillaBooking => tblVilla
                    -> tblAptBooking => tblApartment => tblBuilding
                 */

                if(regNo%100==0){
                    regNo += 1000;
                }
                regNo += 1;
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void generateBookingTables(){
        /*
        This method generates initial default values for tblVillaBooking and tblAptBooking
         */

        try{
            String dbURL = url + "dbPortal";
            Connection connection = DriverManager.getConnection(dbURL,user,password);
            Statement stmt = connection.createStatement();

            int bed_id = 1;
            int availability = 1;

            for(int villa_id=1; villa_id<=95; villa_id++){
                for(int bed_count=1; bed_count<=10; bed_count++){
                    String insertBed = "INSERT INTO tblVillaBooking " +
                            "(bed_id,villa_id,availability) VALUES (" +
                            "'"+Integer.toString(bed_id)+"','"+Integer.toString(villa_id)+"'," +
                            Integer.toString(availability)+")";
                    stmt.executeUpdate(insertBed);
                    bed_id += 1;
                }
            }

            bed_id = 1;

            for(int apt_id=1; apt_id <=360; apt_id++){
                for(int bed_count=1; bed_count<=10; bed_count++){
                    String insertBed = "INSERT INTO tblAptBooking " +
                            "(bed_id, apt_id, availability) VALUES (" +
                            "'"+Integer.toString(bed_id)+"','"+Integer.toString(apt_id)+"'," +
                            Integer.toString(availability)+")";

                    bed_id += 1;

                    stmt.executeUpdate(insertBed);
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static String generateBedID(String houseType){
        /*
        This method generates a random bedId for given housing type and returns it ONLY if its corresponding availability
        is 0 in the concerned table
         */

        Random random = new Random();
        String bed_id = "";
        int check = 0;
        int availability = 0;
        try{
            String dbURL = url + "dbPortal";
            Connection connection = DriverManager.getConnection(dbURL,user,password);
            Statement stmt = connection.createStatement();

            if(houseType.equalsIgnoreCase("Villa")){
                // Check if availability is 1
                while(check==0){
                    bed_id = Integer.toString(random.nextInt(950)+1);
                    String getAvailability = "SELECT availability from tblVillaBooking WHERE bed_id = '"+bed_id+"'";
                    ResultSet rs = stmt.executeQuery(getAvailability);
                    while(rs.next()){
                        availability = rs.getInt("availability");
                    }
                    if(availability==1){
                        check = 1;
                    }
                }
            } else{
                // APARTMENT
                // Check if availability is 1 --> tblAptBooking
                while(check==0){
                    bed_id = Integer.toString(random.nextInt(3600)+1);
                    String getAvailability = "SELECT availability from tblAptBooking WHERE bed_id = '"+bed_id+"'";
                    ResultSet rs = stmt.executeQuery(getAvailability);
                    while(rs.next()){
                        availability = rs.getInt("availability");
                    }
                    if(availability==1){
                        check = 1;
                    }
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        }

        return bed_id;
    }

    public static void generateBookings(){
        /*
        In this method, 2500 user bookings are generated based and updated accordingly in the villa, apartment and
        booking details tables
         */

        Random random = new Random();
        int count = 1;
        try{
            String dbURL = url + "dbPortal";
            Connection connection = DriverManager.getConnection(dbURL,user,password);
            Statement stmt = connection.createStatement();

            // First, we will retrieve the entire set of user_ids
            String getUserID = "SELECT user_id from tblUser where user_id<>'1' and user_id<>'2'";
            ResultSet rs = stmt.executeQuery(getUserID);
            System.out.println("Retrieving user_ids...");
            String[] userID = new String[2500];
            int index = 0;
            while(rs.next()){
                userID[index] = rs.getString("user_id");
                index += 1;
            }

            /*
            Now, for each given user_id we will randomly assign a housing type and bed_id.

            Housing type can be:
                -> Villa
                -> Apartment

            IF Villa:
                -> Range of bed_id [1,950]
            IF Apartment:
                -> Range of bed_id [1,3600]

            This can be simply executed using BackendMethods.UpdateTables.UpdateTables()

            Since there are ~3.8x more apt beds than villa_beds, we need to incorporate this in the code as well, to
            ensure that villa beds do not get overbooked by any chance

            Total units: 3.8 + 1 = 4.8 ~ 5 --> We keep a 1:4 ratio for random generation of villaBeds:aptBeds

             */

            int randomHouseType = 0;
            String randomBedID = "";
            String housing = "";
            Boolean b;
            for(String user_id : userID){
                randomHouseType = random.nextInt(5);
                if(randomHouseType<1){
                    housing = "Villa";
                } else{
                    housing = "Apartment";
                }

                randomBedID = generateBedID(housing);
                b = UpdateTables.updateTables(user_id,randomBedID,housing);
                System.out.println("Booking id: "+count+" has been generated...");
                count += 1;

            }

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {


//        createTables();
//        addStrCourse();
//        addBuilding();
//        addVilla();
//        addApartment();
//        addAdmin();

//        System.out.println(generateReligion());
//        System.out.println(getDate());
//        String[][] arr = readFile();
//        addUsersAndPreferences(arr);

//        generateBookingTables();

        generateBookings();

    }
}
