package org.example.tutoria5.controller;

import org.example.tutoria5.utils.Conversion;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
public class ConversionController {
    @PostMapping("/convert")
    @ResponseBody
    public String convert(@RequestParam String value, @RequestParam String fromBase, @RequestParam String toBase) {
        int fromBaseInt, toBaseInt;
        String exceptionHTML;

        try {
            exceptionHTML = Files.readString(Path.of("src/main/resources/static/exception.html"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            fromBaseInt = Integer.parseInt(fromBase);
            toBaseInt = Integer.parseInt(toBase);
        } catch (NumberFormatException e) {
            return exceptionHTML.replace("EXC", "Please provide a correct numerical input");
        }

        if (fromBaseInt < 2 || fromBaseInt > 100) {
            return exceptionHTML.replace("EXC", "fromBase outside the allowed range");
        }
        if (toBaseInt < 2 || toBaseInt > 100) {
            return exceptionHTML.replace("EXC", "toBase outside the allowed range");
        }

        BigInteger decimal;

        try{
            decimal = Conversion.toDecimal(value, fromBaseInt);
        } catch (IllegalArgumentException e){
            return exceptionHTML.replace("EXC", e.getMessage());
        }

        String conversionHTML;
        try {
            conversionHTML = Files.readString(Path.of("src/main/resources/static/conversion.html"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String converted = Conversion.fromDecimal(decimal, toBaseInt);
        String bin = Conversion.fromDecimal(decimal, 2);
        String oct = Conversion.fromDecimal(decimal, 8);
        String dec = Conversion.fromDecimal(decimal, 10);
        String hex = Conversion.fromDecimal(decimal, 16);

        return conversionHTML.replace("FROMBASE", fromBase)
            .replace("TOBASE", toBase)
            .replace("VALUE", value)
            .replace("CONVERTED", converted)
            .replace("BINR", bin)
            .replace("OCTR", oct)
            .replace("DECR", dec)
            .replace("HEXR", hex);
    }
}