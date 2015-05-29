package com.locusenergy.homework;

import java.util.ArrayList;
import java.util.List;

/*
 * Note: nFloors means that the last floor of the building is nFloors,
 * therefore there are actually (nFloors + 1) with ground floor
 */
public class Building implements ElevatorController, Runnable {
	
	List<ElevatorImp> elevators;
	final int minFloor = 0;
	final int maxFloor;
	final int nElev;
	
	Thread buldingThread;
	private volatile boolean daytime;
	
	
	
	Building(int nElev, int nFloors) {
		this.maxFloor = nFloors;
		this.nElev = nElev;
		createElevators();
	}

	@Override
	public Elevator callElevator(int fromFloor, int direction)
			throws InvalidRequestException {
		if (fromFloor > maxFloor || fromFloor < minFloor)
			throw new InvalidRequestException();
		if (fromFloor == maxFloor && direction > 0
				|| fromFloor == minFloor && direction <=0)
			throw new InvalidRequestException();
		return null;
	}
	
	private void createElevators() {
		this.elevators = new ArrayList<ElevatorImp>();
		for(int i = 0; i <= nElev; i++) {
			elevators.add(new ElevatorImp(i+1, maxFloor));
		}
	}
	
	public void switchOnElevators() {
		for(ElevatorImp e: elevators) {
			e.switchOn();
		}
	}
	
	public void switchOffElevators() {
		for(ElevatorImp e: elevators) {
			e.switchOff();
		}
	}
	
	public void startDay() {
		if(buldingThread == null) {
			buldingThread = new Thread();
		}
		daytime = true;
		buldingThread.start();
	}
	
	public static void main(String[] args) {
		Building building = new Building(1, 100);
		building.startDay();
	}

	@Override
	public void run() {
		switchOnElevators();
		while(daytime) {
			
		}
		switchOffElevators();
		// TODO Auto-generated method stub
		
	}
	
}