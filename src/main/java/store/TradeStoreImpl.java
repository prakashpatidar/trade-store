package store;

import exception.LowerTradeVersionException;
import exception.MaturityDateExpiredException;
import model.TradeInfo;
import org.springframework.scheduling.annotation.Scheduled;

public class TradeStoreImpl implements TradeStore{
    @Override
    public void processTrade(TradeInfo tradeInfo) throws LowerTradeVersionException, MaturityDateExpiredException {

    }

    @Override
    public TradeInfo getTradeById(String tradeId) {
        return null;
    }

    @Scheduled(cron = "0 0 0,1 * * *")
    public void autoExpireTrades(){

    }
}
