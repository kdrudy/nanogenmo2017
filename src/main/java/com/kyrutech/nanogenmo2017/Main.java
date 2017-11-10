package com.kyrutech.nanogenmo2017;

import com.kyrutech.nanogenmo2017.engines.PlotEngine;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //generateDissociatedPress();

        //Generate actors

        //Generate a plotline
        try {
            PlotEngine plot = new PlotEngine(20);
            plot.loadPlotPoints(new File("files/plots"));
            List<String> finalPlotPoints = plot.generatePlot();
            finalPlotPoints = PlotEngine.normalizePlots(finalPlotPoints);
            finalPlotPoints.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Generate outline of scenes to push actors along plotline

        //Generate scenes themselves
    }

    private static void generateDissociatedPress() {
        File booksDir = new File("books");
        File[] books = booksDir.listFiles();

        DissociatedPress dp = new DissociatedPress();
        Arrays.stream(books).forEach((b) -> {
            try {
                dp.addTextWords(b, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        System.out.println(dp.dissociateWords(2, 50000));
    }
}
