public interface ElevatorSimulator {
    Elevator callElevator(int fromFloor);
    void releaseElevator(Elevator elev);
}
