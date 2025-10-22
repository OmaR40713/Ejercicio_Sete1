package org.example.back;

import java.io.File;
import java.nio.file.*;
import java.io.IOException;
import java.util.Map;

public class ReportGenerator {

    private LogProcessor logProcessor;
    private String lastReport;

    public ReportGenerator(LogProcessor logProcessor) {
        if (logProcessor == null) {
            throw new IllegalArgumentException("LogProcessor no puede ser null");
        }
        this.logProcessor = logProcessor;
    }

    public String generateReport() {
        Map<String, Integer> stats = logProcessor.getStatistics();
        StringBuilder report = new StringBuilder();

        report.append("=== INFORME DE LOGS ===\n");


        int total = 0;
        for (String level : stats.keySet()) {
            int count = stats.get(level);
            total += count;
            report.append(String.format("%s: %d\n", level, count));
        }
        //Se revisa luego
        report.append("------------------------\n");
        report.append(String.format("TOTAL: %d\n", total));

        lastReport = report.toString();


        return lastReport;
    }

    public void exportReport(String file) {
        Path path = Path.of("files/",file);
        try {
            if (!Files.exists(path)){
                Files.createFile(path);
            }
            if (lastReport == null) {
                lastReport = generateReport();
            }
            Files.writeString(path, lastReport);
        } catch (IOException e) {
            System.err.println("Error al escribir el informe: " + e.getMessage());
        }

    }

}
