package ErrorPack;

public class IncorrectPassword extends RuntimeException {
    public IncorrectPassword() {
        super("Wrong Password");
    }
}
