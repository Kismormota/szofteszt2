package org.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import hu.me.felevesFeladat.enums.Gyarto;
import hu.me.felevesFeladat.exceptions.RosszSuly;
import hu.me.felevesFeladat.exceptions.RosszDatum;
import hu.me.felevesFeladat.exceptions.RosszSorozatSzam;
import hu.me.felevesFeladat.modell.Mobile;
import hu.me.iit.repository.MobileDAOImpl;

public class MobileDAOImplTest {
	
	private MobileDAOImpl mock,dao2,dao;
	private List<Mobile> mobiles;
	private File file,file2;
	
	@Before
	public void init() throws IOException {
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
	
		file = new File("someTestFile.txt");
		file2 = new File("someTestFile2.txt");
		dao = new MobileDAOImpl(mobiles, file.getName());
		dao2 = new MobileDAOImpl(mobiles, file2.getName());
		mock = Mockito.spy(new MobileDAOImpl(mobiles, file2.getName()));
	}
	
    @Test
    public void createMobileListTest() throws IOException {
    	assertEquals(0, dao.createMobileList().size());
    	file.delete();
    }

    @Test
    public void writeToFileTest() {
    	dao2.writeToFile(mobiles);
    	assertEquals(5, dao2.createMobileList().size());
    }
    
    @Test
    public void readAllMobilesTest() {
    	Collection<Mobile> result = mock.readAllMobiles();
    	verify(mock, Mockito.times(1)).createMobileList();
    	assertEquals(5, result.size());
    }
    
    @Test
    public void readMobileByIdTest() throws RosszSorozatSzam {
    	when(mock.createMobileList()).thenReturn((ArrayList<Mobile>) mobiles);
    	Mobile result = mock.readMobileById("asd1");
    	assertEquals("Apple", result.getGyarto());
    	assertTrue(mobiles.contains(result));
    }
    
    @Test(expected = RosszSorozatSzam.class)
    public void readMobileByIdTestExcpetion() throws RosszSorozatSzam {
    	mock.readMobileById("asd3");
    }
    
    @Test
    public void readMobileByIdNotFindTest() throws RosszSorozatSzam {
    	Mobile result = mock.readMobileById("asd4");
    	when(mock.createMobileList()).thenReturn((ArrayList<Mobile>) mobiles);
    	assertEquals(null, result);
    }
    
    @SuppressWarnings("unchecked")
	@Test
    public void createMobileTest() {
    	doNothing().when(mock).writeToFile(anyList());
    	when(mock.createMobileList()).thenReturn((ArrayList<Mobile>) mobiles);
    	Mobile mobile = new Mobile();
    	assertEquals(5, mock.readAllMobiles().size());
    	mock.createMobile(mobile);
    	assertEquals(6, mock.readAllMobiles().size());
    }
    
    @SuppressWarnings("unchecked")
	@Test
    public void updateMobileTest() throws RosszSorozatSzam {
    	doNothing().when(mock).writeToFile(anyList());
    	when(mock.createMobileList()).thenReturn((ArrayList<Mobile>) mobiles);
    	Mobile mobile = new Mobile();
    	mobile.setSorozatszam("asd2");
    	mobile.setGyarto(Gyarto.Apple);
    	for (int i = 0; i < mobiles.size(); i++) {
			if(mobiles.get(i).getSorozatszam().equals(mobile.getSorozatszam())) {
				assertEquals("Samsung", mobiles.get(i).getGyarto());
			}
		}
    	mock.updateMobile(mobile);
    	for (int i = 0; i < mobiles.size(); i++) {
			if(mobiles.get(i).getSorozatszam().equals(mobile.getSorozatszam())) {
				assertEquals("Apple", mobiles.get(i).getGyarto());
			}
		}
    }
    
    @SuppressWarnings("unchecked")
	@Test
    public void deleteMobileTest() throws RosszSorozatSzam {
    	int count=0;
    	doNothing().when(mock).writeToFile(anyList());
    	when(mock.createMobileList()).thenReturn((ArrayList<Mobile>) mobiles);
    	Mobile mobile = new Mobile(Gyarto.Samsung,"Modell2","asd2",4,true,LocalDate.parse("2019-05-12"),1.3);
    	for (int i = 0; i < mobiles.size(); i++) {
    		if(mobiles.get(i).getSorozatszam().equals(mobile.getSorozatszam())) {
				count++;
			}
		}
    	assertEquals(1, count);
    	count=0;
    	mock.deleteMobile(mobile);
    	for (int i = 0; i < mobiles.size(); i++) {
    		if(mobiles.get(i).getSorozatszam().equals(mobile.getSorozatszam())) {
				count++;
			}
		}
    	assertEquals(0, count);
    }
    
