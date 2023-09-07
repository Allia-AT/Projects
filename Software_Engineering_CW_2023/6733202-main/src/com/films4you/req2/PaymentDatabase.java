package com.films4you.req2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.checkerframework.checker.nullness.qual.Nullable;

import com.films4you.main.Database;

/**
 * Class interacts with the payment table of the Films4You database
 * 
 * @author aa05722
 *
 */
public class PaymentDatabase extends Database{
  private Map<String,Double> custToPayment;
  private List<String> list;
  private LinkedHashMap<String,Double> sortedMap;
	
  public PaymentDatabase() {
    super();
    this.custToPayment = new HashMap<String,Double>();
    this.sortedMap = new LinkedHashMap<String,Double>();
    this.list = new ArrayList<String>();

  }
	
	/**Returns a list of the top customers' IDs and their total payments
	 * 
	 * @return this.list List<String> the top10 customers' IDs and their
	 *	 payments such that it looks like: ID,payment,ID,payment
	 */
  public List<String> getTopTenCustByPayment() {
    @Nullable ResultSet set;
    String query = "SELECT * FROM payment;";
    int c = 0;
    String a;
		
    set = super.query(query);
    this.makeHashMap(set);
    this.sortHashMap();
   
    for (String key : this.sortedMap.keySet()) {
	  this.list.add(key);
	  double p = this.sortedMap.get(key);
	  a = this.formatAmount(p);
	  this.list.add(a);
	  c =c+ 2;
	  if (c == 20) {
	    //you have the top 10
  	    break;
	  }
    }
	  
    return this.list;
  }
	
  /**Format the total amounts before adding to the list
   * 
   * @param amount double the number you want to format
   * @return a.toString String the amount in correct formatting
   */
  private String formatAmount(double amount) {
	StringBuffer a = new StringBuffer();
	DecimalFormat style = new DecimalFormat("000.00");
	a.append(style.format(amount));
	return a.toString();
	}
	
  /**Takes the set and converts it to a HashMap
   * 
   * @param set ResultSet the data you wish to convert
   */
  private void makeHashMap(ResultSet set) {
	try {
  	  while(set.next()) {
		String key = set.getString("customer_id");
		double now = set.getDouble("amount");
		if (this.custToPayment.containsKey(key)) {
	  	  double old = this.custToPayment.get(key);
  		  now += old;
		  this.custToPayment.replace(key,old,now);
		} else {
		  this.custToPayment.put(key, now);
		}		
	  }
  	  set.close();
	} catch (SQLException e) {
	// TODO Auto-generated catch block
	  e.printStackTrace();
	}
  }
	
  /**sort the HashMap in descending order
   * 
   * {@link http://www.java2s.com/example/java-utility-method/map-sort/sortbycomparatordouble-final-map-string-double-map-a5e9f.html} 
   *
   */
  private void sortHashMap() {
	List<Entry<String,Double>> map_arr = new LinkedList<Entry<String,Double>>(this.custToPayment.entrySet());
	Collections.sort(map_arr, new Comparator<Entry<String, Double>>(){
	  public int compare(Entry<String,Double> v1, Entry<String,Double> v2) {
  	    return v2.getValue().compareTo(v1.getValue());
	  }
	});
		
	for (Entry<String,Double> e : map_arr) {
	  this.sortedMap.put(e.getKey(), e.getValue());
	}
  }
}

