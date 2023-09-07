package com.films4you.req3;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.checkerframework.checker.nullness.qual.Nullable;

import com.films4you.main.Database;

/**
 * Interacts with the Rental table in the Films4You database
 * 
 * @author aa05722
 *
 */
public class RentalDatabase extends Database{
  private Map<String,Integer> custToAmount;
  private int highest =0;
  private String mostFrequentRenter;
  public RentalDatabase() {
    super();
    this.custToAmount = new HashMap<String,Integer>();
    
  }
  /**
   * finds the most frequent renter in 2005
   */
  public void findMostFrequentRenter() {
	@Nullable ResultSet set;
	String query = "SELECT * FROM RENTAL;";
	set = super.query(query);
	
	this.makeHashMap(set);
	
	for (Entry<String, Integer> i: this.custToAmount.entrySet()) {
	  if (i.getValue() > this.highest) {
		this.highest = i.getValue();
		this.mostFrequentRenter = i.getKey();
	  }
	}
  }
 
  /**
   * Returns the number of films rented by the most frequent renter
   * 
   * @return this.highest int number of films rented
   */
  public int getHighest() {
    return this.highest;
  }
  
  /**
   * Returns the customer ID of the most frequent renter
   * 
   * @return this.mostFrequentRenter String customer ID
   */
  public String getMostFrequentRenterID() {
	return this.mostFrequentRenter;
  }
  
  /**
   * Populates the Hashmap custToAmount with the relevant data
   * 
   * @param set ResultSet the output of a query
   */
  private void makeHashMap(ResultSet set) {
	try {
  	  while(set.next()) {
  		String key = set.getString("customer_id");
	    int now = 1;
	    int old;
	    if (this.custToAmount.containsKey(key)) {
		  old = this.custToAmount.get(key);
		  now = old + 1;
		  this.custToAmount.replace(key,old,now);
	    } else{
		  this.custToAmount.putIfAbsent(key, now);
	    }
	  }
  	  set.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
	  e.printStackTrace();
	}
  }
}
