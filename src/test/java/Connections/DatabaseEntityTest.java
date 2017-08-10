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
    public void testObtainValues() throws Exception {
        DatabaseEntity db = new DatabaseEntity();
        List<String> result = db.obtainValues();
        assertNotNull(result);
        System.out.println(result.toString());
        assertEquals(90, result.size());
    }

    public void testDeleteDatabase() throws Exception {
        DatabaseEntity db = new DatabaseEntity();
        db.insertData(10d,10d,0d,"a","b");

        db.deleteDatabase();

    }

    public void testReadData() throws Exception {
        DatabaseEntity db = new DatabaseEntity();
        String output = db.readData(52.0937245, 5.1286816);
        assertEquals("amenity///parking", output);
        output = db.readData(52.0937245, 5.1286817);
        assertEquals("null///null", output);

    }

    public void testInsertData() throws Exception {
        DatabaseEntity db = new DatabaseEntity();
        db.insertData(10d,10d,0d,"a","b");

        db.deleteDatabase();
    }

}