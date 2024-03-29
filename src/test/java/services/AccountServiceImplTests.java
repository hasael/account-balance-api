package services;

import dataAccess.Dao;
import dataAccess.dto.AccountDto;
import dataAccess.dto.AmountDto;
import dataAccess.dto.UUID;
import domain.dataTypes.*;
import domain.entities.Account;
import domain.entities.AccountData;
import domain.responses.Response;
import domain.responses.Success;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static domain.responses.NotFound.notFound;
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
        when(mockDao.read(UUID.Of("1"))).thenReturn(Success.Of(new AccountDto(name, lastName, address, new AmountDto(amount, currency))));

        //WHEN

        Response<Account> actual = sut.get(AccountId.Of("1"));

        //THEN

        Response<Account> expected = Success.Of(
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
        when(mockDao.read(UUID.Of("1"))).thenReturn(notFound());

        //WHEN

        Response<Account> actual = sut.get(AccountId.Of("1"));

        //THEN

        Response<Account> expected = notFound();
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

        String previousName = "name";
        String previousLastname = "plastname";
        String previousAddress = "paddress";

        AccountDto previousAccount = new AccountDto(previousName, previousLastname, previousAddress, AmountDto.Of(amount, currency));

        AccountDto updateData = new AccountDto(updatedName, lastName, address, AmountDto.Of(amount, currency));
        when(mockDao.update(updateData, UUID.Of(id))).thenReturn(Success.Of(new AccountDto(updatedName, lastName, address, new AmountDto(amount, currency))));
        when(mockDao.read(UUID.Of(id))).thenReturn(Success.Of(previousAccount));

        //WHEN
        AccountData accountData = new AccountData(Name.Of(updatedName), LastName.Of(lastName), Address.Of(address), Currency.Of(currency));
        Response<Account> actual = sut.update(AccountId.Of("1"), accountData);

        //THEN

        Account expectedAccount = new Account(AccountId.Of(id),
                Name.Of(updatedName),
                LastName.Of(lastName),
                Address.Of(address),
                Amount.Of(amount, Currency.Of(currency)));

        Response<Account> expected = Success.Of(expectedAccount);
        Account actualAccount = ((Success<Account>)actual).getValue();
        assertEquals(expectedAccount,actualAccount);
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
        String currency = "EUR";


        when(mockDao.read(UUID.Of(id))).thenReturn(notFound());

        //WHEN

        AccountData accountData = new AccountData(Name.Of(updatedName), LastName.Of(lastName), Address.Of(address), Currency.Of(currency));
        Response<Account> actual = sut.update(AccountId.Of("1"), accountData);

        //THEN

        Response<Account> expected = notFound();
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

        when(mockDao.delete(UUID.Of(id))).thenReturn(Success.Of(new AccountDto(name, lastName, address, new AmountDto(amount, currency))));

        //WHEN

        Response<Account> actual = sut.delete(AccountId.Of(id));

        //THEN

        Response<Account> expected = Success.Of(
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

        when(mockDao.delete(UUID.Of(id))).thenReturn(notFound());

        //WHEN

        Response<Account> actual = sut.delete(AccountId.Of(id));

        //THEN

        Response<Account> expected = notFound();
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
        AccountDto createData = new AccountDto(updatedName, lastName, address, AmountDto.Of(0.0, currency));

        when(mockDao.create(createData)).thenReturn(Success.Of(Pair.of(UUID.Of("1"), new AccountDto(updatedName, lastName, address, new AmountDto(amount, currency)))));

        //WHEN

        AccountData accountData = new AccountData(Name.Of(updatedName), LastName.Of(lastName), Address.Of(address), Currency.Of(currency));

        Response<Account> actual = sut.create(accountData);

        //THEN

        Response<Account> expected = Success.Of(new Account(AccountId.Of(id),
                Name.Of(updatedName),
                LastName.Of(lastName),
                Address.Of(address),
                Amount.Of(amount, Currency.Of(currency))));
        assertEquals(expected, actual);
    }
}
