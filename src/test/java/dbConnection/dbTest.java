package dbConnection;

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
public class dbTest extends TestCase {
    public void testConnectAndSelect() throws Exception {
        db database = new db();
        database.connectAndSelect();
    }

    public void testConnectAndInsert() throws Exception {
        db database = new db();
        database.connectAndInsert();
    }

    public void testConnectAndCreateTable() throws Exception {
        db database = new db();
        database.connectAndCreateTable();
    }

    public void testConnect() throws Exception {
        db database = new db();
        database.connect();
    }

}