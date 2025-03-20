package ngen.calendar02.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

//ログインしているユーザーに、自分が登録したTodoのみが表示されるようにするための実装で作ったカスタムクラス。

@Data
@AllArgsConstructor
public class LoginUserTodo {
    private LocalDate date;
    private int userId;
}
