package com.films4you.req2;

import java.nio.Buffer;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import org.checkerframework.checker.nullness.qual.Nullable;

import com.films4you.main.Database;
/**
 * Class interacts with the customer table from the Films4You database
 * 
 * @author aa05722
 *
 */
public class CustomerDatabase extends Database{
  private PaymentDatabase p = new PaymentDatabase();
  
  public CustomerDatabase() {
    super();
  }
	
  /**
   * Returns the top 10 customers by total payments
   * 
   * @return a String the top10 customers & their payments
   */
  public String TopTenCust() {
	@Nullable ResultSet set;
	String query = "SELECT * FROM customer;";
	String a = "";
	List<String> topTen = p.getTopTenCustByPayment();
		
	set = super.query(query);
	try {	
      for (int i=0; i<20;i=i+2) {
  	    while(set.next()) {
	  	  if (set.getString("customer_id").equals(topTen.get(i))) {
			a += topTen.get(i) + ","+ set.getString("first_name") + " "
	  		  + set.getString("last_name") +","+ topTen.get(i+1) + "\n";
			
			set.close();
			set = super.query(query);
			break;
		  }
		}
	  }
      set.close();
	} catch (SQLException e) {
	  e.printStackTrace();
	}
	return a;
  }
}
