package ah_doc_manag.web.controller;

import ah_doc_manag.web.FlashMessage;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class UniqueViolationException extends Throwable {

    public void addFlashMessage(RedirectAttributes redirectAttributes, String message, FlashMessage.Status status) {
        FlashMessage flashMessage = new FlashMessage(message, status);
        redirectAttributes.addFlashAttribute("flash", flashMessage);
    }
}
