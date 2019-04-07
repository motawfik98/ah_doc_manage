package ah_doc_manag.model;

import org.hibernate.annotations.Nationalized;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Audited
public class SentLetter extends Letter {
    @Column
    @Nationalized
    private String sentTo;


    public SentLetter() {
    }


    public String getSentTo() {
        if (sentTo == null)
            return "";
        return sentTo;
    }

    public void setSentTo(String sentTo) {
        this.sentTo = sentTo;
    }


}
