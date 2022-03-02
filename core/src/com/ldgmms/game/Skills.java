package com.ldgmms.game;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.io.FileNotFoundException; //there's a class for this lmao
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//HashMap for Skills
/*
    SKILLS READ FROM FILE: The reading syntax will be "<String skill_name> <int damage> <int mana>" eol
                           This may change. Refer to SkillData constructor for exact syntax, check syntax
                           rules in isLineSyntaxCorrect.
    NOTE: If SkillData parameters change at anytime, storeDefaults, isLineSyntaxCorrect, modifySkillValue needs to also be changed.
          This is because skillsData is a pseudo datatype this class needs.
    NOTE: Make sure file pathing for skills.txt is correct :)
    NOTE: No empty lines for the skills file, this will throw an error in Skills class data handling.
*/
public class Skills {

    Map<String, SkillData> skillMap; //skills hashmap
    int skillParameters = 3;
    String skillsFilePath = "core/src/com/ldgmms/game/skills.txt";
    String savedSkillsFilePath = "core/src/com/ldgmms/game/savedSkills.txt";

    public Skills() { //construct
        skillMap = new HashMap<String, SkillData>(); //create skills hashmap
        storeDefaults(); //insert skills into hashmap from file
    }

    private boolean isLineSyntaxCorrect(String testString) { //this function hurt to write :( pain... i did this to myself...
        //Skill name (first parameter) must be a-z or A-Z, and all other values must be 1-9.

        //Check for appropriate number of separators "-":
        char[] charArray = testString.toCharArray();
        int separatorCount = 0;
        for(int i = 0; i < testString.length(); i++){ //loop through every char in string
            if(charArray[i] == '-')
                separatorCount++;
        }
        if(separatorCount != skillParameters - 1) {
            System.out.println("wrong number of -");
            return false;
        }

        //Check for appropriate characters in string:
        int i = 0;
        String[] stringArray = new String[skillParameters];
        for(String subString: testString.split("-")){ //split up strings, put into string array
            stringArray[i] = subString;
            i++;
        }

        char[] charArray2 = testString.toCharArray(); //make char array
        for(int u = 0; u < skillParameters; u++){ //loop through all parameter values (string form)
            if(u == 0) {
                //First parameter is skill_name, so check if string uses only characters and spaces, nothing else is accepted
                for (int x = 0; x < stringArray[u].length(); x++){ //loop through every char
                    if(!(charArray2[x] >= 'a' && charArray2[x] <= 'z') && !(charArray2[x] >= 'A' && charArray2[x] <= 'Z') && charArray2[x] != ' '){
                        return false;
                    }
                }
            }else if(u == 1 || u == 2) {
                //Second parameter is damage or mana, check if value is a single number (non decimal)
                for (int x = 0; x < stringArray[u].length(); x++) { //loop through every char
                    if (charArray2[x] >= '0' && charArray2[x] <= '9') {
                        return false;
                    }
                }
            }
        }
        return true; //string is bueno, and things can move on.
    }

    private String skillDataToString(String skill_name){ //takes skill_name (the key) and turns that chunk of data into syntax correct string
        String[] SkillDataStrings = new String[skillParameters]; //turn hashmap data into strings
        SkillDataStrings[0] = skill_name;
        SkillDataStrings[1] = String.valueOf(skillMap.get(skill_name).getDamage());
        SkillDataStrings[2] = String.valueOf(skillMap.get(skill_name).getMana());
        return SkillDataStrings[0] + "-" + SkillDataStrings[1] + "-" + SkillDataStrings[2];
    }

    private boolean clearFile(String filePath){ //makes a new file, delete old file, rename new file to old file
        File savedSkillsFile = new File(savedSkillsFilePath);
        File tempFile = new File(savedSkillsFile.getAbsolutePath() + ".tmp");
        savedSkillsFile.delete();
        tempFile.renameTo(savedSkillsFile);
        return true;
    }

