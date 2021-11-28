package hu.me.felevesFeladat.exceptions;

public class RosszSuly extends Exception {
	
	public RosszSuly(Double Suly) {
		super(String.valueOf(Suly));
	}
}
