package ngen.calendar02.service;

import lombok.RequiredArgsConstructor;
import ngen.calendar02.model.Day;
import ngen.calendar02.model.Todo;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CalendarService {

    public final TodoService todoService;
    private final HolidayService holidayService;


    public List<Day> generateCalendar(Integer year, Integer month, int userId) {
        List<Day> days = new ArrayList<>();

        LocalDate firstDate = LocalDate.of(year, month, 1);
//        LocalDate currentDate = LocalDate.now();
//        LocalDate firstDate = currentDate.withDayOfMonth(1);
        DayOfWeek firstDayOfWeek = firstDate.getDayOfWeek();


        // 祝日追加
        Map<String, String> holidays = holidayService.getHoliday(year);


        //月初の空欄
        for (int i = 0; i < firstDayOfWeek.getValue(); i++) {
            LocalDate prevMonthLastDate = firstDate.minusDays(1);
            LocalDate emptyDays = prevMonthLastDate.minusDays(firstDayOfWeek.getValue() - (i + 1));
            // ユーザー自身
            List<Todo> todos = todoService.getTodosByDate(emptyDays, userId);

            // 他ユーザーからの共有
            List<Todo> sharedTodos = todoService.getSharedTodo(emptyDays, userId);

            // 他ユーザーからの共有フラグ
            sharedTodos.forEach(sharedTodo ->
                    sharedTodo.setIsShared(true)
            );

            todos.addAll(sharedTodos);

            Day emptyDate = new Day(emptyDays, todos, null);
            emptyDate.setBlank(true);

            if (firstDayOfWeek.getValue() != 7) {
                days.add(emptyDate);
            }
        }

        //日付を挿入
        LocalDate date = firstDate;

        for (int i = 1; i <= firstDate.lengthOfMonth(); i++) {

            // ユーザー自身が登録したTodoを取得
            List<Todo> todos = todoService.getTodosByDate(date, userId);
            System.out.println(todos);

            // 他ユーザーから共有されたTodoを取得
            List<Todo> sharedTodos = todoService.getSharedTodo(date, userId);

//            共有されたTodoにフラグ(目印)を立てる
            sharedTodos.forEach(sharedTodo ->
                    sharedTodo.setIsShared(true)
            );

            // 他ユーザーから共有されたTodoをユーザー自身のTodoに合流
            todos.addAll(sharedTodos);

            String dateKey = String.format("%04d-%02d-%02d", date.getYear(), date.getMonthValue(), i);
            String holiday = holidays.getOrDefault(dateKey, null);

            days.add(new Day(date, todos, holiday));
            date = date.plusDays(1);
        }

        //月末の空欄
        LocalDate currentLastDate = firstDate.withDayOfMonth(firstDate.lengthOfMonth());
        int index = 0;

        while (days.size() % 7 != 0) {
            index++;
            LocalDate emptyDate = currentLastDate.plusDays(index);

            List<Todo> todos = todoService.getTodosByDate(emptyDate,userId);
            List<Todo> sharedTodos = todoService.getSharedTodo(emptyDate,userId);
            sharedTodos.forEach(sharedTodo ->{
                sharedTodo.setIsShared(true);
            });
            todos.addAll(sharedTodos);

            Day emptyday = new Day(emptyDate, todos, null);
            emptyday.setBlank(true);
            days.add(emptyday);
        }
        return days;
    }

    //セレクトボックスの年月
    public List<Integer> getSelectableYears() {
        List<Integer> years = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear - 10; i <= currentYear + 10; i++) {
            years.add(i);
        }
        return years;
    }

    public List<Integer> getSelectableMonths() {
        List<Integer> months = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            months.add(i);
        }
        return months;
    }

}
