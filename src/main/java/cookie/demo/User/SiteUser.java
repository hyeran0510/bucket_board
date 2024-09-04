package cookie.demo.User;


import Shop.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.management.relation.Role;

@Getter
@Setter
@Entity
public class SiteUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;
    
    private String address;
    
    @Enumerated(EnumType.STRING)
    private UserRole UserRole;

    public static SiteUser createSiteUser(SiteUserFormDto SiteUserFormDto, PasswordEncoder passwordEncoder){
        SiteUser SiteUser = new SiteUser();
        SiteUser.setUsername(SiteUserFormDto.getName());
        SiteUser.setEmail(SiteUserFormDto.getEmail());
        SiteUser.setAddress(SiteUserFormDto.getAddress());
        String password = passwordEncoder.encode(SiteUserFormDto.getPassword());
        SiteUser.setPassword(password);
        return SiteUser;
}

}