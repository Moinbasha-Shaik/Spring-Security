package in.basha.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuth2Controller {


    
    @GetMapping("/api/auth/oauth2/success")
    public String getMsg() {
    	return "welcome to OAUth2";
    }
}
