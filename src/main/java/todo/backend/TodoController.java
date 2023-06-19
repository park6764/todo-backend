package todo.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class TodoController {
    @Autowired
    private TodoRepository todoRepository; // 상속받음.
    
    @GetMapping("/")
    public List<Todo> selectAll() {
        List<Todo> todos = new ArrayList<>();

        for (Todo todo : todoRepository.findAll()) {
            todos.add(todo);
        }
        return todos;
    }

    @PutMapping("/addTodo")
    public void addTodo( @RequestBody TodoRecord todo ) { // def :: requierd : T
        todoRepository.save(todo.toTodo());
    }

    @PostMapping("/editTodo")
    public void editTodo( 
        @RequestBody TodoRecord todo, // name, title
        @RequestParam Integer id,
        @RequestParam Boolean complete
    ) {
        
        if(todoRepository.findById(id).isPresent()) {
            Todo newTodo = todoRepository.findById(id).get();
            newTodo.setName(todo.name());
            newTodo.setTitle(todo.title());
            newTodo.setComplete(complete);
            todoRepository.save(newTodo);
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id가 존재하지 않습니다.");
    }

    @DeleteMapping("/delTodo")
    public void delTodo(@RequestParam Integer id) {
        if(todoRepository.findById(id).isPresent()) {
            todoRepository.delete(todoRepository.findById(id).get());
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id가 존재하지 않습니다.");
    }

    @GetMapping("/findTodo")
    public List<Todo> findTodo(
        @RequestParam Optional<String> name,
        @RequestParam Optional<String> title
    ) {
        List<Todo> todos = new ArrayList<>();

        if(name.isEmpty() && title.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "제목 또는 내용을 입력해주세요.");
        else {
            for (Todo todo : todoRepository.findAll()) {
                if(title.map(t -> todo.getTitle().contains(t)).orElse(false) || name.map(n -> todo.getName().contains(n)).orElse(false)) {
                    todos.add(todo);
                } else continue;
            }
        }
        return todos;
    }
}
