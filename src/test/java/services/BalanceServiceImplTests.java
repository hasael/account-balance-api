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
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

import static java.util.concurrent.TimeUnit.SECONDS;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;


public class BalanceServiceImplTests {



    @Test
    public void concurrencyTest() throws InterruptedException {
        AccountDto accountDto = new AccountDto("name", "lastname", "address", AmountDto.Of(0.0, "EUR"));

        UIDGenerator uidGenerator = new UIDGeneratorImpl();
        HashMap<UUID, AccountDto> map = new HashMap();
        map.put(UUID.Of("1"),accountDto);

        Dao<AccountDto> accountDtoDao = new Dao<>(map, uidGenerator);
        ExchangeService exchangeService = new ExchangeServiceImpl();
        BalanceServiceImpl sut = new BalanceServiceImpl(accountDtoDao, exchangeService);

        final CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < 1000; i++) {

            final int number = i;

            new Thread(() -> {
                sut.addAccountBalance(AccountId.Of("1"), Amount.Of(50.0, Currency.Of("EUR")));
                countDownLatch.countDown();
            }).start();

        }
        assertTrue(countDownLatch.await(30, SECONDS));
        assertEquals(50000.0,map.get(UUID.Of("1")).getBalance().getMoneyAmount(),0.0001);
    }
}
