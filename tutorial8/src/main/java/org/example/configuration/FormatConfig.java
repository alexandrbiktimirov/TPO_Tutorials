package org.example.configuration;

import com.google.googlejavaformat.java.Formatter;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FormatConfig {

    @Bean
    public Formatter formatter(){
        return new Formatter();
    }

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
}
