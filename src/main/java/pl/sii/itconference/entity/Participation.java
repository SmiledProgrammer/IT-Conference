package pl.sii.itconference.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "participations")
@Getter
public class Participation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int lectureId;
}
