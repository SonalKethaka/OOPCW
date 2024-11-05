package com.example.Event.Ticketing.System.Configuraion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/configuration")
public class ConfigurationController {

//    @PostMapping
//    public void saveConfiguration(@RequestBody Configuration config) throws IOException{
//        ConfigurationManager.saveConfigurationAsJson(config);
//    }
//
//    @GetMapping
//    public Configuration loadConfig() throws IOException {
//        return ConfigurationManager.loadConfigurationFromJson();
//    }

    private final ConfigService configService;

    @Autowired
    public ConfigurationController(ConfigService configService) {
        this.configService = configService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveConfig(@RequestBody Configuration configuration) throws IOException {
        configService.saveConfigurationAsJson(configuration);
        configService.saveConfigurationAsText(configuration);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
