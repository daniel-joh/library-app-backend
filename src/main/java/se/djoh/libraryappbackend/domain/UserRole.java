package se.djoh.libraryappbackend.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    public static String determineHighestUserRole(User user) {
        String userRole = "";
        for (UserRole role : user.getRoles()) {
            if (role.getRole().name().equals("ROLE_ADMIN")) {
                userRole = "ADMIN";
            } else {
                userRole = "USER";
            }
        }
        return userRole;
    }

}
