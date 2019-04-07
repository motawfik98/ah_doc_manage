package ah_doc_manag.model;

import org.hibernate.annotations.Nationalized;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Audited
public class ReceivedLetter extends Letter {
    private static String[] types = new String[]{
            "MAIL", "EMAIL", "FAX"
    };


    @Column
    @Nationalized
    private String type;

    @Column(length = 256)
    @Nationalized
    private String senderName;

    @Column(length = 256)
    @Nationalized
    private String departmentSentFrom;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column
    private LocalDate sentLetterDate;

    @Column
    private Long sentLetterNumber;



    public ReceivedLetter() {
    }



    public String getSenderName() {
        if(senderName == null)
            return "";
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }


    public String getType() {
        if(type == null)
            return "";
        return type;
    }

    public void setType(String type) {
        if (type.equals(""))
            type = null;
        this.type = type;
    }

    public String getDepartmentSentFrom() {
        if(departmentSentFrom == null)
            return "";
        return departmentSentFrom;
    }

    public void setDepartmentSentFrom(String departmentSentFrom) {
        if (departmentSentFrom.equals(""))
            departmentSentFrom = null;
        this.departmentSentFrom = departmentSentFrom;
    }

    public LocalDate getSentLetterDate() {
        return sentLetterDate;
    }

    public void setSentLetterDate(LocalDate sentLetterDate) {
        this.sentLetterDate = sentLetterDate;
    }

    public Long getSentLetterNumber() {
        return sentLetterNumber;
    }

    public void setSentLetterNumber(Long sentLetterNumber) {
        this.sentLetterNumber = sentLetterNumber;
    }

    public static String[] getTypes() {
        return types;
    }
}
