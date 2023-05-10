package com.example.check1.Table;

public class Preference {
    String user_id;
    int food_pref;

    String bio;
    int personality;
    int cooking_ability;
    int smoker;
    int alcohol;
    String languages;

    public Preference(){
        // Empty constructor
    }

    public Preference(String ID, int food, String userBio, int persona, int cooking,
                      int smo, int alc, String lang){
        this.user_id = ID;
        this.food_pref = food;
        this.bio = userBio;
        this.personality = persona;
        this.cooking_ability = cooking;
        this.smoker = smo;
        this.alcohol = alc;
        this.languages = lang;
    }

    public String getUserID(){
        return this.user_id;
    }

    public int getFoodPref(){
        return this.food_pref;
    }

    public String getBio(){
        return this.bio;
    }

    public int getPersonality(){
        return this.personality;
    }

    public int getCookingAbility(){
        return this.cooking_ability;
    }

    public int getSmoker(){
        return this.smoker;
    }

    public int getAlcohol(){
        return this.alcohol;
    }

    public String getLanguages(){
        return this.languages;
    }
}
