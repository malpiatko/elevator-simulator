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
	private volatile int currentDirection;
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
	 * If elevator idle wake up and go to the right direction,
	 * otherwise add to queue of requests.
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

	/*
	 * Each iteration consists of checking if arrived
	 * at destination, switching directions if neccessary
	 * and moving one floor in the correct direction.
	 */
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
	
	/**
	 * Creates the thread for the elevator and starts it
	 */
	public void switchOn() {
		if(elevatorThread == null) {
			elevatorThread = new Thread(this);
		}
		switchedOn = true;
		elevatorThread.start();
	}
	
	/**
	 * Stops the elevator thread from running
	 */
	public void switchOff() {
		switchedOn = false;
	}
	
	/*
	 * Move one floor in the current direction
	 */
	private void move(int direction) {
		currentFloor += direction;
		sleep(FLOOR_TIME);
	}
	
	/*
	 * Checks if arrived at a destination and performs
	 * neccessary actions
	 */
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
	
	/*
	 * Changes the direction if no more requests
	 * in the current direction
	 */
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
			//No thread is expected to interrupt,
			//ignore if it happens
			e.printStackTrace();
		}	
	}
	
	public synchronized boolean isDoorOpen() {
		return doorOpen;
	}
	
	/**
	 * Returns the current floor for debugging purposes
	 */
	private synchronized int openDoor() {
		this.doorOpen = true;
		return currentFloor;
	}
	
	/**
	 * Returns the current floor for debugging purposes
	 */
	private synchronized int closeDoor() {
		this.doorOpen = false;
		return currentFloor;
	}
	
	/**
	 * Returns true if elevator is at the floor
	 * passed as argument and the floor is it's destination
	 */
	public synchronized boolean checkElevator(int floor) {
		return currentFloor() == floor && isDoorOpen();
	}
	
	/**
	 * Returns the number of floors between the floor given
	 * as argument and current floor, the value is negative
	 * if the lift is going in the opposite direction.
	 */
	public synchronized int diff(int floor) {
		return (floor - currentFloor())*currentDirection;
	}

}
