/**
 @author nick.drakopoulos
 */
package com.ef.parser.shell;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultPromptProvider;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomPromptProvider extends DefaultPromptProvider {

    public String getPrompt() {
        return ">";
    }

    public String getProviderName() {
        return "Parser Prompt";
    }
}