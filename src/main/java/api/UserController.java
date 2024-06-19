package api;

import aspect.Timer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import model.Role;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import security.CustomPasswordEncoder;
import service.RoleService;
import service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name= "Users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CustomPasswordEncoder passwordEncoder;


    // получить пользователя по id
    @Operation(summary = "get user by ID", description = "Поиск пользователя по ID пользователя")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    // получить пользователя по логину
    @Operation(summary = "get user by login", description = "Поиск пользователя по логину пользователя")
    @GetMapping("/login/{login}")
    public ResponseEntity<User> getUserByLogin(@PathVariable String login) {
        try {
            return ResponseEntity.ok(userService.getUserByLogin(login));
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //получить список всех пользователей
    @Timer
    @Operation(summary = "get all users", description = "Поиск всех пользователей в системе")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers()); }

    // создание пользователя
    @Operation(summary = "create user", description = "Добавить пользователя в общий список системы")
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user){
        User newUser = new User();
        newUser.setLogin(user.getLogin());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
    }

    // удаление пользователя
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }
    //добавление роли пользователю
    @PostMapping("/{userId}/role/{roleId}")
    public ResponseEntity<User> addRoleToUser(@PathVariable Long userId, @PathVariable Long roleId) {
        User user = userService.getUserById(userId);
        Role role = roleService.getRoleById(roleId);
        user.getRoles().add(role);
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.OK);
    }
}