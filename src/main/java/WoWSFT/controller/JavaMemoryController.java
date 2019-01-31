package WoWSFT.controller;

import WoWSFT.service.JavaMemoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Qualson-Lee on 2017-06-13.
 */
@RestController
public class JavaMemoryController
{
    @Autowired
    private JavaMemoryService javaMemoryService;

    @RequestMapping(value = "/javaMemory", method = RequestMethod.GET)
    public String getJavaMemory()
    {
        return javaMemoryService.showMemory();
    }
}
