package com.locusenergy.homework;

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
	 * I do not monitor when a person gets off
	 */
	@Override
	public void run() {
		int direction = from > to ? -1 : 1;
		System.out.println(direction);
		try {
			System.out.println(name + " calling lift at level " + from);
			Elevator e = b.callElevator(from, direction);
			System.out.println(name + " going to " + to);
			e.requestFloor(to);
		} catch (InvalidRequestException e) {
			e.printStackTrace();
		}
	}

}
