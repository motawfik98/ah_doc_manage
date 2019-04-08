package ah_doc_manag.web.controller;

import ah_doc_manag.model.ActiveUser;
import ah_doc_manag.model.Department;
import ah_doc_manag.model.IAuthenticationFacade;
import ah_doc_manag.model.ReceivedLetter;
import ah_doc_manag.service.ReceivedLetterService;
import ah_doc_manag.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class ReceivedLettersController {
    @Autowired
    private ReceivedLetterService receivedLetterService;

    @Autowired
    private IAuthenticationFacade authenticationFacade;


    @PreAuthorize("hasRole('ROLE_CREATE')")
    @RequestMapping(value = "/received/add-header", method = RequestMethod.POST)
    public String addHeader(@Valid ReceivedLetter receivedLetter, BindingResult result, RedirectAttributes redirectAttributes) {
        Department currentDepartmentLoggedIn = authenticationFacade.getDepartment(); // gets the current logged in department
        ActiveUser loggedInUser = authenticationFacade.getLoggedInUser(); // gets the current logged in user
        if (result.hasErrors()) { // checks if there is any errors in filling the fields
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.receivedLetter", result); // adds a flash attribute with the error field and message
            redirectAttributes.addFlashAttribute("receivedLetter", receivedLetter); // adds the sentLetter to the user for not wasting all of the filled data
            return "redirect:/received/add-header"; // redirect to the same page to make the user correct these errors
        }


        currentDepartmentLoggedIn.addLetter(receivedLetter); // adds the sentLetter to the current logged in department
        loggedInUser.addLetter(receivedLetter);
        if (!receivedLetterService.save(receivedLetter)) {
            currentDepartmentLoggedIn.removeLetter(receivedLetter); // remove the added sentLetter from the current logged in department
            loggedInUser.removeLetter(receivedLetter); // remove the added sentLetter from the current logged in user
            receivedLetter.setId(null);
            receivedLetter.setDepartment(currentDepartmentLoggedIn);
            redirectAttributes.addFlashAttribute("receivedLetter", receivedLetter);
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("This row has been added", FlashMessage.Status.SUCCESS));
            return "redirect:/received/add-header";
        }
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Letter has been successfully saved", FlashMessage.Status.SUCCESS));
        return String.format("redirect:/received/form/%s/edit", receivedLetter.getId());
    }




    @PreAuthorize("hasRole('ROLE_UPDATE')")
    @RequestMapping(value = "received/form/{receivedLetterID}/edit", method = RequestMethod.POST)
    public String editLetter(@Valid ReceivedLetter receivedLetter, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) { // checks if there is any errors in filling the fields
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.receivedLetter", result); // adds a flash attribute with the error field and message
            redirectAttributes.addFlashAttribute("receivedLetter", receivedLetter); // adds the sentLetter to the user for not wasting all of the filled data
            return String.format("redirect:/received/form/%s/edit", receivedLetter.getId()); // redirect to the same page to make the user correct these errors
        }

        receivedLetterService.save(receivedLetter);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Letter has been successfully edited", FlashMessage.Status.SUCCESS));
        return String.format("redirect:/received/%s", receivedLetter.getId());
    }
}
