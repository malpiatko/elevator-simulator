package com.locusenergy.homework;

import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeUnit;

/*
 * TODO: Bugs: not taking into account direction
 */
public class ElevatorImp implements Elevator, Runnable {
	
	private static final int FLOOR_TIME = 2;
	private static final int OPEN_DOOR = 4;
	
	private static final int MOVING_UP = 1;
	private static final int MOVING_DOWN = -1;
	private static final int IDLE = 0;
	private final int id;
	
	private volatile int currentFloor;
	volatile int currentDirection;
	private volatile boolean switchedOn;
	Thread elevatorThread;
	private NavigableSet<Integer> calls = new ConcurrentSkipListSet<Integer>();
	private boolean doorOpen;

	/**
	 * All lifts are created at the ground floor.
	 * Each elevator has a unique (per Controller) id
	 */
	public ElevatorImp(int id, int nFloors) {
		this.id = id;
		this.currentFloor = 0;
		this.currentDirection = IDLE;
		this.doorOpen = false;
	}

	/*
	 * TODO: Have a separate function for summoning lift
	 */
	@Override
	public synchronized void requestFloor(int floor) {
		if (calls.isEmpty()) {
			if(floor > currentFloor){
				currentDirection = MOVING_UP;
			} else if(floor < currentDirection){
				currentDirection = MOVING_DOWN;
			}
			calls.add(floor);
			elevatorThread.interrupt();
		} else {
			calls.add(floor);
		}
		
	}

	@Override
	public synchronized boolean isBusy() {
		return currentDirection != IDLE;
	}

	@Override
	public int currentFloor() {
		return currentFloor;
	}

	@Override
	public void run() {
		while(switchedOn) {
			checkArrived();
			checkDirections();
			if(currentDirection != IDLE) {
				move(currentDirection);
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
			elevatorThread = new Thread(this);
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
			System.out.println("Elevator " + id + " arrived at "
					+ currentFloor);
			openDoor();
			sleep(OPEN_DOOR);
			closeDoor();
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
		} else if(currentDirection == MOVING_DOWN){
			if(calls.lower(currentFloor) == null) {
				currentDirection = MOVING_UP;
			}
		}
	}
	
	private void sleep(int time) {
		try {
			TimeUnit.SECONDS.sleep(time);
		} catch (InterruptedException e) {
		}	
	}
	
	public synchronized boolean isDoorOpen() {
		return doorOpen;
	}
	
	private synchronized void openDoor() {
		this.doorOpen = true;
	}
	
	private synchronized void closeDoor() {
		this.doorOpen = false;
	}
	
	public synchronized boolean checkElevator(int floor) {
		return currentFloor() == floor && isDoorOpen();
	}
	
	public synchronized int diff(int floor) {
		return (floor - currentFloor())*currentDirection;
	}

}
