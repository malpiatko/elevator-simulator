package com.locusenergy.homework;

import java.util.ArrayList;
import java.util.List;

/*
 * Note: nFloors means that the last floor of the building is nFloors,
 * therefore there are actually (nFloors + 1) with ground floor
 */
public class Building implements ElevatorController{
	
	List<Elevator> elevators;
	final int minFloor = 0;
	final int maxFloor;
	final int nElev;
	
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
		this.elevators = new ArrayList<Elevator>();
		for(int i = 0; i <= nElev; i++) {
			elevators.add(new ElevatorImp(i+1, maxFloor));
		}
	}
	
}
