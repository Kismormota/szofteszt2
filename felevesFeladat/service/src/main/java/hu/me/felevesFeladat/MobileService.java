package hu.me.felevesFeladat;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import hu.me.felevesFeladat.enums.Gyarto;
import hu.me.felevesFeladat.exceptions.RosszSorozatSzam;
import hu.me.felevesFeladat.exceptions.RosszMéret;
import hu.me.felevesFeladat.modell.Mobile;
import hu.me.iit.repository.MobileDAO;

public class MobileService {
	private MobileDAO dao;

	public MobileService(MobileDAO dao) {
		super();
		this.dao = dao;
	}
	
	public Collection<Mobile> getAllMobile(){
		return dao.readAllMobiles();
	}
	
	public Mobile getMobileBySorozatszam(String sorozatszam) throws RosszSorozatSzam {
		return dao.readMobileById(sorozatszam);
	}

	public Collection<Mobile> getAllserultMobile() throws FileNotFoundException{
		Collection<Mobile> Mobileek = getAllMobile();
		Collection<Mobile> serult = Mobileek.stream().filter(a -> a.isSerult()).collect(Collectors.toList());
		return serult;
	}
	
	public Collection<Mobile> getAllMobileDatumKozott(LocalDate tol, LocalDate ig){
		List<Mobile> Mobileek = (List<Mobile>) getAllMobile();
		List<Mobile> datumKozott = new ArrayList<Mobile>();
		for (int i = 0; i < Mobileek.size(); i++) {
			if(Mobileek.get(i).getGyartasiIdo().isAfter(tol) && Mobileek.get(i).getGyartasiIdo().isBefore(ig)) {
				datumKozott.add(Mobileek.get(i));
			}
		}
		return datumKozott;
	}
	
	public List<Mobile> deleteAllserultMobile() {
		List<Mobile> Mobileek = (List<Mobile>) getAllMobile();
		for (int i = 0; i < Mobileek.size(); i++) {
			if(Mobileek.get(i).isSerult())
				Mobileek.remove(i);
		}
		return Mobileek;
	}
	
	public List<Mobile> setToAsusIf4Core(){
		List<Mobile> Mobileek = (List<Mobile>) getAllMobile();
		for (int i = 0; i < Mobileek.size(); i++) {
			if(Mobileek.get(i).getMéret()==4)
				Mobileek.get(i).setGyarto(Gyarto.Apple);
		}
		return Mobileek;
	}
	
	public List<Mobile> ifBetween2006And2011Then2Core() throws RosszMéret {
		int LowerYear = 2006, HigherYear=2011;
		List<Mobile> Mobileek = (List<Mobile>) getAllMobile();
		for (int i = 0; i < Mobileek.size(); i++) {
			if(Mobileek.get(i).getGyartasiIdo().getYear()>=LowerYear && Mobileek.get(i).getGyartasiIdo().getYear()<=HigherYear) {
				Mobileek.get(i).setMéret(2);
			}
		}
		return Mobileek;
	}
	
	public void addMobile(Mobile mobile) {
		dao.createMobile(mobile);
	}
	
	public void deleteMobile(Mobile mobile) {
		dao.deleteMobile(mobile);
	}
	
	public void updateMobile(Mobile mobile) {
		dao.updateMobile(mobile);
	}
}
