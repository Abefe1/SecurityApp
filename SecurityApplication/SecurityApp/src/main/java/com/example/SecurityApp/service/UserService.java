package com.example.SecurityApp.service;

import com.example.SecurityApp.model.AuthenticationRequest;
import com.example.SecurityApp.model.AuthenticationResponse;
import com.example.SecurityApp.model.User;
import com.example.SecurityApp.repository.UserRepository;
import com.example.SecurityApp.request.UserRegistrationRequest;
import com.example.SecurityApp.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(UserRegistrationRequest userRequest){
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(userRequest.getRole());

        return userRepository.save(user);
    }

        public AuthenticationResponse userLogin( AuthenticationRequest authenticationRequest ) {
            try{
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()));
                User user = userRepository.findByUsername(authenticationRequest.getUsername());
                String token = jwtService.generateToken(user);
                System.out.println("Login Successful");
                System.out.println(token);
                return AuthenticationResponse.builder().token(token).build();

            } catch (Exception e){
                System.out.println("Something went wrong: " + e.getMessage());
                return null;
            }
        }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(int id){
        return userRepository.findById(id).get();
    }

    public User updateUser(int id, UserUpdateRequest updateRequest){
        User toUpdate = getUserById(id);
        toUpdate.setUsername(updateRequest.getUsername());
        toUpdate.setPassword(updateRequest.getPassword());
        toUpdate.setRole(updateRequest.getRole());

        return userRepository.save(toUpdate);
    }

    public void deleteUser(int id){
        userRepository.deleteById(id);
    }
}
