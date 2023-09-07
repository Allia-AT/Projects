package com.films4you.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class RequirementTest {
  
  /*
   * A test for checking that getValueAsString works.
   */
  @Test
  public void testRequirementGetActual() {
    Requirement r = new Requirement();
    String value = r.getValueAsString();
    
    if (value == null) {
      fail("Requirement value was null");
    } else {
      assertEquals("28 MySQL Boulevard:2311", value);
    }
  }
  
  /*
   * A test for checking that getHumanReadable works.
   */
  @Test
  public void testRequirementGetHumanReadable() {
    Requirement r = new Requirement();
    assertEquals("The store with the most inventory is on 28 MySQL Boulevard with 2311 items",
        r.getHumanReadable());
  }
 
}
