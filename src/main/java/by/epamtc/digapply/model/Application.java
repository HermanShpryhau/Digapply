package by.epamtc.digapply.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "Applications")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Application implements DataBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;

    @OneToMany(mappedBy = "application", orphanRemoval = true)
    private Set<Result> results = new LinkedHashSet<>();

    @Column(name = "apply_date", nullable = false)
    private Timestamp applyDate;

    @Column(name = "approved", nullable = false)
    private Boolean approved = false;

    @Column(name = "approve_date")
    private Timestamp approveDate;

    @Column(name = "accepted", nullable = false)
    private Boolean accepted = false;

    @Column(name = "accepted_date")
    private Timestamp acceptedDate;
}