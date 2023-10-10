package com.paulrichter.tutoring.controller.rest;

import com.paulrichter.tutoring.Enum.ERole;
import com.paulrichter.tutoring.config.security.jwt.JwtUtils;
import com.paulrichter.tutoring.dto.user.UserDto;
import com.paulrichter.tutoring.model.Role;
import com.paulrichter.tutoring.model.User;
import com.paulrichter.tutoring.payload.request.LoginRequest;
import com.paulrichter.tutoring.payload.request.SignupRequest;
import com.paulrichter.tutoring.payload.response.JwtResponse;
import com.paulrichter.tutoring.payload.response.MessageResponse;
import com.paulrichter.tutoring.repository.RoleRepository;
import com.paulrichter.tutoring.service.TutoringSecurityServiceImpl;
import com.paulrichter.tutoring.service.TutoringUserDetailsImpl;
import com.paulrichter.tutoring.service.TutoringUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final TutoringUserService userService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;
    private final TutoringSecurityServiceImpl securityService;


    public UserController(TutoringUserService userService,
                          JwtUtils jwtUtils,
                          AuthenticationManager authenticationManager,
                          PasswordEncoder encoder,
                          RoleRepository roleRepository,
                          TutoringSecurityServiceImpl securityService) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
        this.securityService = securityService;
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        TutoringUserDetailsImpl userDetails = (TutoringUserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles));
    }

    @PostMapping("/register")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userService.findByUsername(signUpRequest.getUsername()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin" -> {
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                    }
                    case "mod" -> {
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                    }
                    default -> {
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                    }
                }
            });
        }

        user.setRoles(roles);
        userService.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @GetMapping("/me")
    @CrossOrigin(origins = "*")
    public ResponseEntity<UserDto> getUserDataFromLoggedInUser(){
        User user = this.userService.findByUsername(securityService.findLoggedInUsername()).orElse(null);
        if(user == null) return ResponseEntity.ok(null);
        UserDto userDto = new UserDto(user.getUsername(), user.getCalendarEvents());
        return ResponseEntity.ok(userDto);
    }
}
