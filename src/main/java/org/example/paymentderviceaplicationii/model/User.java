package org.example.paymentderviceaplicationii.model;

import jakarta.persistence.*;
import org.example.paymentderviceaplicationii.converter.RoleConverter;
import org.example.paymentderviceaplicationii.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User extends BaseEntity {

    private String username;

    private String password;

    @Convert(converter = RoleConverter.class)
    private Role role;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<BankAccount> bankAccounts;

}
