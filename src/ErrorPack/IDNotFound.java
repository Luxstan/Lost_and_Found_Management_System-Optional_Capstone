package ErrorPack;

public class IDNotFound extends RuntimeException {
    public IDNotFound() {
        super("ID not found");
    }
}
