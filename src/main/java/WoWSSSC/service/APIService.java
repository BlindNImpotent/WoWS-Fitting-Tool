package WoWSSSC.service;

import WoWSSSC.parser.AsyncHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Aesis on 2016-10-15.
 */
@Service
public class APIService
{
   @Autowired
    private AsyncHashMap asyncHashMap;

    public LinkedHashMap<String, LinkedHashMap> getData()
    {
        return asyncHashMap.getData();
    }

}
