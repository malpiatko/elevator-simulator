package com.locusenergy.homework;

/**
 * Represents a request for an elevator
 * @author daffodil
 *
 */
public class Person implements Runnable {
	
	String name;
	int from;
	int to;
	Building b;
	
	/*
	 * TODO: what if from and to is the same
	 */
	Person(Building b, String name, int from, int to){
		this.name = name;
		this.from = from;
		this.to = to;
		this.b = b;
	}
	
	/*
	 * TODO: monitor when a person gets off
	 * Makes a requests for an elevator, waits for the
	 * elevator to arrive and requests a floor to go to.
	 */
	@Override
	public void run() {
		int direction = from > to ? -1 : 1;
		try {
			System.out.println(name + " calling lift at level " + from);
			Elevator e = b.callElevator(from, direction);
			e.requestFloor(to);
		} catch (InvalidRequestException e) {
			e.printStackTrace();
		}
	}

}
