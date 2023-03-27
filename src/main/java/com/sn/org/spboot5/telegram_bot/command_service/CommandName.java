package com.sn.org.spboot5.telegram_bot.command_service;

public enum CommandName {
    START("/start"),
    TEST("/test"),
    HELP("/help"),
    BUTTON("BUTTON"),
    INLINE("/inline"),
    STOP("/stopbot"),
    NO("NO");

    private final String comName;

    CommandName(String commandName) {
        this.comName = commandName;
    }
    public String getComName(){
        return comName;
    }
}
