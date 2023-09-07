package com.films4you.req4;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;

public class CityDatabaseTest {

	@Test
	public void testGetTop10CityByPayment() {
		CityDatabase cd = new CityDatabase();
		cd.makeCityToPayment();
		cd.getTop10CityByPayment();
	}
	@Test
	public void testMakeCityToPayment() {
		CityDatabase cd = new CityDatabase();
		Map<String,Double> map;
		cd.makeCityToPayment();
		map = cd.getCityToPayment();
//		for (String key: map.keySet()) {
//			System.out.println(key + ": " + map.get(key));
//		}
		
	}
	
	@Test
	public void testSortHashmap() {
		CityDatabase cd = new CityDatabase();
		cd.makeCityToPayment();
		List<String> list = cd.getSortedCityToPayment();
		assertEquals("221.55", list.get(1)); //total of 101
		assertEquals("29", list.get(8)); //id of 5th highest
		assertEquals("050.85", list.get(list.size() -1)); //last total city is 520
	}

}
