package Connections;

import org.sqlite.javax.SQLiteConnectionPoolDataSource;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Alessandro Zonta on 09/08/2017.
 * PhD Situational Analytics
 * <p>
 * Computational Intelligence Group
 * Computer Science Department
 * Faculty of Sciences - VU University Amsterdam
 * <p>
 * a.zonta@vu.nl
 *
 *
 * implementation database entity
 */
public class DatabaseEntity {
    private static final Logger logger = Logger.getLogger(DatabaseEntity.class.getName()); //logger for this class
    private SQLiteConnectionPoolDataSource dataSource;


    /**
     * Create the database if it does not exist
     */
    public DatabaseEntity(){
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            this.dataSource = new SQLiteConnectionPoolDataSource();
            this.dataSource.setUrl("jdbc:sqlite:entity.db");
            c = this.dataSource.getPooledConnection().getConnection();
            logger.log(Level.INFO, "Open DB entity successfully");

            stmt = c.createStatement();
            String sql = String.format("CREATE TABLE IF NOT EXISTS ENTITY (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, LATITUDE DOUBLE NOT NULL, LONGITUDE DOUBLE NOT NULL, IDD DOUBLE NOT NULL, KEYV TEXT NOT NULL, VALUE TEXT NOT NULL)");
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            logger.log(Level.INFO, e.getClass().getName() + ": " + e.getMessage());
        }
        logger.log(Level.INFO, "Table created successfully (If was not there)");
    }


    /**
     * Insert the data on the db
     * @param lat latitude entity
     * @param lon longitude entity
     * @param id id entity
     * @param key key of the entity
     * @param value value of the entity
     */
    public void insertData(Double lat, Double lon, Double id, String key, String value){
        Connection c = null;
        Statement stmt = null;

        try {
            c = this.dataSource.getPooledConnection().getConnection();
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = String.format("INSERT INTO ENTITY (LATITUDE,LONGITUDE,IDD,KEYV,VALUE) VALUES ('%s','%s','%s','%s','%s');", lat, lon, id, key, value);
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            logger.log(Level.INFO, e.getClass().getName() + ": " + e.getMessage());
        }
    }

    /**
     * Delete the database from memory
     */
    public void deleteDatabase() {
        try {
            Files.delete(Paths.get("entity.db"));
        } catch (Exception e) {
            logger.log(Level.INFO, e.getClass().getName() + ": " + e.getMessage());
        }
    }


    /**
     * Read the data from the db using latitude and logitude
     * It returns a string containing the key and the value of the tag corresponding to that place
     * @param lat latitude variable
     * @param lon longitude variable
     * @return string value (two result are separated by a /// as escape). If not present the two results will be NULL
     */
    public String readData(Double lat, Double lon){
        Connection c = null;
        Statement stmt = null;
        try {
            c = this.dataSource.getPooledConnection().getConnection();

            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM ENTITY WHERE LATITUDE='%s' AND LONGITUDE='%s';", lat, lon));

            String key = null;
            String value = null;
            while ( rs.next() ) {
                key = rs.getString("KEYV");
                value = rs.getString("VALUE");
            }

            rs.close();
            stmt.close();
            c.close();

            return String.format("%s///%s", key, value);

        } catch ( Exception e ) {
            logger.log(Level.INFO, e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Obtain the list of the values present in the database
     * @return list od distinct values
     */
    public List<String> obtainValues(){
        Connection c = null;
        Statement stmt = null;
        try {
            c = this.dataSource.getPooledConnection().getConnection();

            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT VALUE FROM 'ENTITY';");

            List<String> values = new ArrayList<>();
            while ( rs.next() ) {
                values.add(rs.getString("VALUE"));
            }

            rs.close();
            stmt.close();
            c.close();

            return values;

        } catch ( Exception e ) {
            logger.log(Level.INFO, e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }
}
