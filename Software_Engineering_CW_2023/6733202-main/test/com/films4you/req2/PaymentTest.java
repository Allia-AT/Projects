package com.films4you.req2;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class PaymentTest {

	PaymentDatabase p = new PaymentDatabase();
	
	@Test
	public void testGetTopTenCustByPayment() {
		List<String> a = p.getTopTenCustByPayment();
		for (String amount: a) {
			System.out.println(amount);
		}
		assertEquals("221.55",a.get(1));
		assertEquals("175.58", a.get(17));
	}

	
}
