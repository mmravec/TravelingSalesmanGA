package Annealing;

import java.util.ArrayList;
import java.util.Collections;

public class Tour {

	private ArrayList tour = new ArrayList<City>();

	private double fitness = 0;
	private int distance = 0;

	public Tour() {
		for (int i = 0; i < TourManager.numberOfCities(); i++) {
			tour.add(null);
		}
	}

	public Tour(ArrayList tour) {
		this.tour = (ArrayList) tour.clone();
	}

	public ArrayList getTour() {
		return tour;
	}

	public void generateIndividual() {
		for (int cityIndex = 0; cityIndex < TourManager.numberOfCities(); cityIndex++) {
			setCity(cityIndex, TourManager.getCity(cityIndex));
		}
		Collections.shuffle(tour);
	}

	public City getCity(int tourPosition) {
		return (City) tour.get(tourPosition);
	}

	public void setCity(int tourPosition, City city) {
		tour.set(tourPosition, city);
		distance = 0;
	}

	public double getFitness() {
		if (fitness == 0) {
			fitness = 1 / (double) getDistance();
		}
		return fitness;
	}

	public int getDistance() {
		if (distance == 0) {
			int tourDistanec = 0;

			for (int cityIndex = 0; cityIndex < tourSize(); cityIndex++) {

				City fromCity = getCity(cityIndex);

				City destinacionCity;

				if (cityIndex + 1 < tourSize()) {
					destinacionCity = getCity(cityIndex + 1);
				} else {
					destinacionCity = getCity(0);
				}
				tourDistanec += fromCity.distanceTo(destinacionCity);
			}
			distance = tourDistanec;
		}
		return distance;
	}

	public int tourSize() {
		return tour.size();
	}

	@Override
	public String toString() {
		String geneString = "Point:";
		for (int i = 0; i < tourSize(); i++) {
			geneString += getCity(i) + ", ";
		}
		return geneString;
	}

	public boolean containsCity(City city) {
		return tour.contains(city);
	}
}
