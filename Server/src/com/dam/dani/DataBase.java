package com.dam.dani;

import java.sql.*;

public class DataBase
{
    public Connection conexion;

    public DataBase()
    {
        connect();
    }

    //TODO MODIFY THE PASSWORD WHEN FINISH
    public void connect()
    {
        this.conexion = null;
        try
        {
            Class.forName ("com.mysql.jdbc.Driver").newInstance();
            this.conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/portsDB", "root", "");
        }
        catch (ClassNotFoundException cnfe)
        {
            cnfe.printStackTrace();
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
        }
        catch (InstantiationException ie)
        {
            ie.printStackTrace();
        }
        catch (IllegalAccessException iae)
        {
            iae.printStackTrace();
        }
    }

    public void disconnect()
    {
        try
        {
            conexion.close();
            conexion = null ;
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
    }

    public void insertUser(String user, String pass)
    {
        String sentenciaSql = "INSERT INTO users (userName, pass, conections) VALUES (?, ?, ?)";
        PreparedStatement sentencia = null ;
        try
        {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString (1, user );
            sentencia.setString (2, pass);
            sentencia.setInt(3, 0);
            sentencia.executeUpdate();
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
        finally
        {
            if(sentencia != null)
            {
                try
                {
                    sentencia.close();
                }
                catch(SQLException sqle)
                {
                    sqle.printStackTrace();
                }
            }
        }
    }

    public void modifyState(String user, boolean connected)
    {
        String sentenciaSql = "UPDATE users SET conections = ? WHERE userName = ?";
        PreparedStatement sentencia = null ;
        try
        {
            sentencia = conexion.prepareStatement(sentenciaSql);
            if(connected)
                sentencia.setInt(1, 1);
            else
                sentencia.setInt(1, 0);
            sentencia.setString(2, user);
            sentencia.executeUpdate();
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
        finally
        {
            if (sentencia != null)
            {
                try
                {
                    sentencia.close();
                }
                catch(SQLException sqle)
                {
                    sqle.printStackTrace();
                }
            }
        }
    }

    public int checkUser(String user)
    {
        String sentenciaSql = "SELECT COUNT(*) as cuenta FROM users WHERE userName = ?";

        PreparedStatement sentencia = null ;
        ResultSet resultado = null ;
        try
        {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, user);
            resultado = sentencia.executeQuery ();
            resultado.next();
            return resultado.getInt(1);

        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
        finally
        {
            if (sentencia != null)
            {
                try
                {
                    sentencia.close();
                }
                catch(SQLException sqle)
                {
                    sqle.printStackTrace();
                }
            }
        }
        return  -1;
    }

    public boolean checkConnection(String user, String pass)
    {
        String sentenciaSql = "SELECT COUNT(*) as cuenta FROM users WHERE userName = ? and pass = ?";

        PreparedStatement sentencia = null ;
        ResultSet resultado = null ;
        try
        {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, user);
            sentencia.setString(2, pass);
            resultado = sentencia.executeQuery ();
            resultado.next();
            if(resultado.getInt(1) == 1)
                return true;
            else
                return false;

        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
        finally
        {
            if (sentencia != null)
            {
                try
                {
                    sentencia.close();
                }
                catch(SQLException sqle)
                {
                    sqle.printStackTrace();
                }
            }
        }
        return false;
    }

    public int getNumberUsers()
    {
        String sentenciaSql = "SELECT COUNT(*) as cuenta FROM users";

        PreparedStatement sentencia = null ;
        ResultSet resultado = null ;
        try
        {
            sentencia = conexion.prepareStatement(sentenciaSql);

            resultado = sentencia.executeQuery ();
            resultado.next();
            return resultado.getInt(1);
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
        finally
        {
            if (sentencia != null)
            {
                try
                {
                    sentencia.close();
                }
                catch(SQLException sqle)
                {
                    sqle.printStackTrace();
                }
            }
        }
        return -1;
    }

    public String getUsers(String userName)
    {
        String users = "";

        String sentenciaSql = "SELECT userName, conections as cuenta FROM users";

        PreparedStatement sentencia = null ;
        ResultSet resultado = null ;
        try
        {
            sentencia = conexion.prepareStatement(sentenciaSql);

            resultado = sentencia.executeQuery ();
            while(resultado.next())
            {
                String name = resultado.getString(1);
                int con = resultado.getInt(2);
                if(!name.equals(userName))
                {
                    if(resultado.isLast())
                        users = users + name + ", " + con;
                    else
                        users = users + name + ", " + con + ", ";
                }
            }
            return users;
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
        finally
        {
            if (sentencia != null)
            {
                try
                {
                    sentencia.close();
                }
                catch(SQLException sqle)
                {
                    sqle.printStackTrace();
                }
            }
        }

        return users;
    }
}