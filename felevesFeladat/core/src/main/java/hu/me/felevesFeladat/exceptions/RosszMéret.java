package hu.me.felevesFeladat.exceptions;

public class RosszMéret extends Exception {
	public RosszMéret(int magok) {
		super(String.valueOf(magok));
	}
}
