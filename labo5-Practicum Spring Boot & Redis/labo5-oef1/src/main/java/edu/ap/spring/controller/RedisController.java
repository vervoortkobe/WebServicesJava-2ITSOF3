package edu.ap.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.ap.spring.redis.RedisService;

@Controller
public class RedisController {
  private final RedisService redisService;

    public RedisController(RedisService redisService) {
      this.redisService = redisService;
    }

    @GetMapping("/")
    public String index(Model model) {
      model.addAttribute("messages", redisService.getAllMessages("chat"));
      return "index";
    }

    @PostMapping("/send")
    public String send(@RequestParam String message) {
      redisService.sendMessage("chat", message);
      return "redirect:/";
    }
}