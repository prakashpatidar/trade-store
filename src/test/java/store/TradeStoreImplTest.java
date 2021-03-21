package store;

import exception.LowerTradeVersionException;
import exception.MaturityDateExpiredException;
import model.TradeInfo;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TradeStoreImplTest {

    @Test
    void processLowerTradeVersionTrade() throws MaturityDateExpiredException, LowerTradeVersionException {
        TradeStore tradeStore=new TradeStoreImpl();
        TradeInfo higherVersionTrade= TradeInfo.builder().tradeId("T1").version(2).counterPartyId("CP-1").bookId("B1")
                .maturityDate(LocalDate.now().plusDays(1)).createdDate(LocalDate.now()).build();
        tradeStore.processTrade(higherVersionTrade);
        TradeInfo lowerVersionTrade= TradeInfo.builder().tradeId("T1").version(1).counterPartyId("CP-1").bookId("B1")
                .maturityDate(LocalDate.now().plusDays(1)).createdDate(LocalDate.now()).build();
        assertThrows(LowerTradeVersionException.class,()->tradeStore.processTrade(lowerVersionTrade));

    }

    @Test
    void processMaturityExpiredTrade() {
        TradeStore tradeStore=new TradeStoreImpl();
        TradeInfo maturityExpiredTrade= TradeInfo.builder().tradeId("T1").version(1).counterPartyId("CP-1").bookId("B1")
                .maturityDate(LocalDate.now().minusDays(1)).createdDate(LocalDate.now()).build();
        assertThrows(MaturityDateExpiredException.class,()->tradeStore.processTrade(maturityExpiredTrade));
    }
    @Test
    void processSuccessfulTrade() throws MaturityDateExpiredException, LowerTradeVersionException {
        TradeStore tradeStore=new TradeStoreImpl();
        TradeInfo tradeInfo= TradeInfo.builder().tradeId("T1").version(1).counterPartyId("CP-1").bookId("B1")
                .maturityDate(LocalDate.now().plusDays(1)).createdDate(LocalDate.now()).build();
        tradeStore.processTrade(tradeInfo);
        assertEquals(tradeInfo.toString(),tradeStore.getTradeById(tradeInfo.getTradeId()).orElse(TradeInfo.builder().build()).toString());
    }
}