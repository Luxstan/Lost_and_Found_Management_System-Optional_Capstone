package ItemPack;

public class Clothing extends ColoredItem implements Wearable{
    private String size;
    private String clothingType;
    private String clothingMaterial;
    private String clothingBrand;

    //Setters and Getters
    public String getSize(){return size;}

    public String getClothingType() {return clothingType;}

    public String getClothingMaterial() {return clothingMaterial;}

    public String getClothingBrand() {return clothingBrand;}

    public String getColor(){return color;}
    public String getMaterial(){
        return clothingMaterial;
    }
}
