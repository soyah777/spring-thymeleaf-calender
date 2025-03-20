package ngen.calendar02.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Todo {
    private LocalDate todoDate;
    private int id;
    private String todo;
    private int userId;
    private String shareCreateUser;
    private String acceptUsername;
    private Boolean isShared=false;
    private Boolean isShareToOtherUsers = false;
    private LocalDateTime completedAt;

    private String detail;

    public Todo(String todo,int userId,LocalDate date){
        this.todoDate = date;
        this.todo = todo;
        this.userId = userId;
    }
}
