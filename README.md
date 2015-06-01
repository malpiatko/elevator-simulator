# Elevator simulator

This is a multi-threaded elevator simulator.

## Running

Project can be easily run as a Java Application with the main class in com.locusenergy.homework.Day
The input can be either from the console or from a text file. An example input file can be found
in input.txt. The input format is as follows:

	<elevator number>
	<floors number>
	<request_id> <from_floor> <to_floor>
	...
	done

## Bugs and extenstions

There are currently number of features that can be improved
1.	Taking direction into account when requesting a lift.
2.	Being able to "refuse" a lift if it's going into the wrong direction
3.	Recording when a Person (request) is finished
4.	Alerting when too many people are in a lift
5.	Sending one lift to the ground floor if all lifts idle.
