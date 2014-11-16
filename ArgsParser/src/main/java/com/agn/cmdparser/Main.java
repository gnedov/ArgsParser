package com.agn.cmdparser;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


// Tip defines how to start without maven
/* - to start application use command line like this:
..\AllGitRepository\ArgsParserREPO\ArgsParser\target\classes >java -cp ../../lib/Spring_lib/*;../../lib/*;. com.agn.cmdparser.Main

- to make JAR:
..\AllGitRepository\ArgsParserREPO\ArgsParser\target\classes>jar -cvmf ..\manifest.txt ..\ArgsParser-1.0-SNAPSHOT.jar *

- to start JAR:
..\AllGitRepository\ArgsParserREPO\ArgsParser\target>java -cp "../lib/Spring_lib/*;../lib/*;./ArgsParser-1.0-SNAPSHOT.jar" com.agn.cmdparser.Main
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