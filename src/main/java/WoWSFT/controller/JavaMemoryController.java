package WoWSFT.controller;

import WoWSFT.service.JavaMemoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JavaMemoryController
{
    private final JavaMemoryService javaMemoryService;

    public JavaMemoryController(JavaMemoryService javaMemoryService)
    {
        this.javaMemoryService = javaMemoryService;
    }

    @RequestMapping(value = "/javaMemory", method = RequestMethod.GET)
    public String getJavaMemory()
    {
        return javaMemoryService.showMemory();
    }
}
