package org.example.back;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogProcessor {

    //Creo los metodos privados logPath y lines para poder usarlo en todos los metodos
    //sin tener que crearlos repetidas veces.
    private Path logPath;
    private List<String> lines;

    public LogProcessor (String filePath) throws IOException{
        //Comprobamos que la ruta del archivo no sea null.
        if(filePath == null){
            throw new IllegalArgumentException("La ruta del archivo no puede ser nula");
        }

        //Convierte el String a un path.
        logPath = Paths.get(filePath);

        //Comprueba que existe el archivo en la ruta.
        if(!Files.exists(logPath)){
            throw new IOException("El archivo no existe");
        }

        //Decimos que lea toda las lineas del archivo para que ya esten disponibles
        //para los proximos metodos
        lines = Files.readAllLines(logPath);
    }

    public List<String> filterByLevel(String level){
        //Lista vacia donde puedo guardar las líneas que coincidan.
        List<String> result = new ArrayList<>();

        //Creo un bucle que revisara cada linea del fichero log.
        for (String line : lines){
            //Añado un if para verificar que en esa linea esta el parametro pedido.
            if (line.contains(level)){
                result.add(line);
            }
        }
        return result;
    }

    public List<String> searchText(String text){
        //Lista vacia donde puedo guardar las líneas que coincidan.
        List<String> result = new ArrayList<>();

        //Creo un bucle que revisara cada linea del fichero log.
        for (String line : lines){
            //Añado un if para verificar que en esa linea esta el parametro pedido.
            if (line.contains(text)){
                result.add(line);
            }
        }
        //Devuelvo el resultado.
        return result;
    }

    public int count(String level){
        //Creamos una variable para el contador.
        int count = 0;

        //Creamos un bucle que leera cada linea.
        for (String line: lines){
            //Creamos un if para verificar que en la linea esta "ERROR"
            //y sumamos uno al contador.
            if (line.contains(level)){
                count++;
            }
        }
        //Devolvemos el contador.
        return count;
    }

    public Map<String, Integer> getStatistics(){
       //Creamos el mapa con cada nivel del log iniciados en 0.
       Map<String, Integer> stats = new HashMap<>();
       stats.put("ERROR", 0);
       stats.put("WARN", 0);
       stats.put("INFO", 0);
       stats.put("DEBUG", 0);

       //Creamos un bucle en el que cada vez que se lea una linea se sume
       //+1 en cada nivel del mapa.
       for (String line: lines){
           if (line.contains("ERROR")){
               stats.put("ERROR", stats.get("ERROR") +1);
           }else if (line.contains("WARN")){
               stats.put("WARN", stats.get("WARN") +1);
           }else if (line.contains("INFO")){
               stats.put("INFO", stats.get("INFO") +1);
           }else if (line.contains("DEBUG")){
               stats.put("DEBUG", stats.get("DEBUG") +1);
           }
       }
       return stats;
    }

}

