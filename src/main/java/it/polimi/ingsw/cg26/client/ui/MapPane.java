package it.polimi.ingsw.cg26.client.ui;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.common.dto.CityDTO;
import javafx.geometry.Point2D;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.*;

/**
 *
 */
public class MapPane extends AnchorPane implements Observer {

    private static final List<Point2D> citiesOrigins = Arrays.asList(new Point2D(0.050, 0.060), new Point2D(0.035, 0.240), new Point2D(0.210, 0.110), new Point2D(0.200, 0.270), new Point2D(0.100, 0.380), new Point2D(0.350, 0.060), new Point2D(0.335, 0.240), new Point2D(0.400, 0.380), new Point2D(0.510, 0.110), new Point2D(0.500, 0.270), new Point2D(0.700, 0.060), new Point2D(0.680, 0.240), new Point2D(0.680, 0.400), new Point2D(0.810, 0.150), new Point2D(0.800, 0.350));

    private Model model;

    private List<CityPane> citiesPanes = new LinkedList<>();

    public MapPane(double width, double height, Model model) {
        this.model = model;
        setPrefSize(width, height);
        buildCities();
    }

    /**
     * Builds the cities
     */
    private void buildCities() {
        List<CityDTO> cities = new LinkedList<>();
        cities.addAll(model.getRegions().get(0).getCities());
        cities.addAll(model.getRegions().get(1).getCities());
        cities.addAll(model.getRegions().get(2).getCities());

        Map<CityDTO, CityPane> map = new LinkedHashMap<>();

        for (CityDTO city: cities) {
            Point2D o = citiesOrigins.get(cities.indexOf(city));
            Point2D origin = new Point2D(o.getX() * getPrefWidth(), o.getY() * getPrefHeight());
            CityPane cityPane = new CityPane(origin, 0.15 * getPrefHeight(), city, model);
            citiesPanes.add(cityPane);
            map.put(city, cityPane);
        }

        linkCities(map, cities);
        getChildren().addAll(citiesPanes);
    }

    /**
     * Draws the routes between linked cities
     */
    private void linkCities(Map<CityDTO, CityPane> map, List<CityDTO> cities) {
        Collection<String> alreadyVisited = new LinkedList<>();
        for (CityDTO city: map.keySet()) {
            for (String nearName: city.getNearCities()) {
                CityDTO nearCity = null;
                for (CityDTO c: map.keySet()) {
                    if (c.getName().equalsIgnoreCase(nearName)) {
                        nearCity = c;
                        break;
                    }
                }
                if (nearCity != null && !alreadyVisited.contains(nearName)) {
                    Point2D p1 = citiesOrigins.get(cities.indexOf(city));
                    Point2D p2 = citiesOrigins.get(cities.indexOf(nearCity));
                    Point2D startPoint = new Point2D((p1.getX() + 0.075) * getPrefWidth(), (p1.getY() + 0.075) * getPrefHeight());
                    Point2D endPoint = new Point2D((p2.getX() + 0.075) * getPrefWidth(), (p2.getY() + 0.075) * getPrefHeight());

                    final Shape line = buildRoute(startPoint, endPoint);
                    getChildren().add(line);
                }
            }
            alreadyVisited.add(city.getName());
        }
    }

    /**
     * Builds a route between startPoint and endPoint
     * @param startPoint is the point where the route begins
     * @param endPoint is the point where the route ends
     * @return the street created
     */
    private Shape buildRoute(Point2D startPoint, Point2D endPoint) {

        double wobble = Math.sqrt((endPoint.getX() - startPoint.getX()) * (endPoint.getX() - startPoint.getX()) + (endPoint.getY() - startPoint.getY()) * (endPoint.getY() - startPoint.getY())) / 25 + 0.5;

        double r1 = Math.random();
        double r2 = Math.random();

        double xfactor = (Math.random() > 0.5 ? wobble : -wobble) * 3.0;
        double yfactor = (Math.random() > 0.5 ? wobble : -wobble) * 3.0;

        Point2D control1 = new Point2D((endPoint.getX() - startPoint.getX()) * r1 + startPoint.getX() + xfactor, (endPoint.getY() - startPoint.getY()) * r1 + startPoint.getY() + yfactor);
        Point2D control2 = new Point2D((endPoint.getX() - startPoint.getX()) * r2 + startPoint.getX() - xfactor, (endPoint.getY() - startPoint.getY()) * r2 + startPoint.getY() - yfactor);

        MoveTo startMove = new MoveTo(startPoint.getX(), startPoint.getY());
        CubicCurveTo curve = new CubicCurveTo(control1.getX(), control1.getY(),
                control2.getX(), control2.getY(),
                endPoint.getX(), endPoint.getY());

        InnerShadow shadow = new InnerShadow();
        shadow.setRadius(10.0);
        //shadow.setColor(Color.rgb(137, 122, 92));
        shadow.setColor(Color.web("#6B5832"));

        Path path = new Path(startMove, curve);
        path.setStrokeLineCap(StrokeLineCap.ROUND);
        path.setStroke(Color.rgb(172, 146, 83));
        path.setEffect(shadow);
        double strokeWidth = 15.0;
        path.setStrokeWidth(strokeWidth + (strokeWidth * (Math.random() - 0.5) / 8.0));
        path.setStrokeType(StrokeType.CENTERED);
        return path;
    }

    @Override
    public void update(Observable o, Object arg) {
        citiesPanes.forEach(p -> p.update(o, arg));
    }
}
