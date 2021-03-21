package db;

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
                .expirationListener((ExpirationListener<String, TradeInfo>) (tradeId, tradeInfo) -> {
                    tradeInfo.setExpired(true);
                    tradeData.put(tradeId,tradeInfo);
                })
                .build();
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
