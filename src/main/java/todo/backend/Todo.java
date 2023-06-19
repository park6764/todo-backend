package todo.backend;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Todo {
    @GeneratedValue(strategy = GenerationType.AUTO) // 1++
    @Id // primary key 설정.
    private Integer id;
    private String name;
    private String title;
    private boolean complete;
}
