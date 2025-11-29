import ItemPack.Item;

public class User {
    private final String name;
    private final String id;
    private String contactNo;
    private String course;
    private int year;
    private Item[] lostList;
    private Item[] foundList;

    public User(String name, String id, String contactNo, String course, int year){
        this.name = name;
        this.id = id;
        this.contactNo = contactNo;
        this.course = course;
        this.year = year;
    }

    //Methods
    public Item reportItemLost(){
        return null;
    }

    public Item reportItemFound(){
        return null;
    }

    //Getter and Setters
    public String getName(){
        return name;
    }

    public String getId() {
        return id;
    }

    public String getContactNo() {
        return contactNo;
    }

    public String getCourse() {
        return course;
    }

    public int getYear() {
        return year;
    }

    public void setContactNo(String contactNo){
        this.contactNo = contactNo;
    }

    public void setCourse(String course){
        this.course = course;
    }

    public void setYear(int year){
        this.year = year;
    }


}
