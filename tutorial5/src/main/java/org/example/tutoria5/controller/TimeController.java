package org.example.tutoria5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.zone.ZoneRulesException;

@Controller
public class TimeController {

    @GetMapping("/current-time")
    @ResponseBody
    public String currentTime(@RequestParam(required = false) String timezone, @RequestParam(required = false) String format) {
        String defaultFormat = "HH:mm:ss.SSSS yyyy/MM/dd";
        StringBuilder warning = new StringBuilder();

        DateTimeFormatter formatter;
        try {
            formatter = format == null ? DateTimeFormatter.ofPattern(defaultFormat) : DateTimeFormatter.ofPattern(format);
        } catch (IllegalArgumentException e) {
            formatter = DateTimeFormatter.ofPattern(defaultFormat);
            warning.append("Invalid format provided. Defaulting to HH:mm:ss.SSSS yyyy/MM/dd.\n");
        }

        ZonedDateTime zonedDateTime;
        try {
            zonedDateTime = timezone == null ? ZonedDateTime.now() : ZonedDateTime.now(ZoneId.of(timezone));
        } catch (ZoneRulesException e) {
            zonedDateTime = ZonedDateTime.now();
            warning.append("Invalid time zone provided. Defaulting to system time zone.\n");
        }

        String currentTimeHTML;

        try{
            currentTimeHTML = Files.readString(Path.of("src/main/resources/static/current-time.html"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return warning.isEmpty() ? currentTimeHTML.replace("TIME", zonedDateTime.format(formatter)).replace("WARNING", "") :
                currentTimeHTML.replace("TIME", zonedDateTime.format(formatter)).replace("WARNING", warning);
    }

    @GetMapping("/current-year")
    @ResponseBody
    public String currentYear() {
        String currentYearHTML;

        try{
            currentYearHTML = Files.readString(Path.of("src/main/resources/static/current-year.html"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return currentYearHTML.replace("YEAR", Year.now().toString());
    }
}
