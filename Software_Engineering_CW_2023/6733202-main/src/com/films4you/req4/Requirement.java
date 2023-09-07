package com.films4you.req4;

import com.films4you.main.RequirementInterface;
import com.films4you.main.TaskNotAttemptedException;

import java.util.List;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Class that can be used by others to get the most frequent
 * renter without the need to access the database and either return
 * the data to be used or return the data in a human readable way.
 * 
 * @author aa05722
 *
 */
public class Requirement implements RequirementInterface {
  
  /**
   * Returns the data in a way that can be used by future programs
   * 
   * @return a String the top 10 cities and their revenue
   */
  @Override
  public @Nullable String getValueAsString() {
    CityDatabase cd = new CityDatabase();
    cd.makeCityToPayment();
    List<String> l = cd.getTop10CityByPayment();
    String a="";
    for(int i=0;i<20;i++) {
    	a += l.get(i) + ",";
    }
    return a;
  }

  /**
   * Returns the top 10 cities' and their revenue in a human readable way
   * 
   * @return a String top 10 cities' and their revenue
   */
  @Override
  public @NonNull String getHumanReadable() {
    CityDatabase cd = new CityDatabase();
    cd.makeCityToPayment();
    List<String> l = cd.getTop10CityByPayment();
    String a="";
    int c = 1;
    for(int i=0;i<20;i+=2) {
      a += c + ": " + l.get(i) + " has total of £" + l.get(i+1) +"\n";
	  c++;
    }
    return a;
  }

}
