package todo.backend;

import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository<Todo, Integer> { }

// (Dependency injection)
// @AutoWired : 인터페이스를 상속하는 클래스를 알아서 생성.