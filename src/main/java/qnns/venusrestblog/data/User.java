package qnns.venusrestblog.data;


import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private long id;
    private String username;
    private String email;
    private String password;
    private LocalDate createdAt;
    private UserRole role;
}

