package ngen.calendar02.entity;

import lombok.Data;

@Data
public class ShareTodo {
    private int id;
    private int acceptUserId;
    private int todoId;
    private Boolean isShared;

    public ShareTodo(int acceptUserId,int todoId){
        this.acceptUserId = acceptUserId;
        this.todoId = todoId;
    }
}
