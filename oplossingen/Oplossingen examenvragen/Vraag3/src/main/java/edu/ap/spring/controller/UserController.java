package edu.ap.spring.controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import edu.ap.spring.redis.RedisService;

@Controller
public class UserController {

	@Autowired
	private RedisService service;

	@GetMapping("/")
	private String getIndex() {
		return "redirect:/signup";
	}

	@GetMapping("/signup")
	public String getUsers() {
		return "signup";
	}

	@PostMapping("/signup")
	@ResponseBody
	public String saveUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
		
		if(!service.exists("usercount")) {
			service.incr("usercount");
		}
		String key = "users:" + bytesToHex(email + password) + ":" + service.getKey("usercount");
		if(!service.exists(key)) {
			service.setKey(key, email);
			service.incr("usercount");
		}

		return "SIGNED UP";
	}

	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}

	@PostMapping("/login")
	@ResponseBody
	public String doLogin(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
		
		service.setBit("login", 1, true);
		String key = bytesToHex(email + password);
		Set<String> s = service.keys("users:" + key + "*");
		if(!s.isEmpty()) {
			return "LOGGED IN";
		} 
		else {
			return "NOT LOGGED IN";
		}
	}

	@GetMapping("/user/{userid}")
	@ResponseBody
	public String list() {
		return "email1@ap.be";
	}

	private String bytesToHex(String str) {
		String retString = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedhash = digest.digest((str).getBytes(StandardCharsets.UTF_8));
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < encodedhash.length; i++) {
				String hex = Integer.toHexString(0xff & encodedhash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			retString = hexString.toString();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		return retString;
	}
}
