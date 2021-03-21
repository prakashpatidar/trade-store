package db;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import model.TradeInfo;
import net.jodah.expiringmap.ExpirationListener;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class InMemoryTradeDB implements TradeDb{
    ExpiringMap<String,TradeInfo> tradeData;
    public InMemoryTradeDB(){
        tradeData=ExpiringMap.builder().variableExpiration()
                .expirationListener(new ExpirationListener<String, TradeInfo>() {
                    @Override
                    public void expired(String tradeId, TradeInfo tradeInfo) {
                        tradeInfo.setExpired(true);
                        tradeData.put(tradeId,tradeInfo);
                    }
                })
                .build();
    }

    @Override
    public Optional<TradeInfo> findById(String tradeId) {
        return Optional.of(tradeData.get(tradeId));
    }

    @Override
    public void save(TradeInfo tradeInfo) {
        Duration duration = Duration.between(LocalDateTime.now(), tradeInfo.getMaturityDate().atStartOfDay());
        tradeData.put(tradeInfo.getTradeId(),tradeInfo, ExpirationPolicy.CREATED,
                duration.getSeconds()
                ,TimeUnit.SECONDS);
    }
}
