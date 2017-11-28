package com.kyrutech.nanogenmo2017.engines;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import edu.stanford.nlp.simple.*;

public class PlotEngine {

    private int sections;
    private Map<Integer, List<String>> sectionMap = new HashMap<>();


    public PlotEngine(int sections) {
        this.sections = sections;
    }

    public void loadPlotPoints(File file) throws IOException {
        List<String> plotPoints = Files.lines(file.toPath())
                .collect(Collectors.toList());

        List<String> story = new ArrayList<>();
        for (int p = 0;p < plotPoints.size(); p++) {
            float percentage = ((float) p/(float)plotPoints.size())*100f;
            System.out.print("Processing " + p + " out of " + plotPoints.size() + " (" + String.format("%.2f", percentage) + "%)\r");
            String point = plotPoints.get(p);
            if ("<EOS>".equalsIgnoreCase(point)) {
                story = normalizePlots(story);
                if (story.size() > 1) {

                    int dividedUp = story.size() / sections;
                    for (int i = 0; i < sections - 1; i++) {
                        for (int j = 0; j < dividedUp; j++) {
                            if (!sectionMap.containsKey(i)) {
                                sectionMap.put(i, new ArrayList<>());
                            }
                            sectionMap.get(i).add(story.remove(0));
                        }
                        if (!sectionMap.containsKey(sections - 1)) {
                            sectionMap.put(sections - 1, new ArrayList<>());
                        }
                        sectionMap.get(sections - 1).addAll(story);
                    }
                }
                story = new ArrayList<>();
            } else {
                story.add(point);
            }
        }
    }

    public List<String> generatePlot() {
        List<String> finalPlotPoints = new ArrayList<>();

        for (int key : sectionMap.keySet()) {
            List<String> points = sectionMap.get(key);
            int index = (int) (Math.random() * points.size());
            finalPlotPoints.add(points.get(index));
        }

        return finalPlotPoints;
    }

    public List<String> generatePlot(int points) {
        List<String> finalPlotPoints = new ArrayList<>();

        int pointsPer = points / sections;

        for (int i = 0; i < sections; i++) { //For each section
            finalPlotPoints.add("");
            finalPlotPoints.add("");
            finalPlotPoints.add("Chapter " + (i + 1));
            finalPlotPoints.add("");
            for (int j = 0; j < pointsPer; j++) { //Get this many plot points
                List<String> plots = sectionMap.get(i);
                int index = (int) (Math.random() * plots.size());
                finalPlotPoints.add(plots.get(index));
            }
        }

        return finalPlotPoints;
    }

    private List<String> normalizePlots(List<String> plotPoints) {
        List<String> finalPlotPoints = new ArrayList<>();

        Map<String, String> actors = new HashMap<>();
        int actorCount = 0;

        for (String point : plotPoints) {
            //Replace character names with <ACTORn> tags
            if (!point.isEmpty()) {
                Sentence s = new Sentence(point);

                List<String> nerTags = s.nerTags();
                for (int i = 0; i < nerTags.size(); i++) {
                    if (nerTags.get(i).equalsIgnoreCase("PERSON")) {
                        StringBuilder text = new StringBuilder(s.word(i));
                        while (nerTags.size() > i + 1 && nerTags.get(i + 1).equalsIgnoreCase("PERSON")) {
                            text.append(" ").append(s.word(i + 1));
                            i++;
                        }
                        if(!actors.containsKey(text.toString())) {
                            actors.put(text.toString(), "<ACTOR" + actorCount + ">");
                            actorCount++;
                        }
                        point = point.replaceAll(text.toString(), actors.get(text.toString()));
                    }
                    //System.out.println(s.word(i) + " : " + nerTags.get(i) + " : " + s.posTag(i));
                }

                //Remove actor names which show up in some plot points
                point = point.replaceAll("\\(<ACTOR.>\\)", "");
            }
            if(point.contains("<ACTOR") || point.isEmpty() || point.contains("Chapter")) {
                finalPlotPoints.add(point);
            }

        }

        return finalPlotPoints;
    }

    public Map<Integer, List<String>> getSectionMap() {
        return sectionMap;
    }
}
