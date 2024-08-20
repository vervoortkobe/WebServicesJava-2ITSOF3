package ap.spring.herexamen.boekenproject.controller;

import ap.spring.herexamen.boekenproject.jpa.Boek;
import ap.spring.herexamen.boekenproject.jpa.BoekenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BoekenController {

    @Autowired
    private BoekenRepository repo;

    @GetMapping("/home")
    public String getHome(@RequestParam(name = "sort", defaultValue = "title") String sort,
            @RequestParam(name = "search", defaultValue = "") String search, Model model) {

        List<Boek> books = (List<Boek>) this.repo.findAll();
        books = filterBooks(books, search);
        books = sortBooks(books, sort);

        model.addAttribute("books", books);
        model.addAttribute("sort", sort);
        model.addAttribute("search", search);
        return "home";
    }

    @GetMapping("/newbook")
    public String getNewBook() {
        return "newbook";
    }

    @PostMapping("/newbook")
    public String createBook(@RequestParam String title, @RequestParam String author) {
        try {
            Boek found = this.repo.findByTitle(title);
            if (found != null)
                return "exists";
            else
                this.repo.save(new Boek(title, author));

        } catch (Exception ex) {
            System.out.println("error boek aanmaken");
        }
        return "redirect:/home";
    }

    @PostMapping("/delete")
    public String deleteBook(@RequestParam String title) {
        try {
            Boek found = this.repo.findByTitle(title);
            if (found != null)
                this.repo.delete(found);

        } catch (Exception ex) {
            System.out.println("error boek deleten");
        }
        return "redirect:/home";
    }

    private List<Boek> filterBooks(List<Boek> books, String search) {
        if (!search.isEmpty()) {
            return books.stream()
                    .filter(b -> b.getTitle().toLowerCase().contains(search.toLowerCase()) ||
                            b.getAuthor().toLowerCase().contains(search.toLowerCase()))
                    .collect(Collectors.toList());
        }
        return books;
    }

    private List<Boek> sortBooks(List<Boek> books, String sort) {
        switch (sort) {
            case "title":
                books.sort(Comparator.comparing(Boek::getTitle));
                break;
            case "actors":
                books.sort(Comparator.comparing(Boek::getAuthor));
                break;
        }
        return books;
    }
}
