package com.techshop.springsecurity.controller;

import com.techshop.springsecurity.entity.AuthRequest;
import com.techshop.springsecurity.entity.UserInfo;
import com.techshop.springsecurity.service.JwtService;
import com.techshop.springsecurity.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private  UserInfoService userInfoService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to spring scurity";
    }

    @PostMapping("/addUser")
    public String addUser(@RequestBody UserInfo userInfo){
       return userInfoService.addUser (userInfo);
    }

    @PostMapping("/login")
    public String addUser(@RequestBody AuthRequest authRequest){
        Authentication authenticate = authenticationManager.authenticate (new UsernamePasswordAuthenticationToken (authRequest.getUserName (), authRequest.getPassword ()));
        if (authenticate.isAuthenticated ()){
            return jwtService.generateToken (authRequest.getUserName ());
        }else {
            throw  new UsernameNotFoundException ("Invalid User Request");
        }

    }

    @GetMapping("/getUsers")
    @PreAuthorize ("hasAnyAuthority('ADMIN_ROLES')")
    public List<UserInfo> getAllUsers(){
        return userInfoService.getAllUser ();
    }

    @GetMapping("/getUsers/{id}")
    @PreAuthorize ("hasAnyAuthority('USER_ROLES')")
    public UserInfo getAllUsers(@PathVariable Integer id){
        return userInfoService.getUser (id);
    }

}
