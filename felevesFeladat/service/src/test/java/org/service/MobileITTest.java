package org.service;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import hu.me.felevesFeladat.MobileService;
import hu.me.felevesFeladat.enums.Gyarto;
import hu.me.felevesFeladat.exceptions.RosszSorozatSzam;
import hu.me.felevesFeladat.modell.Mobile;
import hu.me.iit.repository.MobileDAOImpl;

public class MobileITTest {
	private static MobileService MobileService;
	private static MobileDAOImpl MobileDao;
	private static List<Mobile> mobiles;
	
	@Before
	public void init() {
		mobiles = new ArrayList<>();

		Mobile mobile1 = new Mobile(Gyarto.Apple,"Modell1","asd1",1,true,LocalDate.parse("2021-01-01"),1.1);
		Mobile mobile2 = new Mobile(Gyarto.Samsung,"Modell2","asd2",2,false,LocalDate.parse("2021-02-02"),1.2);
		Mobile mobile3 = new Mobile(Gyarto.Xiaomi,"Modell3","asd3",3,false,LocalDate.parse("2021-03-03"),1.3);
		Mobile mobile4 = new Mobile(Gyarto.Huawei,"Modell4","asd4",4,false,LocalDate.parse("2021-04-04"),1.4);
		Mobile mobile5 = new Mobile(Gyarto.Nokia,"Modell5","asd5",5,false,LocalDate.parse("2021-05-05"),1.5);

		mobiles.add(mobile1);
		mobiles.add(mobile2);
		mobiles.add(mobile3);
		mobiles.add(mobile4);
		mobiles.add(mobile5);
		
		MobileDao = new MobileDAOImpl(mobiles, "someTestFile2.txt");
		MobileService = new MobileService(MobileDao);
		MobileDao.writeToFile(mobiles);
	}
	
	@After
	public void deleteAll() {
		MobileDao.deleteAllMobiles();
	}
	
	@Test(expected = RosszSorozatSzam.class)
	public void getByIdExceptionTest() throws RosszSorozatSzam {
		MobileService.getMobileBySorozatszam("AAAagag");
	}
	
	//@Test
	/*public void getByIdTest() throws RosszSorozatSzam {
		Mobile mobile = MobileService.getMobileBySorozatszam("aegags");
		mobiles.contains(mobile);
	}*/
	
	@Test
	public void getAllMobileITTest() {
		assertEquals(1, MobileService.getAllMobile().size());
	}
	
	@Test
	public void getAllserultMobileITTest() throws FileNotFoundException {
		assertEquals(1, MobileService.getAllserultMobile().size());
	}
	
	@Test
	public void deleteAllserultMobileITTest() {
		assertEquals(0, MobileService.deleteAllserultMobile().size());
	}
	
	@Test
	public void createMobile() {
		Mobile mobile = new Mobile(Gyarto.Huawei,"Modell12","asd423",3,false,LocalDate.parse("2021-04-02"),1.4);
		MobileService.addMobile(mobile);
		assertEquals(1,MobileService.getAllMobile().size());
	}
}
