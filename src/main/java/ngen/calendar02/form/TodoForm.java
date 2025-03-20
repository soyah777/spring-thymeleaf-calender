package ngen.calendar02.form;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor

public class TodoForm {
    private LocalDate date;
    private String todo;
}
