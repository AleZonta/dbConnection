package Config;

import junit.framework.TestCase;

import java.io.IOException;

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
public class ReadConfigTest extends TestCase {
    public void testGetDb() throws Exception {
        //test if I return a location -> that is not null
        ReadConfig conf = new ReadConfig();
        try {
            conf.getDb();
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("Try to access config file before reading it.") );
        }
        try {
            conf.readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull(conf.getDb());

        new ReadConfig.Configurations();
        assertEquals(conf.getDb(), ReadConfig.Configurations.getUsingDB());
    }

    public void testGetDbNameCoordNode() throws Exception {
        //test if I return a location -> that is not null
        ReadConfig conf = new ReadConfig();
        try {
            conf.getDbNameCoordNode();
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("Try to access config file before reading it.") );
        }
        try {
            conf.readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull(conf.getDbNameCoordNode());

        new ReadConfig.Configurations();
        assertEquals(conf.getDbNameCoordNode(), ReadConfig.Configurations.getDbNameCoordNode());
    }

    public void testGetDbNameDistance() throws Exception {
        //test if I return a location -> that is not null
        ReadConfig conf = new ReadConfig();
        try {
            conf.getDbNameDistance();
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("Try to access config file before reading it.") );
        }
        try {
            conf.readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull(conf.getDbNameDistance());

        new ReadConfig.Configurations();
        assertEquals(conf.getDbNameDistance(), ReadConfig.Configurations.getDbNameDistance());
    }


    public void testReadFile() throws Exception {
        //test if I read the file without exception
        //the name of the file is hardcoded
        ReadConfig conf = new ReadConfig();
        try {
            conf.readFile();
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("Name is wrong or missing.")  || e.getMessage().equals("JSON file not well formatted."));
        }
    }

}