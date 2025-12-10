package ErrorPack;

public class EmptyField extends RuntimeException {

    public EmptyField(String fieldName) {
        super(fieldName + " field cannot be empty.");
    }
}
