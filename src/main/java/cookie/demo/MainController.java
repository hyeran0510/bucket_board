package cookie.demo;

import Shop.Item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {


    @GetMapping("/Cookie")
    @ResponseBody
    public String index() {
        return "미니 쿠키 홈페이지";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/question/list";
    }

    @GetMapping("/itemForm")
    public String itemForm() {
        return "itemForm";
    }

}
