package ItemPack;

public class Clothing extends ColoredItem implements Wearable{
    private String size;
    private String brand;
    private String material;
    private String type;

    public Clothing(String size, String brand, String material, String type, String color){
        super.color = color;
        this.size = size;
        this.brand = brand;
        this.material = material;
        this.type = type;
    }
    public Clothing(){}
    //Setters and Getters
    public String getSize(){return size;}
    public String getType() {return type;}
    public String getBrand() {return brand;}

    public String getColor(){return color;}
    public String getMaterial(){
        return material;
    }
}
