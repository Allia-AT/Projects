package com.films4you.req4;

import com.films4you.main.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Interacts with the Address table in the Films4You database
 * 
 * @author aa05722
 *
 */
public class AddressDatabase extends Database{
  private Map<String,String> addressToCity;
  
  public AddressDatabase() {
	super();
	this.addressToCity = new HashMap<String,String>();
  }
  
  /**
   * Populates the HashMap addressToCity and returns it
   * 
   * @return this.adressToCiry Map<String,String> the address IDs mapped to their
   * 	respective customer IDs
   */
  public Map<String,String> getAddressToCity() {
	String query = "SELECT * FROM ADDRESS;";
	@Nullable ResultSet set;
	
	set = super.query(query);
	
	try {
	  while (set.next()) {
		this.addressToCity.put(set.getString("address_id"), set.getString("city_id"));
	  }
	  set.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
	  e.printStackTrace();
	}
	return this.addressToCity;
  }

}
