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

        for (String level : stats.keySet());

        return lastReport;
    }
}
