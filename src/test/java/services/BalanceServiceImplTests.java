package services;

import dataAccess.Dao;
import dataAccess.UIDGenerator;
import dataAccess.dto.AccountDto;
import dataAccess.dto.AmountDto;
import dataAccess.dto.UUID;
import domain.abstractions.ExchangeService;
import domain.dataTypes.AccountId;
import domain.dataTypes.Amount;
import domain.dataTypes.Currency;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.assertEquals;


public class BalanceServiceImplTests {


    @Test
    public void concurrencyTest() throws InterruptedException {
        int maxThreads = 10000;
        Double amount = 50.0;
        String id = "1";
        AccountDto accountDto = new AccountDto("name", "lastname", "address", AmountDto.Of(0.0, "EUR"));

        UIDGenerator uidGenerator = new UIDGeneratorImpl();
        HashMap<UUID, AccountDto> map = new HashMap();
        map.put(UUID.Of(id), accountDto);

        Dao<AccountDto> accountDtoDao = new Dao<>(map, uidGenerator);
        ExchangeService exchangeService = new ExchangeServiceImpl();
        BalanceServiceImpl sut = new BalanceServiceImpl(accountDtoDao, exchangeService);

        List<Thread> threads = new CopyOnWriteArrayList<>();
        for (int i = 0; i < maxThreads; i++) {
            Thread t = new Thread(() -> sut.addAccountBalance(AccountId.Of(id), Amount.Of(amount, Currency.Of("EUR"))));
            threads.add(t);
            t.start();
        }
        while (threads.stream().anyMatch(Thread::isAlive)) {
            Thread.sleep(1000);
        }
        assertEquals(maxThreads * amount, map.get(UUID.Of(id)).getBalance().getMoneyAmount(), 0.0001);
    }

}
