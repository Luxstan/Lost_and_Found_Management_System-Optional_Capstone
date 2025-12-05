package ItemPack;

public class Accessory extends ColoredItem implements Wearable{
    private String material;
    private String type;

    public Accessory(String material, String type, String color){
        super.color = color;
        this.material = material;
        this.type = type;
    }

    //Setters and Getters
    public String getColor(){return color;}
    public String getMaterial(){
        return material;
    }
    public String getType(){return type;}
}
