package com.films4you.example;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AddressTest {

  /**
   * A test for checking Address's toString.
   */
  @Test
  public void testAddressToString() {
    Address addr1 = new Address("LINE1", null, null);
    assertEquals("Address: LINE1", addr1.toString());
    
    addr1 = new Address("LINE1", null, "POSTCODE");
    assertEquals("Address: LINE1, POSTCODE", addr1.toString());
  }
  
  /**
   * A test for Address's getAddressFormatted method.
   */
  @Test
  public void testAddressGetFormatted() {
    Address addr1 = new Address("LINE1", null, null);
    assertEquals("LINE1", addr1.getAddressFormatted());
    
    addr1 = new Address("LINE1", null, "POSTCODE");
    assertEquals("LINE1, POSTCODE", addr1.getAddressFormatted());
  }
  
}
