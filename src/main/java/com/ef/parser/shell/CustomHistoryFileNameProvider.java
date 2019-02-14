/**
 @author nick.drakopoulos
 */
package com.ef.parser.shell;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultHistoryFileNameProvider;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomHistoryFileNameProvider extends DefaultHistoryFileNameProvider {

    public String getHistoryFileName() {
        return "parser-shell.log";
    }

    public String getProviderName() {
        return "Parser Shell History";
    }

}