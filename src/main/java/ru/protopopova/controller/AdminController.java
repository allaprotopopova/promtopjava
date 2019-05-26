package ru.protopopova.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.protopopova.model.Dish;
import ru.protopopova.model.Menu;
import ru.protopopova.model.Restaurant;
import ru.protopopova.model.User;
import ru.protopopova.service.DishService;
import ru.protopopova.service.MenuService;
import ru.protopopova.service.RestaurantService;
import ru.protopopova.service.UserService;

import java.util.List;

import static ru.protopopova.util.ValidationUtil.assureIdConsistent;
import static ru.protopopova.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {
	static final String REST_URL = "/rest/admin";

	private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserService userService;
    private final RestaurantService restaurantService;
    private final MenuService menuService;
    private final DishService dishService;

    @Autowired
	public AdminController(UserService userService, RestaurantService restaurantService, MenuService menuService, DishService dishService) {
		this.userService = userService;
		this.restaurantService = restaurantService;
		this.menuService = menuService;
		this.dishService = dishService;
	}
//users
	@GetMapping("/users")
	public List<User> getUsers() {
    	log.info("get all users");
    	return userService.getAll();
	}
	@GetMapping("/users/{id}")
	public User getUser(@PathVariable("id") int id) {
		log.info("get user with id={}", id);
		return userService.get(id);
	}

	@PostMapping("/users")
	public User createUser(@RequestBody User user) {
    	log.info("create user {}", user);
		checkNew(user);
		return userService.create(user);

	}
	@PutMapping("/users/{id}")
	public void updateUser(@RequestBody User user, @PathVariable("id") int id) {
		log.info("update user {} with id={}", user, id);
		assureIdConsistent(user, id);
		userService.update(user);
	}
	@DeleteMapping(value = "/users/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable("id") int id) {
		log.info("delete {}", id);
		userService.delete(id);
	}
	@GetMapping("/users/by")
	public User getUserByMail( @RequestParam("email") String email) {
		log.info("getByEmail {}", email);
		return userService.getByEmail(email);
	}

	@PostMapping("/users/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void enableUser(@PathVariable("id") int id, @RequestParam("enabled") boolean enabled) {
		log.info(enabled ? "enable {}" : "disable {}", id);
		userService.enable(id, enabled);
	}
//restaurants
    @GetMapping("/restaurants")
	List<Restaurant> getRestaurants() {
    	log.info("get restaurants");
    	return restaurantService.get();
	}
    @GetMapping("/restaurants/{id}")
	Restaurant getRestaurant(int id) {
    	log.info("get restaurant  with id={}", id);
    	return restaurantService.getById(id);
	}
    @DeleteMapping("/restaurants/{id}")
	 public void deleteRestaurant(@PathVariable("id") int id) {
    	log.info("delete restaurant with id={}", id);
    	restaurantService.delete(id);
	}

	@PostMapping("/restaurants")
	public Restaurant createRestaurant(@RequestBody Restaurant restaurant) {
		log.info("create restaurant {}", restaurant);
		checkNew(restaurant);
		return restaurantService.save(restaurant);

	}
	@PutMapping("/restaurants/{id}")
	public void updateRestaurant(@RequestBody Restaurant restaurant, @PathVariable("id") int id) {
		log.info("update restaurant {} with id={}", restaurant, id);
		assureIdConsistent(restaurant, id);
		restaurantService.save(restaurant);
	}
	//menu and dishes
	@DeleteMapping("/menu/{id}")
	public void deleteMenu(@PathVariable("id") int id) {
		log.info("delete menu with id={}", id);

	}
	@PostMapping("/menu")
	public Menu saveMenu(@RequestBody Menu menu, @RequestParam("restaurantId") int restaurantId) {
		log.info("save menu {} for restaurantId={}", menu, restaurantId);
		return menuService.save(menu, restaurantId);
	}


	@GetMapping("/dishes")
	public List<Dish> getDishes() {
		log.info("get dishes");
		return dishService.get();
	}
	@GetMapping("/dishes/{id}")
	public Dish getById(@PathVariable("id") int id) {
		log.info("get dish with id={}", id);
		return dishService.getById(id);
	}
	@DeleteMapping("/dishes/{id}")
	public void delete(@PathVariable("id") int id){
		log.info("delete dish with id={}", id);
		dishService.delete(id);
	}
	@PostMapping("/dishes")
	public Dish save(@RequestBody Dish dish) {
		log.info("save dish {}", dish);
		return dishService.save(dish);
	}

}