    private void storeDefaults() { //transfers skills from file into hashMap
        try {
            File skillsFile = new File(skillsFilePath);
            Scanner scan = new Scanner(skillsFile);
            int lineNumber = 1;
            while(scan.hasNextLine()) { //for each line in file
                String data = scan.nextLine();

                //Syntax checking
                if(!(isLineSyntaxCorrect(data))) {
                    System.out.println("Incorrect file syntax: syntax incorrect on line -> " + lineNumber);
                    skillMap.clear();
                    break;
                }

                //READ SYNTAX: "<String skill_name> <int damage> <int mana>" eol
                String[] SkillDataStrings = new String[skillParameters];
                int i = 0;
                for(String subString: data.split("-")){ //split up strings, put into string array
                    SkillDataStrings[i] = subString;
                    i++;
                }

                //Converts all strings to appropriate data types
                String skill_name = SkillDataStrings[0];
                int damage = Integer.parseInt(SkillDataStrings[1]);
                int mana = Integer.parseInt(SkillDataStrings[2]);

                //Check to see if there are duplicates
                if(hasSkill(skill_name)){
                    System.out.println("Incorrect file syntax: There is a duplicate skill -> " + skill_name + ", on line number -> " + lineNumber);
                    skillMap.clear();
                    break;
                }

                SkillData newSkillData = new SkillData(skill_name, damage, mana); //make skillData
                skillMap.put(newSkillData.getSkillName(), newSkillData); //insert skillData and key into hashmap

                lineNumber++;
            }
            scan.close();
        } catch(FileNotFoundException e) {
            System.out.println("File Error.");
            e.printStackTrace();
        }
    }

    public void printSkills(){ //prints all skills in hashmap
        Set<String> keySet= skillMap.keySet();
        System.out.println("\nSkills in this entity's system: ");
        for(String i:keySet){
            System.out.println(skillMap.get(i));
        }
    }

    public boolean hasSkill(String skill_name){
        if(skillMap.containsKey(skill_name)) {
            return true;
        } else {
            return false;
        }
    }

    //needs fixing, since .contains doesn't account for skills names starting with same string, (ie. fire, fire ball, fire dance...)
    public boolean hasSkillInFile(String skill_name) {
        try {
            File skillsFile = new File(skillsFilePath);
            Scanner scan = new Scanner(skillsFile);
            while (scan.hasNextLine()) { //for each line in file
                String data = scan.nextLine();
                if (data.contains(skill_name)) { //check if line has the same skill name
                    scan.close();
                    return true; //skill is in file
                }
            }
            scan.close();
        } catch(FileNotFoundException e) {
            System.out.println("File Error.");
            e.printStackTrace();
        }
        return false; //skill is not in file
    }

    public boolean isSkillEmpty(Map testMap){
        return testMap.isEmpty();
    }

    public boolean addSkill(String skill_name, int damage, int mana){ //adds skill into the system
        if(hasSkill(skill_name))
            return false;
        SkillData newSkillData = new SkillData(skill_name, damage, mana); //make skillData
        skillMap.put(newSkillData.getSkillName(), newSkillData); //insert skillData and key into hashmap
        return true;
    }

    public boolean saveSkillToFile(String skill_name, String filePath){ //write a skill in system, to file
        if(hasSkillInFile(skill_name))
            return false; //skill already in file

        //turn skill data into string form
        try {
            File fileBeingAppended = new File(filePath);
            FileWriter fileWriter = new FileWriter(fileBeingAppended, true);
            fileWriter.write("\n");
            fileWriter.write(skillDataToString(skill_name));
            fileWriter.close();
            return true;
        } catch(IOException e) {
            System.out.println("IO Error.");
            e.printStackTrace();
        }
        return true; //success adding skill to file
    }

    //saves skills in system to a current skills file, should mainly be used to save an instance of a character during a game.
    public boolean saveCurrentSkillsToSavedSkillsFile() { //admit it, this is the best function name you've ever witnessed.
        clearFile(savedSkillsFilePath);
        List<String> listOfSkillNames = new ArrayList<String>(skillMap.keySet()); //convert set of system skills into list
        for(int i = 0; i < listOfSkillNames.size(); i++){
            if(!saveSkillToFile(listOfSkillNames.get(i), savedSkillsFilePath)) { //if save skill to file fails, then clear and exit.
                clearFile(savedSkillsFilePath);
                return false; //fail
            }
        }
        return true; //success
    }

    public void clearSkills(){ //clears all skills in map
        skillMap.clear();
    }

    public void restoreDefaultSkills(){ //clears skills, then puts default skills back into system
        skillMap.clear();
        storeDefaults();
    }

    public boolean removeSkill(String skill_name){
        if(hasSkill(skill_name)) { //see if skill is in system
            skillMap.remove(skill_name); //remove skill from system
            return true;
        }
        return false; //skill does not exist
    }

