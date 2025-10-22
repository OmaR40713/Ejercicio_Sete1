package org.example.front;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class LogManager {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Introduce la ruta del fichero de logs: ");
        String logFile = sc.nextLine().trim();

        int opcion = 0;

        do {
            mostrarMenu();

            try {
                opcion = Integer.parseInt(sc.nextLine().trim());

                switch (opcion) {
                    case 1 -> contarLogs(sc, logFile);
                    case 2 -> filtrarLogs(sc, logFile);
                    case 3 -> buscarTexto(sc, logFile);
                    case 4 -> verEstadisticas(logFile);
                    case 5 -> exportaInforme(sc, logFile);
                    case 6 -> System.out.println("Saliendo del programa...");
                    default -> System.out.println("Opción no valida. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduzca un número valido");
            }
        } while (opcion != 6);
    }

    //Metodo que muestra el menu y las distintas opciones
    private static void mostrarMenu() {
        System.out.println("1. Contar logs por nivel");
        System.out.println("2. Filtrar logs por nivel");
        System.out.println("3. Buscar texto en logs");
        System.out.println("4. Ver estadísticas generales");
        System.out.println("5. Exportar informe");
        System.out.println("6. Salir");
        System.out.println("Selecciona una opción: ");
    }

    private static void contarLogs(Scanner sc, String logFile) {
        System.out.println("Introduce el nivel (ERROR, WARN, INFO, DEBUG): ");
        String nivel = sc.nextLine().toUpperCase();

        if (!nivel.matches("ERROR|WARN|INFO|DEBUG")) {
            System.out.println("Nivel no válido. Debe ser ERROR, WARN, INFO o DEBUG");
            return;
        }
        List<String> comandos = List.of("java", "-jar", "out/artifacts/LogAnalyzer/PSPPrac1AL.jar", "count", logFile, nivel);
        ejecutarProceso(comandos);
    }

    private static void filtrarLogs(Scanner sc, String logFile) {
        System.out.print("Introduce el nivel (ERROR, WARN, INFO, DEBUG): ");
        String nivel = sc.nextLine().trim().toUpperCase();

        if (!nivel.matches("ERROR|WARN|INFO|DEBUG")) {
            System.out.println("Nivel no válido. Debe ser ERROR, WARN, INFO o DEBUG.");
            return;
        }

        List<String> comandos = List.of("java", "-jar", "out/artifacts/LogAnalyzer/PSPPrac1AL.jar", "filter", logFile, nivel);
        ejecutarProceso(comandos);
    }

    private static void buscarTexto(Scanner sc, String logFile) {
        System.out.println("Introduce el texto a buscar: ");
        String texto = sc.nextLine();

        if (texto.isEmpty()) {
            System.out.println("El texto no puede estar vacio.");
            return;
        }

        List<String> comandos = List.of("java", "-jar", "out/artifacts/LogAnalyzer/PSPPrac1AL.jar", "search", logFile);
        ejecutarProceso(comandos);
    }

    private static void verEstadisticas(String logFile) {
        List<String> comandos = List.of("java", "-jar", "out/artifacts/LogAnalyzer/PSPPrac1AL.jar", "stats", logFile);
        ejecutarProceso(comandos);
    }

    private static void exportaInforme(Scanner sc, String logFile) {
        System.out.println("¿Deseas exportar el informe a fichero (1) o mostrarlo por pantalla (2)?");
        String opcion = sc.nextLine().trim();

        if (opcion.equals("1")) {
            System.out.println("Introduce el nombre del fichero destino: ");
            String destino = sc.nextLine().trim();

            List<String> comandos = List.of("java", "-jar", "out/artifacts/LogAnalyzer/PSPPrac1AL.jar", "report", logFile, destino);
            ejecutarProceso(comandos);
        } else if (opcion.equals("2")) {
            List<String> comandos = List.of("java", "-jar", "out/artifacts/LogAnalyzer/PSPPrac1AL.jar", "report", logFile);
            ejecutarProceso(comandos);
        } else {
            System.out.println("Opción no valida. Introduce 1 o 2.");
        }
    }

    private static void ejecutarProceso(List<String> comandos) {
        try {
            ProcessBuilder pb = new ProcessBuilder(comandos);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    System.out.println(linea);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al ejecutar el proceso: " + e.getMessage());
        }
    }


}
