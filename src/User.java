import ItemPack.Item;
import ItemPack.Money;
import ItemPack.Wearable;

import java.util.ArrayList;

public class User {
    private final String name;
    private final String id;
    private String password;
    private String contactNo;
    private String course;
    private int year;
    private ArrayList<Item> lostList;
    private ArrayList<Item> foundList;

    public User(String name, String id, String password, String contactNo, String course, int year){
        this.name = name;
        this.id = id;
        this.password = password;
        this.contactNo = contactNo;
        this.course = course;
        this.year = year;
        lostList = new ArrayList<>();
        foundList = new ArrayList<>();
    }

    //Methods
    public void addLostItem(Item a){
        lostList.add(a);
    }

    public void addFoundItem(Item a){
        foundList.add(a);
    }

    public void removeLostItem(String item_id){ //This was auto made by intellij btw
        lostList.removeIf(a -> a.getItemName().equalsIgnoreCase(item_id));
    }

    public void removeFoundItem(String item_id){
        foundList.removeIf(a -> a.getItemName().equalsIgnoreCase(item_id));
    }

    public void displayLostList(){
        for(Item a : lostList){
            System.out.println(a.getItemID() + " : " + a.getItemName());
            if(a instanceof Money){
                System.out.println("Amount: " + ((Money) a).getAmount());
                System.out.println("In Wallet? : " + ((Money) a).getInWallet());
            }
        }
    }


    //Getter and Setters
    public String getName(){
        return name;
    }

    public String getId() {
        return id;
    }
    public String getPassword(){
        return password;
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

    public void setPassword(String password){
        this.password = password;
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
