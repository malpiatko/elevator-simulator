package com.locusenergy.homework;

import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListSet;

public class ElevatorImp implements Elevator, Runnable {
	
	private static final int FLOOR_TIME = 100;
	private static final int OPEN_DOOR = 500;
	
	private static final int MOVING_UP = 1;
	private static final int MOVING_DOWN = -1;
	private static final int IDLE = 0;
	private final int id;
	private final int maxFloor;
	private final int minFloor = 0;
	
	private int currentFloor;
	private int currentDirection;
	private boolean switchedOn;
	private boolean busy;
	Thread elevatorThread;
	private NavigableSet<Integer> calls = new ConcurrentSkipListSet<Integer>();

	/**
	 * All lifts are created at the ground floor.
	 * Each elevator has a unique (per Controller) id
	 */
	public ElevatorImp(int id, int nFloors) {
		this.id = id;
		this.maxFloor = nFloors;
		this.currentFloor = 0;
		this.currentDirection = IDLE;
		this.busy = false;
	}

	@Override
	public synchronized void requestFloor(int floor) {
		calls.add(floor);
	}

	@Override
	public boolean isBusy() {
		return (currentDirection == IDLE);
	}

	@Override
	public int currentFloor() {
		return currentFloor;
	}

	@Override
	public void run() {
		while(switchedOn) {
			if(currentDirection != 0) {
				move(currentDirection);
				checkArrived();
				checkDirections();
			} else {
				sleep(FLOOR_TIME);
			}
				
		}
	}
	
	public int getID() {
		return id;
	}
	
	public void switchOn() {
		if(elevatorThread == null) {
			elevatorThread = new Thread();
		}
		switchedOn = true;
		elevatorThread.start();
	}
	
	public void switchOff() {
		switchedOn = false;
	}
	
	private void move(int direction) {
		currentFloor += direction;
		sleep(FLOOR_TIME);
	}
	
	private void checkArrived() {
		if(calls.remove(currentFloor)) {
			sleep(OPEN_DOOR);
			if(calls.isEmpty()) {
				currentDirection = IDLE;
			}
		}
	}
	
	private void checkDirections() {
		if(currentDirection == MOVING_UP) {
			if(calls.higher(currentFloor) == null) {
				currentDirection = MOVING_DOWN;
			}
		} else {
			if(calls.lower(currentFloor) == null) {
				currentDirection = MOVING_UP;
			}
		}
	}
	
	private void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}	
	}

}
