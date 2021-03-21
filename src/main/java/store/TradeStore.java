package store;

import exception.LowerTradeVersionException;
import exception.MaturityDateExpiredException;
import model.TradeInfo;

public interface TradeStore {
    void processTrade(TradeInfo tradeInfo) throws LowerTradeVersionException, MaturityDateExpiredException;
    TradeInfo getTradeById(String tradeId);
    void autoExpireTrades();
}
