package Connections;

import junit.framework.TestCase;

/**
 * Created by Alessandro Zonta on 19/07/2017.
 * PhD Situational Analytics
 * <p>
 * Computational Intelligence Group
 * Computer Science Department
 * Faculty of Sciences - VU University Amsterdam
 * <p>
 * a.zonta@vu.nl
 */
public class DatabaseDistanceTest extends TestCase {
    public void testGetEnable() throws Exception {
        DatabaseDistance db = new DatabaseDistance();
        assertFalse(db.getEnable());
    }

    public void testInsertData() throws Exception {

        DatabaseDistance db = new DatabaseDistance();
        db.insertData(10d,10d,20d,20d,50d);

        db.deleteDatabase();
    }

    public void testReadData() throws Exception {

        DatabaseDistance db = new DatabaseDistance();
        db.insertData(10d,10d,20d,20d,50d);
        //if present
        Double result = db.readData(10d,10d,20d,20d);
        assertNotNull(result);
        assertEquals(50d,result);

        //not present
        result = db.readData(10d,10d,20d,10d);
        assertNull(result);

        db.deleteDatabase();
    }


}