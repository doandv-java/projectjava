package application.controller.web;

import application.model.dto.ProductDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping(path = "/report")
public class ReportController {

    @GetMapping(value = {""})
    public String cart(Model model,
                       @Valid @ModelAttribute("productname") ProductDTO productName,
                       HttpServletResponse response,
                       HttpServletRequest request,
                       final Principal principal)
    {

        return "a";
    }
}
