package com.films4you.req3;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.checkerframework.checker.nullness.qual.Nullable;

import com.films4you.main.Database;

/**
 * Access the Address table in the films4You database
 * for a given customers records
 * 
 * @author aa05722
 *
 */
public class AddressDatabase extends Database{
  private int ID;
  private String address;
  private @Nullable ResultSet set;
  /**
   * Constructor and initialising variables
   * 
   * @param ID int the Address ID the user wants to look up
   */
  public AddressDatabase(int ID) {
    super();
	this.ID = ID;
	String query = "SELECT * FROM ADDRESS;";
	this.set = super.query(query);
  }
  
  /**
   * Returns the given customer's address
   * 
   * @return this.address String the customer's address
   */
  public String getAddress() {
	try {
	  while(this.set.next()) {
		if (this.set.getInt("address_id") == (this.ID)) {
		  this.address = this.set.getString("address") + " "+ this.set.getString("postal_code");
		  break;
		}
	  }
	  this.set.close();
    } catch (SQLException e) {
	// TODO Auto-generated catch block
	  e.printStackTrace();
	}
	return this.address;
	}
}
