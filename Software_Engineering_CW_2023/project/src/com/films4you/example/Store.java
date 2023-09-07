package com.films4you.example;

import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A class to model a store entity.
 */
public class Store {
  private int stockCount = 0;
  private @Nullable Address addr = null;
  private int storeId = 0;
  
  /**
   * Create a store with a given ID.

   * @param storeId The ID of the store, must be >= 0.
   * @throws IllegalArgumentException if the storeId is invalid.
   */
  public Store(int storeId) {
    if (storeId < 0) {
      throw new IllegalArgumentException("storeId must be greater than or equal to 0");
    }
    
    this.storeId = storeId;
  }
  
  /**
   * Get the store's address or 'UNKNOWN' if not set.

   * @return store's formatted address or 'UNKNOWN'.
   */
  public String getAddress() {
    if (addr == null) {
      return "UNKNOWN";
    }
    
    return addr.getAddressFormatted();
  }
  
  public int getStoreId() {
    return storeId;
  }
  
  public int getStockCount() {
    return stockCount;
  }
  
  public void addStockItem() {
    stockCount++;
  }
  
  /**
   * Set address. As the address getter can handle null addresses, allow null values.

   * @param addr The address of the store, may be null.
   */
  public void setAddress(@Nullable Address addr) {
    this.addr = addr;
  }
  
  @Override
  public String toString() {
    return "Store ID " + storeId + " with address " + getAddress()
      + " and stock count " + stockCount;
  }

}
