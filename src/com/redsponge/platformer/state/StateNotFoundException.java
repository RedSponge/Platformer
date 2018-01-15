package com.redsponge.platformer.state;

public class StateNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9033801267472881705L;

	public StateNotFoundException() {
		super("State couldn\'t ne fetched!");
	}
	
	public StateNotFoundException(String state) {
		super("State \"" + state +  "\" couldn\'t ne fetched!");
	}
	
}
