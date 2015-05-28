package com.locusenergy.homework;

import java.util.ArrayList;
import java.util.List;

/*
 * Note: nFloors means that the last floor of the building is (nFloors - 1)
 */
public class Building implements ElevatorController{
	
	List<Elevator> elevators;
	final int nFloors;
	final int nElev;
	
	Building(int nElev, int nFloors) {
		this.nFloors = nFloors;
		this.nElev = nElev;
		createElevators();
	}

	@Override
	public Elevator callElevator(int fromFloor, int direction)
			throws InvalidRequestException {
		return null;
	}
	
	private void createElevators() {
		this.elevators = new ArrayList<Elevator>();
		for(int i = 0; i <= nElev; i++) {
			elevators.add(new ElevatorImp(i+1, nFloors));
		}
	}
	
	
}
