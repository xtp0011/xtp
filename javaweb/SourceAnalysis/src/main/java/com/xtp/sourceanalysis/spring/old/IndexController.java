package com.xtp.sourceanalysis.spring.old;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")

public class IndexController {
    @RequestMapping(value = {
            "/head",
            "/index"},
    method = RequestMethod.GET,
    headers = {
            "content-type=text/plain",
            "content-type=text/html"  },
    consumes = {
            "application/JSON",
            "application/XML"
    },
    produces = {
            "application/JSON"
    })
    String post(){
        return "Mapping applied along with headers";
    }
}
