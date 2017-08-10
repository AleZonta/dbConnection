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
 *
 *
 * Class that will enable connection with the DatabaseDistance
 */
public class DatabaseDistance {
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
    public DatabaseDistance(){
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
                this.dataSource.setUrl("jdbc:sqlite:" + ReadConfig.Configurations.getDbNameDistance() + ".db");
                c = this.dataSource.getPooledConnection().getConnection();
                logger.log(Level.INFO, "Open DatabaseCoordNode successfully");

                stmt = c.createStatement();
                String sql = String.format("CREATE TABLE IF NOT EXISTS DISTANCE (ID INTEGER PRIMARY KEY AUTOINCREMENT   NOT NULL, LATITUDEC           DOUBLE    NOT NULL,  LONGITUDEC          DOUBLE    NOT NULL,  LATITUDEI           DOUBLE    NOT NULL,  LONGITUDEI          DOUBLE    NOT NULL,  DISTANCE            DOUBLE NOT NULL)");
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
     * @param latc latitude coordinate to save
     * @param lonc longitude coordinate to save
     * @param lati latitude infonode to save
     * @param loni longitude infonode to save
     * @param distance distance to save
     */
    public void insertData(Double latc, Double lonc, Double lati, Double loni, Double distance){
        Connection c = null;
        Statement stmt = null;

        try {
            c = this.dataSource.getPooledConnection().getConnection();
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = String.format("INSERT INTO DISTANCE (LATITUDEC,LONGITUDEC,LATITUDEI,LONGITUDEI,DISTANCE) VALUES ('%s','%s','%s','%s','%s');", latc, lonc, lati, loni, distance);
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
     * @param latc latitude coordinate to looking for
     * @param lonc longitude coordinate to looking for
     * @param lati latitude infonode to looking for
     * @param loni longitude infonode to looking for
     * @return Double distance
     */
    public Double readData(Double latc, Double lonc, Double lati, Double loni){
        Connection c = null;
        Statement stmt = null;
        try {
            c = this.dataSource.getPooledConnection().getConnection();

            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM DISTANCE WHERE LATITUDEC='%s' AND LONGITUDEC='%s' AND LATITUDEI='%s' AND LONGITUDEI='%s';", latc, latc, lati, loni));

            Double id = null;
            while ( rs.next() ) {
                id = rs.getDouble("DISTANCE");
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
            Files.delete(Paths.get(ReadConfig.Configurations.getDbNameDistance() +".db"));
        } catch (Exception e) {
            logger.log(Level.INFO, e.getClass().getName() + ": " + e.getMessage());
        }
    }

}
