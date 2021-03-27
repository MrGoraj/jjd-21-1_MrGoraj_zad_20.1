package me.goraj.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping("/add")
    public String hello(@RequestParam(name = "imie") String firstName,
                        @RequestParam(name = "nazwisko", required = false) String lastName,
                        @RequestParam(name = "wiek", required = false) Integer age) {
        User user = new User(firstName, lastName, age);
        if (firstName.equals("")) {
            return "redirect:/err.html";
        } else {
            userRepository.add(user);
            return "redirect:/success.html";
        }
    }

    @ResponseBody
    @RequestMapping("/list")
    public String list() {
        List<User> userList = userRepository.getAll();
        String result = "";
        for (User user : userList) {
            result += " Imie: " + user.getFirstName() + " Nazwisko: " + user.getLastName() + " Wiek: " + user.getAge() + "<br />";
        }
        return result;
    }
}