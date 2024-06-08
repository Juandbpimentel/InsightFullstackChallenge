package br.ufc.quixada.SupplierApplicationInsight.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/")
public class AppController {
    @GetMapping(path = "/isup")
    public ResponseEntity<String> verifyAPIStatus() {
        return ResponseEntity.ok("The API is up!");
    }
}
