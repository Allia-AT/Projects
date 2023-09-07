package com.films4you.req1;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CustomerDatabaseTest {

	@Test
	public void testQuery() {
		//should output 599
		CustomerDatabase c = new CustomerDatabase();
		assertEquals("599",c.allCustomers());
		
	}
	
}
