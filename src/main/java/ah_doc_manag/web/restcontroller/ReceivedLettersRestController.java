package ah_doc_manag.web.restcontroller;

import ah_doc_manag.dao.LetterSpecificationsBuilder;
import ah_doc_manag.dao.datatables.ReceivedLettersDataTablesRepository;
import ah_doc_manag.model.AuthenticationFacade;
import ah_doc_manag.model.ReceivedLetter;
import ah_doc_manag.pdf.PdfView;
import ah_doc_manag.service.ReceivedLetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.Column;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ReceivedLettersRestController extends LetterRestController<ReceivedLetter> {
    @Autowired
    private ReceivedLettersDataTablesRepository receivedLettersDataTablesRepository;
    @Autowired
    private AuthenticationFacade authenticationFacade;
    @Autowired
    private ReceivedLetterService receivedLetterService;

    private LetterSpecificationsBuilder<ReceivedLetter> specificationsBuilder;

    @RequestMapping(value = "/data/received-letters", method = RequestMethod.GET)
    public DataTablesOutput<ReceivedLetter> getAllReceivedLetters(@Valid DataTablesInput input) {
        specificationsBuilder = new LetterSpecificationsBuilder<>(authenticationFacade.getDepartmentID() + "");

        buildSpecifications(input, specificationsBuilder);

        return receivedLettersDataTablesRepository.findAll(input,
                specificationsBuilder.getResult());
    }

    @RequestMapping("/data/select-received-letters")
    public DataTablesOutput<ReceivedLetter> buildSpecificationsWithNoCustomSearch(@Valid DataTablesInput input) {
        LetterSpecificationsBuilder<ReceivedLetter> linksBuilder =
                new LetterSpecificationsBuilder<>(authenticationFacade.getDepartmentID() + "");
        buildSpecifications(input, linksBuilder);

        return receivedLettersDataTablesRepository.findAll(input, linksBuilder.getResult());
    }

    @RequestMapping("/received/print")
    public ModelAndView printReceived() {
        Map<String, Object> model = new HashMap<>();
        Iterable<ReceivedLetter> receivedLetters = receivedLetterService.findAll(specificationsBuilder.getResult());
        String[] columns = {
                "الرقم", "التاريخ", "السنه", "اسم المرسل", "الجهه المرسل منها", "رقم الخطاب المرسل", "الرساله"
        };
        int[] widths = {
                90, 38, 43, 43, 30, 48, 27
        };
        model.put("type", "received");
        model.put("columns", columns);
        model.put("widths", widths);
        model.put("receivedLetters", receivedLetters);
        return new ModelAndView(new PdfView(), model);
    }

}
