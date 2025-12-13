package ErrorPack;

public class UsernameNotFound extends RuntimeException {
    public UsernameNotFound() {
        super("Username not found");
    }
}
