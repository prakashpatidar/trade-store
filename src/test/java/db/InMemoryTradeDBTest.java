package db;

import model.TradeInfo;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryTradeDBTest {

    @Test
    void findById() {
        TradeDb tradeDb= new InMemoryTradeDB();
        TradeInfo tradeInfo= TradeInfo.builder().tradeId("T1").version(2).counterPartyId("CP-1").bookId("B1")
                .maturityDate(LocalDate.now().plusDays(1)).createdDate(LocalDate.now()).build();
        tradeDb.save(tradeInfo);
        Optional<TradeInfo> expectedTradeInfo =tradeDb.findById(tradeInfo.getTradeId());
        assertTrue(expectedTradeInfo.isPresent());
    }
    @Test
    void checkAutoExpiry()
    {
        TradeDb tradeDb= new InMemoryTradeDB();
        TradeInfo tradeInfo= TradeInfo.builder().tradeId("T1").version(2).counterPartyId("CP-1").bookId("B1")
                .maturityDate(LocalDate.now().minusDays(1)).createdDate(LocalDate.now()).build();
        tradeDb.save(tradeInfo);
        Optional<TradeInfo> expectedTradeInfo =tradeDb.findById(tradeInfo.getTradeId());
        assertTrue(expectedTradeInfo.get().isExpired());
    }
    @Test
    void save() {
        TradeDb tradeDb= new InMemoryTradeDB();
        TradeInfo tradeInfo= TradeInfo.builder().tradeId("T1").version(2).counterPartyId("CP-1").bookId("B1")
                .maturityDate(LocalDate.now().plusDays(1)).createdDate(LocalDate.now()).build();
        tradeDb.save(tradeInfo);
        Optional<TradeInfo> expectedTradeInfo =tradeDb.findById(tradeInfo.getTradeId());
        assertTrue(expectedTradeInfo.isPresent());
    }
}