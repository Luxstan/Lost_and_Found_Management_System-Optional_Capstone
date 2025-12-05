package ItemPack;

public class Bag extends ColoredItem implements Wearable{
    private String brand;
    private String material;
    private String type;

    public Bag(String brand, String material, String type, String color){
        super.color = color;
        this.brand = brand;
        this.material = material;
        this.type = type;
    }
    //Setters and Getters
    public String getBrand(){return brand;}
    public String getType(){return type;}

    public String getColor(){return color;}
    public String getMaterial(){
        return material;
    }
}
