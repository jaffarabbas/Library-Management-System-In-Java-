package Libaray;
import com.google.gson.Gson;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Preferences {
    public static final String CONFIG_FILE = "config.txt";
    int NoOfDaysWithOutFIne;
    float finePerDay;

    public Preferences(){
        NoOfDaysWithOutFIne = 14;
        finePerDay = 200;
    }

    public int getNoOfDaysWithOutFIne() {
        return NoOfDaysWithOutFIne;
    }
    public float getFinePerDay() {
        return finePerDay;
    }

    public void setFinePerDay(float finePerDay) {
        this.finePerDay = finePerDay;
    }
    public void setNoOfDaysWithOutFIne(int noOfDaysWithOutFIne) {
        NoOfDaysWithOutFIne = noOfDaysWithOutFIne;
    }

    public static void initConfig(){
        Writer writer = null;
        try {
            Preferences preferences = new Preferences();
            Gson gson = new Gson();
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(preferences,writer);
        } catch (IOException e) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE,null,e);
        }finally {
            try {
                writer.close();
            }catch (IOException e){
                Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE,null,e);
            }
        }
    }

    public static Preferences getPreferences(){
        Preferences preferences = new Preferences();
        try {
            Gson gson = new Gson();
            preferences = gson.fromJson(new FileReader(CONFIG_FILE),Preferences.class);
        }catch (FileNotFoundException e){
            initConfig();
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE,null,e);
        }
        return preferences;
    }

    //Update config.txt file
    public static void WritePreferenceToFile(Preferences preferences){
        Writer writer = null;
        try {
            Gson gson = new Gson();
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(preferences,writer);
            AlertMaker.showAlert("Successful!!!!","Settings Updated");
        } catch (IOException e) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE,null,e);
            AlertMaker.showError("Error!!","Error In Setting");
        }finally {
            try {
                writer.close();
            }catch (IOException e){
                Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE,null,e);
            }
        }
    }
}