    @SuppressWarnings("unchecked")
	@Test
    public void deleteMobileByIdTest() throws RosszSorozatSzam {
    	int count=0;
    	String id="asdasd2";
    	doNothing().when(mock).writeToFile(anyList());
    	when(mock.createMobileList()).thenReturn((ArrayList<Mobile>) mobiles);
    	for (int i = 0; i < mobiles.size(); i++) {
    		if(mobiles.get(i).getSorozatszam().equals(id)) {
				count++;
			}
		}
    	assertEquals(1, count);
    	count=0;
    	mock.deleteMobilebyId(id);
    	for (int i = 0; i < mobiles.size(); i++) {
    		if(mobiles.get(i).getSorozatszam().equals(id)) {
				count++;
			}
		}
    	assertEquals(0, count);
    }
    
    @SuppressWarnings("unchecked")
	@Test(expected = RosszSorozatSzam.class)
    public void deleteMobileByIdTestException() throws RosszSorozatSzam {
    	String id="segse12";
    	doNothing().when(mock).writeToFile(anyList());
    	when(mock.createMobileList()).thenReturn((ArrayList<Mobile>) mobiles);
    	mock.deleteMobilebyId(id);
    }
    
    @SuppressWarnings("unchecked")
	@Test
    public void deleteMobileByGyartasiIdoTest() throws RosszDatum {
    	int count=0;
    	String date = "2021-01-05";
    	doNothing().when(mock).writeToFile(anyList());
    	when(mock.createMobileList()).thenReturn((ArrayList<Mobile>) mobiles);
    	for (int i = 0; i < mobiles.size(); i++) {
			if(mobiles.get(i).getGyartasiIdo().equals(LocalDate.parse(date)))
				count++;
		}
    	assertEquals(1, count);
    	count=0;
    	mock.deleteMobileByGyartasiIdo(date);
    	for (int i = 0; i < mobiles.size(); i++) {
			if(mobiles.get(i).getGyartasiIdo().equals(LocalDate.parse(date)))
				count++;
		}
    	assertEquals(0, count);
    }
    
    @SuppressWarnings("unchecked")
	@Test(expected = RosszDatum.class)
    public void deleteMobileByGyartasiIdoTestExpected() throws RosszDatum {
    	String date = "2021-4-20";
    	doNothing().when(mock).writeToFile(anyList());
    	when(mock.createMobileList()).thenReturn((ArrayList<Mobile>) mobiles);
    	mock.deleteMobileByGyartasiIdo(date);
    }
    
    @SuppressWarnings("unchecked")
	@Test
    public void changeSulyTest() throws RosszSuly, RosszSorozatSzam {
    	String id = "asd44";
    	doNothing().when(mock).writeToFile(anyList());
    	when(mock.createMobileList()).thenReturn((ArrayList<Mobile>) mobiles);
    	for (int i = 0; i < mobiles.size(); i++) {
			if(mobiles.get(i).getSorozatszam().equals(id))
				assertEquals(1.2, mobiles.get(i).getSuly(),0.000001);
		}
    	mock.changeSuly(1.5, "asd2");
    	for (int i = 0; i < mobiles.size(); i++) {
			if(mobiles.get(i).getSorozatszam().equals(id))
				assertEquals(1.5, mobiles.get(i).getSuly(),0.000001);
		}
    }
    
    @SuppressWarnings("unchecked")
	@Test(expected = RosszSuly.class)
    public void changeSulyTestTimeException() throws RosszSuly, RosszSorozatSzam {
    	String id = "QQEseg";
    	doNothing().when(mock).writeToFile(anyList());
    	when(mock.createMobileList()).thenReturn((ArrayList<Mobile>) mobiles);
    	mock.changeSuly(-1.3, id);
    }
    
    @SuppressWarnings("unchecked")
	@Test(expected = RosszSorozatSzam.class)
    public void changeSulyTestIdException() throws RosszSuly, RosszSorozatSzam {
    	String id = "QQWQFASD";
    	doNothing().when(mock).writeToFile(anyList());
    	when(mock.createMobileList()).thenReturn((ArrayList<Mobile>) mobiles);
    	mock.changeSuly(1.3, id);
    }
    
    @Test
    public void deleteAllMobileTest() {
    	dao.deleteAllMobiles();
    	assertEquals(0, dao.createMobileList().size());
    }
    
    @Test
    public void test() {
    	MobileDAOImpl impl = new MobileDAOImpl(mobiles);
    }
}
