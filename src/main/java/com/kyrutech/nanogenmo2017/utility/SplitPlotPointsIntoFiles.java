package com.kyrutech.nanogenmo2017.utility;

import com.kyrutech.nanogenmo2017.engines.PlotEngine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SplitPlotPointsIntoFiles {
    public static void main(String[] args) throws IOException {
        PlotEngine plot = new PlotEngine(20);
        plot.loadPlotPoints(new File("files/plots"));
        Map<Integer, List<String>> sectionedPlotPoints = plot.getSectionMap();
        for(Integer key : sectionedPlotPoints.keySet()) {
            List<String> points = sectionedPlotPoints.get(key);
            FileWriter fw = new FileWriter(new File("files/plots_section_" + key + ".txt"));
            for(String point : points) {
                fw.append(point + "\n");
            }
            fw.close();
        }

    }
}
