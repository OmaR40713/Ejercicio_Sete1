package org.example.back;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static java.nio.file.StandardOpenOption.APPEND;

public class LogAnalyzer {
    public static void main(String[] args) throws IOException {

        String operation = args[0];
        String savefile = args[1];
        Path path = Paths.get(savefile);

        if (args.length < 2) {
            System.exit(10);
        }
        if (!Files.exists(path)) {
            System.out.println("El archivo no existe");
            System.exit(12);
        }

        if (!Files.isReadable(path)) {
            System.out.println("El archivo" + path + "no se puede ser leer");
            System.exit(13);
        }

        LogProcessor processor = new LogProcessor(savefile);


        switch (operation) {
            case "count":
                FaltaArguemnto(args.length < 3, "Falta el nivel de log para buscar ", 10);

                String cuentaLog = args[2];
                List<String> countLines = processor.filterByLevel(cuentaLog);
                System.out.println("Total de lineas con nivel " + cuentaLog + ": " + countLines.size());
                break;

            case "filter":
                FaltaArguemnto(args.length < 3, "Falta el nivel de log para buscar ", 10);

                String nivelLog = args[2];
                List<String> filtered = processor.filterByLevel(nivelLog);
                filtered.forEach(System.out::println);
                break;

            case "search":
                FaltaArguemnto(args.length < 3, "Falta el texto a buscar", 10);

                String busquedaLog = args[2];
                List<String> matches = processor.searchText(busquedaLog);
                matches.forEach(System.out::println);
                break;

            case "stats":
                Map<String, Integer> stats = processor.getStatistics();
                System.out.println("Las Estadisticas del log son:");
                stats.forEach((key, value) -> System.out.println(key + ": " + value));
                break;

            case "report":
                Path reporteLog = null;
                if (args.length >= 3) {
                    reporteLog = Paths.get(args[2]);
                }
                Map<String, Integer> ReporteStadisticas = processor.getStatistics();
                StringBuilder report = new StringBuilder();
                report.append("Informe del log: ").append(savefile).append("\n");
                for (Map.Entry<String, Integer> entry : ReporteStadisticas.entrySet()) {
                    report.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
                }
                if (reporteLog != null) {
                    Files.writeString(reporteLog, report.toString());
                    System.out.println("Informe guardado en:" + reporteLog);
                } else {
                    System.out.println(report);
                }
                break;

            default:
                System.out.println("Error: operación no válida -> " + operation);
                break;
        }

        System.exit(0);

    }

    private static void FaltaArguemnto(boolean length, String x, int status) {
        if (length) {
            System.out.println(x);
            System.exit(status);
        }
    }
}
