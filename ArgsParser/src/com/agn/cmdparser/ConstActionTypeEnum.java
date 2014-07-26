package com.agn.cmdparser;

import java.util.HashMap;
import java.util.Map;

public class ConstActionTypeEnum {
    public static final String CREATE = "create";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";
    public static final String DISPLAY = "display";
    public static final String LOAD = "load";
    public static final String HELP = "help";
    public static final String EXIT = "exit";

    public static final int CREATE_CODE = 100;
    public static final int UPDATE_CODE = 200;
    public static final int DELETE_CODE = 300;
    public static final int DISPLAY_CODE = 400;
    public static final int LOAD_CODE = 500;
    public static final int HELP_CODE = 505; // like SOS :)
    public static final int EXIT_CODE = 999;

    public final Map<String, Integer> commandCode = new HashMap<>();

    public ConstActionTypeEnum() {
        commandCode.put(CREATE, CREATE_CODE);
        commandCode.put(UPDATE, UPDATE_CODE);
        commandCode.put(DELETE, DELETE_CODE);
        commandCode.put(DISPLAY, DISPLAY_CODE);
        commandCode.put(LOAD, LOAD_CODE);
        commandCode.put(HELP, HELP_CODE);
        commandCode.put(EXIT, EXIT_CODE);
    }

}
