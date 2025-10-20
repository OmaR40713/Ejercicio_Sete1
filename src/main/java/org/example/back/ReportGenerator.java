package org.example.back;

import java.nio.file.*;
import java.io.IOException;
import java.util.Map;

public class ReportGenerator {

    private LogProcessor logProcessor;
    private String lastReport;

    public ReportGenerator(LogProcessor logProcessor){
        if (logProcessor == null){
            throw new IllegalArgumentException("LogProcessor no puede ser null");
        }
        this.logProcessor = logProcessor;
    }

    public String generateReport(){
        Map<String, Integer> stats = logProcessor.getStatistics();
        StringBuilder report = new StringBuilder();

        int total =0;
        for (String level : stats.keySet()){
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

    public String exportReport(String file){

    }
}
