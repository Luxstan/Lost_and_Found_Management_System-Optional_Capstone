package ItemPack;

public class Miscellaneous extends Item{
    private String itemCategory;

    public Miscellaneous(String itemCategory){
        this.itemCategory = itemCategory;
    }
    public Miscellaneous(){}
    //Setters and Getters
    public String getItemCategory(){
        return itemCategory;
    }
}
