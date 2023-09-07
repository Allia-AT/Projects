package com.films4you.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Test;

/**
 * A simple JUnit test to see if database queries work.
 * 

 * @author Nick Frymann
 * 
 */
public class DatabaseTest {

  /**
   * Test a query on testing database and check against the expected values.
   */
  @Test
  public void testDatabaseQuery() {
    try {
      Database db = new Database();
      ResultSet staffData = db.query("SELECT * FROM staff");
      String results = "";
      
      while (staffData.next()) {
        results += staffData.getString(3);
        results += ", " + staffData.getString(2);
        results += " (ID: " + staffData.getInt(1) + ")\n";
      }
     
      db.close();
      System.out.print("Staff are:\n" + results);
      assertEquals("Hillyer, Mike (ID: 1)\nStephens, Jon (ID: 2)\n", results);
      
    } catch (SQLException e) {
      System.err.print(e);
      fail("Database could not be queried.");
    }
  }

}
