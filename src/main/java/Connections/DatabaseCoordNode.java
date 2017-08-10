package Connections;

import Config.ReadConfig;
import org.sqlite.javax.SQLiteConnectionPoolDataSource;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Alessandro Zonta on 18/07/2017.
 * PhD Situational Analytics
 * <p>
 * Computational Intelligence Group
 * Computer Science Department
 * Faculty of Sciences - VU University Amsterdam
 * <p>
 * a.zonta@vu.nl
 *
 * Class that will enable connection with the DatabaseCoordNode
 */
public class DatabaseCoordNode {
    private static final Logger logger = Logger.getLogger(DatabaseCoordNode.class.getName()); //logger for this class
    private SQLiteConnectionPoolDataSource dataSource;
    private Boolean enable;


    /**
     * Getter for variable that is saying if I am using the db
     * @return Boolean value
     */
    public Boolean getEnable() {
        return enable;
    }

    /**
     * Constructor of the class
     * It creates the table if it does not exist in the database selected
     */
    public DatabaseCoordNode(){
        try {
            new ReadConfig.Configurations();
            this.enable = ReadConfig.Configurations.getUsingDB();
        } catch (Exception e) {
            logger.log(Level.INFO, e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        if(this.enable) {
            Connection c = null;
            Statement stmt = null;

            try {
                Class.forName("org.sqlite.JDBC");
                this.dataSource = new SQLiteConnectionPoolDataSource();
                this.dataSource.setUrl("jdbc:sqlite:" + ReadConfig.Configurations.getDbNameCoordNode() + ".db");
                c = this.dataSource.getPooledConnection().getConnection();
                logger.log(Level.INFO, "Open DatabaseCoordNode successfully");

                stmt = c.createStatement();
                String sql = "CREATE TABLE IF NOT EXISTS COORDINFO " +
                        "(ID INTEGER PRIMARY KEY AUTOINCREMENT   NOT NULL," +
                        " LATITUDE           DOUBLE    NOT NULL, " +
                        " LONGITUDE          DOUBLE    NOT NULL, " +
                        " IDNODE        TEXT)";
                stmt.executeUpdate(sql);
                stmt.close();
                c.close();
            } catch (Exception e) {
                logger.log(Level.INFO, e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
            logger.log(Level.INFO, "Table created successfully (If was not there)");
        }
    }

    /**
     * Insert data into the Database
     * @param lat latitude to save
     * @param lon longitude to save
     * @param idNode id node to save
     */
    public void insertData(Double lat, Double lon, String idNode){
        Connection c = null;
        Statement stmt = null;

        try {
            c = this.dataSource.getPooledConnection().getConnection();
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "INSERT INTO COORDINFO (LATITUDE,LONGITUDE,IDNODE) " +
                    "VALUES ('" + lat + "','" + lon + "','" + idNode + "');";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            logger.log(Level.INFO, e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }


    /**
     * Read data from database
     * @param lat Latitude to looking for
     * @param lon longitude to looking for
     * @return string of the id of the node
     */
    public String readData(Double lat, Double lon){
        Connection c = null;
        Statement stmt = null;
        try {
            c = this.dataSource.getPooledConnection().getConnection();
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM COORDINFO " +
                    "WHERE LATITUDE='"+ lat +"' AND " +
                    "LONGITUDE='"+ lon +"';" );

            String id = null;
            while ( rs.next() ) {
                id = rs.getString("IDNODE");
            }

            rs.close();
            stmt.close();
            c.close();

            return id;
        } catch ( Exception e ) {
            logger.log(Level.INFO, e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return null;
        }
    }


    /**
     * Delete the database from memory
     */
    public void deleteDatabase() {
        try {
            Files.delete(Paths.get(ReadConfig.Configurations.getDbNameCoordNode() +".db"));
        } catch (Exception e) {
            logger.log(Level.INFO, e.getClass().getName() + ": " + e.getMessage());
        }
    }

}
