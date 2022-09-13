package qnns.venusrestblog.data;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String username;

    @Email
    @NotEmpty
    private String email;

    @ToString.Exclude
    private String password;

    private LocalDate createdAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Transient
    private Collection<Post> posts;

}

