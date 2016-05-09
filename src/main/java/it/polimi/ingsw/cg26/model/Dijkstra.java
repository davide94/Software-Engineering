package it.polimi.ingsw.cg26.model;

import it.polimi.ingsw.cg26.model.board.City;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Collections;


public class Dijkstra
{
    public static void computePaths(City source)
    {
        source.distance = 0.;
        PriorityQueue<City> vertexQueue = new PriorityQueue<>();
        vertexQueue.add(source);

        while (!vertexQueue.isEmpty()) {
            City u = vertexQueue.poll();

            for (City v: u.nearCities)
            {
                double distanceThroughU = u.distance + 1;
                if (distanceThroughU < v.distance) {
                    vertexQueue.remove(v);

                    v.distance = distanceThroughU;
                    v.previous = u;
                    vertexQueue.add(v);
                }
            }
        }
    }

    public static List<City> getShortestPathTo(City target)
    {
        List<City> path = new ArrayList<>();
        for (City vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);

        Collections.reverse(path);
        return path;
    }

}