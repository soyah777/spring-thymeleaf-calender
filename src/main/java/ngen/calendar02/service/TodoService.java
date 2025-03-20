package ngen.calendar02.service;

import ngen.calendar02.entity.ShareTodo;
import ngen.calendar02.model.LoginUserTodo;
import ngen.calendar02.model.Todo;
import ngen.calendar02.model.TodoRequest;
import ngen.calendar02.repository.UserMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {
    private final UserMapper userMapper;
    private List<Todo> todos = new ArrayList<>();

    public TodoService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<Todo> getTodosByDate(LocalDate localDate, int userId) {

//        return todos.stream()
//                .filter(todo -> todo.getDate().equals(localDate))
//                .collect(Collectors.toList());
        LoginUserTodo loginUserTodo = new LoginUserTodo(localDate, userId);
        return userMapper.getTodosByDate(loginUserTodo);
    }

    public void addTodo(LocalDate date, String todo, String username) {
        ngen.calendar02.entity.User user = userMapper.findPassword(username);
        if (user == null) {
            throw new UsernameNotFoundException("ユーザーが見つかりません:" + username);
        }
        int userId = user.getId();

        Todo newTodo = new Todo(todo, userId, date);
        userMapper.insertTodo(newTodo);
        todos.add(newTodo);

        System.out.println("登録されたTodo: " + newTodo);
    }

//    public void deleteTodo(LocalDate date, String todo) {
//        todos.removeIf(t -> t.getTodoDate().equals(date) && t.getTodo().equals(todo));
//    }

    @Transactional
    public void deleteTodo(int id) {
        userMapper.deleteSharedTodo(id);
        userMapper.deleteTodo(id);
    }

    public void shareTodo(int acceptUserId, int todoId) {
        System.out.println("Sharing Todo ID: " + todoId);
        ShareTodo shareTodo = new ShareTodo(acceptUserId, todoId);
        userMapper.insertShareTodo(shareTodo);


    }

    public List<ngen.calendar02.entity.User> getAllUsers() {
        return userMapper.findAllUsers();
    }

    public List<Todo> getSharedTodo(LocalDate date, int userId) {
        TodoRequest request = new TodoRequest(date, userId);
        return userMapper.getSharedTodo(request);
    }

    public void completeTodo(int todoId, boolean isCompleted) {
        Todo todo = userMapper.getTodosById(todoId);
        todo.setCompletedAt(isCompleted ? LocalDateTime.now() : null);
        userMapper.todoDone(todo);
    }

    public List<Todo> getMyShareTodo(LocalDate date,int userId){
        TodoRequest todoRequest = new TodoRequest(date,userId);
        return userMapper.getMyShareTodo(todoRequest);
    }

    public void shareCancel(int id){
        userMapper.removeShare(id);
        userMapper.deleteSharedTodo(id);
    }

}
