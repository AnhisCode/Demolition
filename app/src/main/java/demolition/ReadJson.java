package demolition;

import processing.data.JSONObject;
import processing.data.JSONArray;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.*;
import java.util.ArrayList;
import java.util.Objects;

public class ReadJson{

    public String filePath;
    public String content;

    public int playerLives;
    public static ArrayList<String> gameLevels = new ArrayList<String>();
    public static ArrayList<Integer> gameTimes = new ArrayList<Integer>();

    /**
     * Constructor for ReadJson object
     * @param filePath the file path for the config json file
     */
    public ReadJson(String filePath){
        this.filePath = filePath;
    }

    /**
     * Read the jSon file and sets necessary variable based on data read
     */
    public void readJsonFile(){
        try {
            content = new Scanner(new File(filePath)).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
        JSONObject json = JSONObject.parse(content);

        if (!json.hasKey("lives") || !json.hasKey("levels")){
            System.out.println("Invalid configuration file!");
            App.getApp().exit();
        }

        // getting player lives
        playerLives = json.getInt("lives");
        

        // getting levels
        JSONArray levels = json.getJSONArray("levels");

        for (int i = 0; i < levels.size(); i++){
            JSONObject temp = JSONObject.parse(levels.get(i).toString());
            gameLevels.add(temp.getString("path"));
            gameTimes.add(temp.getInt("time"));
        }
        
    }


    /**
     * getter method for player lives
     * @return the total number of lives the player start off with 
     */
    public int getPlayerLives(){
        return playerLives;
    }

}