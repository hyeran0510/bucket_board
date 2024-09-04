package cookie.demo.User;



import cookie.demo.Question.DataNotFoundException;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // 주입받은 PasswordEncoder 사용

    // 사용자 생성 메소드
    public SiteUser create(String username, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        // 주입받은 PasswordEncoder 사용
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }

    // 사용자 조회 메소드
    public SiteUser getUser(String email) {
        Optional<SiteUser> siteUser = Optional.ofNullable(UserRepository.findByEmail(email));
        return siteUser.orElseThrow(() -> new DataNotFoundException("회원을 찾을 수 없습니다."));
    }
}
