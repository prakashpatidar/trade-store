package com.example.tradestore.db;

import com.example.tradestore.model.TradeInfo;

import java.util.Optional;

public interface TradeDb {
    Optional<TradeInfo> findById(String tradeId);
    void save(TradeInfo tradeInfo);
}
