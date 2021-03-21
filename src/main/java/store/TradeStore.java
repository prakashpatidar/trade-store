package store;

import exception.LowerTradeVersionException;
import exception.MaturityDateExpiredException;
import model.TradeInfo;

import java.util.Optional;

public interface TradeStore {
    void processTrade(TradeInfo tradeInfo) throws LowerTradeVersionException, MaturityDateExpiredException;
    Optional<TradeInfo> getTradeById(String tradeId);
}
