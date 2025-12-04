package ItemPack;

public class Document extends Item{
    private String ownerName;
    private String ownerID;
    private String docType;

    public Document(String ownerName, String ownerID, String docType){
        super("Document");
        this.ownerName = ownerName;
        this.ownerID = ownerID;
        this.docType = docType;
    }

    //Setters and Getters
    public void setOwnerName(String ownerName){
        this.ownerName = ownerName;
    }

    public void setOwnerID(String ownerID){
        this.ownerID = ownerID;
    }

    public void setDocType(String docType){
        this.docType = docType;
    }

    public String getOwnerName(){
        return ownerName;
    }

    public String getOwnerID(){
        return ownerID;
    }

    public String getDocType(){
        return docType;
    }
}
