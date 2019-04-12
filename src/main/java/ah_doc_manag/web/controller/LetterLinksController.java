package ah_doc_manag.web.controller;

import ah_doc_manag.model.Letter;
import ah_doc_manag.service.LetterService;
import ah_doc_manag.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LetterLinksController<T extends Letter> {
    @Autowired
    private LetterService<T> letterService;


    @PreAuthorize("hasRole('ROLE_READ')")
    @RequestMapping(value = "/links/{type}/{letterID}/confirm", method = RequestMethod.POST)
    public String confirmLinkToPreviousLetter(@PathVariable String type, @RequestParam long selected,
                                              @PathVariable long letterID, RedirectAttributes redirectAttributes) {
        T letter = letterService.findById(letterID).get();
        T selectedLetter = letterService.findById(selected).get();
        letter.addLinkedLetter(selectedLetter);
        letterService.save(letter);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("تم الربط", FlashMessage.Status.SUCCESS));
        return String.format("redirect:/generic/%s", letterID);
    }

}
