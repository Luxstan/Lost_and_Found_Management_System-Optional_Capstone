package ErrorPack;

public class AlreadyExists extends RuntimeException {

    public AlreadyExists(String fieldName, String input) {
        super(fieldName + " " + input +  " already exists.");
    }
}
