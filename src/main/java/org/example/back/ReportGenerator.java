package org.example.back;

import java.nio.file.*;
import java.io.IOException;
import java.util.Map;

public class ReportGenerator {

    private LogProcessor logProcessor;
    private String lastReport;
    //Creamos el constructor con if para que no pueda ser null y lanzando la excepcion
    public ReportGenerator(LogProcessor logProcessor) {
        if (logProcessor == null) {
            throw new IllegalArgumentException("LogProcessor no puede ser null");
        }
        this.logProcessor = logProcessor;
    }
    //Metodo para generar el reporte
    public String generateReport() {
        //Utilizamos el map para que nos devuelva los niveles del log (INFO, ERROR, etc..)
        Map<String, Integer> stats = logProcessor.getStatistics();
        //Creamos Stringbuilder para construir el informe
        StringBuilder report = new StringBuilder();

        //Creamos la variable para el contador que ira en el bucle for
        int total = 0;
        for (String level : stats.keySet()) {
            int count = stats.get(level);
            total += count;
            report.append(String.format("%s: %d\n", level, count));
        }
        report.append("------------------------\n");
        report.append(String.format("TOTAL: %d\n", total));

        //Convierte el Stringbuilder en un String.
        lastReport = report.toString();

        return lastReport;
    }
    //En este metodo exportamos el reporte a un archivo.
    public void exportReport(String file) {
        //Convertimos el String en un path.
        Path path = Paths.get("files",file);
        //Si no existe el archivo en la ruta se crea uno.
        try {
            if (!Files.exists(path)){
                Files.createFile(path);
            }
            //Si el informe no se ha generado, llama al metodo "generate" para generarlo.
            if (lastReport == null) {
                lastReport = generateReport();
            }
            //Escribe el contenido del informe dentro de la ruta y si el archivo ya existe lo sobreescribe.
            Files.writeString(path, lastReport);
        } catch (IOException e) {
            System.err.println("Error al escribir el informe: " + e.getMessage());
        }

    }

}
