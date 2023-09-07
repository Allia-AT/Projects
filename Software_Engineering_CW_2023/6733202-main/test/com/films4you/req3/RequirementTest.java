package com.films4you.req3;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RequirementTest {
  
  /**
   * A sample test. You'll need to test your solution properly.
   */
  @Test
  public void testRequirementGetActual() {
    Requirement r = new Requirement();
    assertEquals("148,ELEANOR HUNT,1952 Pune Lane 92150,46", r.getValueAsString());
  }
  
  @Test
  public void testRequirementGetHumanReadable() {
	  Requirement r = new Requirement();
	  assertEquals("The most frequent renter in 2005 is: ELEANOR HUNT"
	  		+ "\n\tCustomer ID: 148"
	  		+ "\n\tAddress: 1952 Pune Lane 92150"
	  		+ "\nThey rented 46 films total",r.getHumanReadable());
  }
 
}
