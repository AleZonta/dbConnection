package Connections;

import junit.framework.TestCase;

/**
 * Created by Alessandro Zonta on 18/07/2017.
 * PhD Situational Analytics
 * <p>
 * Computational Intelligence Group
 * Computer Science Department
 * Faculty of Sciences - VU University Amsterdam
 * <p>
 * a.zonta@vu.nl
 */
public class DatabaseCoordNodeTest extends TestCase {
    public void testGetEnable() throws Exception {
        DatabaseCoordNode db = new DatabaseCoordNode();
        assertFalse(db.getEnable());
    }

    public void testInsertData() throws Exception {

        DatabaseCoordNode db = new DatabaseCoordNode();
        db.insertData(10d,10d,"id");

        db.deleteDatabase();
    }

    public void testReadData() throws Exception {

        DatabaseCoordNode db = new DatabaseCoordNode();
        db.insertData(10d,10d,"id");
        //if present
        String result = db.readData(10d,10d);
        assertNotNull(result);
        assertEquals("id",result);

        //not present
        result = db.readData(20d,10d);
        assertNull(result);

        db.deleteDatabase();
    }

}