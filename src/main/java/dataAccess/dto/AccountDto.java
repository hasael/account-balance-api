package dataAccess.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class AccountDto extends BaseDto{

   private final String name;
   private final String lastName;
   private final String address;
   private final AmountDto balance;
}
