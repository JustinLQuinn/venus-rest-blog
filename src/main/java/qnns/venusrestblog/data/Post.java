package qnns.venusrestblog.data;

import lombok.*;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Post {
    private Long id;
    private String title;
    private String content;
    private User author;
    private Collection<Category> categories;
}
