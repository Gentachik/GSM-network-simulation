public class StationNotFoundException extends Exception {
    public StationNotFoundException() {
        super("Station wasn't found");
    }
}
