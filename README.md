# account-balance-api
Sample API that manages accounts transactions.
Using SPARK api.

Applying Dependency Inversion:

Domain is composed of
Models:
- Account
- Transaction

Services:
- AccountService -> CRUD account data
- TransactionService -> Get or Create a transaction between accounts
- BalanceService -> Manage single account balance
- ExchangeService -> Manage currency exchange during transactions with different currencies (rightnow is set to 1 for any currency)

With their respective types for each value 
 - Currency
 - Amount
 - TransactionId 
 ...
 
 On the outer layer the JSON models for parsing
 - AccountJson
 - TransactionJson

 
 DataAccess is implemented in memory using DAO objects, that saves DTO objects.
 - AccountDto
 - TransactionDto
  The DTOs represent the domain data persisted on the repository.
DAO layer is a generic persistence layer that CRUDs data on a in-memory Dictionary.

 A concurrency logic is added on BalanceService implementation, which is a high traffic method.
 All the calls will be passed to a BlockingQueue which will be consumed from a different thred that will update the account balance.
 
 Further improvements
 - More unit test coverage
 - Use Dozer to map Json models -> Domain entities -> Dto models
 - Concurrency management to other calls
