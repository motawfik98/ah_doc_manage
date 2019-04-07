package ah_doc_manag.model;

import ah_doc_manag.web.validation.YearEqualsDate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Nationalized;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Audited
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"year", "number", "department_id"})
})
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@SequenceGenerator(name="seq", initialValue=100000)

@YearEqualsDate()

public abstract class Letter {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private Long id;

    @NotNull(message = "{field.null}")
    @Column(updatable = false)
    private Long number;

    @NotNull(message = "{field.null}")
    @ManyToOne
    @JoinColumn(
            name = "department_id",
            updatable = false
    )
    private Department department;

    @NotNull(message = "{field.null}")
    @Column(updatable = false)
    private Long year;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{field.null}")
    @Column(updatable = false)
    private LocalDate date;

    @Column(length = 256)
    @Nationalized
    private String subject;

    @Column(length = 5000)
    @Nationalized
    private String message;


    @ManyToMany(cascade={CascadeType.ALL})
    @JoinTable(name = "SENT_RECEIVED",
            joinColumns = {@JoinColumn(name = "LETTER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "LINKED_ID")})
    @JsonIgnore
    @NotAudited
    private Set<Letter> links = new HashSet<>();

    @ManyToMany(mappedBy="links")
    @JsonIgnore
    @NotAudited
    private Set<Letter> letters = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ActiveUser user;

    @OneToMany(mappedBy = "letter", cascade = CascadeType.ALL)
    @JsonIgnore
    @NotAudited
    private Set<Image> images;


    public Letter() {
        date = LocalDate.now();
        year = Long.parseLong(date.getYear() + "");

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public String getSubject() {
        if(subject == null)
            return "";
        return subject;
    }

    public void setSubject(String subject) {
        if (subject.equals(""))
            subject = null;
        this.subject = subject;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMessage() {
        if(message == null)
            return "";
        return message;
    }

    public void setMessage(String message) {
        if (message.equals(""))
            message = null;
        this.message = message;
    }

    public Set<Letter> getLinks() {
        return links;
    }

    public void setLinks(Set<Letter> links) {
        this.links = links;
    }

    public Set<Letter> getLetters() {
        return letters;
    }

    public void setLetters(Set<Letter> letters) {
        this.letters = letters;
    }

    public boolean isSentLetter() {
        return this instanceof SentLetter;
    }

    public boolean isReceivedLetter() {
        return this instanceof ReceivedLetter;
    }


    public void addLinkedLetter(Letter letter) {
        links.add(letter);
        letter.links.add(this);
    }

    public ActiveUser getUser() {
        return user;
    }

    public void setUser(ActiveUser user) {
        this.user = user;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if(! (obj instanceof Letter))
            return false;
        Letter letter = (Letter) obj;
        return letter.number.equals(number) && letter.date.equals(date) && letter.year.equals(year);
    }
}
