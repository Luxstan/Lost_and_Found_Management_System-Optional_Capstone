package ItemPack;

public class Document extends Item{
    private String ownerName;
    private String ownerID;
    private String type;

    public Document(String ownerName, String ownerID, String docType){
        this.ownerName = ownerName;
        this.ownerID = ownerID;
        this.type = docType;
    }
    public Document(){}
    //Setters and Getters
    public void setOwnerName(String ownerName){
        this.ownerName = ownerName;
    }

    public void setOwnerID(String ownerID){
        this.ownerID = ownerID;
    }

    public void setDocType(String type){
        this.type = type;
    }

    public String getOwnerName(){
        return ownerName;
    }

    public String getOwnerID(){
        return ownerID;
    }

    public String getDocType(){
        return type;
    }
}
