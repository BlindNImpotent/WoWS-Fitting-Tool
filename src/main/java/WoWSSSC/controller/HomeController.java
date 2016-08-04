package WoWSSSC.controller;

import WoWSSSC.service.HomeService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

/**
 * Created by Aesis on 2016-08-04.
 */
@Controller
public class HomeController
{
    @Autowired
    private HomeService homeService;

    @RequestMapping (value = "/", method = RequestMethod.GET)
    public String home(Model model) throws IOException, ParseException
    {
        model.addAttribute("name", homeService.getNameList());
        return "home";
    }

    @RequestMapping (value = "/ship/{name}", method = RequestMethod.GET)
    public String getShip(Model model, @PathVariable("name") String name) throws IOException, ParseException
    {
        model.addAttribute("ship", homeService.getCalc(name));

        return "ship";
    }
}
