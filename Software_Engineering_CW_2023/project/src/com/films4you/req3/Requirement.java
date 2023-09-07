package com.films4you.req3;

import com.films4you.main.RequirementInterface;
import com.films4you.main.TaskNotAttemptedException;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Class that can be used by others to get the most frequent
 * renter without the need to access the database and either return
 * the data to be used or return the data in a human readable way

 * @author aa05722
 *
 */
public class Requirement implements RequirementInterface {
  private RentalDatabase rd;
  private CustomerDatabase cd;
  
  public Requirement() {
	  this.rd = new RentalDatabase();
	  this.cd = new CustomerDatabase();
  }
  /**
   * Returns the data in a way that can be used by future programs
   * 
   * @return a String the most frequent renter
   */
  @Override
  public @Nullable String getValueAsString() {
    String a = "";
    rd.findMostFrequentRenter();
    a = rd.getMostFrequentRenterID();
    cd.findCustomerDetails(a);
    a += "," + cd.getCustName() + "," +cd.getAddress() +","+  rd.getHighest();
    return a;
  }

  /**
   * Returns the most frequent renter in a human readable way
   * 
   * @return a String all the details of the most frequent renter
   */
  @Override
  public @NonNull String getHumanReadable() {
	String a = "The most frequent renter in 2005 is: ";
	rd.findMostFrequentRenter();
	cd.findCustomerDetails(rd.getMostFrequentRenterID());
	
	a += cd.getCustName() + "\n\tCustomer ID: ";
	a += rd.getMostFrequentRenterID() + "\n\tAddress: ";
	a += cd.getAddress() + "\nThey rented ";
	a += rd.getHighest() + " films total";
	return a;
  }

}
