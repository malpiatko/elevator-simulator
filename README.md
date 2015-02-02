# Locus Energy Coding Challenge

This is a very basic scaffold project for you to work in for the elevator simulator assignment.

## Instructions

Please clone the repository and deliver your solution via email in an archive format of your choice, including all project files, within 1 calendar week.  _Do not submit your solution via pull request to this repository._

Write an elevator simulator class that implements the following interfaces (they are also located in the src directory of this repo):

    public interface ElevatorSimulator {
        Elevator callElevator(int fromFloor);
        void releaseElevator(Elevator elev);
    }
    
    public interface Elevator {
        void moveElevator(int toFloor);
        boolean isBusy();
        int currentFloor();
    }

While we know there are many college project implementations of elevator simulators, this assignment allows for a variety of solutions to a real-world problem.  Your solution will be reviewed by the engineers you would be working with if you joined Locus Energy.  We are interested in seeing your real-world design, coding, and testing skills.

## Using this scaffold

This scaffold is provided to help you (and us) build your homework code. 
We've included a `pom.xml`, which is a file used by [maven][maven] to build the project and run other commands.   It also contains information on downloading dependent jars needed by your project.  This one contains JUnit, EasyMock and Log4J already, but feel free to change it as you see fit.

    mvn compile      # compiles your code in src/main/java
    mvn test-compile # compile test code in src/test/java
    mvn test         # run tests in src/test/java for files named Test*.java


[maven]:http://maven.apache.org/


