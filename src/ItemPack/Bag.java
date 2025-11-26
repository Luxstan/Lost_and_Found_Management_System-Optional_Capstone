package ItemPack;

public class Bag extends ColoredItem implements Wearable{
    private String bagBrand;
    private String bagType;
    private String bagMaterial;

    public String getMaterial(){
        return bagMaterial;
    }

}
