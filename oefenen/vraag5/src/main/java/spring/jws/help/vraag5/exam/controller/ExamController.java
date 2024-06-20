package spring.jws.help.vraag5.exam.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ExamController {

    @GetMapping("/exam/{*path}")
    public String interpretURL(Model model, HttpServletRequest request) {
        List<String> parts = new ArrayList<>();
        String[] partArray = request.getRequestURI().split("/");
        for (int i = 2; i < partArray.length; i++) {
            if (!partArray[i].isEmpty()) {
                parts.add(partArray[i]);
            }
        }
        model.addAttribute("parts", parts);
        return "urlInterpreter";
    }

}
