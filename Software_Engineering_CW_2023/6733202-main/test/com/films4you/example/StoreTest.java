package com.films4you.example;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StoreTest {
  
  @Test(expected = IllegalArgumentException.class)
  public void testStoreConstructorInvalid() {
    new Store(-1);
  }
  
  @Test
  public void testStoreAddress() {
    Store store = new Store(0);
    assertEquals("UNKNOWN", store.getAddress());
    assertEquals("Store ID 0 with address UNKNOWN and stock count 0", 
        store.toString());
    
    Address addr = new Address("ADDRLINE1", null, "POSTCODE");
    store.setAddress(addr);
    assertEquals("ADDRLINE1, POSTCODE", store.getAddress());
    assertEquals("Store ID 0 with address ADDRLINE1, POSTCODE and stock count 0", 
        store.toString());
  }
  
  @Test
  public void testStoreStock() {
    Store store = new Store(0);
    assertEquals(0, store.getStockCount());
    assertEquals("Store ID 0 with address UNKNOWN and stock count 0", 
        store.toString());
    
    store.addStockItem();
    store.addStockItem();
    store.addStockItem();
    assertEquals(3, store.getStockCount());
    assertEquals("Store ID 0 with address UNKNOWN and stock count 3", 
        store.toString());
  }
  
}
