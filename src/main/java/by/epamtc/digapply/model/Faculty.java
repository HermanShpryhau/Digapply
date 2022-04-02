package by.epamtc.digapply.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "Faculties")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Faculty implements DataBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faculty_id", nullable = false)
    private Long id;

    @Column(name = "faculty_name", nullable = false, length = 45)
    private String facultyName;

    @Column(name = "faculty_short_description", nullable = false, length = 300)
    private String facultyShortDescription;

    @Lob
    @Column(name = "faculty_description", nullable = false)
    private String facultyDescription;

    @Column(name = "places", nullable = false)
    private Integer places;

    @Column(name = "is_application_closed", nullable = false)
    private Boolean isApplicationClosed = false;

    @ManyToMany
    @JoinTable(name = "Faculties_has_Subjects",
            joinColumns = @JoinColumn(name = "Faculties_faculty_id"),
            inverseJoinColumns = @JoinColumn(name = "Subjects_subject_id"))
    private Set<Subject> subjects = new LinkedHashSet<>();
}