package com.example.tradestore.store;

import com.example.tradestore.db.InMemoryTradeDB;
import com.example.tradestore.db.TradeDb;
import com.example.tradestore.exception.LowerTradeVersionException;
import com.example.tradestore.exception.MaturityDateExpiredException;
import com.example.tradestore.model.TradeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Optional;


public class TradeStoreImpl implements TradeStore{
    final private static Logger logger = LoggerFactory.getLogger(TradeStoreImpl.class);
    final private TradeDb tradeDb;
    private static final String MATURITY_DATE_EXPIRED_EXCEPTION_MESSAGE="Maturity date of trade is already expired";
    private static final String LOWER_TRADE_VERSION_EXCEPTION_MESSAGE="incoming trade version is lower than stored trade version for the same trade";
    public TradeStoreImpl() {
        tradeDb= new InMemoryTradeDB();
    }

    @Override
    public void processTrade(TradeInfo tradeInfo) throws LowerTradeVersionException, MaturityDateExpiredException {
        logger.debug("trade arrived : {}",tradeInfo.toString());
        if(tradeInfo.getMaturityDate().isBefore(LocalDate.now())){
            logger.warn("{} : {}",MATURITY_DATE_EXPIRED_EXCEPTION_MESSAGE,tradeInfo.toString());
            throw new MaturityDateExpiredException(MATURITY_DATE_EXPIRED_EXCEPTION_MESSAGE);
        }
        Optional<TradeInfo> storedTradeRecord = tradeDb.findById(tradeInfo.getTradeId());
        if(storedTradeRecord.isPresent() && storedTradeRecord.get().getVersion()>tradeInfo.getVersion()){
            logger.warn("{} : {}",LOWER_TRADE_VERSION_EXCEPTION_MESSAGE,tradeInfo.toString());
            throw new LowerTradeVersionException(LOWER_TRADE_VERSION_EXCEPTION_MESSAGE);
        }
        tradeDb.save(tradeInfo);
        logger.debug("trade successfully processed in store : {}",tradeInfo.toString());
    }

    @Override
    public Optional<TradeInfo> getTradeById(String tradeId) {
        return tradeDb.findById(tradeId);
    }
}
