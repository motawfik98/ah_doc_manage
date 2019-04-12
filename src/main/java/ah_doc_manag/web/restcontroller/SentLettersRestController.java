package ah_doc_manag.web.restcontroller;

import ah_doc_manag.dao.LetterSpecificationsBuilder;
import ah_doc_manag.dao.datatables.SentLettersDataTableRepository;
import ah_doc_manag.model.IAuthenticationFacade;
import ah_doc_manag.model.SentLetter;
import ah_doc_manag.pdf.PdfView;
import ah_doc_manag.service.LetterService;
import ah_doc_manag.service.SentLetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SentLettersRestController extends LetterRestController<SentLetter> {
    @Autowired
    private SentLettersDataTableRepository sentLettersDataTableRepository;
    @Autowired
    private IAuthenticationFacade authenticationFacade;
    @Autowired
    private SentLetterService sentLetterService;

    private LetterSpecificationsBuilder<SentLetter> specificationsBuilder;


    @RequestMapping(value = "/data/sent-letters", method = RequestMethod.GET)
    public DataTablesOutput<SentLetter> getAllSentLetters(@Valid DataTablesInput input) {
        specificationsBuilder = new LetterSpecificationsBuilder<>(authenticationFacade.getDepartmentID() + "");

        buildSpecifications(input, specificationsBuilder);

        return sentLettersDataTableRepository.findAll(input,
                specificationsBuilder.getResult());

    }

    @RequestMapping("/data/select-sent-letters")
    public DataTablesOutput<SentLetter> buildSpecificationsWithNoCustomSearch(@Valid DataTablesInput input) {
        LetterSpecificationsBuilder<SentLetter> linksBuilder =
                new LetterSpecificationsBuilder<>(authenticationFacade.getDepartmentID() + "");
        buildSpecifications(input, linksBuilder);
        return sentLettersDataTableRepository.findAll(input, linksBuilder.getResult());
    }

    @RequestMapping("/sent/print")
    public ModelAndView printSent() {
        Map<String, Object> model = new HashMap<>();
        Iterable<SentLetter> sentLetters = sentLetterService.findAll(specificationsBuilder.getResult());
        String[] columns = {
                "الرقم", "التاريخ", "السنه", "العنوان", "مرسل الى", "الرساله"
        };
        int[] widths = {
            370, 150, 79, 40, 65, 35
        };
        model.put("type", "sent");
        model.put("columns", columns);
        model.put("widths", widths);
        model.put("sentLetters", sentLetters);
        return new ModelAndView(new PdfView(), model);
    }

}
