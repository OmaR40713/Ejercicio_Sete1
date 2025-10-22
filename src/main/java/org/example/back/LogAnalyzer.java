package org.example.back;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class LogAnalyzer {
    //Creamos el main lanzando el IOException
    public static void main(String[] args) throws IOException {
        //Creamos las variables de los argumentos y el Path
        String operation = args[0];
        String savefile = args[1];
        Path path = Paths.get(savefile);
        //Si el path no existe nos imprime por pantalla el mensaje con el status 12
        if (!Files.exists(path)) {
            System.out.println("El archivo no existe");
            System.exit(12);
        }
        //Si no se puede leer sale por pantalla el mensaje con el estatus 13
        if (!Files.isReadable(path)) {
            System.out.println("El archivo" + path + "no se puede ser leer");
            System.exit(13);
        }
        //Creamos un objeto Logprocessor
        LogProcessor processor = new LogProcessor(savefile);

        //Creamos el switch con las distintas opciones
        switch (operation) {
            case "count":
                FaltaArguemnto(args.length < 3, "Falta el nivel de log para buscar ", 10);
                //Creamos la variable cuentaLog y lo igualamos al segundo argumento.
                String cuentaLog = args[2];
                //Creamos una variable total y la igualamos al metodo count
                int total = processor.count(cuentaLog);
                System.out.println("Total de lineas con nivel " + cuentaLog + ": " + total);
                break;

            case "filter":
                FaltaArguemnto(args.length < 3, "Falta el nivel de log para buscar ", 10);
                //Creamos las variables y llamamos al metodo creado en LogProcessor
                String nivelLog = args[2];
                List<String> filtered = processor.filterByLevel(nivelLog);
                filtered.forEach(System.out::println);
                break;

            case "search":
                FaltaArguemnto(args.length < 3, "Falta el texto a buscar", 10);
                //Creamos las variables y llamamos al metodo creado en LogProcessor
                String busquedaLog = args[2];
                List<String> matches = processor.searchText(busquedaLog);
                matches.forEach(System.out::println);
                break;

            case "stats":
                //Creamos las variables y llamamos al metodo creado en LogProcessor
                Map<String, Integer> stats = processor.getStatistics();
                System.out.println("Las Estadisticas del log son:");
                stats.forEach((key, value) -> System.out.println(key + ": " + value));
                break;

            case "report":
                Path reporteLog = null;
                if (args.length >= 3) {
                    reporteLog = Paths.get(args[2]);
                }
                //Generamos la clase reportgenerator y la igualamos a la variable report
                ReportGenerator reportGenerator = new ReportGenerator(processor);
                String report = reportGenerator.generateReport();
                //Si reportLog es distinto de null generara el reporte y si no lo imprimira solo
                if (reporteLog != null) {
                    reportGenerator.exportReport(reporteLog.toString());
                    System.out.println("Informe guardado en:" + reporteLog);
                } else {
                    System.out.println(report);
                }
                break;
            //Si no ocurre ninguna de las opciones, por default saldra el siguiente mensaje por pantalla
            default:
                System.out.println("Error: operación no válida -> " + operation);
                break;
        }

        System.exit(0);

    }
    //Metodo por si falta algun argumento salte por pantalla el error
    private static void FaltaArguemnto(boolean length, String x,int status) {
        if (length) {
            System.out.println(x);
            System.exit(status);
        }
    }
}