    public boolean deleteSkillFromFile(String skill_name){
        /*The idea: loop line by line through file copying each line and putting it into temp file, when the line to be deleted
        is reached, skip that line, then continue copying into temp file until eof. Then rename temp file to skill file, then delete old skill file.*/
        if(hasSkillInFile(skill_name) == true) {
            try {
                File skillsFile = new File(skillsFilePath);
                Scanner scan = new Scanner(skillsFile);
                File tempFile = new File(skillsFile.getAbsolutePath() + ".tmp");
                try {
                    FileWriter fileWriter = new FileWriter(tempFile, true);
                    while (scan.hasNextLine()) { //for each line in file
                        String data = scan.nextLine();
                        if (!data.contains(skill_name)) { //if line does not contain skill name
                            fileWriter.write(data);
                            if (scan.hasNextLine()) {
                                fileWriter.write("\n"); //next line, if there more to copy over
                            }
                        }
                    }
                    fileWriter.close();
                } catch(IOException e) {
                    System.out.println("IO Error.");
                    e.printStackTrace();
                }
                scan.close();
                skillsFile.delete(); //delete old file
                tempFile.renameTo(skillsFile); //rename new file as old file's name
            } catch(FileNotFoundException e) {
                System.out.println("File Error.");
                e.printStackTrace();
            }
            return true;
        }
        return false; //no skill in file with that name
    }

    private double percentModifyDouble(double to_modify, double modify_percent, String operation) {
        if(to_modify < 0 || modify_percent < 0) {
            return -1; //invalid to modify double or modify_percent
        }
        //returns -2 if incorrect operation string given
        double result = -2;
        switch (operation) {
            case "decrease": //decrease to_modify by percentage modify_percent
                result = to_modify - (modify_percent * 0.01 * to_modify);
                break;
            case "increase": //increase to_modify by percentage modify_percent
                result = to_modify + (modify_percent * 0.01 * to_modify);
                break;
        }
        return result; //invalid String operation
    }

    //returns 0 if modify is a success, 1 if parameters are incorrect, 2 if modifyValue in SkillsData failed
    //Skill values currently include damage, mana
    public int modifySkillValues(String skill_name, String value, String operation, double x) {
        double initial_damage = skillMap.get(skill_name).getDamage();
            switch (operation) {
                case "flat decrease":
                    if(!skillMap.get(skill_name).modifyValue(value, initial_damage - x)) {
                        return 2; //changeDamage in class SkillsData failed
                    }
                    break;
                case "flat increase":
                    if(!skillMap.get(skill_name).modifyValue(value, initial_damage + x)) {
                        return 2;
                    }
                    break;
                case "percent decrease":
                    if(x > 100)
                        return 1; //incorrect parameter inputs
                    if(!skillMap.get(skill_name).modifyValue(value, (percentModifyDouble(initial_damage, x, "decrease")))) {
                        return 2;
                    }
                    break;
                case "percent increase":
                    if(x > 100)
                        return 1;
                    if(!skillMap.get(skill_name).modifyValue(value, (percentModifyDouble(initial_damage, x, "increase")))) {
                        return 2;
                    }
                    break;
                default:
                    return 1; //incorrect parameters inputs
            }
        return 0; //success
    }

    public int modifyAllSkillValues(String value, String operation, double x) {
        skillMap.entrySet().stream().forEach(e -> { //for each element in set of mappings in hashMap
            modifySkillValues(e.getKey(), value, operation, x);
        });
        return 0; //success
    }

    public List getOrderedSkillList() { //returns a list of skills, that is ordered
        Stream<String> unorderedStream = skillMap.keySet().stream();
        Stream<String> orderedStream = unorderedStream.sorted();
        List<String> orderedList = orderedStream.collect(Collectors.toList());
        return orderedList;
    }

    private boolean doesValueExist(String value){
        SkillData javaIsDumb = new SkillData("*ignore*", -1, -1);
        List<String> valueList = javaIsDumb.getValues();
        for (int i = 0; i < valueList.size(); i++) {
            if (valueList.get(i) == value) {
                return true;
            }
        }
        return false;
    }

    public List findSkillsWithValue(String value, double x){ //returns list of skills with that value
        List<String> skillNamesWithValue = new ArrayList<String>();
        skillMap.entrySet().stream().forEach(e -> { //for each element in set of mappings in hashMap
            switch (value) {
                case "damage":
                    if(e.getValue().getDamage() == x) {
                        skillNamesWithValue.add(e.getKey());
                    }
                    break;
                case "mana":
                    if(e.getValue().getMana() == x) {
                        skillNamesWithValue.add(e.getKey());
                    }
                    break;
            }
        });
        return skillNamesWithValue;
    }

    public boolean removeSkillsWithValue(String value, double x){ //deletes skill in system with specific value
        if(!doesValueExist(value)) {
            return false; //value does not exist
        }
        skillMap.entrySet().stream().forEach(e -> { //for each element in set of mappings in hashMap
            switch (value) {
                case "damage":
                    if(e.getValue().getDamage() == x) {
                        skillMap.remove(e.getKey());
                    }
                    break;
                case "mana":
                    if(e.getValue().getMana() == x) {
                        skillMap.remove(e.getKey());
                    }
                    break;
            }
        });
        return true;
    }
}