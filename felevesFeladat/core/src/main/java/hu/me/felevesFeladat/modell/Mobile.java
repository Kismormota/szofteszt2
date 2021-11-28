package hu.me.felevesFeladat.modell;

import java.time.LocalDate;

import hu.me.felevesFeladat.enums.Gyarto;
import hu.me.felevesFeladat.exceptions.RosszSuly;
import hu.me.felevesFeladat.exceptions.RosszDatum;
import hu.me.felevesFeladat.exceptions.RosszSorozatSzam;
import hu.me.felevesFeladat.exceptions.RosszMéret;

public class Mobile {

	
	protected Gyarto gyarto;
	protected String modell;
	protected String sorozatszam;
	protected int méret;
	protected boolean serult;
	protected LocalDate gyartasiIdo;
	protected Double suly;

	

	public Gyarto getGyarto() {
		return gyarto;
	}
	public void setGyarto(Gyarto gyarto) {
		this.gyarto = gyarto;
	}
	public String getModell() {
		return modell;
	}
	public void setModell(String modell) {
		this.modell = modell;
	}

	public void setMéret(int méret) throws RosszMéret {
		if(méret<1 || méret>16) {
			throw new RosszMéret(méret);
		}
		this.méret = méret;
	}

	public int getMéret() {
		return méret;
	}

	public boolean isSerult() {
		return serult;
	}

	public void setSerult(boolean serult) {
		this.serult = serult;
	}

	public LocalDate getGyartasiIdo() {
		return gyartasiIdo;
	}

	public void setGyartasiIdo(String gyartasiIdo) throws RosszDatum {
		///yyyy-mm-dd
		String ido = String.valueOf(gyartasiIdo);
		String regex = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";
		if(!ido.matches(regex)) {
			throw new RosszDatum(gyartasiIdo);
		}
		this.gyartasiIdo = LocalDate.parse(gyartasiIdo);
	}
	public Double getSuly() {
		return suly;
	}
	public void setSuly(Double suly) throws RosszSuly {
		if(suly <= 0) {
			throw new RosszSuly(suly);
		}
		this.suly = suly;
	}

	public String getSorozatszam() {
		return sorozatszam;
	}

	public void setSorozatszam(String sorozatszam) throws RosszSorozatSzam {
		//AAAA1234
		String regex = "^[A-Za-z]{4}\\d{4}$";
		if(!sorozatszam.matches(regex)) {
			throw new RosszSorozatSzam(sorozatszam);
		}
		this.sorozatszam = sorozatszam;
	}

	public Mobile(Gyarto gyarto, String modell, String sorozatszam, int méret, boolean serult, LocalDate gyartasiIdo, Double suly) {
		this.gyarto = gyarto;
		this.modell = modell;
		this.sorozatszam = sorozatszam;
		this.méret = méret;
		this.serult = serult;
		this.gyartasiIdo = gyartasiIdo;
		this.suly = suly;
	}

	public Mobile() {
		
	}
}
