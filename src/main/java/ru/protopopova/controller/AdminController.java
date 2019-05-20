package ru.protopopova.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.protopopova.model.User;
import ru.protopopova.repository.CrudUserRepository;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = AdminController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {
	static final String REST_URL = "/admin/users";

//
//    @Autowired
//    public AdminController(CrudUserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @GetMapping(value="/")
//	public String test(HttpServletResponse response) {
//		return "home";
//	}
//	@GetMapping(value="/users", produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody
//	public List<User> getUsers() {
//		return userRepository.findAll();
//	}
}
