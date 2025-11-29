package ItemPack;

public class Money extends Item{
    private double amount;
    private boolean inWallet;

    public Money(double amount, boolean inWallet){
        super("Money");
        this.amount = amount;
        this.inWallet = inWallet;
    }

    //Setters and Getters
    public double getAmount(){
        return amount;
    }

    public boolean getInWallet(){
        return inWallet;
    }
}
