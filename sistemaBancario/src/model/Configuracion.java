package model;

import java.util.Properties;
import java.io.*;

public class Configuracion {
    public static double obtenerTasaAhorro() {
        return leerTasa("tasaAhorro");
    }

    public static double obtenerTasaDebito() {
        return leerTasa("tasaDebito");
    }

    private static double leerTasa(String clave) {
        try (InputStream input = new FileInputStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            return Double.parseDouble(prop.getProperty(clave));
        } catch (Exception e) {
            return 0.02; // valor por defecto si no se encuentra
        }
    }
}

