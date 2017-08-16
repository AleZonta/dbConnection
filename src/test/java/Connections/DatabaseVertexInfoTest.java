package Connections;

import junit.framework.TestCase;

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
public class DatabaseVertexInfoTest extends TestCase {
    public void testSetConnection() throws Exception {
        DatabaseVertexInfo db = null;
        try {
            db = new DatabaseVertexInfo();
        }catch (Exception e){
            assertEquals("Database file is not available", e.getMessage());
        }
        db = new DatabaseVertexInfo(Boolean.TRUE);
        db.setConnection();
        db.closeConnection();
        db.deleteDatabase();
    }

    public void testInsertDataBis() throws Exception {
        DatabaseVertexInfo db = null;
        try {
            db = new DatabaseVertexInfo();
        }catch (Exception e){
            assertEquals("Database file is not available", e.getMessage());
        }
        db = new DatabaseVertexInfo(Boolean.TRUE);
        db.setConnection();
        db.insertData(10d,10d,0d,0d,"b");
        db.closeConnection();
        db.deleteDatabase();
    }


    public void testInsertData() throws Exception {
        DatabaseVertexInfo db = null;
        try {
            db = new DatabaseVertexInfo();
        }catch (Exception e){
            assertEquals("Database file is not available", e.getMessage());
        }
        db = new DatabaseVertexInfo(Boolean.TRUE);
        db.insertData(10d,10d,0d,0d,"b");

        db.deleteDatabase();
    }

    public void testDeleteDatabase() throws Exception {
        DatabaseVertexInfo db = null;
        try {
            db = new DatabaseVertexInfo();
        }catch (Exception e){
            assertEquals("Database file is not available", e.getMessage());
        }
        db = new DatabaseVertexInfo(Boolean.TRUE);
        db.insertData(10d,11d,0d,0d,"b");

        db.deleteDatabase();
    }

    public void testReadData() throws Exception {
        DatabaseVertexInfo db = null;
        try {
            db = new DatabaseVertexInfo();
        }catch (Exception e){
            assertEquals("Database file is not available", e.getMessage());
        }
        db = new DatabaseVertexInfo(Boolean.TRUE);
        db.insertData(10d,10d,0d,0d,"b");

        String result = db.readData(10d,10d,0d,0d);
        assertEquals("b", result);

        result = db.readData(11d,10d,0d,0d);
        assertNull(result);

        db.deleteDatabase();
    }

}