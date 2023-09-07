package com.films4you.req3;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CustomerDatabaseTest {

	
	@Test
	public void testFindCustomer1() {
		CustomerDatabase cd = new CustomerDatabase();
		
		cd.findCustomerDetails("599");
		assertEquals("AUSTIN CINTRON", cd.getCustName());
		
		cd.findCustomerDetails("241");
		assertEquals("HEIDI LARSON", cd.getCustName());
		
		cd.findCustomerDetails("52");
		assertEquals("JULIE SANCHEZ", cd.getCustName());
	}
	
	@Test
	public void testFindCustomerValidation() {
		CustomerDatabase cd = new CustomerDatabase();
		
		cd.findCustomerDetails("hello");
		assertEquals(null, cd.getCustName());
	}
	
	@Test
	public void testFindAddressValidation() {
		CustomerDatabase cd = new CustomerDatabase();
	
		assertEquals("No address found.",cd.findAddress(2));
		
	}
	
	@Test
	public void testFindAddress() {
		CustomerDatabase cd = new CustomerDatabase();
		assertEquals("1837 Kaduna Parkway 82580",cd.findAddress(600));
	}
	
	@Test
	public void testFindCustomer2() {
		CustomerDatabase cd = new CustomerDatabase();
		cd.findCustomerDetails("594");
		assertEquals("1837 Kaduna Parkway 82580", cd.getAddress());
		assertEquals("EDUARDO HIATT", cd.getCustName());
	}
	
}
	

