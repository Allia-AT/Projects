package com.films4you.req4;

import com.films4you.main.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Interacts with the Customer table in the Films4You database
 * 
 * @author aa05722
 *
 */
public class CustomerDatabase extends Database{
  private Map<String,String> addressToCustomer;
  
  public CustomerDatabase() {
	super();
	this.addressToCustomer = new HashMap<String,String>();
  }
  
  /**
   * Populates and returns the HashMap addressToCustomer using the customer table
   * 
   * @return this.addressToCustomer Map<String,Double> every address ID
   *   to the corresponding customer ID
   */
  public Map<String,String> getAddressToCustomer(){
	String query = "SELECT * FROM CUSTOMER;";
	@Nullable ResultSet set;
	  
	set = super.query(query);
	
	try {
	  while(set.next()) {
	    this.addressToCustomer.put(set.getString("address_id"), set.getString("customer_id"));
	  }
	  set.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
	  e.printStackTrace();
	}

	return this.addressToCustomer;
  }
}
