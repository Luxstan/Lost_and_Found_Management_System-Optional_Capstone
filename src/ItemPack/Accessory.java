package ItemPack;

public class Accessory extends ColoredItem implements Wearable{
    private String accessoryMaterial;
    private String accessoryType;

    //Setters and Getters
    public String getAccessoryMaterial(){return accessoryMaterial;}
    public String getAccessoryType(){return accessoryType;}

    public String getColor(){return color;}
    public String getMaterial(){
        return accessoryMaterial;
    }


}
