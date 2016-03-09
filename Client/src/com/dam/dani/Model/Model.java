package com.dam.dani.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Model
{
    public void writeFile(String name, String[] lines)
    {
        FileWriter fichero = null ;
        PrintWriter escritor = null ;
        try
        {
            fichero = new FileWriter(name + ".txt");
            escritor = new PrintWriter(fichero );
            for(int i=0; i<lines.length; i++)
            {
                escritor.println(lines[i]);
            }
        }
        catch ( IOException ioe )
        {
            ioe . printStackTrace ();
        }
        finally
        {
            if ( fichero != null )
            {
                try
                {
                    fichero.close();
                }
                catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }
            }
        }
    }

    public void writeFile(String line, Boolean op)
    {
        FileWriter fichero = null ;
        PrintWriter escritor = null ;
        try
        {
            fichero = new FileWriter("Ignored.txt", op);
            escritor = new PrintWriter(fichero);
            escritor.println(line);
            fichero.close();
        }
        catch ( IOException ioe )
        {
            ioe . printStackTrace ();
        }

    }

    public void writeFile(List<String> lines)
    {
        FileWriter fichero = null ;
        PrintWriter escritor = null ;
        try
        {
            fichero = new FileWriter("Ignored.txt", false);
            escritor = new PrintWriter(fichero);
            for (String s: lines) {
                escritor.println(s);
            }

            fichero.close();
        }
        catch ( IOException ioe )
        {
            ioe . printStackTrace ();
        }

    }

    public List<String> readFile()
    {
        List<String> lineas = new ArrayList<>();

        if (new File("Ignored.txt").exists()) {

            File fichero = null ;
            FileReader lector = null ;
            BufferedReader buffer = null ;
            try
            {
                buffer = new BufferedReader(new FileReader(new File("Ignored.txt")));
                String linea = null ;
                while((linea = buffer.readLine()) != null)
                {
                    lineas.add(linea);
                }
                return lineas;
            }
            catch(FileNotFoundException fnfe)
            {
                fnfe.printStackTrace();
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
            finally
            {
                if (buffer != null)
                {
                    try
                    {
                        buffer.close();
                    }
                    catch(IOException ioe)
                    {
                        ioe.printStackTrace();
                    }
                }
            }
        }

        return lineas;
    }

    public void removeLine(String line)
    {
        FileReader fichero = null ;
        BufferedReader read = null ;
        List<String> lineas = new ArrayList<>();
        String x;
        try
        {
            fichero = new FileReader("Ignored.txt");
            read = new BufferedReader(fichero);
            while((x = read.readLine()) != null)
            {
                if (!line.equalsIgnoreCase(x)) lineas.add(x);
            }

            if (lineas.size() == 0) {

                writeFile("", false);
            } else {

                writeFile(lineas);
            }


        } catch ( IOException ioe )
        {
            ioe.printStackTrace();
        }
        finally
        {
            if ( fichero != null )
            {
                try
                {
                    fichero.close();
                }
                catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }
            }
        }
    }

    public ArrayList<String> readFile(String name)
    {
        File fichero = null ;
        FileReader lector = null ;
        BufferedReader buffer = null ;
        ArrayList<String> lineas = new ArrayList<>();
        try
        {
            buffer = new BufferedReader(new FileReader(new File(name + ".txt")));
            String linea = null ;
            while((linea = buffer.readLine()) != null)
            {
                lineas.add(linea);
            }
            return lineas;
        }
        catch(FileNotFoundException fnfe)
        {
            fnfe.printStackTrace();
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
        finally
        {
            if (buffer != null)
            {
                try
                {
                    buffer.close();
                }
                catch(IOException ioe)
                {
                    ioe.printStackTrace();
                }
            }
        }
        return lineas;
    }

    public void writeProperties(String ip)
    {
        Properties configuracion = new Properties();
        configuracion.setProperty ("ip", ip);
        try
        {
            configuracion.store(new FileOutputStream("config.props"), "Fichero de configuracion");
        }
        catch(FileNotFoundException fnfe)
        {
            fnfe.printStackTrace();
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    public String readProperties()
    {
        Properties configuracion = new Properties ();
        String ip = "";
        try
        {
            configuracion.load(new FileInputStream("config.props"));
            ip = configuracion.getProperty("ip");
        }
        catch(FileNotFoundException fnfe)
        {
            fnfe.printStackTrace();
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }

        return ip;
    }
}