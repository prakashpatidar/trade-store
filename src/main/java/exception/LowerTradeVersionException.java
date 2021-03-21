package exception;

public class LowerTradeVersionException extends Exception{
    public LowerTradeVersionException(String errorMessage){
        super(errorMessage);
    }
}
