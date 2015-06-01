package com.locusenergy.homework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Day {
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		//Choosing input type
		InputStreamReader input;
		if(args.length == 1) {
			input = new InputStreamReader(new FileInputStream(args[0]));
		} else {
			input = new InputStreamReader(System.in);
		}
		
		//Preparing building
		BufferedReader br = new BufferedReader(input);
		System.out.print("Enter elevator number: ");
		int elevators = Integer.parseInt(br.readLine());
		System.out.print("Enter floors number: ");
		int floors = Integer.parseInt(br.readLine());
		Building building = new Building(elevators, floors);
		building.switchOnElevators();
		//Input requests for lifts
		System.out.println("Enter lift requests in seperate line, finishing with 'done'."
				+ "\nFormat: name from to");
		String line;
		while(!(line = br.readLine()).equals("done")){
			String[] request = line.split(" ");
			if(request.length != 3) {
				System.out.println("Bad format: name from to");
			}
			String name = request[0];
			int from = Integer.parseInt(request[1]);
			int to = Integer.parseInt(request[2]);
			Person p = new Person(building, name, from, to);
			new Thread(p).start();
		}
		//building.switchOffElevators();
	}
}