/**
 @author nick.drakopoulos
 */
package com.ef.parser;

import com.ef.parser.shell.SpringShell;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ParserApplication {

	private static ApplicationContext ctx;

	public static void main(String[] args) throws Exception{
		ctx = SpringApplication.run(ParserApplication.class);
		SpringShell.get(ctx).runShell(args);
	}


}