//package ah_doc_manag.add_initial_data_to_track;
//
////import ah_doc_manag.add_initial_data_to_track.TempDao;
//
//import ah_doc_manag.model.SentLetter;
//import ah_doc_manag.service.DepartmentService;
//import ah_doc_manag.service.SentLetterService;
//import ah_doc_manag.pdf.PdfView;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Controller
//public class TempController {
//
//    @Autowired
//    private SentLetterService sentLetterService;
//
//
//    @Autowired
//    DepartmentService departmentService;
//
//    @Autowired
//    TempDao tempDao;
//
////    @RequestMapping("/pdf2")
////    public ModelAndView generateITextPDF() {
////        Map<String, Object> model = new HashMap<>();
////        Iterable<SentLetter> sentLetters = sentLetterService.findAll();
////        String[] columns = {
////           "Department Name", "Number", "Date", "Subject", "Sent To", "Message"
////        };
////        model.put("columns", columns);
////        model.put("sentLetters", sentLetters);
////        return new ModelAndView(new PdfView(), model);
////    }
//
//    @RequestMapping("/addData")
//    public void addData() {
//        tempDao.addDepartment(30, "اداره السكرتارية الادارية");
//        tempDao.addDepartment(20, "ادارة مجلس الوزراء");
//        tempDao.addDepartment(10, "ادارة خدمة المواطنين");
//        tempDao.addDepartment(45, "ادارة مجلس النواب");
//        tempDao.addDepartment(40, "ادارة طلبات الاحاطة");
//        tempDao.addDepartment(15, "ادارة الشئون الفنية");
//        tempDao.addDepartment(5, "ادارة خدمة المواطنين-جهات رسمية");
//        tempDao.addDepartment(25, "مشروعات النقل");
//
//        tempDao.addUser("read", "$2a$10$MCGcN84j11V8li5F5HnK/eSd0Ce1hzVauJEb0CvjJ5jACaBf7CByG", 5);
//        tempDao.addUser("write", "$2a$10$3IlnZt/bwPk5sdGGIa6lO.yPDw8mFSoIi1mGWzluZ.cjP438at9bC", 10);
//        tempDao.addUser("admin", "$2a$10$t/yHchnbZmgcN4anG2K94OGavAJgP9mgRL6L/h5byV4LiX5aqsbd2", 15);
//    }
//
//}