package hu.me.iit.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import hu.me.felevesFeladat.enums.Gyarto;
import hu.me.felevesFeladat.exceptions.RosszSuly;
import hu.me.felevesFeladat.exceptions.RosszDatum;
import hu.me.felevesFeladat.exceptions.RosszSorozatSzam;
import hu.me.felevesFeladat.modell.Mobile;

public class MobileDAOImpl implements MobileDAO {

	private List<Mobile> mobiles;
	private String file;
	
	public MobileDAOImpl(List<Mobile> mobiles) {
		this.mobiles = mobiles;
		this.file = "mobiles.txt";
	}
	
	public MobileDAOImpl(List<Mobile> mobiles, String file) {
		this.mobiles = mobiles;
		this.file = file;
	}

	@Override
	public Collection<Mobile> readAllMobiles() {
		return mobiles = createMobileList();
		
	}

	@Override
	public Mobile readMobileById(String sorozatszam) throws RosszSorozatSzam {
		String regex = "^[A-Za-z]{4}\\d{4}$";
		if(!sorozatszam.matches(regex)) {
			throw new RosszSorozatSzam(sorozatszam);
		}
		mobiles = createMobileList();
		for (int i = 0; i < mobiles.size(); i++) {
			if(mobiles.get(i).getSorozatszam().equals(sorozatszam))
				return mobiles.get(i);
		}
		return null;
	}

	@Override
	public void createMobile(Mobile mobile) {
		mobiles = createMobileList();
		mobiles.add(mobile);
		writeToFile(mobiles);

	}

	@Override
	public void updateMobile(Mobile mobile) {
		mobiles = createMobileList();
		for (int i = 0; i < mobiles.size(); i++) {
			if(mobiles.get(i).getSorozatszam().equals(mobile.getSorozatszam())) {
				mobiles.remove(i);
				mobiles.add(mobile);
			}
		}
		writeToFile(mobiles);
	}

	@Override
	public void deleteMobile(Mobile mobile) {
		mobiles = createMobileList();
		for (int i = 0; i < mobiles.size(); i++) {
			if(mobiles.get(i).getSorozatszam().equals(mobile.getSorozatszam())) {
				mobiles.remove(i);
			}
		}
		writeToFile(mobiles);
	}

	@Override
	public void deleteMobilebyId(String sorozatszam) throws RosszSorozatSzam {
		String regex = "^[A-Za-z]{4}\\d{4}$";
		if(!sorozatszam.matches(regex)) {
			throw new RosszSorozatSzam(sorozatszam);
		}
		mobiles = createMobileList();
		for (int i = 0; i < mobiles.size(); i++) {
			if(mobiles.get(i).getSorozatszam().equals(sorozatszam)) {
				mobiles.remove(i);
			}
		}
		writeToFile(mobiles);
	}

	@Override
	public void deleteMobileByGyartasiIdo(String gyartasiIdo) throws RosszDatum {
		String regex = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";
		if(!gyartasiIdo.matches(regex)) {
			throw new RosszDatum(gyartasiIdo);
		}
		mobiles = createMobileList();
		for (int i = 0; i < mobiles.size(); i++) {
			if(mobiles.get(i).getGyartasiIdo().equals(LocalDate.parse(gyartasiIdo))) {
				mobiles.remove(i);
			}
		}
		writeToFile(mobiles);
	}

	@Override
	public void changeSuly(Double suly, String sorozatszam) throws RosszSuly, RosszSorozatSzam {
		if(suly <= 0) {
			throw new RosszSuly(suly);
		}
		String regex = "^[A-Za-z]{4}\\d{4}$";
		if(!sorozatszam.matches(regex)) {
			throw new RosszSorozatSzam(sorozatszam);
		}
		mobiles = createMobileList();
		for (int i = 0; i < mobiles.size(); i++) {
			if(mobiles.get(i).getSorozatszam().equals(sorozatszam)) {
				mobiles.get(i).setSuly(suly);;
			}
		}
		writeToFile(mobiles);
	}
	
	public ArrayList<Mobile> createMobileList(){
		 ArrayList<String> list = new ArrayList<>();
		 ArrayList<Mobile> mobileList = new ArrayList<Mobile>();
	     try {
	    	 BufferedReader br = new BufferedReader(new FileReader(file));
	         String word = br.readLine();
	         while(word != null){
	        	 list.add(word);
	             word = br.readLine();
	        }
	        br.close();
	     }
	     catch (Exception e){
	    	return mobileList;
	     }
	     for (int i = 0; i < list.size(); i++) {
			String[] mobileData = list.get(i).split(",");
			mobileList.add(new Mobile( Gyarto.valueOf(mobileData[0]),
					mobileData[1],
					mobileData[2],
					Integer.parseInt(mobileData[3]),
					Boolean.parseBoolean(mobileData[4]),
					LocalDate.parse(mobileData[5]),
					Double.parseDouble(mobileData[6])));

		}
	     return mobileList;
	 }
	
	public void writeToFile(List<Mobile> mobiles) {
		try {
		      FileWriter writer = new FileWriter(file);
		      for (int i = 0; i < mobiles.size(); i++) {
		    	String str = mobiles.get(i).getGyarto()+","+ mobiles.get(i).getModell()+","+
		    			mobiles.get(i).getSorozatszam()+","+ mobiles.get(i).getMéret()+","+
		    			mobiles.get(i).isSerult()+","+ mobiles.get(i).getGyartasiIdo()+","+
		    			mobiles.get(i).getSuly()+",";
				writer.write(str);
			}
		      writer.close();
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
	}

	@Override
	public void deleteAllMobiles() {
		List<Mobile> emptyList = new ArrayList<>();
		writeToFile(emptyList);
	}
}
