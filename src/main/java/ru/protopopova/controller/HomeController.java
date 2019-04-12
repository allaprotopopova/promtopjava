package ru.protopopova.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.protopopova.model.User;
import ru.protopopova.repository.UserRepository;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class HomeController {

    private final UserRepository userRepository;

    @Autowired
    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value="/")
	public String test(HttpServletResponse response) {
		return "home";
	}
	@GetMapping(value="/users", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<User> getUsers() {
		return userRepository.findAll();
	}
}
