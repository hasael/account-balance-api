package services;

import dataAccess.Dao;
import dataAccess.dto.AmountDto;
import dataAccess.dto.TransactionDto;
import dataAccess.dto.UUID;
import domain.abstractions.BalanceService;
import domain.abstractions.TimeProvider;
import domain.dataTypes.*;
import domain.entities.Account;
import domain.entities.Transaction;
import domain.entities.TransactionData;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImplTests {

    @Mock
    Dao<TransactionDto> mockDao;
    @Mock
    BalanceService balanceService;
    @Mock
    TimeProvider timeProvider;

    @Test
    public void getFoundValue() {
        //GIVEN

        String id = "1";
        String senderId = "2";
        String receiverId = "3";
        Date now = new Date();
        Double amount = 10.0;
        String currency = "EUR";

        TransactionServiceImpl sut = new TransactionServiceImpl(mockDao, balanceService, timeProvider);


        when(timeProvider.now()).thenReturn(now);
        when(mockDao.read(UUID.Of(id))).thenReturn(Optional.of(new TransactionDto(UUID.Of(senderId), UUID.Of(receiverId), AmountDto.Of(amount, currency), now)));

        //WHEN

        Optional<Transaction> actual = sut.get(TransactionId.Of(id));

        //THEN

        Optional<Transaction> expected = Optional.of(
                new Transaction(TransactionId.Of(id),
                        AccountId.Of(senderId),
                        AccountId.Of(receiverId),
                        Amount.Of(amount, Currency.Of(currency)),
                        TransactionTime.Of(now)));
        assertEquals(expected, actual);
    }

    @Test
    public void createValue() {
        //GIVEN

        String id = "1";
        String senderId = "2";
        String receiverId = "3";
        Date now = new Date();
        Double amount = 10.0;
        String currency = "EUR";
        Double currentAccountAmount = 42.0;

        TransactionData transactionData = new TransactionData(AccountId.Of(senderId), AccountId.Of(receiverId), Amount.Of(amount, Currency.Of(currency)));
        Account senderAccount = new Account(AccountId.Of(senderId), Name.Of("name"), LastName.Of("lastname"), Address.Of("address"), Amount.Of(currentAccountAmount, Currency.Of(currency)));
        Account receiverAccount = new Account(AccountId.Of(receiverId), Name.Of("name"), LastName.Of("lastname"), Address.Of("address"), Amount.Of(currentAccountAmount, Currency.Of(currency)));

        TransactionDto transactionDto = new TransactionDto(UUID.Of(senderId), UUID.Of(receiverId), AmountDto.Of(amount, currency), now);
        TransactionServiceImpl sut = new TransactionServiceImpl(mockDao, balanceService, timeProvider);

        when(timeProvider.now()).thenReturn(now);

        when(balanceService.updateAccountBalance(AccountId.Of(senderId), Amount.Of(amount, Currency.Of(currency)).withNegativeAmount())).thenReturn(Optional.of(senderAccount));
        when(balanceService.updateAccountBalance(AccountId.Of(receiverId), Amount.Of(amount, Currency.Of(currency)))).thenReturn(Optional.of(receiverAccount));

        when(mockDao.create(transactionDto)).thenReturn(Pair.of(UUID.Of(id), transactionDto));

        //WHEN

        Transaction actual = sut.create(transactionData);

        //THEN

        Transaction expected = new Transaction(TransactionId.Of(id),
                AccountId.Of(senderId),
                AccountId.Of(receiverId),
                Amount.Of(amount, Currency.Of(currency)),
                TransactionTime.Of(now));

        verify(balanceService, times(1)).updateAccountBalance(AccountId.Of(senderId), Amount.Of(amount, Currency.Of(currency)).withNegativeAmount());
        verify(balanceService, times(1)).updateAccountBalance(AccountId.Of(receiverId), Amount.Of(amount, Currency.Of(currency)));
        assertEquals(expected, actual);
    }
}
