package com.dam.dani;

public class User
{
    private String name;
    private String pass;
    public boolean connected;

    public User(String name, String pass)
    {
        this.name = name;
        this.pass = pass;
    }

    public User()
    {

    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPass()
    {
        return pass;
    }

    public void setPass(String pass)
    {
        this.pass = pass;
    }

    @Override
    public String toString()
    {
        return getName();
    }

    public boolean isConnected()
    {
        return connected;
    }

    public void setConnected(boolean connected)
    {
        this.connected = connected;
    }
}