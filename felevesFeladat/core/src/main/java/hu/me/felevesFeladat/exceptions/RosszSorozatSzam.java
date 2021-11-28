package hu.me.felevesFeladat.exceptions;

import java.time.LocalDate;

public class RosszSorozatSzam extends Exception {
	
public RosszSorozatSzam(String sorozatszam) {
		super(sorozatszam);
	}

public RosszSorozatSzam() {
	
}
}
