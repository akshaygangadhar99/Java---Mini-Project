# Java---Mini-Project  

## Collaborators  
- Akshay Gangadhar (Reg. No. 22122103)  
- Karan Punjabi (Reg. No. 22122140)  

## About  
- This is a mini-project developed by myself and Karan Punjabi (GitHub User ID - karanpunjabi18) as part of our Java course in Semester-2 of our MSc in Data Science program.  
- We are developing a Roommate Portal for the university students. The objective is to develop a fast and efficient system wherein students can check for available housing based on a number of filters such as pricing, distance from campus and room-sharing.  
- We hope this serves as a good foundation to help our university develop a real-time portal which can help smoothen the hostel-booking process in the near future.  

## Database Rules  
MySQL has been selected as the RDBMS for our portal. There are 10 tables in our MySQL database. These are:  
  (1) tblStreet:  
      - street_id (PK): varchar(10)  
      - street_name: varchar(50)  
  (2) tblCourse:  
      - course_id (PK): varchar(10)  
      - course_name: varchar(100)  
  (3) tblUser:  
      - user_id (PK): varchar(10)  
      - user_fname: varchar(100)  
      - user_lname: varchar(100)  
      - user_dob: date  
      - user_gender: int  
      - user_course_id (FK): varchar(10) -> references tblCourse(course_id)  
      - user_city: varchar(100)  
      - user_state: varchar(100)  
      - user_country: varchar(100)  
      - user_email: varchar(150)  
      - user_image: varchar(250)  
      - user_religion: varchar(50)  
      - user_type: int  
      - user_status: int  
      - user_password: varchar(100)  
  (4) tblPreferences:  
      - user_id (FK): varchar(10) -> references tblUser(user_id)  
      - user_food_preference: int  
      - user_bio: varchar(100)  
      - user_personality: int  
      - user_cooking_ability: int  
      - user_smoker: int  
      - user_alcohol: int  
      - user_language: varchar(250)  
  (5) tblVilla:  
      - villa_id (PK): varchar(10)  
      - street_id (FK): varchar(10) -> references tblStreet(street_id)  
      - villa_no: varchar(10)  
      - room_1: int  
      - room_2: int  
      - room_3: int  
      - uni_distance: float  
      - availability  
   (6) tblVillaBooking:  
      - bed_id (PK): varchar(10)  
      - villa_id (FK): varchar(10) -> references tblVilla(villa_id)  
      - user_id (FK): varchar(10) -> references tblUser(user_id)  
      - availability: int  
   (7)
