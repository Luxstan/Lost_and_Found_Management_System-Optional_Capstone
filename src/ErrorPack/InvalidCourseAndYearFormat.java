package ErrorPack;

public class InvalidCourseAndYearFormat extends RuntimeException {
    public InvalidCourseAndYearFormat() {
        super("Invalid Course and Year Format (ex. BSCE-1, BSN-2, BSCpE-3)");
    }
}
