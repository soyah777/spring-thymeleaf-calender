package ngen.calendar02.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TodoRequest {
    private LocalDate date;
    private int userId;
}
