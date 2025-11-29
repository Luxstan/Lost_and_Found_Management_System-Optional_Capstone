package ItemPack;

public class Bag extends ColoredItem implements Wearable{
    private String bagBrand;
    private String bagType;
    private String bagMaterial;

    //Setters and Getters
    public String getBagBrand(){return bagBrand;}
    public String getBagType(){return bagType;}
    public String getBagMaterial(){return bagMaterial;}

    public String getColor(){return color;}
    public String getMaterial(){
        return bagMaterial;
    }

}
