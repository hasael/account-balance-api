package services;

import dataAccess.Dao;
import dataAccess.dto.AccountDto;
import dataAccess.dto.AmountDto;
import dataAccess.dto.UUID;
import domain.dataTypes.*;
import domain.entities.Account;
import domain.entities.AccountData;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTests {

    @Mock
    Dao<AccountDto> mockDao;

    @Test
    public void getFoundValue() {
        //GIVEN

        String id = "1";
        String name = "name";
        String lastName = "lastname";
        String address = "address";
        Double amount = 42.0;
        String currency = "EUR";

        AccountServiceImpl sut = new AccountServiceImpl(mockDao);
        when(mockDao.read(UUID.Of("1"))).thenReturn(Optional.of(new AccountDto(name, lastName, address, new AmountDto(amount, currency))));

        //WHEN

        Optional<Account> actual = sut.get(AccountId.Of("1"));

        //THEN

        Optional<Account> expected = Optional.of(
                new Account(AccountId.Of(id),
                        Name.Of(name),
                        LastName.Of(lastName),
                        Address.Of(address),
                        Amount.Of(amount, Currency.Of(currency))));
        assertEquals(expected, actual);
    }

    @Test
    public void getNoFoundValue() {
        //GIVEN
        AccountServiceImpl sut = new AccountServiceImpl(mockDao);
        when(mockDao.read(UUID.Of("1"))).thenReturn(Optional.empty());

        //WHEN

        Optional<Account> actual = sut.get(AccountId.Of("1"));

        //THEN

        Optional<Account> expected = Optional.empty();
        assertEquals(expected, actual);
    }

    @Test
    public void updateFoundValue() {
        //GIVEN
        AccountServiceImpl sut = new AccountServiceImpl(mockDao);

        String id = "1";
        String updatedName = "updatedName";
        String lastName = "lastname";
        String address = "address";
        Double amount = 42.0;
        String currency = "EUR";

        AccountDto updateData = new AccountDto(updatedName, lastName, address, AmountDto.empty());

        AccountData accountData = new AccountData(Name.Of(updatedName), LastName.Of(lastName), Address.Of(address));
        when(mockDao.update(updateData, UUID.Of(id))).thenReturn(Optional.of(new AccountDto(updatedName, lastName, address, new AmountDto(amount, currency))));

        //WHEN

        Optional<Account> actual = sut.update(AccountId.Of("1"), accountData);

        //THEN

        Optional<Account> expected = Optional.of(
                new Account(AccountId.Of(id),
                        Name.Of(updatedName),
                        LastName.Of(lastName),
                        Address.Of(address),
                        Amount.Of(amount, Currency.Of(currency))));
        assertEquals(expected, actual);
    }

    @Test
    public void updateNoFoundValue() {
        //GIVEN

        AccountServiceImpl sut = new AccountServiceImpl(mockDao);

        String id = "1";
        String updatedName = "updatedName";
        String lastName = "lastname";
        String address = "address";

        AccountDto updateData = new AccountDto(updatedName, lastName, address, AmountDto.empty());

        when(mockDao.update(updateData, UUID.Of(id))).thenReturn(Optional.empty());

        //WHEN

        AccountData accountData = new AccountData(Name.Of(updatedName), LastName.Of(lastName), Address.Of(address));
        Optional<Account> actual = sut.update(AccountId.Of("1"), accountData);

        //THEN

        Optional<Account> expected = Optional.empty();
        assertEquals(expected, actual);
    }

    @Test
    public void deleteFoundValue() {
        //GIVEN

        AccountServiceImpl sut = new AccountServiceImpl(mockDao);

        String id = "1";
        String name = "name";
        String lastName = "lastname";
        String address = "address";
        Double amount = 42.0;
        String currency = "EUR";

        when(mockDao.delete(UUID.Of(id))).thenReturn(Optional.of(new AccountDto(name, lastName, address, new AmountDto(amount, currency))));

        //WHEN

        Optional<Account> actual = sut.delete(AccountId.Of(id));

        //THEN

        Optional<Account> expected = Optional.of(
                new Account(AccountId.Of(id),
                        Name.Of(name),
                        LastName.Of(lastName),
                        Address.Of(address),
                        Amount.Of(amount, Currency.Of(currency))));
        assertEquals(expected, actual);
    }

    @Test
    public void deleteFoundNoValue() {
        //GIVEN

        AccountServiceImpl sut = new AccountServiceImpl(mockDao);

        String id = "1";

        when(mockDao.delete(UUID.Of(id))).thenReturn(Optional.empty());

        //WHEN

        Optional<Account> actual = sut.delete(AccountId.Of(id));

        //THEN

        Optional<Account> expected = Optional.empty();
        assertEquals(expected, actual);
    }

    @Test
    public void createValue() {
        //GIVEN

        AccountServiceImpl sut = new AccountServiceImpl(mockDao);

        String id = "1";
        String updatedName = "updatedName";
        String lastName = "lastname";
        String address = "address";
        Double amount = 42.0;
        String currency = "EUR";
        AccountDto createData = new AccountDto(updatedName, lastName, address, AmountDto.empty());

        when(mockDao.create(createData)).thenReturn(Pair.of(UUID.Of("1"), new AccountDto(updatedName, lastName, address, new AmountDto(amount, currency))));

        //WHEN

        AccountData accountData = new AccountData(Name.Of(updatedName), LastName.Of(lastName), Address.Of(address));

        Account actual = sut.create(accountData);

        //THEN

        Account expected = new Account(AccountId.Of(id),
                Name.Of(updatedName),
                LastName.Of(lastName),
                Address.Of(address),
                Amount.Of(amount, Currency.Of(currency)));
        assertEquals(expected, actual);
    }
}
