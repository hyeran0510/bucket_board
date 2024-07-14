package cookie.demo.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return  "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "sign_form";
        }

        try {
            //패스워드1과 패스워드2가 동일한지 확인
            //bindingResult.rejectValue의 매개변수는 순서대로 각각 bindingResult.rejectValue(필드명, 오류 코드, 오류 메시지)
            if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
                bindingResult.rejectValue("password2", "passwordInCorrect",
                        "2개의 패스워드가 일치하지 않습니다.");
                return "signup_form";
            }
            userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1());
        }
        //DataIntegrityViolationException이라는 예외가 발생
        //e.getMessage() 구체적인 오류 메시지를 출력
        catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("singupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        }catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }
}





