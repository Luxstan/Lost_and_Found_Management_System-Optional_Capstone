package ItemPack;

public class Accessory extends ColoredItem implements Wearable{
    private String accessoryMaterial;
    private String accessoryType;

    public String getMaterial(){
        return accessoryMaterial;
    }

}
