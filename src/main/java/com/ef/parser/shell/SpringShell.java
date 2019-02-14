/**
 @author nick.drakopoulos
 */
package com.ef.parser.shell;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.shell.CommandLine;
import org.springframework.shell.SimpleShellCommandLineOptions;
import org.springframework.shell.core.JLineShellComponent;

public class SpringShell {

    private final ApplicationContext ctx;

    private SpringShell(ApplicationContext applicationContext) {
        this.ctx = applicationContext;
    }

    public void runShell(String[] args) throws Exception{

        CommandLine commandLine = SimpleShellCommandLineOptions.parseCommandLine(args);
        String[] commandsToExecuteAndThenQuit = commandLine.getShellCommandsToExecute();

        JLineShellComponent shell = ctx.getBean(JLineShellComponent.class);

        if (null != commandsToExecuteAndThenQuit) {
            boolean successful;
            for (String cmd : commandsToExecuteAndThenQuit) {
                successful = shell.executeCommand(cmd).isSuccess();
                if (!successful) {
                    break;
                }
            }
        } else {
            shell.start();
            shell.promptLoop();
            shell.waitForComplete();
        }
        ((ConfigurableApplicationContext) ctx).close();
    }

    public static SpringShell get(ApplicationContext ctx) {
        return new SpringShell(ctx);
    }
}
