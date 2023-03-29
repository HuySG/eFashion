package com.cybersoft.eFashion.controller;

import com.cybersoft.eFashion.payload.ResponseData;
import com.cybersoft.eFashion.utils.JwtUtilsHelpers;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/***
 * NGƯỜI THỰC HIỆN: PHẠM NGỌC HÙNG
 * NGÀY: 29/03/2023
 *
 * LƯU Ý CALL API:
 *     - /signin /signup không cần chứng thực (/signin truyền vào username password type form-data)
 *     - tất cả các link còn lại đều phải chứng thực
 */

@RestController
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtilsHelpers jwtUtilsHelpers;
    @PostMapping("/signin")
    public ResponseEntity<?> signin(
            @RequestParam String username,
            @RequestParam String password
    ) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        //Tạo chứng thực
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        //Parse về Json
        Gson gson = new Gson();
        String data = gson.toJson(authentication);

        System.out.println("Data: "+data);

        ResponseData responseData = new ResponseData();
        responseData.setData(jwtUtilsHelpers.generateToken(data));

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup() {

        return new ResponseEntity<>("Hello signup", HttpStatus.OK);
    }

}