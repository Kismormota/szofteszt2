package org.service;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import hu.me.felevesFeladat.MobileService;
import hu.me.felevesFeladat.enums.Gyarto;
import hu.me.felevesFeladat.exceptions.RosszSorozatSzam;
import hu.me.felevesFeladat.exceptions.RosszMéret;
import hu.me.felevesFeladat.modell.Mobile;
import hu.me.iit.repository.MobileDAO;

public class MobileServiceTest {
	private MobileService service;
	private MobileDAO mock;
	private Collection<Mobile> mobiles;
	
	@Before
	public void serviceInit() throws RosszSorozatSzam, FileNotFoundException {
		mock = Mockito.mock(MobileDAO.class);
		service = new MobileService(mock);

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

		Mockito.doReturn(mobile1).when(mock).readMobileById("asd1");
		Mockito.doReturn(mobile2).when(mock).readMobileById("asd2");
		Mockito.doReturn(mobile3).when(mock).readMobileById("asd3");
		Mockito.doReturn(mobile4).when(mock).readMobileById("asd4");
		Mockito.doReturn(mobile5).when(mock).readMobileById("asd5");
		Mockito.when(mock.readAllMobiles()).thenReturn(mobiles);
	}
	
	@Test
	public void getAllMobileTest() {
		assertEquals(5, service.getAllMobile().size());
		for (Mobile mobile : mobiles) {
			MatcherAssert.assertThat(mobiles, org.hamcrest.Matchers.hasItem(mobile));
		}
	}
	
	@Test
	public void getMobileBySorozatszamTest() throws RosszSorozatSzam {
		Mobile mobile = service.getMobileBySorozatszam("QQED3451");
		boolean ok = false;
		if(mobiles.contains(mobile))
			ok = true;
		assertEquals(true, ok);
		
	}
	
	@Test
	public void getAllSerultMobileTest() throws FileNotFoundException {
		assertEquals(1,service.getAllserultMobile().size());
	}
	
	@Test
	public void getAllMobileDatumKozott() {
		assertEquals(3,service.getAllMobileDatumKozott(LocalDate.parse("2021-01-28"), LocalDate.parse("2021-04-03")).size());
		assertEquals(1,service.getAllMobileDatumKozott(LocalDate.parse("2021-02-03"), LocalDate.parse("2021-04-03")).size());
		assertEquals(1,service.getAllMobileDatumKozott(LocalDate.parse("2021-02-01"), LocalDate.parse("2021-02-04")).size());
	}
	
	@Test
	public void deleteAllserultMobileTest() {
		assertEquals(4, service.deleteAllserultMobile().size());
	}
	
	@Test
	public void setToAsusIf4CoreTest() {
		int count=0;
		List<Mobile> result = service.setToAsusIf4Core();
		for (int i = 0; i < result.size(); i++) {
			if(result.get(i).getGyarto().equals("Asus") && result.get(i).getMéret()==4)
				count++;
		}
		assertEquals(2, count);
	}
	
	@Test
	public void ifBetween2006And2011Then2Core() throws RosszMéret {
		int count=0;
		List<Mobile> result = service.ifBetween2006And2011Then2Core();
		for (int i = 0; i < result.size(); i++) {
			if(result.get(i).getMéret()==2)
				count++;
		}
		assertEquals(1, count);
	}
	
	@Test
	public void addMobileTest() {
		Mobile mobile = new Mobile();
		service.addMobile(mobile);
		Mockito.verify(mock,Mockito.times(1)).createMobile(mobile);
	}
	
	@Test
	public void deleteMobileTest() {
		Mobile mobile = new Mobile();
		service.deleteMobile(mobile);
		Mockito.verify(mock,Mockito.times(1)).deleteMobile(mobile);
	}
	
	@Test
	public void updateMobileTest() {
		Mobile mobile = new Mobile();
		service.updateMobile(mobile);
		Mockito.verify(mock,Mockito.times(1)).updateMobile(mobile);
	}

}
