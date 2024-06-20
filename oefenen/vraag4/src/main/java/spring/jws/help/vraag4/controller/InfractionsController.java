package spring.jws.help.vraag4.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.jws.help.vraag4.jpa.Infraction;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
public class InfractionsController {

    /*@Autowired
    private InfractionRepository repo;

    ObjectMapper mapper = new ObjectMapper();
    TypeReference<List<Infraction>> typeReference = new TypeReference<List<Infraction>>(){};
    InputStream inputStream = TypeReference.class.getResourceAsStream("classpath:static/infractions.json");

    List<Infraction> infractions = mapper.readValue(inputStream, typeReference);*/

    @Value("classpath:static/infractions.json")
    public InputStream data;
    ObjectMapper objectMapper = new ObjectMapper();
    private List<Infraction> infractions = new ArrayList<>();

    @GetMapping("/infractions")
    public String index() {
        return "index";
    }

    @GetMapping("/infractions/{aantal}")
    public String aantalInfractions(@PathVariable("aantal") int aantal, Model model) throws IOException {
        if (infractions.size() < 1) {
            infractions = Arrays.asList(objectMapper.readValue(data, Infraction[].class));
        }

        List<Infraction> results = new ArrayList<>();

        for (Infraction infraction : infractions) {
            try {
                if (Integer.parseInt(infraction.getInfractions_speed()) >= aantal) {
                    results.add(infraction);
                }
            } catch (Exception ex) {
            }
        }

        Collections.sort(results);

        model.addAttribute("infractions", results);

        return "infractionsResult";
    }

    @GetMapping("/infractions/form")
    public String infractionsForm() {
        return "infractionsForm";
    }

    @PostMapping("/year")
    public String infractionsFromYear(@RequestParam("year") int year, Model model) throws IOException {
        if (infractions.size() < 1) {
            infractions = Arrays.asList(objectMapper.readValue(data, Infraction[].class));
        }

        List<Infraction> results = new ArrayList<>();

        for (Infraction infraction : infractions) {
            try {
                if (Integer.parseInt(infraction.getYear()) == year) {
                    results.add(infraction);
                }
            } catch (Exception ex) {
            }
        }

        Collections.sort(results);

        model.addAttribute("infractions", results);

        return "infractionsResult";
    }
}
