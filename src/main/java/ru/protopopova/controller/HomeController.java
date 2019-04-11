package ru.protopopova.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.protopopova.model.User;
import ru.protopopova.repository.UserRepository;

@Controller
public class HomeController {

    private final UserRepository userRepository;

    @Autowired
    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value="/")
	public ModelAndView test(HttpServletResponse response) throws IOException{
		return new ModelAndView("home");
	}
	@RequestMapping(value="/users", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<User> getUsers(HttpServletResponse response) throws IOException{
		return userRepository.findAll();
	}
}
