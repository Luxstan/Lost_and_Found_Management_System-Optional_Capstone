package ItemPack;

public class Tumbler extends ColoredItem{
    private String size;
    public Tumbler(){}
    public Tumbler(String size){
        this.size = size;
    }

    @Override
    public String getColor() {
        return super.color;
    }

    public String getSize(){
        return size;
    }
}
