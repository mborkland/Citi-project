package edu.usf.cse.controller;

import edu.usf.cse.exception.UserAlreadyExistsException;
import edu.usf.cse.model.User;
import edu.usf.cse.security.CurrentUser;
import edu.usf.cse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/add")
    public ResponseEntity addUser(@RequestBody @Valid User user) throws Exception {
        User newUser;
        try {
            newUser = userService.addUser(user);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(newUser);
    }

    @GetMapping(value = "/current")
    public ResponseEntity getCurrentUser(@CurrentUser Authentication authentication) {
        return ResponseEntity.ok(authentication.getDetails());
    }

}