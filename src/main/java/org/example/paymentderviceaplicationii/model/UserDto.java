package org.example.paymentderviceaplicationii.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.paymentderviceaplicationii.model.enums.Role;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private Long id;

    private String username;

    private String password;

    private Role role;

    private List<BankAccount> bankAccounts;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
