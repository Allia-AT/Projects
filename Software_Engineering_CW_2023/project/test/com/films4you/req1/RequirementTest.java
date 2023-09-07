package com.films4you.req1;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RequirementTest {
  Requirement r = new Requirement();
  
  /**
   * Tests the function getValueAsString in the Requirement's class
   */
  @Test
  public void testRequirementGetActual() {
    assertEquals("599", r.getValueAsString());
  }
  
  /**
   * Tests the function getHumanReadable in the Requirement's class
   */
  @Test
  public void testRequirementGetHumanReadable() {
	assertEquals("The total number of customers in the database is: 599", r.getHumanReadable());
  }
}
