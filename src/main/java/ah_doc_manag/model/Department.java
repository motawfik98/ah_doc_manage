package ah_doc_manag.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Nationalized;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.Set;

@Entity
@Audited
@Table(name = "DEPARTMENT")
@SequenceGenerator(name = "dept", initialValue = 100)
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dept")
    private Integer id;

    @Column(length = 512)
    @Nationalized
    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    @JsonIgnore
    @NotAudited
    private Set<ActiveUser> activeUsers;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    @JsonIgnore
    @NotAudited
    private Set<Letter> letters;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ActiveUser> getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(Set<ActiveUser> activeUsers) {
        this.activeUsers = activeUsers;
    }

    public Set<Letter> getLetters() {
        return letters;
    }

    public void setLetters(Set<Letter> letters) {
        this.letters = letters;
    }



    public void addLetter(Letter letter) {
        letters.add(letter);
        letter.setDepartment(this);
    }

    public void removeLetter(Letter letter) {
        letter.setDepartment(null);
        letters.remove(letter);
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!(obj instanceof Department))
            return false;

        Department d = (Department) obj;
        if(d.getName() == null)
            return false;

        return d.id == id;
    }
}
