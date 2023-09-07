package com.films4you.req1;

import com.films4you.main.RequirementInterface;
import com.films4you.main.TaskNotAttemptedException;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * The class that tests if all the interactions
 * between the other classes are outputting the
 * correct information and converting it to Strings

 * @author aa05722
 *
 */
public class Requirement implements RequirementInterface {
  private CustomerDatabase c;
  
  /**
   * Constructor and initialising variables
   */
  public Requirement() {
	this.c = new CustomerDatabase();
  }
  
  /**
   * Returns the total number of customers as a String
   * 
   * @return c.allCustomers() String the total number of customers
   */
  @Override
  public @Nullable String getValueAsString() {
	return c.allCustomers();
  }

 /**
  * Returns the total number of customers as a String in a way
  * that is easy for humans to understand the data being presented
  * to them.
  * 
  * @return String requirement in a human readable format
  */
  @Override
  public @NonNull String getHumanReadable() {
    return ("The total number of customers in the database is: " + this.getValueAsString());
    
  }

}
