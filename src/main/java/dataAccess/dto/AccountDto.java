package dataAccess.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;


@Getter
@EqualsAndHashCode(callSuper = false)
public class AccountDto extends BaseDto{

   private final String name;
   private final String lastName;
   private final String address;
   private final AmountDto balance;


   public AccountDto(UUID id,String name, String lastName, String address, AmountDto balance)  {
      super(id);
      this.name = name;
      this.lastName = lastName;
      this.address = address;
      this.balance = balance;
   }
}
