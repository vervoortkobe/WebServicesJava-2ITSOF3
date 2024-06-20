package com.examen.vraag2.datecheck.controller;

import com.examen.vraag2.datecheck.jpa.DateCheck;
import com.examen.vraag2.datecheck.jpa.DateCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class DateCheckController {

    @Autowired
    DateCheckRepository dateCheckEntryRepository;

    @GetMapping("/")
    public String index(){
        return  "redirect:/date";
    }

    @GetMapping("/date")
    public String datePage(){
        return "dateForm";
    }

    @PostMapping("/validatedate")
    public String validateDate(@RequestParam("dateCheck") String dateCheck,
                               @RequestParam("dateStart") String dateStart,
                               @RequestParam("dateEnd") String dateEnd,
                               RedirectAttributes redirectAttributes){
        Boolean result = checkDate(formatToLocalDate(dateCheck), formatToLocalDate(dateStart), formatToLocalDate(dateEnd));
        Long amountOfDays = checkAmountOfDays(LocalDate.now(), formatToLocalDate(dateCheck));
        if(result){
            redirectAttributes.addAttribute("result", "Yes");
        } else {
            redirectAttributes.addAttribute("result", "No");
        }
        redirectAttributes.addAttribute("days",amountOfDays);

        dateCheckEntryRepository.save(new DateCheck(formatToLocalDate(dateCheck), result, amountOfDays));

        return "redirect:/result";
    }

    private Boolean checkDate(LocalDate dateCheck, LocalDate dateStart, LocalDate dateEnd){
        return (dateCheck.isAfter(dateStart) && dateCheck.isBefore(dateEnd));
    }

    private Long checkAmountOfDays(LocalDate dateCheck, LocalDate secondDate){
        return Duration.between(dateCheck.atStartOfDay(), secondDate.atStartOfDay()).toDays();
    }

    private LocalDate formatToLocalDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try{
            return LocalDate.parse(date, formatter);
        }catch(Exception e){
            return null;
        }
    }

    @GetMapping("/result")
    public String resultPage(Model model, @ModelAttribute("result") String result, @ModelAttribute("days") Long days){
        model.addAttribute("result", result);
        model.addAttribute("days", days);
        return "result";
    }
}
