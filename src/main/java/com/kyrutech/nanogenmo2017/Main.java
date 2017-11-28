package com.kyrutech.nanogenmo2017;

import com.kyrutech.nanogenmo2017.engines.Actor;
import com.kyrutech.nanogenmo2017.engines.ActorEngine;
import com.kyrutech.nanogenmo2017.engines.PlotEngine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //generateDissociatedPress();

        //Generate actors

        ActorEngine ae = new ActorEngine();
        List<Actor> actors = new ArrayList<>();
        actors.add(ae.generateActor());
        actors.add(ae.generateActor());
        actors.add(ae.generateActor());
        actors.add(ae.generateActor());
        actors.add(ae.generateActor());
        actors.add(ae.generateActor());
        actors.add(ae.generateActor());
        actors.add(ae.generateActor());
        actors.add(ae.generateActor());


        //Generate a plotline
        try {
            PlotEngine plot = new PlotEngine(20);
            plot.loadPlotPoints(new File("files/plots"));
            List<String> finalPlotPoints = plot.generatePlot(500);
            //finalPlotPoints = PlotEngine.normalizePlots(finalPlotPoints);

            combineActorsAndPlot(finalPlotPoints, actors);


            System.out.println("");
            System.out.println("Writing out file");

            File story = new File("story.txt");
            FileWriter fw = new FileWriter(story);
            finalPlotPoints.forEach((s) -> {
                try {
                    fw.append(s);
                    fw.append("\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fw.flush();
            fw.close();

            System.out.println("File written to story.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Generate outline of scenes to push actors along plotline

        //Generate scenes themselves
    }

    private static void combineActorsAndPlot(List<String> plot, List<Actor> actors) {
        for(int i = 0;i<plot.size();i++) {
            plot.set(i, plot.get(i).replace("<ACTOR0>", i%2 == 0 ? actors.get(0).getFullName() : actors.get(1).getFullName()));
            plot.set(i, plot.get(i).replace("<ACTOR1>", i%2 == 0 ? actors.get(3).getFullName() : actors.get(2).getFullName()));
            plot.set(i, plot.get(i).replace("<ACTOR2>", i%2 == 0 ? actors.get(1).getFullName() : actors.get(0).getFullName()));
            plot.set(i, plot.get(i).replace("<ACTOR3>", i%2 == 0 ? actors.get(2).getFullName() : actors.get(5).getFullName()));
            plot.set(i, plot.get(i).replace("<ACTOR4>", i%2 == 0 ? actors.get(4).getFullName() : actors.get(3).getFullName()));
            plot.set(i, plot.get(i).replace("<ACTOR5>", i%2 == 0 ? actors.get(5).getFullName() : actors.get(4).getFullName()));
        }
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
