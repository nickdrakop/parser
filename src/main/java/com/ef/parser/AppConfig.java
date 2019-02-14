/**
 @author nick.drakopoulos
 */
package com.ef.parser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.shell.CommandLine;
import org.springframework.shell.core.JLineShellComponent;

@Configuration
@ComponentScan(basePackages={"org.springframework.shell.commands", "org.springframework.shell.converters", "com.ef.parser"})
@PropertySource({"classpath:environment.default.properties"})
public class AppConfig {

    @Bean("shell")
    public JLineShellComponent jLineShellComponet(){
        return new JLineShellComponent();
    }

    @Bean
    public CommandLine commandLine(){
        return new CommandLine(null, 3000, null);
    }


}