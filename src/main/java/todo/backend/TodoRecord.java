package todo.backend;

// record는 모든 필기가 읽기 전용, 생성자를 같이 쓰고, 공개.
public record TodoRecord(String name, String title) {
    public Todo toTodo() {
        Todo t = new Todo();
        t.setName(name);
        t.setTitle(title);
        t.setComplete(false);
        return t;
    }
}