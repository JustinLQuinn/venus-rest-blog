package qnns.venusrestblog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("/hello/{name}")
    @ResponseBody
    public String Hello(@PathVariable String name) {
        return "Hello, " + name + "!";
    }
    @GetMapping("/hello")
    @ResponseBody
    public String Hello() {
        return "Hello, from Justin.";
    }

}
