# Java---Mini-Project  

## Collaborators  
- Akshay Gangadhar (Reg. No. 22122103)  
- Karan Punjabi (Reg. No. 22122140)  

## About  
- This is a mini-project developed by myself and Karan Punjabi (GitHub User ID - karanpunjabi18) as part of our Java course in Semester-2 of our MSc in Data Science program.  
- We are developing a Roommate Portal for the university students. The objective is to develop a fast and efficient system wherein students can check for available housing based on a number of filters such as pricing, distance from campus and room-sharing.  
- We hope this serves as a good foundation to help our university develop a real-time portal which can help smoothen the hostel-booking process in the near future.  

## Database   
MySQL has been selected as the RDBMS for our portal. There are 10 tables in our MySQL database. These are:  
- ### tblStreet:  
 ![image](https://user-images.githubusercontent.com/118504392/235843172-1bd245ca-4ba4-4224-9b59-1c540b65d727.png)
 
- ### tblCourse:  
 ![image](https://user-images.githubusercontent.com/118504392/235843256-80c71970-79b2-42bc-a2a2-76b989934e74.png)

- ### tblUser:
 ![image](https://user-images.githubusercontent.com/118504392/235843327-18960920-d56f-4d50-bf0b-13cd700001af.png)  
 **user_course_id**: references _tblCourse(course_id)_  

- ### tblPreferences:  
 ![image](https://user-images.githubusercontent.com/118504392/235843431-aa468a74-25d0-403b-b9f8-e9a8c5d5e92a.png)  
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
 **street_id**: references _tblStreet(street_id) _   

- ### tblApartment:  
 ![image](https://user-images.githubusercontent.com/118504392/235843609-380a6f55-c90f-4dd7-82c4-8b880b7f1a0a.png)  
 **bld_id**: references _tblBuilding(bld_id)_ 

- ### tblAptBooking:  
 ![image](https://user-images.githubusercontent.com/118504392/235843647-e237fb11-e736-402b-93ec-13288014a070.png)   
 **apt_id**: references _tblApartment(apt_id)_  
 **user_id**: references _tblUser(user_id)_

- ### tblBookingDetails:  
 ![image](https://user-images.githubusercontent.com/118504392/235843726-79d44f62-f2a2-4f48-a4c5-e0fe978671fb.png)  
 **user_id**: references _tblUser(user_id)_  
 **villa_bed_id**: references _tblVillaBooking(bed_id)_  
 **apt_bed_id**: references _tblAptBooking(bed_id)_   
 
## Database Rules  
To ease the backend programming load, we have implemented several rules for the entry of attributes into the tables in our database. These rules are briefly described (table-wise) below:  
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
    - 1 - Veg  
    - 2 - Non-veg  
    - 3 - Both  
  - user_bio:  
    - User is required to write a bio briefly describing their interests, hobbies, and anything else they would like others to know about themselves within a 1000 charaacter limit.  
  - user_personality:  
    - 0 - Introvert  
    - 5 - Ambivert  
    - 10 - Extrovert
  - user_cooking_ability:  
    - 1 - Cannot cook  
    - 2 - Can cook (partially)  
    - 3 - Can cook well  
  - user_smoker:  
    - 1 - non-smoker  
    - 2 - occasional smoker  
    - 3 - heavy smoker  
  - user_alcohol:  
    - 1 - non-consumer  
    - 2 - occasional drinker  
    - 3 - heavy drinker  
  - user_language:  
    - User is required to sequentially list all languages they can speak  
 
