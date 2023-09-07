package com.films4you.req4;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.text.DecimalFormat;


import org.checkerframework.checker.nullness.qual.Nullable;

import com.films4you.main.Database;

/**
 * Interacts with the City table in the Films4You database
 * 
 * @author aa05722
 *
 */
public class CityDatabase extends Database{
  private Map<String,String> addressToCity;
  private Map<String,Double> custToPayment;
  private Map<String,Double> cityToPayment;
  private Map<String,String> addresstoCust;
  private Map<String,String> custToCity;
  private List<String> list;
	
  public CityDatabase() {
	super();
	this.cityToPayment = new HashMap<String,Double>();
	this.custToPayment = new HashMap<String,Double>();
	this.list = new ArrayList<String>();
	this.addressToCity = new HashMap<String,String>();
	this.addresstoCust = new HashMap<String,String>();
	this.custToCity = new HashMap<String,String>();

  }
  
  /**
   * converts the top 10 city IDs into city names,
   *   saving the names and total payments into a list
   *   with the format [city,payment,city,payment]
   * 
   * @return list List<String> the top 10 cities and their total payments
   */
  public List<String> getTop10CityByPayment(){
    @Nullable ResultSet set;	
    List<String> list = this.sort();

    set = super.query("SELECT * FROM CITY;");
    try {
      while(set.next()) {
        if (list.contains(set.getString("city_id"))) {
          for(int i=0;i<20;i+=2) {
	        if (list.get(i).equals(set.getString("city_id"))) {
	     	  list.remove(i);
	    	  list.add(i, set.getString("city"));
	   		  break;
	   		}
	   	  }
        }
	  }
      set.close();
	} catch (SQLException e) {
			// TODO Auto-generated catch block
	  e.printStackTrace();
	}
	for (int i =0;i<20;i++) {
	  this.list.add(i, list.get(i));
    }
    return this.list;
  }
  
  /**
   * Populates the HashMap cityToPayment map
   */
  public void makeCityToPayment() {
    PaymentDatabase pd = new PaymentDatabase();
    CustomerDatabase cd = new CustomerDatabase();
    AddressDatabase ad = new AddressDatabase();
    
    this.addressToCity = ad.getAddressToCity();
	this.custToPayment = pd.getCustToPayment();
	this.addresstoCust = cd.getAddressToCustomer();
	
	String nkey,val;
	double amount;
	
	for (String key: this.addressToCity.keySet()) {
	  val = this.addressToCity.get(key);
	  nkey = this.addresstoCust.get(key);
	  this.custToCity.put(nkey, val);
	}

	for (String key: this.custToPayment.keySet()) {
	  nkey = this.custToCity.get(key);
	  amount = this.custToPayment.get(key);
	  if (this.cityToPayment.containsKey(nkey)) {
		amount += this.cityToPayment.get(nkey);
  	  }
	  this.cityToPayment.put(nkey, amount);
	}
  }
  
  /**
   * sorts the HashMap cityToPayment by the total payments and
   * 	converts it to an array
   * 
   * @return list List<String> the sorted list of cities by total
   * 	payments in format [city,payment,city,payment] where the
   * 	payments are in format 000.00
   */
  private List<String> sort() {
	List<Double> arr = new ArrayList<Double>();
	List<String> list = new ArrayList<String>();
	for (Entry<String,Double> entry : this.cityToPayment.entrySet()) {
	  arr.add(entry.getValue());
	}
    Collections.sort(arr);
    int i =0;
    for(double p : arr) {
  	  for (String key : this.cityToPayment.keySet()) {
		if (this.cityToPayment.get(key).equals(p) && !list.contains(key)) {
		  list.add(i, key);
		  list.add(i+1, this.formatAmount(this.cityToPayment.get(key)));
	  	  break;
		}
	  }
	}
	return list;
  }
  
  /**
   * Standardise the format of the double to 000.00 and converts
   * 	it to a string
   * 
   * @param amount double the number wanting to be standardised
   * @return a.toString() String the formatted amount
   */
  private String formatAmount(double amount) {
	StringBuffer a = new StringBuffer();
	DecimalFormat style = new DecimalFormat("000.00");
	a.append(style.format(amount));
	return a.toString();
  }
  
  /**
   * Returns the attribute cityToPayment
   * 
   * @return this.cityToPayment Map<String,Double> a map of the
   * 	city IDs and their total payments
   */
  public Map<String,Double> getCityToPayment(){
	return this.cityToPayment;
  }
  
  /**
   * Sorts the cityToPayment by Payments and returns it in a list
   * 	data type
   * 
   * @return list List<String> the sorted list of cities by total
   * 	payments in format [city,payment,city,payment] where the
   * 	payments are in format 000.00 
   */
  public List<String> getSortedCityToPayment(){
	List<String> list = this.sort();
	return list;
  }
}

