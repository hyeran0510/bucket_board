package cookie.demo;

import jakarta.persistence.Entity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class te {
    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello Cookie ";
    }
}
