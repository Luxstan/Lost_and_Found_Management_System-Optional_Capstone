package ItemPack;

public class Money extends Item{
    private double amount;
    private boolean inWallet;

    public Money(double amount, boolean inWallet){
        this.amount = amount;
        this.inWallet = inWallet;
    }
    public Money(){}

    //Setters and Getters
    public double getAmount(){
        return amount;
    }

    public boolean getInWallet(){
        return inWallet;
    }

    public void setAmount(double amount){
        this.amount = amount;
    }

    public void setInWallet(boolean inWallet){
        this.inWallet = inWallet;
    }
}
