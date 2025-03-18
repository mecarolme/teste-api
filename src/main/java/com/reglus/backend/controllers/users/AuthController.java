package com.reglus.backend.controllers.users;

import com.reglus.backend.dto.UserDTO;
import com.reglus.backend.model.entities.users.User;
import com.reglus.backend.model.entities.users.login.LoginRequest;
import com.reglus.backend.repositories.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (loginRequest.getPassword().equals(user.getPasswordHash())) {
                Map<String, Object> response = new HashMap<>();
                response.put("user", new UserDTO(user));
                response.put("user_type", user.getUserType());

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta.");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado.");
    }
}
