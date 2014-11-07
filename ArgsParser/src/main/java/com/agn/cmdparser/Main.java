package com.agn.cmdparser;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


// Tip defines how to start without maven
/* - to start application use command line like this:
..\ArgsParser\out\production\ArgsParser>java -cp ../../../lib/Spring_lib/*;../../../lib/*;. com.agn.cmdparser.Main

- to make JAR:
  H:\AllGitRepository\ArgsParserREPO\ArgsParser\out\production\ArgsParser>jar -cvmf manifest.txt MainConsoleInput.jar .

- to start JAR:
..\ArgsParserREPO\ArgsParser\lib>java -cp "Spring_lib/*;MainConsoleInput.jar;*" com.agn.cmdparser.Main
 */

public class Main {
    public static void main(String[] args) {
        Logger LOG = Logger.getLogger(Main.class);
        LOG.info("The application is started!");
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        ConsoleInput consoleInput = (ConsoleInput) context.getBean("consoleInput");

        consoleInput.startConsole(args);

        LOG.info("The application is ended!");
    }
}