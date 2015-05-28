package com.locusenergy.homework;

public class ElevatorImp implements Elevator {
	
	private final int id;
	private final int nFloors;
	private int currentFloor;

	/*
	 * All lifts are created at the ground floor.
	 * Each elevator has a unique (per Controller) id
	 */
	public ElevatorImp(int id, int nFloors) {
		this.id = id;
		this.nFloors = nFloors;
		this.currentFloor = 0;
	}

	@Override
	public void requestFloor(int floor) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isBusy() {
		return false;
	}

	@Override
	public int currentFloor() {
		return 0;
	}

}
