package ErrorPack;

public class InvalidIDFormat extends RuntimeException {
    public InvalidIDFormat() {
        super("Invalid ID Format (XX-XXXX-XXX)");
    }
}
