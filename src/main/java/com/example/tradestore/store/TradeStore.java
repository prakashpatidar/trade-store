package com.example.tradestore.store;

import com.example.tradestore.exception.LowerTradeVersionException;
import com.example.tradestore.exception.MaturityDateExpiredException;
import com.example.tradestore.model.TradeInfo;

import java.util.Optional;

public interface TradeStore {
    void processTrade(TradeInfo tradeInfo) throws LowerTradeVersionException, MaturityDateExpiredException;
    Optional<TradeInfo> getTradeById(String tradeId);
}
