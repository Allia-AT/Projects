package com.films4you.req4;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RequirementTest {
  
  /**
   * A sample test. You'll need to test your solution properly.
   */
  @Test
  public void testRequirementGetActual() {
    Requirement r = new Requirement();
    assertEquals("Cape Coral,221.55,Saint-Denis,216.54,Aurora,198.50,Molodetno,195.58,"
    		+ "Apeldoorn,194.61,Santa Brbara dOeste,194.61,Qomsheh,186.62,London,180.52,"
    		+ "Ourense (Orense),177.60,Bijapur,175.61,", r.getValueAsString());
  }
  
  @Test
  public void testRequirementGetHumanReadable() {
	  Requirement r = new Requirement();
	  assertEquals("1: Cape Coral has total of £221.55\n"
	  		+ "2: Saint-Denis has total of £216.54\n"
	  		+ "3: Aurora has total of £198.50\n"
	  		+ "4: Molodetno has total of £195.58\n"
	  		+ "5: Apeldoorn has total of £194.61\n"
	  		+ "6: Santa Brbara dOeste has total of £194.61\n"
	  		+ "7: Qomsheh has total of £186.62\n"
	  		+ "8: London has total of £180.52\n"
	  		+ "9: Ourense (Orense) has total of £177.60\n"
	  		+ "10: Bijapur has total of £175.61\n", r.getHumanReadable());
  }
 
}
