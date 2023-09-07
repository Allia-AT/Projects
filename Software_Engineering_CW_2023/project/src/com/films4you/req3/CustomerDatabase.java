package com.films4you.req3;


import java.sql.ResultSet;
import java.sql.SQLException;
import org.checkerframework.checker.nullness.qual.Nullable;
import com.films4you.main.Database;

/**
 * Interacts with the Customer table from the films4You database
 * 
 * @author aa05722
 *
 */
public class CustomerDatabase extends Database{
  private String name;
  private String address;
	
  public CustomerDatabase() {
	super();
  }
  
  /**
   * finds the address of a customer
   * 
   * @param ID int the address ID of the customer
   * @return a String the address of the customer
   */
  public String findAddress(int ID) {
	String a = "No address found.";
	if (ID > 4) {
	  AddressDatabase ad = new AddressDatabase(ID);
	  a = ad.getAddress();
	}
	return a;
  }

  /**
   * Returns the address of a specified customer
   * 
   * @return address String the address of the specified customer
   */
  public String getAddress() {
	return this.address;
  }

  /**
   * Returns a customer's full name
   * 
   * @return this.name String customer's full name
   */
  public String getCustName() {
	return this.name;
  }

  /**
   * Finds a customer's details given their customer ID
   * 
   * @param ID String customer's ID
   */
  public void findCustomerDetails(String ID) {
	@Nullable ResultSet set;
	String query = "SELECT * FROM customer;";
	set = super.query(query);
	
	if (ID.matches("[0-9]+")) {
	  try {
	    while(set.next()) {
		  if(set.getString("customer_id").equals(ID)) {
			this.name = set.getString("first_name") + " " 
						+ set.getString("last_name");
			this.address = this.findAddress(set.getInt("address_id"));
		  }
		}
	    set.close();
	  } catch (SQLException e) {
		// TODO Auto-generated catch block
	    e.printStackTrace();
	  }
	}
  }
}
