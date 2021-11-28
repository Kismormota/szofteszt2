package org.core;

import static org.junit.Assert.*;


import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import hu.me.felevesFeladat.enums.Gyarto;
import hu.me.felevesFeladat.exceptions.RosszSuly;
import hu.me.felevesFeladat.exceptions.RosszDatum;
import hu.me.felevesFeladat.exceptions.RosszSorozatSzam;
import hu.me.felevesFeladat.exceptions.RosszMéret;
import hu.me.felevesFeladat.modell.Mobile;

public class MobileTest {
	
	private Mobile testMobile;
	
	@Before
	public void initMobile() {

		testMobile = new Mobile(Gyarto.Xiaomi,
				"MODEL1",
				"aasd123",
				12,false,LocalDate.parse("2010-12-02"),2.1);

	}
	

	@Test
	public void setGyartoTest(){
		Mobile mobile = new Mobile();

		mobile.setGyarto(Gyarto.Apple);
	}
	
	@Test
	public void setModellTest(){
		Mobile mobile = new Mobile();
		mobile.setModell("Redmi9T");
	}
	
	@Test
	public void setMéretTest() throws RosszMéret {
		Mobile mobile = new Mobile();
		mobile.setMéret(4);
	}
	
	@Test(expected = RosszMéret.class)
	public void setMéretTestException() throws RosszMéret {
		Mobile mobile = new Mobile();
		mobile.setMéret(17);
	}
	
	@Test(expected = RosszMéret.class)
	public void setMéretTestException2() throws RosszMéret {
		Mobile mobile = new Mobile();
		mobile.setMéret(0);
	}
	
	@Test
	public void setSerultTest(){
		Mobile mobile = new Mobile();
		mobile.setSerult(true);
	}
	
	@Test
	public void setGyartasiIdoTest() throws RosszDatum {
		Mobile mobile = new Mobile();
		mobile.setGyartasiIdo("2021-02-02");
	}
	
	@Test(expected = RosszDatum.class)
	public void setGyartasiIdoTestException() throws RosszDatum {
		Mobile mobile = new Mobile();
		mobile.setGyartasiIdo("2021-2-02");
	}
	
	@Test
	public void setSulyTest() throws RosszSuly {
		Mobile mobile = new Mobile();
		mobile.setSuly(1.2);
	}
	
	@Test(expected = RosszSuly.class)
	public void setSulyTestExcpetion() throws RosszSuly {
		Mobile mobile = new Mobile();
		mobile.setSuly(-1.2);
	}
	

	

	/*public void setSorozatszam() throws RosszSorozatSzam {
		Mobile mobile = new Mobile();
		mobile.setSorozatszam("asd1%");
	}*/
	
	@Test(expected = RosszSorozatSzam.class)
	public void setSorozatszamExcpetion() throws RosszSorozatSzam {
		Mobile mobile = new Mobile();
		mobile.setSorozatszam("aasd123s");
	}
	
	@Test
	public void getGyarto() {
		assertEquals(Gyarto.Xiaomi, testMobile.getGyarto());
	}
	

	@Test
	public void getModell() {
		assertEquals("MODEL1", testMobile.getModell());
	}
	
	@Test
	public void getSorozatszam() {
		assertEquals("aasd123", testMobile.getSorozatszam());
	}
	
	@Test
	public void getMéret() {
		assertEquals(12, testMobile.getMéret());
	}
	
	@Test
	public void getSerult() {
		assertEquals(false, testMobile.isSerult());
	}
	
	@Test
	public void getGyartasiIdo() {
		assertEquals(LocalDate.parse("2010-12-02"), testMobile.getGyartasiIdo());
	}
	
	@Test
	public void getSuly() {
		assertEquals(2.1, testMobile.getSuly(),0.000001);
	}

}
