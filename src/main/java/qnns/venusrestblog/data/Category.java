package qnns.venusrestblog.data;
import lombok.*;

import java.util.Collection;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Category {
    private Long id;
    private String name;
    private Collection<Post> posts;

}
