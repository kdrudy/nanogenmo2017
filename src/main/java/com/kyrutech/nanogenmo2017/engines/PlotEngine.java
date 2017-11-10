package com.kyrutech.nanogenmo2017.engines;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import edu.stanford.nlp.ie.KBPRelationExtractor;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.simple.*;
import edu.stanford.nlp.util.Triple;

public class PlotEngine {

    private int sections;
    private Map<Integer, List<String>> sectionMap = new HashMap<>();

//    private List<String> startingPlotPoints = new ArrayList<>();
//    private List<String> firstThird = new ArrayList<>();
//    private List<String> secondThird = new ArrayList<>();
//    private List<String> thirdThird = new ArrayList<>();
//    private List<String> endingPlotPoints = new ArrayList<>();

    public PlotEngine(int sections) {
        this.sections = sections;
    }

    public void loadPlotPoints(File file) throws IOException {
        List<String> plotPoints = Files.lines(file.toPath())
                .collect(Collectors.toList());

        List<String> story = new ArrayList<>();
        for (String point : plotPoints) {
            if ("<EOS>".equalsIgnoreCase(point)) {
                if (story.size() > 1) {

                    int dividedUp = story.size()/sections;
                    for(int i = 0;i<sections-1;i++) {
                        for(int j = 0;j<dividedUp;j++) {
                            if(!sectionMap.containsKey(i)) {
                              sectionMap.put(i, new ArrayList<>());
                            }
                            sectionMap.get(i).add(story.remove(0));
                        }
                        if(!sectionMap.containsKey(sections-1)) {
                            sectionMap.put(sections-1, new ArrayList<>());
                        }
                        sectionMap.get(sections-1).addAll(story);
                    }


//                    startingPlotPoints.add(story.remove(0));
//                    endingPlotPoints.add(story.remove(story.size() - 1));
//
//                    int remaining = story.size();
//                    int dividedUp = remaining / 3;
//                    for (int i = 0; i < dividedUp; i++) {
//                        firstThird.add(story.remove(0));
//                    }
//                    for (int i = 0; i < dividedUp; i++) {
//                        secondThird.add(story.remove(0));
//                    }
//                    thirdThird.addAll(story);
                }
                story = new ArrayList<>();
            } else {
                story.add(point);
            }
        }
    }

    public List<String> generatePlot() {
        List<String> finalPlotPoints = new ArrayList<>();

        for(int key : sectionMap.keySet()) {
            List<String> points = sectionMap.get(key);
            int index = (int) (Math.random()*points.size());
            finalPlotPoints.add(points.get(index));
        }

//        int firstPlotIndex = (int) (Math.random() * startingPlotPoints.size());
//        finalPlotPoints.add(startingPlotPoints.get(firstPlotIndex));
//
//        int middlePlotPoints = totalPlotPoints - 2;
//
//        int firstMiddlePlotPoints = middlePlotPoints / 3 + (middlePlotPoints % 3 == 0 ? 0 : 1);
//        for (int i = 0; i < firstMiddlePlotPoints; i++) {
//            int firstIndex = (int) (Math.random() * firstThird.size());
//            finalPlotPoints.add(firstThird.remove(firstIndex));
//        }
//
//        int secondMiddlePlotPoints = middlePlotPoints / 3 + (middlePlotPoints % 3 == 0 ? 0 : 1);
//        for (int i = 0; i < secondMiddlePlotPoints; i++) {
//            int secondIndex = (int) (Math.random() * secondThird.size());
//            finalPlotPoints.add(secondThird.remove(secondIndex));
//        }
//
//        int thirdMiddlePlotPoints = (middlePlotPoints % 3 == 0 ? middlePlotPoints / 3 : middlePlotPoints % 3);
//        for (int i = 0; i < thirdMiddlePlotPoints; i++) {
//            int thirdIndex = (int) (Math.random() * thirdThird.size());
//            finalPlotPoints.add(thirdThird.remove(thirdIndex));
//        }
//
//        int endingPlotIndex = (int) (Math.random() * endingPlotPoints.size());
//        finalPlotPoints.add(endingPlotPoints.get(endingPlotIndex));

        return finalPlotPoints;
    }

    public static List<String> normalizePlots(List<String> plotPoints) {
        List<String> finalPlotPoints = new ArrayList<>();

        for(String point : plotPoints) {
            //Replace character names with <ACTORn> tags
            int actorCount = 0;
            Sentence s = new Sentence(point);
            List<String> nerTags = s.nerTags();
            for(int i = 0;i <nerTags.size();i++) {
                if(nerTags.get(i).equalsIgnoreCase("PERSON")) {
                    StringBuilder text = new StringBuilder(s.word(i));
                    while(nerTags.size() > i + 1 && nerTags.get(i+1).equalsIgnoreCase("PERSON")) {
                        text.append(" ").append(s.word(i + 1));
                        i++;
                    }
                    point = point.replaceAll(text.toString(), "<ACTOR" + actorCount + ">");
                    actorCount++;
                }
                //System.out.println(s.word(i) + " : " + nerTags.get(i) + " : " + s.posTag(i));
            }

            //Remove actor names which show up in some plot points
            point = point.replaceFirst("\\(<ACTOR.>\\)", "");


            finalPlotPoints.add(point);
        }

        return finalPlotPoints;
    }

    public Map<Integer, List<String>> getSectionMap() {
        return sectionMap;
    }
}
