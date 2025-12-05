package ItemPack;

public class WearableElectronic extends Electronic implements Wearable{

    public WearableElectronic(String model, String brand, String material, String type, String color){
        super(model, brand, material, type, color);
    }
    public String getMaterial(){
        return super.getMaterial();
    }
}
