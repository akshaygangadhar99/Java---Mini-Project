# Java---Mini-Project  

## Collaborators  
- [Akshay Gangadhar](https://github.com/akshaygangadhar99) (Reg. No. 22122103)  
- [Karan Punjabi](https://github.com/karanpunjabi18) (Reg. No. 22122140)  

## About  
- This is a mini-project developed by us as part of our Java course in Semester-2 of our MSc in Data Science program at Christ University, Lavasa.  
- We are developing a _Roommate Portal_ for the university students. The objective is to develop a fast and efficient system wherein students can check for available housing based on a number of filters such as pricing, distance from campus and room-sharing.  
- The objective is to aid students in booking a hostel bed at the start of the academic year.  
- We hope this serves as a concrete foundation to help our university develop a real-time portal which can help smoothen the hostel-booking process in the near future.    

## Key Features of the Roommate Portal Application  
There are two user modules - **Student & Admin**.  

### Student Module  
- House listings are displayed based on user-specified choice of filters. Students can search for available aparments and villas based on their choice of room type (3/4-sharing).  
- On viewing a room (within an apartment or a villa), profiles of potential roommates (who have already booked a bed) will be visible.    
- Users will be able to view key insights such as:  
  - Gender-based demographics of users  
  - Total bookings based on distance from the university  
  - Street-wise distribution of religions  
- The intention is to help students get as much crucial information as possible that will help them make the best decision with respect to their housing concerns.  

### Admin Module  
Admins can avail several different functionalities such as:  
  - View all bookings  
  - Delete bookings  
  - Block / Unblock buildings  
  - Block / Unblock apartments  
  - Block / Unblock villas  

### Packages & Classes Developed - Backend  
 - All the classes and methods used for backend modifications (based on frontend user-actions) are contained within the package aptly named [BackendMethods](Roommate%20Portal/src/BackendMethods).  
 - Important classes within this package are:  
   - [GenerateDatabase2](Roommate%20Portal/src/BackendMethods/GenerateDatabase2.java)  
      - This class contains the entire set of methods required to generate the simulated database.  
   - [AdminMethods](Roommate%20Portal/src/BackendMethods/AdminMethods.java)  
      - This class contains the complete set of methods required to run any and all Admin-related queries and functionalities.  
      - This involves a series of steps including updation of all relevant tables in the database that are triggered by an admin action on the portal.  
      - For example, something relatively simple such as - Deleting a Booking - requires subsequent updates to several attributes in the following tables:  
        - tblBookingDetails  
        - tblAptBooking / tblApartment / tblBuilding : For the apartment housing type  
        - tblVillaBooking / tblVilla : For the villa housing type  
      - In case of other such admin actions, all the linked tables need to be modified.  
      - Appropriate checks are introduced in place to ensure that the front-end user is notified in case a task fails in the background of the application.  
   - [MinorMethods](Roommate%20Portal/src/BackendMethods/MinorMethods.java)  
      - This class contains a set of repetitively-utilized methods that have been developed for convenience to aid the development of other back-end functionalities.   
   - [UpdateTables](Roommate%20Portal/src/BackendMethods/UpdateTables.java)  
      - Along with the AdminMethods class, this class represents the other set of methods that are crucial to the working of our portal application.  
      - Alterations in any of the tables as a result of a user-action triggers a subsequent sequence of background tasks that updates the entire database. The complete set of methods that implement this are contained within this class.  
   - [VisualMethods](Roommate%20Portal/src/BackendMethods/VisualMethods.java)  
      - This class contains the complete set of methods that retrieve the data that is pertinent to generating key insights for the roommate portal.  

### Graphical User Interface (GUI)
## Database   
MySQL has been selected as the RDBMS for our portal. There are 10 tables in our MySQL database. These are:  

![image](https://github.com/akshaygangadhar99/Java---Mini-Project/assets/118504392/9cae1e84-8c35-4791-97c4-a8eb4166af01)

- ### tblStreet:  
 ![image](https://user-images.githubusercontent.com/118504392/235843172-1bd245ca-4ba4-4224-9b59-1c540b65d727.png)
 
- ### tblCourse:  
 ![image](https://user-images.githubusercontent.com/118504392/235843256-80c71970-79b2-42bc-a2a2-76b989934e74.png)

- ### tblUser:
 ![image](https://user-images.githubusercontent.com/118504392/235843327-18960920-d56f-4d50-bf0b-13cd700001af.png)  
 **user_course_id**: references _tblCourse(course_id)_  

- ### tblPreferences:  
 ![image](https://user-images.githubusercontent.com/118504392/236744490-f5344089-2c8b-4d97-ac6b-a1390de7e659.png)  
 **user_id**: references _tblUser(user_id)_  

- ### tblVilla:  
 ![image](https://user-images.githubusercontent.com/118504392/235843471-8e1bdec8-cdba-44b0-8b67-d907940b2f44.png)  
 **street_id**: references _tblStreet(street_id)_  
 
- ### tblVillaBooking:  
 ![image](https://user-images.githubusercontent.com/118504392/235843518-63788ea3-e950-4b8f-9322-3a4935192572.png)   
 **villa_id**: references _tblVilla(villa_id)_  
 **user_id**: references _tblUser(user_id)_  
 
- ### tblBuilding:  
 ![image](https://user-images.githubusercontent.com/118504392/235843561-66f02447-d9bb-4df6-b0a3-d9f8acf14bd8.png)  
 **street_id**: references _tblStreet(street_id)_  

- ### tblApartment:  
 ![image](https://user-images.githubusercontent.com/118504392/235843609-380a6f55-c90f-4dd7-82c4-8b880b7f1a0a.png)  
 **bld_id**: references _tblBuilding(bld_id)_ 

- ### tblAptBooking:  
 ![image](https://user-images.githubusercontent.com/118504392/235843647-e237fb11-e736-402b-93ec-13288014a070.png)   
 **apt_id**: references _tblApartment(apt_id)_  
 **user_id**: references _tblUser(user_id)_

- ### tblBookingDetails:  
 ![image](https://github.com/akshaygangadhar99/Java---Mini-Project/assets/118504392/0118162b-a410-4d1e-b205-866c621f760e)  
 **user_id**: references _tblUser(user_id)_  
 **villa_bed_id**: references _tblVillaBooking(bed_id)_  
 **apt_bed_id**: references _tblAptBooking(bed_id)_   
 
## Database Rules  
To ease backend programming load, several rules have been implemented for the entry of attributes into the tables in our database. These rules are briefly described (table-wise) below:  
- ### tblUser  
  - user_gender:  
    - 1 - Male  
    - 2 - Female  
    - 3 - Others  
  - user_type:  
    - 1 - Student  
    - 2 - Admin  
  - user_status:  
    - 1 - Active  
    - 2 - Inactive  
- ### tblPreferences  
  - user_food_preferences:  
    - 1 - Vegetarian    
    - 2 - Non-vegetarian    
    - 3 - Both  
  - user_bio:  
    - User is required to write a bio briefly describing their interests, hobbies, and anything else they would like others to know about themselves within a 1000 charaacter limit.  
  - user_personality:  
    - 1 - Introvert  
    - 5 - Ambivert  
    - 10 - Extrovert
  - user_cooking_ability:  
    - 1 - Cannot cook  
    - 2 - Can cook (partially)  
    - 3 - Can cook well  
  - user_smoker:  
    - 1 - Non-smoker  
    - 2 - Occasional smoker  
    - 3 - Heavy smoker  
  - user_alcohol:  
    - 1 - Non-consumer  
    - 2 - Occasional   
    - 3 - Frequent    
  - user_language:  
    - User is required to sequentially list all languages they can speak  

### Important Points to Note  
 1. To demonstrate the entire range of functionalities possessed by our application, a comprehensize database with 2500 unique users has been designed. This includes a complete set of user-id's, first and last names, date of births, email-id's, religions, and a complete entry of preferences (in tblPreferences). Each user is assigned a particular bed in a specific room of a villa / apartment (on a random basis).  
 2. To ensure a more "realistic" feel to the database, religions have been assigned to each user on a random basis while keeping in mind the relative popularity of each religion in today's world. For example, Hinduism/Christianity/Islam has been allotted a higher proportion than, say, Buddhism, Sikh, or Judaism.  
 3. Another point to keep in mind while generating the bookings for the database is to ensure that the villas do not get "overbooked" as a result of the lesser number of available beds (950) as compared to the apartments (3600).  
 4. Unique user names have been obtained from a dataset containing over 800,000 names from [Kaggle](https://www.kaggle.com/datasets/ironicninja/baby-names).  
 5. The entire set of methods required for generating the same are available within the class aptly named [GenerateDatabase2](Roommate%20Portal/src/BackendMethods/GenerateDatabase2.java).  

  



     





