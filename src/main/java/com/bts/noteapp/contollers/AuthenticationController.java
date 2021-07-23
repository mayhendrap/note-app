package com.bts.noteapp.contollers;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bts.noteapp.dto.AuthenticationRequest;
import com.bts.noteapp.dto.AuthenticationResponse;
import com.bts.noteapp.dto.ResponseData;
import com.bts.noteapp.dto.UserData;
import com.bts.noteapp.models.entities.Role;
import com.bts.noteapp.models.entities.UserDAO;
import com.bts.noteapp.services.CustomUserDetailsService;
import com.bts.noteapp.services.RoleServices;
import com.bts.noteapp.services.UserService;
import com.bts.noteapp.utils.jwt.JwtUtils;

import io.jsonwebtoken.impl.DefaultClaims;

@RestController
@RequestMapping(path = "api/v1")
public class AuthenticationController {

	@Autowired
    private UserService userService;
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
    @Autowired
    private RoleServices roleServices;
    
    @Autowired
	private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtUtils jwtUtils;
    
	@GetMapping
	public String test() {
		return "Test";
	}

    @PostMapping("register")
    public ResponseEntity<ResponseData<UserDAO>> register(@RequestBody UserData userData) {
    	
        ResponseData<UserDAO> responseData = new ResponseData<>();
        UserDAO user = new UserDAO();

        user.setFullName(userData.getFullName());
        user.setEmail(userData.getEmail());
        user.setPassword(userData.getPassword());

        for (String roleName : userData.getRoles()) {
            Role role = roleServices.findByRole(roleName);
            user.getRoles().add(role);
        }

        responseData.setStatus(true);
        responseData.getMessage().add("User created");
        responseData.setPayload(userService.registerUser(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
    }
    
    @PostMapping("login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
    	
    	ResponseData<AuthenticationResponse> responseData = new ResponseData<>();
    	
		try {
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
			authenticationManager.authenticate(authentication);
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		String token = jwtUtils.generateToken(userDetails);
		
		responseData.setStatus(true);
		responseData.getMessage().add("Success");
		responseData.setPayload(new AuthenticationResponse(token));
		
		return ResponseEntity.status(HttpStatus.OK).body(responseData);
	}
    
    @GetMapping("refreshtoken")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) throws Exception {
    	
    	DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");
    	
    	Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
		String token = jwtUtils.generateRefreshToken(expectedMap, expectedMap.get("sub").toString());
		return ResponseEntity.ok(new AuthenticationResponse(token));
    }
    
    public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
		Map<String, Object> expectedMap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : claims.entrySet()) {
			expectedMap.put(entry.getKey(), entry.getValue());
		}
		return expectedMap;
	}
}
