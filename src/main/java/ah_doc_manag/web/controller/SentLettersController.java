package ah_doc_manag.web.controller;

import ah_doc_manag.model.*;
import ah_doc_manag.service.ImageService;
import ah_doc_manag.service.SentLetterService;
import ah_doc_manag.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Controller
public class SentLettersController {
    @Autowired
    private SentLetterService sentLetterService;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private ImageService imageService;

    @PreAuthorize("hasRole('ROLE_READ')")
    @RequestMapping("/")
    public String basePage() {
        return "index";
    }


    @PreAuthorize("hasRole('ROLE_CREATE')")
    @RequestMapping(value = "/add-sent-header", method = RequestMethod.POST)
    public String addHeader(@Valid SentLetter sentLetter, BindingResult result, RedirectAttributes redirectAttributes) {
        Department currentDepartmentLoggedIn = authenticationFacade.getDepartment(); // gets the current logged in department
        ActiveUser loggedInUser = authenticationFacade.getLoggedInUser(); // gets the current logged in user
        if (result.hasErrors()) { // checks if there is any errors in filling the fields
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.sentLetter", result); // adds a flash attribute with the error field and message
            redirectAttributes.addFlashAttribute("sentLetter", sentLetter); // adds the sentLetter to the user for not wasting all of the filled data
            return "redirect:/add-sent-header";
        }

        currentDepartmentLoggedIn.addLetter(sentLetter); // adds the sentLetter to the current logged in department
        loggedInUser.addLetter(sentLetter);
        if (!sentLetterService.save(sentLetter)) {
            currentDepartmentLoggedIn.removeLetter(sentLetter); // remove the added sentLetter from the current logged in department
            loggedInUser.removeLetter(sentLetter); // remove the added sentLetter from the current logged in user
            sentLetter.setId(null); // sets the ID to null to give hibernate access to assign it another ID
            sentLetter.setDepartment(currentDepartmentLoggedIn); // sets the department of the letter to the current logged in
            redirectAttributes.addFlashAttribute("sentLetter", sentLetter); // adds the sentLetter to the user for not wasting all of the filled data
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("This row has been added", FlashMessage.Status.FAILURE));
            return "redirect:/add-sent-header";
        }
        // save completed successfully
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Letter has been successfully saved", FlashMessage.Status.SUCCESS));
        return String.format("redirect:sent/form/%s/edit", sentLetter.getId()); // redirect to the edit page
    }


    @PreAuthorize("hasRole('ROLE_READ')")
    @RequestMapping(value = "sent/form/{sentLetterID}/edit", method = RequestMethod.POST)
    public String editLetter(@Valid SentLetter sentLetter, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) { // checks if there is any errors in filling the fields
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.sentLetter", result); // adds a flash attribute with the error field and message
            redirectAttributes.addFlashAttribute("sentLetter", sentLetter); // adds the sentLetter to the user for not wasting all of the filled data
            return String.format("redirect:/sent/form/%s/edit", sentLetter.getId());
        }

        sentLetterService.save(sentLetter);
        // inform the user that the letter was updated
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Letter has been successfully edited", FlashMessage.Status.SUCCESS));
        return String.format("redirect:/sent/%s", sentLetter.getId()); // redirect to pdf the letter details
    }


    @RequestMapping("/received/add-images")
    public void addImages() {
//        for (long i = 5; i <= 6; i++) {
//            addPhotosToLetters(i);
//            System.gc();
//        }
//        addPhotosToLetters(3L);
//        System.gc();
//        addPhotosToLetters(4L);
//        System.gc();
//        addPhotosToLetters(1L, 30);
//        System.gc();
        for (long i = 1; i <= 23; i++) {
            addPhotosToLetters(i, 45L);
            Runtime.getRuntime().runFinalization();
            Runtime.getRuntime().gc();
            System.gc();
        }
//        addPhotosToLetters(1L, 15L);
    }

    private void addPhotosToLetters(long i, long department) {
//        String fullPath = "E:/ah_doc_manag_images/in_" + department;
        String fullPath = String.format("E:/ah_doc_manag_images/%s/%s", "in_" + department, i);
//        String fullPath = "E:/ah_doc_manag_images/out_20/" + i;

        File directory = new File(fullPath);
        File[] files = directory.listFiles();
        directory = null;
        if (files != null) {
            for (int j = 0, filesLength = files.length; j < filesLength; j++) {
                File photo = files[j];
                System.out.println(photo.getPath());
                Image image = imageService.findByPathAndDepartmentNumberAndLetterType(photo.getName(), department, "RECEIVED");
                if (image == null)
                    continue;


                try {
                    image.setBytes(Files.readAllBytes(photo.toPath()));
                    imageService.save(image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
