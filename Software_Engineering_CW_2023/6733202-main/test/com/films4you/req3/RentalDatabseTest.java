package com.films4you.req3;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RentalDatabseTest {
  @Test
  public void testMostFrequentCustID() {
	RentalDatabase r = new RentalDatabase();
	r.findMostFrequentRenter();
	assertEquals("148",r.getMostFrequentRenterID());
  }
  
  @Test
  public void testgetHighest() {
	  RentalDatabase r = new RentalDatabase();
	  r.findMostFrequentRenter();
	  assertEquals(46,r.getHighest());
  }
}
