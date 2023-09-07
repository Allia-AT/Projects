package com.films4you.req2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CustomerDatabaseTest {
	CustomerDatabase c = new CustomerDatabase();
	
	@Test
	public void testTopTenCust() {
		String a = c.TopTenCust();
		assertEquals("526,KARL SEAL,221.55\n"
				+ "148,ELEANOR HUNT,216.54\n"
				+ "144,CLARA SHAW,195.58\n"
				+ "137,RHONDA KENNEDY,194.61\n"
				+ "178,MARION SNYDER,194.61\n"
				+ "459,TOMMY COLLAZO,186.62\n"
				+ "469,WESLEY BULL,177.60\n"
				+ "468,TIM CARY,175.61\n"
				+ "236,MARCIA DEAN,175.58\n"
				+ "181,ANA BRADLEY,174.66\n",
				a);
	}
}
