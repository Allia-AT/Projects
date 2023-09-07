package com.films4you.example;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A class to represent an address.
 */
public class Address {
  private String addrLine1 = "";
  private String addrLine2 = "";
  private String postcode = "";
  
  /**
   * Create an address given two lines and a postcode. Line 1 is required.

   * @param line1 First line of the address, required.
   * @param line2 Second line of the address, may be null.
   * @param postcode Postcode of the address, may be null.
   */
  public Address(@NonNull String line1, @Nullable String line2, @Nullable String postcode) {
    if (line1 == null || line1.isBlank()) {
      throw new IllegalArgumentException("Address line 1 is required");
    }
    
    if (line2 != null) {
      addrLine2 = line2;
    }
    
    if (postcode != null) {
      this.postcode = postcode;
    }
    
    addrLine1 = line1;
  }
  
  /**
   * Return the address in a formatted way, with sections comma-separated.

   * @return the formatted address.
   */
  public String getAddressFormatted() {
    String finalString = addrLine1;
    
    if (!addrLine2.isBlank()) {
      finalString += ", " + addrLine2;
    }
    
    if (!postcode.isBlank()) {
      finalString += ", " + postcode;
    }
    
    return finalString;
  }
  
  @Override
  public String toString() {   
    return "Address: " + getAddressFormatted();
  }

}
