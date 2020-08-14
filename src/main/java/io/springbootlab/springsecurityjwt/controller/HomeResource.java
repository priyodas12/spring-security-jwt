package io.springbootlab.springsecurityjwt.controller;

import io.springbootlab.springsecurityjwt.model.AuthenticateRequest;
import io.springbootlab.springsecurityjwt.model.AuthenticateResponse;
import io.springbootlab.springsecurityjwt.service.UserDetailsServiceImpl;
import io.springbootlab.springsecurityjwt.util.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/home")
public class HomeResource {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    JWTUtility jwtUtility;

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String sayHello(){
        return "Welcome User!\n\t"+LocalDateTime.now() +"";
    }

    @RequestMapping(value = "/authenticate",method = RequestMethod.POST)
    public ResponseEntity<?> authenticate(@RequestBody AuthenticateRequest authenticateRequest) throws Exception{
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticateRequest.getUsername(),authenticateRequest.getPassword()));
          }catch (BadCredentialsException e){
            throw new RuntimeException("Incorrect Credentials!");
        }

        final UserDetails userDetails=userDetailsServiceImpl.loadUserByUsername(authenticateRequest.getUsername());

        final String jwt=jwtUtility.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticateResponse(jwt));

    }


}
