package org.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import hu.me.felevesFeladat.exceptions.RosszSorozatSzam;
import hu.me.felevesFeladat.modell.Mobile;

@RunWith(Parameterized.class)
public class SorozatszamTest {

	@Parameters
	public static Collection<Object[]> data() {
		List<Object[]> data = new ArrayList<Object[]>();
		data.add(new Object[]{"Asege2"});
		data.add(new Object[]{"shrshsrh3"});
		data.add(new Object[]{"sdrhsr23"});
		data.add(new Object[]{"kkkk1"});
		return data;
		
	}
	String sorozatszam;
	
	public SorozatszamTest(String sorozatszam) {
		this.sorozatszam=sorozatszam;
	}
	
	@Test(expected = RosszSorozatSzam.class)
	public void testRosszSorozatszam() throws RosszSorozatSzam {
		Mobile mobile = new Mobile();
		mobile.setSorozatszam(sorozatszam);
	}
}
