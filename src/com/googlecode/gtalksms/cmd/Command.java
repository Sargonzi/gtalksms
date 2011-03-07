package com.googlecode.gtalksms.cmd;

import java.util.StringTokenizer;

import android.content.Context;

import com.googlecode.gtalksms.MainService;
import com.googlecode.gtalksms.SettingsManager;
import com.googlecode.gtalksms.xmpp.XmppMsg;

public abstract class Command {    
    protected SettingsManager _settingsMgr;
    protected Context _context;
    protected MainService _mainService;
    protected final String[] _commands;
        
    Command(MainService mainService, String[] commands) {
        _mainService = mainService;
        _settingsMgr = mainService.getSettingsManager();
        _context = mainService.getBaseContext();
        _commands = commands;
    }
    
    protected String getString(int id, Object... args) {
        return _context.getString(id, args);
    }
    
    protected void send(String message) {
        _mainService.send(message);
    }
    
    protected void send(XmppMsg message) {
        _mainService.send(message);
    }
    
    public String[] getCommands() {
        return _commands;
    }
   
    /**
     * Executes the given command
     * has no return value, the method has to do the error reporting by itself
     * 
     * @param cmd command
     * @param args substring after the first ":" 
     */
    public abstract void execute(String cmd, String args);
    
    /**
     * Stop all ongoing actions caused by a command
     * gets called in mainService when "stop" command recieved
     */
    public void stop() {}
    
    /**
     * Cleans up the structures holden by the Command Class
     * Usually issued on exit of GtalkSMS
     */
    public void cleanUp() {};
    
    /**
     * Request a help String array from the command
     * The String is formated with your internal BOLD/ITALIC/etc Tags
     * 
     * @return Help String array, null if there is no help available
     */
    public abstract String[] help();
    
    protected String makeBold(String msg) {
        return XmppMsg.makeBold(msg);
    }
    
    protected String[] splitArgs(String args) {
        StringTokenizer strtok = new StringTokenizer(args, ":");
        int tokenCount = strtok.countTokens();
        String[] res = new String[tokenCount];
        for(int i = 0; i < tokenCount; i++)
            res[i] = strtok.nextToken();
        return res;
    }
}
