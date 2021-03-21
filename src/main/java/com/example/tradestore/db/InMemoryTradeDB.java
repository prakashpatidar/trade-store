package com.example.tradestore.db;

import com.example.tradestore.model.TradeInfo;
import com.example.tradestore.store.TradeStoreImpl;
import net.jodah.expiringmap.ExpirationListener;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class InMemoryTradeDB implements TradeDb{
    final private static Logger logger = LoggerFactory.getLogger(TradeStoreImpl.class);
    final private ExpiringMap<String,TradeInfo> tradeData;
    public InMemoryTradeDB(){
        tradeData=ExpiringMap.builder().variableExpiration()
                .expirationListener(tradeExpiryListener())
                .build();
    }

    private ExpirationListener<String, TradeInfo> tradeExpiryListener() {
        return (tradeId, tradeInfo) -> {
            tradeInfo.setExpired(true);
            tradeData.put(tradeId, tradeInfo);
            logger.info("trade automatically expired : {}" ,tradeInfo.toString());
        };
    }

    @Override
    public Optional<TradeInfo> findById(String tradeId) {
        return Optional.ofNullable(tradeData.get(tradeId));
    }

    @Override
    public void save(TradeInfo tradeInfo) {
        Duration duration = Duration.between(LocalDateTime.now(), tradeInfo.getMaturityDate().atStartOfDay());
        tradeData.put(tradeInfo.getTradeId(),tradeInfo, ExpirationPolicy.CREATED,
                duration.getSeconds()
                ,TimeUnit.SECONDS);
    }
}
