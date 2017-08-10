package Config;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by Alessandro Zonta on 11/05/2017.
 * PhD Situational Analytics
 * <p>
 * Computational Intelligence Group
 * Computer Science Department
 * Faculty of Sciences - VU University Amsterdam
 * <p>
 * a.zonta@vu.nl
 *
 * This class reads the config file with the info needed by the program
 */
public class ReadConfig {
    private String dbNameCoordNode;
    private String dbNameDistance;
    private Boolean db;

    /**
     * Constructor with zero parameter
     * Everything is set to null.
     */
    protected ReadConfig(){
        this.dbNameDistance = null;
        this.dbNameCoordNode = null;
        this.db = null;
    }

    /**
     * Method that returns the name of the DatabaseCoordNode
     * @return String with the name
     * @throws Exception if I am trying to access it before reading it
     */
    public String getDbNameCoordNode() throws Exception {
        if(this.dbNameCoordNode == null) throw new Exception("Try to access config file before reading it.");
        return this.dbNameCoordNode;
    }


    /**
     * Method that returns the name of the dbNameDistance
     * @return String with the name
     * @throws Exception if I am trying to access it before reading it
     */
    public String getDbNameDistance() throws Exception {
        if(this.dbNameDistance == null) throw new Exception("Try to access config file before reading it.");
        return this.dbNameDistance;
    }

    /**
     * Method that returns if I am using the db
     * @return Boolean value if I am using the db
     * @throws Exception if I am trying to access it before reading it
     */
    public Boolean getDb() throws Exception {
        if(this.db == null) throw new Exception("Try to access config file before reading it.");
        return this.db;
    }

    /**
     * Method that reads the file with all the settings.
     * The file's name is hardcoded as "graph_setting.json".
     * @throws Exception If the file is not available, not well formatted or the settings are not all coded an exception
     * is raised
     */
    protected void readFile() throws Exception {
        //config file has to be located in the same directory as the program is
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString() + "/settings.json";
        //file is a json file, need to parse it and than I can read it
        FileReader reader;
        try {
            reader = new FileReader(currentPath);
        } catch (FileNotFoundException e) {
            throw new Exception("Config file not found.");
        }
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) parser.parse(reader);
        } catch (IOException | ParseException e) {
            throw new Exception("JSON file not well formatted.");
        }
        //reading the settings
        try {
            this.dbNameCoordNode = (String)jsonObject.get("dbNameCoordNode");
        }catch (ClassCastException | NullPointerException e) {
            throw new Exception("dbNameCoordNode is wrong or missing.");
        }
        try {
            this.dbNameDistance = (String)jsonObject.get("dbNameDistance");
        }catch (ClassCastException | NullPointerException e) {
            throw new Exception("dbNameDistance is wrong or missing.");
        }
        try {
            this.db = (Boolean)jsonObject.get("usingDB");
        }catch (ClassCastException | NullPointerException e) {
            throw new Exception("usingDB is wrong or missing.");
        }
    }


    /**
     * Static class offering all the info read from file
     */
    public static class Configurations{
        private static ReadConfig config;

        /**
         * Initialise and read the settings from file
         * @throws Exception if something goes wrong during the reading procedure
         */
        public Configurations() throws Exception {
            config = new ReadConfig();
            config.readFile();
        }

        /**
         * Return getDbNameCoordNode setting
         * @return String value
         * @throws Exception if I am trying to access it before reading it
         */
        public static String getDbNameCoordNode() throws Exception {
            return config.getDbNameCoordNode();
        }

        /**
         * Return getDbNameCoordNode setting
         * @return String value
         * @throws Exception if I am trying to access it before reading it
         */
        public static String getDbNameDistance() throws Exception {
            return config.getDbNameDistance();
        }

        /**
         * Method that reads the file with all the settings.
         * @return Boolean value if I am using the db
         * @throws Exception if I am trying to access it before reading it
         */
        public static Boolean getUsingDB() throws Exception {
            return config.getDb();
        }

    }

}
