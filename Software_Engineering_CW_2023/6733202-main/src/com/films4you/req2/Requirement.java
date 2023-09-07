package com.films4you.req2;

import com.films4you.main.RequirementInterface;
import com.films4you.main.TaskNotAttemptedException;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * What methods the user will interactive with to see the
 * 	the results of the requirement given

 * @author aa05722
 *
 */
public class Requirement implements RequirementInterface {
  private CustomerDatabase cd;
  
  public Requirement() {
   super();
   this.cd = new CustomerDatabase();
  }
  
  /**
   * The raw data of the finished query
   * This can be used by other programmers
   * 
   * @return a String the raw data
   */
  @Override
  public @Nullable String getValueAsString() {
    String a = "";
    a = cd.TopTenCust();
    return a;
  }

  /**
   * The result of the requirement in a way that is easily
   * 	readable for humans
   * 
   * @return a String output of the result
   */
  @Override
  public @NonNull String getHumanReadable() {
    String a = "The top 10 customers and their revenue:\n\n";
    a+="ID|  NAME  | TOTAL SPENT\n----------------------\n";
    a += this.getValueAsString();
    return a;
  }

}
