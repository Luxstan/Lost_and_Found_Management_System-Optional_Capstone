package ItemPack;

public class Item {
    private int itemID;
    private String itemName;
    private String details = "-";
    private String lastLocationSeen;
    private String foundBy;
    private boolean isClaimed;
    private boolean isFound;

    public Item(String itemName){
        this.itemName = itemName;
    }
    public Item(){

    }

    //Methods
    public void claim(){
        isClaimed = true;
    }

    public void found(String name){
        foundBy = name;
        isFound = true;
    }

    //Setters and Getters
    public void setItemID(int itemID){
        this.itemID = itemID;
    }


    public int getItemID(){
        return itemID;
    }
    public String getItemName() {
        return itemName;
    }

    public String getDetails() {
        return details;
    }

    public String getLastLocationSeen() {
        return lastLocationSeen;
    }

    public String getFoundBy() {
        return foundBy;
    }

    public boolean isClaimed() {
        return isClaimed;
    }

    public boolean isFound() {
        return isFound;
    }
}
