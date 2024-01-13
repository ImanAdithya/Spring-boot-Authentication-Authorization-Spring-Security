package com.techshop.springsecurity.service;

import com.techshop.springsecurity.entity.UserInfo;
import com.techshop.springsecurity.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userRepository.findByName (username);
        return userInfo.map (UserInfoDetails::new).orElseThrow (()-> new UsernameNotFoundException ("user not found"+username));
    }

    public String addUser(UserInfo userInfo){
        userInfo.setPassword (passwordEncoder.encode (userInfo.getPassword ()));
       userRepository.save (userInfo);
       return "user added succuss";
    }

    public List<UserInfo> getAllUser(){
       return userRepository.findAll ();
    }

    public UserInfo getUser(Integer id){
        return userRepository.findById (id).get ();
    }


}
