package Connections;

import junit.framework.TestCase;

import java.util.List;

/**
 * Created by Alessandro Zonta on 09/08/2017.
 * PhD Situational Analytics
 * <p>
 * Computational Intelligence Group
 * Computer Science Department
 * Faculty of Sciences - VU University Amsterdam
 * <p>
 * a.zonta@vu.nl
 */
public class DatabaseEntityTest extends TestCase {
    public void testObtainCoordinates() throws Exception {
        DatabaseEntity db = null;
        try {
            db = new DatabaseEntity();
        }catch (Exception e){
            assertEquals("Database file is not available", e.getMessage());
        }
        db = new DatabaseEntity(Boolean.TRUE);
        db.insertData(11d,10d,0d,"a","b");

        List<DatabaseEntity.Point> p = db.obtainCoordinates();
        assertNotNull(p);
        assertEquals(1, p.size());
        assertEquals(11d, p.get(0).getLat());
        assertEquals(10d, p.get(0).getLon());
        db.deleteDatabase();
    }

    public void testObtainValues() throws Exception {
        DatabaseEntity db = null;
        try {
            db = new DatabaseEntity();
        }catch (Exception e){
            assertEquals("Database file is not available", e.getMessage());
        }
        db = new DatabaseEntity(Boolean.TRUE);
        List<String> result = db.obtainValues();
        assertNotNull(result);
        System.out.println(result.toString());
        assertEquals(0, result.size());
    }

    public void testDeleteDatabase() throws Exception {
        DatabaseEntity db = null;
        try {
            db = new DatabaseEntity();
        }catch (Exception e){
            assertEquals("Database file is not available", e.getMessage());
        }
        db = new DatabaseEntity(Boolean.TRUE);
        db.insertData(11d,10d,0d,"a","b");

        db.deleteDatabase();

    }

    public void testReadData() throws Exception {
        DatabaseEntity db = null;
        try {
            db = new DatabaseEntity();
        }catch (Exception e){
            assertEquals("Database file is not available", e.getMessage());
        }
        db = new DatabaseEntity(Boolean.TRUE);
        db.insertData(10d,10d,0d,"a","b");
        String output = db.readData(10d, 10d);
        assertEquals("a///b", output);
        output = db.readData(11d, 5.17);
        assertEquals("null///null", output);

    }

    public void testInsertData() throws Exception {
        DatabaseEntity db = null;
        try {
            db = new DatabaseEntity();
        }catch (Exception e){
            assertEquals("Database file is not available", e.getMessage());
        }
        db = new DatabaseEntity(Boolean.TRUE);
        db.insertData(10d,10d,0d,"a","b");

        db.deleteDatabase();
    }

}