/**
 *  Pandora interview design question: design an elevator
 */
public class ElevatorService {

    public static class ElevatorFrontEndService {

        interface IElevatorCaller {
            void callUp();
            void callDown();
        }

        interface IElevatorDriverPanel {
            void gotoFloor(int inFloorNumber);
        }

    }

    public static class ElevatorBackEndService {

        interface IElevatorCaller {
            void callUp(int inFloor);
            void callDown(int inFloor);
        }

        interface IElevatorDriverPanel {
            void gotoFloor(int inElevatorId, int inFloorNumber);
        }

        interface IElevatorStatusUpdate {

        }

    }

}
