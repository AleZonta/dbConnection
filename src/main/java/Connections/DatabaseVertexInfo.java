package Connections;

import org.sqlite.javax.SQLiteConnectionPoolDataSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Alessandro Zonta on 11/08/2017.
 * PhD Situational Analytics
 * <p>
 * Computational Intelligence Group
 * Computer Science Department
 * Faculty of Sciences - VU University Amsterdam
 * <p>
 * a.zonta@vu.nl
 */
public class DatabaseVertexInfo {
    private static final Logger logger = Logger.getLogger(DatabaseEntity.class.getName()); //logger for this class
    private SQLiteConnectionPoolDataSource dataSource;


    /**
     * check if db still exist
     */
    public DatabaseVertexInfo() throws FileNotFoundException {
        File f = new File("entity.db");
        if(!f.exists()) {
            throw new FileNotFoundException("Database file is not available");
        }
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.dataSource = new SQLiteConnectionPoolDataSource();
        this.dataSource.setUrl("jdbc:sqlite:vertexinfo.db");
    }

    /**
     * Constructor one parameter
     * If true creates the db
     * @param var boolean variable
     */
    public DatabaseVertexInfo(Boolean var){
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.dataSource = new SQLiteConnectionPoolDataSource();
        this.dataSource.setUrl("jdbc:sqlite:vertexinfo.db");
        if(var){
            this.createDb();
        }
    }


    /**
     * Create the database
     */
    private void createDb(){
        Connection c = null;
        Statement stmt = null;

        try {
            c = this.dataSource.getPooledConnection().getConnection();
            logger.log(Level.INFO, "Open DB entity successfully");

            stmt = c.createStatement();
            String sql = String.format("CREATE TABLE IF NOT EXISTS ENTITY (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, LATITUDEI DOUBLE NOT NULL, LONGITUDEI DOUBLE NOT NULL, LATITUDEE DOUBLE NOT NULL, LONGITUDEE DOUBLE NOT NULL, VALUE TEXT NOT NULL)");
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            logger.log(Level.INFO, e.getClass().getName() + ": " + e.getMessage());
        }
        logger.log(Level.INFO, "Table created successfully (If was not there)");
    }


    /**
     * Insert data into db
     * @param lati latitude start
     * @param loni longitude start
     * @param late latitude end
     * @param lone longitude end
     * @param value string value containing info
     */
    public void insertData(Double lati, Double loni, Double late, Double lone, String value){
        Connection c = null;
        Statement stmt = null;

        try {
            c = this.dataSource.getPooledConnection().getConnection();
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = String.format("INSERT INTO ENTITY (LATITUDEI,LONGITUDEI,LATITUDEE,LONGITUDEE,VALUE) VALUES ('%s','%s','%s','%s','%s');", lati, loni, late, lone, value);
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            logger.log(Level.INFO, e.getClass().getName() + ": " + e.getMessage());
        }
    }



    private Connection c;
    private Statement stmt;

    /**
     * Set up the connection
     * @throws SQLException if problems appear
     */
    public void setConnection() throws SQLException {
        this.c = this.dataSource.getPooledConnection().getConnection();
        this.c.setAutoCommit(false);
    }

    /**
     * Insert the data into the db
     * @param lati latitude start
     * @param loni longitude start
     * @param late latitude end
     * @param lone longitude end
     * @param value string value containing info
     * @throws SQLException if problems appear
     */
    public void insertDataBis(Double lati, Double loni, Double late, Double lone, String value) throws SQLException {
        this.stmt = this.c.createStatement();
        String sql = String.format("INSERT INTO ENTITY (LATITUDEI,LONGITUDEI,LATITUDEE,LONGITUDEE,VALUE) VALUES ('%s','%s','%s','%s','%s');", lati, loni, late, lone, value);
        this.stmt.executeUpdate(sql);
    }

    /**
     * Close the connection
     * @throws SQLException  if problems appear
     */
    public void closeConnection() throws SQLException {
        this.c.commit();
        this.c.close();
    }
















    /**
     * Delete the database from memory
     */
    public void deleteDatabase() {
        try {
            Files.delete(Paths.get("vertexinfo.db"));
        } catch (Exception e) {
            logger.log(Level.INFO, e.getClass().getName() + ": " + e.getMessage());
        }
    }


    /**
     * Read the data from the db using latitude and logitude of the start and and point
     * It returns a string containing all the info
     * @param lati latitude variable
     * @param loni longitude variable
     * @param late latitude variable
     * @param lone longitude variable
     * @return al the info present in that vertex
     */
    public String readData(Double lati, Double loni, Double late, Double lone){
        Connection c = null;
        Statement stmt = null;
        try {
            c = this.dataSource.getPooledConnection().getConnection();

            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM ENTITY WHERE LATITUDEI='%s' AND LONGITUDEI='%s' AND LATITUDEE='%s' AND LONGITUDEE='%s';", lati, loni, late, lone));

            String value = null;
            while ( rs.next() ) {
                value = rs.getString("VALUE");
            }

            rs.close();
            stmt.close();
            c.close();

            return value;

        } catch ( Exception e ) {
            logger.log(Level.INFO, e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }



}
