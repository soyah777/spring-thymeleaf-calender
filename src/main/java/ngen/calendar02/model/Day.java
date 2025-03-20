package ngen.calendar02.model;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class Day {

    private Integer year;
    private Integer month;
    private Integer date;
    private DayOfWeek dayOfWeek;
    private List<Todo> todoList;
    private boolean isBlank;
    private String holiday;

    public Day(LocalDate localDate, List<Todo> todoList, String holiday) {
        if (localDate != null) {
            this.year = localDate.getYear();
            this.month = localDate.getMonthValue();
            this.date = localDate.getDayOfMonth();
            this.dayOfWeek = localDate.getDayOfWeek();
//            this.isBlank = false;
//        } else {
//            this.isBlank = true;
        }
        this.todoList = todoList;
        this.holiday = holiday;
    }
}
