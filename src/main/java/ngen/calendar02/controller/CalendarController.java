package ngen.calendar02.controller;

import lombok.RequiredArgsConstructor;
import ngen.calendar02.form.TodoForm;
import ngen.calendar02.model.Day;
import ngen.calendar02.model.Todo;
import ngen.calendar02.repository.UserMapper;
import ngen.calendar02.service.CalendarService;
import ngen.calendar02.service.LoginUserService;
import ngen.calendar02.service.TodoService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDate;


@Controller
@RequiredArgsConstructor

public class CalendarController {

    private final CalendarService calendarService;
    private final TodoService todoService;
    private final LoginUserService loginUserService;
    private final UserMapper userMapper;


    @GetMapping("/calendar")
    public String showCalendar(
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "month", required = false) Integer month,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {

        // ログインユーザーのIDを取得
        ngen.calendar02.entity.User user = userMapper.findPassword(userDetails.getUsername());
        int userId = user.getId();


        //yearとmonthの入力がないとき・存在しない年月をリクエストしたとき
        int minYear = LocalDate.now().getYear() -10;
        int maxYear = LocalDate.now().getYear()+10;

        if (year == null || month == null || year < minYear || year > maxYear || month < 1 || month > 12) {
            year = LocalDate.now().getYear();
            month = LocalDate.now().getMonthValue();
            String redirectUrl = "redirect:/calendar?year=" + year + "&month=" + month;

            return redirectUrl;
        }

        List<Day> days = calendarService.generateCalendar(year, month,userId);

        model.addAttribute("selectedYear", year);
        model.addAttribute("selectedMonth", month);
        model.addAttribute("years", calendarService.getSelectableYears());
        model.addAttribute("months", calendarService.getSelectableMonths());
        model.addAttribute("days", days);

        String selectedYearAndMonth = year.toString() + "年 " + month.toString()+ "月";
        model.addAttribute("selectedYearAndMonth", selectedYearAndMonth);

        return "calendar";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/todo-details")
    public String showTodoForm(
            @RequestParam Integer year,
            @RequestParam Integer month,
            @RequestParam Integer date,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {

        ngen.calendar02.entity.User user = userMapper.findPassword(userDetails.getUsername());
        int userId = user.getId();

        LocalDate localDate = LocalDate.of(year, month, date);
        List<Todo> todos = todoService.getTodosByDate(localDate,userId);
        List<Todo> sharedTodos = todoService.getSharedTodo(localDate,userId);
        List<ngen.calendar02.entity.User> allUsers = todoService.getAllUsers();

        List<Todo> myShareTodos = todoService.getMyShareTodo(localDate,userId);

        model.addAttribute("year", year);
        model.addAttribute("month", month);

        model.addAttribute("date", localDate);
        model.addAttribute("todos", todos);
        model.addAttribute("sharedTodos",sharedTodos);
        model.addAttribute("shareUser", user);
        model.addAttribute("todoForm", new TodoForm(localDate,""));
        model.addAttribute("allUsers", allUsers);
        model.addAttribute("currentUserId", userId);
        model.addAttribute("myShareTodos", myShareTodos);
        return "todo-details";
    }

    @PostMapping("/todo/add")
    public String addTodo(@ModelAttribute TodoForm todoForm) {
        LocalDate date = todoForm.getDate();
        String todoText = todoForm.getTodo();
        String username = loginUserService.getAuthenticatedUsername();


        todoService.addTodo(date, todoText,username);

        int temporaryYear = date.getYear();
        int temporaryMonth = date.getMonthValue();
        int temporaryDate = date.getDayOfMonth();

        return String.format("redirect:/todo-details?year=%d&month=%d&date=%d", temporaryYear, temporaryMonth,temporaryDate);
//        return String.format("redirect:/calendar?year=%d&month=%d", temporaryYear, temporaryMonth);
    }


    @PostMapping("/todo/delete")
    public String deleteTodo(@RequestParam LocalDate date,@RequestParam int id) {
        
        todoService.deleteTodo(id);

        int temporaryYear = date.getYear();
        int temporaryMonth = date.getMonthValue();
        int temporaryDate = date.getDayOfMonth();

        return String.format("redirect:/todo-details?year=%d&month=%d&date=%d", temporaryYear, temporaryMonth,temporaryDate);
    }

    @PostMapping("/todo/share")
    public String shareTodo(@RequestParam("todoId") int todoId,
                            @RequestParam("acceptUserId") int acceptUserId,
                            @RequestParam("date") LocalDate date) {
        todoService.shareTodo(acceptUserId,todoId);

        userMapper.shareToOtherUser(todoId);

        int temporaryYear = date.getYear();
        int temporaryMonth = date.getMonthValue();
        int temporaryDate = date.getDayOfMonth();

        return String.format("redirect:/todo-details?year=%d&month=%d&date=%d", temporaryYear, temporaryMonth,temporaryDate);
//        return "redirect:/calendar";
    }

    @PostMapping("/todo/complete")
    public String completeTodo(@RequestParam("todoId") int todoId,
                               @RequestParam("date") LocalDate date,
                               @RequestParam(value = "completed",defaultValue = "false") boolean completed) {
        todoService.completeTodo(todoId,completed);

        int temporaryYear = date.getYear();
        int temporaryMonth = date.getMonthValue();
        int temporaryDate = date.getDayOfMonth();

        return String.format("redirect:/todo-details?year=%d&month=%d&date=%d", temporaryYear, temporaryMonth,temporaryDate);
    }

    @PostMapping("/share/cancellation")
    public String shareCancel(@RequestParam("todoDate") LocalDate todoDate,
            @RequestParam("todoId") int todoId) {
        todoService.shareCancel(todoId);
        
        int temporaryYear = todoDate.getYear();
        int temporaryMonth = todoDate.getMonthValue();
        int temporaryDate = todoDate.getDayOfMonth();

        return String.format("redirect:/todo-details?year=%d&month=%d&date=%d" , temporaryYear,temporaryMonth,temporaryDate);
    }

}
