package fr.pala.accounting.controller;

import fr.pala.accounting.service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController implements ErrorController {

    @Autowired
    HealthService healthService;

    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public String error() {
        return "Error";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }


    @GetMapping("/health2")
    public ResponseEntity health2() {
        return ResponseEntity.ok("Hello");
    }

    @RequestMapping("/healthCheck")
    public ResponseEntity checkHealth() {

        String parsedResult = healthService.sendGetToOCR();

        if (parsedResult.startsWith("Lorem Ipsum")) {
            return new ResponseEntity<>( HttpStatus.OK);
        }
        return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
