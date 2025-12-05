package ItemPack;

public class FoodContainer extends ColoredItem{
    private String capacity;
    private String brand;
    private String type;


    public FoodContainer(String capacity, String brand, String type, String color){
        super.color = color;
        this.capacity = capacity;
        this.brand = brand;
        this.type = type;
    }
    //Setters and Getters
    public String getType(){return type;}
    public String getBrand(){return brand;}
    public String getCapacity(){return capacity;}
    public String getColor(){return color;}

}
