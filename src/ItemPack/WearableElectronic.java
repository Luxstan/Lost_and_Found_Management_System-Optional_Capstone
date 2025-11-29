package ItemPack;

public class WearableElectronic extends Electronic implements Wearable{
    public String getMaterial(){
        return super.getElectronicMaterial();
    }
}
