import ItemPack.Item;

public class User {
    private String name;
    private final String id;
    private String contactNo;
    private String course;
    private int year;
    private Item[] lostList;
    private Item[] foundList;

    public User(String id){
        this.id = id;
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


}
