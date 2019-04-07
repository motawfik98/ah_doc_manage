package ah_doc_manag.web.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorIndexController implements ErrorController {
    @RequestMapping(value = "/error")
    public String error(Model model, HttpServletRequest httpRequest) {
        String errorMessage = "";
        int errorCode = getErrorCode(httpRequest);

        if(errorCode == HttpStatus.NOT_FOUND.value()) {
            errorMessage = "Wrong URL";
        } else if (errorCode == HttpStatus.FORBIDDEN.value()) {
            errorMessage = "You are not authorized";
        } else if (errorCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            errorMessage = "Internal Server Error";
        }
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("errorCode", errorCode);
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
    }
}
