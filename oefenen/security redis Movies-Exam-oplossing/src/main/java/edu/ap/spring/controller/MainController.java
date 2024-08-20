package edu.ap.spring.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import edu.ap.spring.redis.RedisService;
import jakarta.annotation.PostConstruct;

@Controller
public class MainController {

	@Autowired
	private RedisService service;

  	@PostConstruct
  	private void init() {
  	}

  	@GetMapping(value="/")
   	public String index() {
	   return "redirect:/home";
   	}

	@GetMapping(value="/home")
   	public String home(Model model) {

		Set<String> movies = this.service.keys("movies:*");
		ArrayList<String> movies2 = new ArrayList<String>();
		for(String m : movies) {
			movies2.add(m.split(":")[2]);
		}
		Collections.sort(movies2);
		model.addAttribute("movies2", movies2);
	   	
		return "home";
   	}

	@GetMapping(value="/login")
  	public String geLoginForm() {
	  return "login";
  	}

	@GetMapping(value="/search")
  	public String geUserForm() {
	  return "search";
  	}

	@PostMapping(value="/searchByYear")
  	public String searchByYear(@RequestParam String year, Model model) {

		Set<String> keys = this.service.keys("movies:" + year + ":*");
		ArrayList<String> found = new ArrayList<String>();
		for(String k : keys) {
			found.add(k.split(":")[2]);
		}

		model.addAttribute("found", found);
		
		return "search";
	}

	@PostMapping(value="/searchByActor")
  	public String searchByActor(@RequestParam String actor, Model model) {

		Set<String> keys = this.service.keys("movies:*");
		ArrayList<String> found = new ArrayList<String>();
		for(String k : keys) {
			List<String> actors = this.service.getList(k);
			if(actors.contains(actor)) {
				found.add(k.split(":")[2]);
			}
		}
		
		model.addAttribute("found", found);

		return "search";
	}

	@PostMapping(value="/delete")
  	public String delete(@RequestParam String movieName) {
	
		// look up movie irrespective of year
		Set<String> keys = this.service.keys("movies:*:" + movieName);
		// should only contain one key
		if(keys.size() > 0) {
			this.service.delete(keys.stream().findFirst().get());
		}

		return "redirect:/home";
	}
}
