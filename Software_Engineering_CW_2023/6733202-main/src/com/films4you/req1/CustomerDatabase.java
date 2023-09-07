package com.films4you.req1;

import java.nio.Buffer;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.checkerframework.checker.nullness.qual.Nullable;

import com.films4you.main.Database;

/**
 * A class to interact with the Customer Database
 * 
 * @author aa05722
 *
 */
public class CustomerDatabase extends Database{
  public CustomerDatabase() {
    super();
  }
/**
 * Returns the number of total customers in database
 * 
 */
  public String allCustomers() {
	@Nullable ResultSet set;
	String a = "";
	int i=0;
	String query = "SELECT * FROM customer;";
		
	set = super.query(query);
		
	try {
	  while (set.next()) {
	    i++;
	  }
	  set.close();
	} catch (SQLException e) {
			// TODO Auto-generated catch block
	  e.printStackTrace();
	}
	  a = i + "";
	  return a;
  }
}
