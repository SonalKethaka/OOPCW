package com.example.Event.Ticketing.System.Configuraion;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/configuration")
public class ConfigurationController {

    @PostMapping
    public void saveConfiguration(@RequestBody Configuration config) throws IOException{
        ConfigurationManager.saveConfigurationAsJson(config);
    }

    @GetMapping
    public Configuration loadConfig() throws IOException {
        return ConfigurationManager.loadConfigurationFromJson();
    }
}
