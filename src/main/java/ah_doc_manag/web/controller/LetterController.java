package ah_doc_manag.web.controller;

import ah_doc_manag.model.*;
import ah_doc_manag.service.ImageService;
import ah_doc_manag.service.LetterHistoryService;
import ah_doc_manag.service.LetterService;
import ah_doc_manag.web.FlashMessage;
import org.hibernate.envers.DefaultRevisionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class LetterController<T extends Letter> {
    @Autowired
    private LetterService<T> letterService;
    @Autowired
    private IAuthenticationFacade authenticationFacade;
    @Autowired
    private LetterHistoryService<T> letterHistoryService;
    @Autowired
    private ImageService imageService;

    @PreAuthorize("hasRole('ROLE_READ')")
    @RequestMapping("/generic/{letterId}")

    public String viewLetter(@PathVariable long letterId, Model model) {
        Letter letter = letterService.findById(letterId).get(); // gets the required letter to pdf its details
        model.addAttribute("archived", false);
        model.addAttribute("letter", letter); // adds it to the template
        if (letter.isSentLetter()) {
            return "sent/details";
        } else {
            return "received/details";
        }
    }

    @RequestMapping("/{type}/add-header")
    @PreAuthorize("hasRole('ROLE_CREATE')")
    public String addSentHeaderForm(Model model, @PathVariable String type) {
        Letter letter;
        if (type.equals("sent")) letter = new SentLetter();
        else letter = new ReceivedLetter();

        Department currentLoggedIn = authenticationFacade.getDepartment(); // sets the department of the new letter to the current logged in department
        if (!model.containsAttribute("letter")) {
            letter.setDepartment(currentLoggedIn);
            model.addAttribute("letter", letter);
        }

        return String.format("%s/form/header", type);
    }

    @PreAuthorize("hasRole('ROLE_CREATE')")
    @RequestMapping(value = "/{type}/add-header", method = RequestMethod.POST)
    public String addHeader(@Valid T letter, BindingResult result, RedirectAttributes redirectAttributes, @PathVariable String type) {
        Department currentDepartmentLoggedIn = authenticationFacade.getDepartment(); // gets the current logged in department
        ActiveUser loggedInUser = authenticationFacade.getLoggedInUser(); // gets the current logged in user
        String fullName = type + "Letter";
        if (result.hasErrors()) { // checks if there is any errors in filling the fields
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult." + fullName, result); // adds a flash attribute with the error field and message
            redirectAttributes.addFlashAttribute("letter", letter); // adds the sentLetter to the user for not wasting all of the filled data
            return String.format("redirect:/%s/add-header", type);
        }

        currentDepartmentLoggedIn.addLetter(letter); // adds the sentLetter to the current logged in department
        loggedInUser.addLetter(letter);
        if (!letterService.save(letter)) {
            currentDepartmentLoggedIn.removeLetter(letter); // remove the added sentLetter from the current logged in department
            loggedInUser.removeLetter(letter); // remove the added sentLetter from the current logged in user
            letter.setId(null); // sets the ID to null to give hibernate access to assign it another ID
            letter.setDepartment(currentDepartmentLoggedIn); // sets the department of the letter to the current logged in
            redirectAttributes.addFlashAttribute("letter", letter); // adds the sentLetter to the user for not wasting all of the filled data
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("This row has been added", FlashMessage.Status.FAILURE));
            return String.format("redirect:/%s/add-header", type);
        }
        // save completed successfully
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Letter has been successfully saved", FlashMessage.Status.SUCCESS));
        return String.format("redirect:%s/form/%s/edit", type, letter.getId()); // redirect to the edit page
    }

    @PreAuthorize("hasRole('ROLE_READ')")
    @RequestMapping("/generic/{type}/form/{letterID}/edit")
    public String editLetterForm(@PathVariable String type, @PathVariable long letterID, Model model) {
        if (!model.containsAttribute("letter")) // gets the sentLetter with the given ID and initializing its links and department and user
            model.addAttribute("letter", letterService.findById(letterID).get());

        return String.format("/%s/form/edit", type);
    }


    @PreAuthorize("hasRole('ROLE_READ')")
    @RequestMapping("/{type}")
    public String showSentLetters(Model model, @PathVariable String type) {
        model.addAttribute("ajax", true); // inform thymeleaf to use ajax to get the data
        model.addAttribute("department", authenticationFacade.getDepartmentName());
        if (type.equals("sent")) {
            model.addAttribute("url", "/data/sent-letters");
            model.addAttribute("fileName", "sentDataTables.js");// add the required javascript name
            model.addAttribute("sent", true); // give a clue to thymeleaf to know the letter type
        } else if (type.equals("received")) {
            model.addAttribute("url", "/data/received-letters");
            model.addAttribute("fileName", "receivedDataTables.js");// add the required javascript name
            model.addAttribute("received", true); // give a clue to thymeleaf to know the letter type

        }
        return "common/letters";
    }

    @PreAuthorize("hasRole('ROLE_DELETE')")
    @RequestMapping(value = "/{type}/{letterID}/delete", method = RequestMethod.POST)
    public String deleteLetter(@PathVariable String type, @PathVariable long letterID, RedirectAttributes redirectAttributes) {
        letterService.delete(letterID);
        // inform the user that the letter was successfully deleted
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Letter has been deleted", FlashMessage.Status.SUCCESS));
        return "redirect:/show-sent";
    }


    @PreAuthorize("hasRole('ROLE_READ')")
    @RequestMapping("/{type}/{letterID}/history")
    public String showAllHistory(@PathVariable String type, @PathVariable long letterID, Model model) {
        List<T> history = letterHistoryService.getLetterHistory(letterID, true);
        List<DefaultRevisionEntity> timeOfHistory = letterHistoryService.getTimeOfHistory(letterID, true);

        model.addAttribute("history", history);
        model.addAttribute("department", authenticationFacade.getDepartmentName()); //adds the department name
        model.addAttribute("revision", timeOfHistory);

        return "common/letters";
    }

    @PreAuthorize("hasRole('ROLE_READ')")
    @RequestMapping("/{type}/{letterID}/history/{versionID}")
    public String showHistoryVersion(@PathVariable String type, @PathVariable long letterID, @PathVariable long versionID, Model model) {
        List<T> history = letterHistoryService.getLetterHistory(versionID, false);
        List<DefaultRevisionEntity> timeOfHistory = letterHistoryService.getTimeOfHistory(versionID, false);
        T archivedVersion = history.get(0);
        DefaultRevisionEntity revision = timeOfHistory.get(0);
        model.addAttribute("letter", archivedVersion);
        model.addAttribute("archived", true);
        model.addAttribute("revision", revision);

        return String.format("%s/details", type);
    }

    @PreAuthorize("hasRole('ROLE_READ')")
    @RequestMapping("/{type}/{letterID}/images")
    public String showImages(@PathVariable String type, @PathVariable long letterID, Model model) {
        List<Image> images = imageService.findByLetterId(letterID);
        model.addAttribute("images", images);
        model.addAttribute("title", "صور الصادر");
        model.addAttribute("sentOrReceived", type);
        return "common/images";
    }

    @PreAuthorize("hasRole('ROLE_READ')")
    @RequestMapping("/{type}/{letterID}/images/{imageID}")
    public ResponseEntity<byte[]> showImage(@PathVariable String type, @PathVariable long letterID, @PathVariable long imageID) {
        HttpHeaders headers = new HttpHeaders();

        Image image = imageService.findById(imageID).get();
        byte[] bytes = image.getBytes();

        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.add("content-disposition", "inline;filename=" + image.getPath());
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

}
