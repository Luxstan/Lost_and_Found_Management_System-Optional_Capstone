import ItemPack.*;

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

    public void displayList(String option){
        if(option.equals("LOST")){
            for(Item a : lostList){
                printItemDetails(a);
                System.out.println();
            }
        } else if (option.equals("FOUND")) {
            for(Item a : foundList){
                printItemDetails(a);
                System.out.println();
            }
        }
    }

    private void printItemDetails(Item item){
        System.out.println(item.getItemID() + " : " + item.getItemName());
        System.out.println("Description: " + item.getDetails());
        System.out.println("Last Location Seen: " + item.getLastLocationSeen());
        if(item instanceof Money){
            System.out.println("Amount: " + ((Money) item).getAmount());
            System.out.println("In Wallet? : " + ((Money) item).getInWallet());
        }
        else if (item instanceof Document) {
            System.out.println("Owner's Name: " + ((Document) item).getOwnerName());
            System.out.println("Owner's ID: " + ((Document) item).getOwnerID());
            System.out.println("Document Type: " + ((Document) item).getDocType());
        }
        else if (item instanceof Accessory) {
            System.out.println("Material: " + ((Accessory) item).getMaterial());
            System.out.println("Type: " + ((Accessory) item).getType());
            System.out.println("Color: " + ((Accessory) item).getColor());
        }
        else if (item instanceof Bag) {
            System.out.println("Brand: " + ((Bag) item).getBrand());
            System.out.println("Material: " + ((Bag) item).getMaterial());
            System.out.println("Type: " + ((Bag) item).getType());
            System.out.println("Color: " + ((Bag) item).getColor());
        }
        else if (item instanceof Clothing) {
            System.out.println("Size: " + ((Clothing) item).getSize());
            System.out.println("Brand: " + ((Clothing) item).getBrand());
            System.out.println("Material: " + ((Clothing) item).getMaterial());
            System.out.println("Type: " + ((Clothing) item).getType());
            System.out.println("Color: " + ((Clothing) item).getColor());
        }
        else if (item instanceof Electronic) {
            System.out.println("Model: " + ((Electronic) item).getModel());
            System.out.println("Brand: " + ((Electronic) item).getBrand());
            System.out.println("Material: " + ((Electronic) item).getMaterial());
            System.out.println("Type: " + ((Electronic) item).getType());
            if(item instanceof WearableElectronic){
                System.out.println("Electronic is a wearable type");
            }
            System.out.println("Color: " + ((Electronic) item).getColor());

        }
        else if (item instanceof FoodContainer) {
            System.out.println("Capacity: " + ((FoodContainer) item).getCapacity());
            System.out.println("Brand: " + ((FoodContainer) item).getBrand());
            System.out.println("Type: " + ((FoodContainer) item).getType());
            System.out.println("Color: " + ((FoodContainer) item).getColor());
        }
        else if (item instanceof Miscellaneous) {
            System.out.println("Item is possibly: " + ((Miscellaneous) item).getItemCategory());
        }
        else{
            System.out.println("INVALID ITEM TYPE INSIDE THE LIST!");
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
