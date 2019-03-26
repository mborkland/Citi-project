/*
        MIT License

        Copyright (c) 2017 murraco - Free and Open Innovation Lab

        Permission is hereby granted, free of charge, to any person obtaining a copy
        of this software and associated documentation files (the "Software"), to deal
        in the Software without restriction, including without limitation the rights
        to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
        copies of the Software, and to permit persons to whom the Software is
        furnished to do so, subject to the following conditions:

        The above copyright notice and this permission notice shall be included in all
        copies or substantial portions of the Software.

        THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
        IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
        FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
        AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
        LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
        OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
        SOFTWARE.
*/

package edu.usf.cse.controller;

import edu.usf.cse.model.Role;
import edu.usf.cse.model.User;
import edu.usf.cse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password,
                                                     HttpServletResponse response) {
        return new ResponseEntity<String>("{\"token\": \"" + userService.login(username, password) + "\"}", HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> add(@RequestParam String username, @RequestParam String password, List<Role> roles) throws Exception {
        return new ResponseEntity<String>(userService.add(username, password, roles), HttpStatus.OK);
    }

    @DeleteMapping("/remove")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String remove(String username) {
        userService.remove(username);
        return username;
    }

    @GetMapping("/find")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> find(@RequestParam String username) {
        return new ResponseEntity<User>(userService.find(username), HttpStatus.OK);
    }

    @GetMapping("/find-all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> findAll() {
        return new ResponseEntity<List<User>>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/current")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<User> getCurrentUser(HttpServletRequest request) {
        return new ResponseEntity<User>(userService.getCurrent(request), HttpStatus.OK);
    }

    @GetMapping("/refresh")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String refresh(HttpServletRequest request) {
        return userService.refresh(request.getRemoteUser());
    }

}