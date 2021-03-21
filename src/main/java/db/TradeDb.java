package db;

import model.TradeInfo;

import java.util.Optional;

public interface TradeDb {
    Optional<TradeInfo> findById(String tradeId);
    void save(TradeInfo tradeInfo);
}
