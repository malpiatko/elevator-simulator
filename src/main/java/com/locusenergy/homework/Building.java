package com.locusenergy.homework;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/*
 * Note: nFloors means that the last floor of the building is nFloors,
 * therefore there are actually (nFloors + 1) with ground floor
 */
public class Building implements ElevatorController {
	
	List<ElevatorImp> elevators;
	final int minFloor = 0;
	final int maxFloor;
	final int nElev;
	
	int rotationLift = 0;
	
	
	Building(int nElev, int nFloors) {
		this.maxFloor = nFloors;
		this.nElev = nElev;
		createElevators();
	}

	/*
	 * TODO: Take into account the direction of the lift when calling
	 */
	@Override
	public Elevator callElevator(int fromFloor, int direction)
			throws InvalidRequestException {
		if (fromFloor > maxFloor || fromFloor < minFloor)
			throw new InvalidRequestException();
		if (fromFloor == maxFloor && direction > 0
				|| fromFloor == minFloor && direction <=0)
			throw new InvalidRequestException();
		ElevatorImp e = getBestElevator(fromFloor, direction);
		e.requestFloor(fromFloor);
		while(!e.checkElevator(fromFloor));
		return e;
	}
	
	private synchronized ElevatorImp getBestElevator(int fromFloor, int direction) {
		int minDiff = maxFloor - minFloor;
		ElevatorImp eMin = null;
		for(ElevatorImp e: elevators) {
			if(!e.isBusy()) {
				return e;
			}
			int diff = e.diff(fromFloor);
			if(diff < minDiff && diff >= 0) {
				minDiff = diff;
				eMin = e;
			}
		}
		if (eMin != null) return eMin;
		rotationLift = (rotationLift + 1) % nElev;
		return elevators.get(rotationLift);
	}
	
	private void createElevators() {
		this.elevators = new ArrayList<ElevatorImp>();
		for(int i = 0; i < nElev; i++) {
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
	
}
