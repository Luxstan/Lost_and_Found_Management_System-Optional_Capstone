package ErrorPack;

public class InvalidNumberFormat extends RuntimeException {
    public InvalidNumberFormat() {
        super("Invalid Contact Number Format (09XXXXXXXXX)");
    }
}
