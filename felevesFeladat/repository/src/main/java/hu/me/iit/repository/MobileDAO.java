package hu.me.iit.repository;

import java.util.Collection;

import hu.me.felevesFeladat.exceptions.RosszSuly;
import hu.me.felevesFeladat.exceptions.RosszDatum;
import hu.me.felevesFeladat.exceptions.RosszSorozatSzam;
import hu.me.felevesFeladat.modell.Mobile;


public interface MobileDAO {
	
	public Collection<Mobile> readAllMobiles();
	
	public void deleteAllMobiles();

	public void createMobile(Mobile mobile);

	public Mobile readMobileById(String sorozatszam) throws RosszSorozatSzam;

	public void updateMobile(Mobile mobile);

	public void deleteMobile(Mobile mobile);

	public void deleteMobilebyId(String sorozatszam) throws RosszSorozatSzam;

	public void deleteMobileByGyartasiIdo(String gyartasiIdo) throws RosszDatum;

	public void changeSuly(Double ido, String sorozatszam) throws RosszSuly, RosszSorozatSzam;

}
