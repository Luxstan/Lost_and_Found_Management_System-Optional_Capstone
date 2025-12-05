package ItemPack;

public class Electronic extends ColoredItem{
    private String model;
    private String brand;
    private String material;
    private String type;

    public Electronic(String model, String brand, String material, String type, String color){
        super.color = color;
        this.model = model;
        this.brand = brand;
        this.material = material;
        this.type = type;
    }

    public String getBrand(){
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getMaterial() {
        return material;
    }

    public String getType() {
        return type;
    }

    public String getColor(){return color;}
}
