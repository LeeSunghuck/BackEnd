package sole_paradise.sole_paradise;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReactRoutingController {

    @GetMapping(value = { "/", "/main", "/login", "/login/test", "/shoppingDetail/**" })
    public String forward() {
        return "forward:/index.html";
    }
}
