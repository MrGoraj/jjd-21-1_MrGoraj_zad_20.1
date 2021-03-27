package me.goraj.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingParams() {
        return "redirect:/err.html";
    }

    @RequestMapping("/add")
    public String hello(@RequestParam(name = "imie") String firstName,
                        @RequestParam(name = "nazwisko", required = false) String lastName,
                        @RequestParam(name = "wiek", required = false) Integer age) {
        if (firstName.equals("")) {
            return "redirect:/err.html";
        } else {
            User user = new User(firstName, lastName, age);
            userRepository.add(user);
            return "redirect:/success.html";
        }
    }

    @ResponseBody
    @RequestMapping("/list")
    public String list() {
        userRepository.add(new User("Andzej", "Kowalski", 54));
        userRepository.add(new User("Anna", "Nowak", 25));
        userRepository.add(new User("Jadwiga", "Pierzyna", 34));
        List<User> userList = userRepository.getAll();
        StringBuilder result = new StringBuilder();
        for (User user : userList) {
            result.append(" Imie: ").append(user.getFirstName()).append(" Nazwisko: ").append(user.getLastName())
                    .append(" Wiek: ").append(user.getAge()).append("<br />");
        }
        return result.toString();
    }
}