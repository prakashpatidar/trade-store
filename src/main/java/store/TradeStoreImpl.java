package store;

import db.InMemoryTradeDB;
import db.TradeDb;
import exception.LowerTradeVersionException;
import exception.MaturityDateExpiredException;
import model.TradeInfo;

import java.time.LocalDate;
import java.util.Optional;

public class TradeStoreImpl implements TradeStore{
    private TradeDb tradeDb;
    private static final String MATURITY_DATE_EXPIRED_EXCEPTION_MESSAGE="Maturity date of trade is already expired";
    private static final String LOWER_TRADE_VERSION_EXCEPTION_MESSAGE="incoming trade version is lower than stored trade version for the same trade";
    public TradeStoreImpl() {
        tradeDb= new InMemoryTradeDB();
    }

    @Override
    public void processTrade(TradeInfo tradeInfo) throws LowerTradeVersionException, MaturityDateExpiredException {
        if(tradeInfo.getMaturityDate().isBefore(LocalDate.now())){
            throw new MaturityDateExpiredException(MATURITY_DATE_EXPIRED_EXCEPTION_MESSAGE);
        }
        Optional<TradeInfo> storedTradeRecord = tradeDb.findById(tradeInfo.getTradeId());
        if(storedTradeRecord.isPresent() && storedTradeRecord.get().getVersion()>tradeInfo.getVersion()){
            throw new LowerTradeVersionException(LOWER_TRADE_VERSION_EXCEPTION_MESSAGE);
        }
        tradeDb.save(tradeInfo);
    }

    @Override
    public Optional<TradeInfo> getTradeById(String tradeId) {
        return tradeDb.findById(tradeId);
    }
}
