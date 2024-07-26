package com.github.mrko900.blockanim;

import java.util.Properties;

public class MessageManager {
    private Properties messages;

    public MessageManager(Properties messages) {
        this.messages = messages;
    }

    public String[] getLines(String msg) {
        String s = messages.getProperty(msg);
        System.out.println("s");
        System.out.println(s);
        if (s == null) {
            return null;
        }
        return s.split("\n");
    }

    public String get(String msg) {
        return messages.getProperty(msg);
    }
}
