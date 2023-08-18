package com.example.TestMockv2.Controller;

import com.example.TestMockv2.DAL.DataBaseWorker;
import com.example.TestMockv2.Exceptions.RequestException;
import com.example.TestMockv2.Exceptions.ServiceException;
import com.example.TestMockv2.Models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticateController {

    @GetMapping("/startPage")
    public ResponseEntity<User> ToStartPageQuery(@RequestParam String login)
            throws ServiceException {

        DataBaseWorker dataBaseWorker = new DataBaseWorker();
        User user = dataBaseWorker.GetUserByLogin(login);

        if(user == null) {
            throw new ServiceException("User with the login does not exist! Please try again.");
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<User> AuthenticateQuery(@RequestBody User user)
            throws RequestException {

        DataBaseWorker dataBaseWorker = new DataBaseWorker();

        String queryResult = dataBaseWorker.InsertUser(user);

        if(queryResult == null)
            throw new RequestException("User with the login already exist!");

        return ResponseEntity.ok(user);
    }
}
