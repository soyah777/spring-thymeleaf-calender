package ngen.calendar02.repository;

import ngen.calendar02.entity.ShareTodo;
import ngen.calendar02.entity.User;
import ngen.calendar02.model.LoginUserTodo;
import ngen.calendar02.model.Todo;
import ngen.calendar02.model.TodoRequest;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface UserMapper {
    public User findPassword(String username);
    public void insertUser(ngen.calendar02.entity.User user);
    public void insertTodo(Todo todo);
    public List<ngen.calendar02.entity.User> findAllUsers();
    public void insertShareTodo(ShareTodo shareTodo);
    public List<Todo> getTodosByDate(LoginUserTodo loginUserTodo);
    public List<Todo> getSharedTodo(TodoRequest todoRequest);
    public Todo getTodosById(int id);
    public void todoDone(Todo todo);
    public void deleteSharedTodo(int id);
    public void deleteTodo(int id);
    public List<Todo> getMyShareTodo(TodoRequest todoRequest);
    public void shareToOtherUser(int id);
    public void removeShare(int id);
}

