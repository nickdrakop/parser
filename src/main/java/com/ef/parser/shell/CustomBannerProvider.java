/**
 @author nick.drakopoulos
 */
package com.ef.parser.shell;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultBannerProvider;
import org.springframework.shell.support.util.OsUtils;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomBannerProvider extends DefaultBannerProvider {

    public String getBanner() {
        StringBuffer buf = new StringBuffer();
        buf.append("=======================================")
                .append(OsUtils.LINE_SEPARATOR);
        buf.append("*          Parser Shell             *")
                .append(OsUtils.LINE_SEPARATOR);
        buf.append("=======================================")
                .append(OsUtils.LINE_SEPARATOR);
        buf.append("Version:")
                .append(this.getVersion());
        return buf.toString();
    }

    public String getVersion() {
        return "1.0.1";
    }

    public String getWelcomeMessage() {
        return "Welcome to Parser Shell. Type `help` to list all commands usage";
    }

    public String getProviderName() {
        return "Parser Banner";
    }
}