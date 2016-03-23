package Annealing;

import org.w3c.dom.events.MutationEvent;

public class GA {

	private static final double mutationRate = 0.015;
	private static final int turnamentSize = 5;
	private static final boolean elitism = true;

	public static Population evolvePopulation(Population pop) {
		Population newPopulation = new Population(pop.populationSize(), false);

		int elitismOffset = 0;
		if (elitism) {
			newPopulation.saveTour(0, pop.getFittnest());
			elitismOffset = 1;
		}

		for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
			Tour parent1 = tournamentSelection(pop);
			Tour parent2 = tournamentSelection(pop);

			Tour child = crossover(parent1, parent2);

			newPopulation.saveTour(i, child);
		}

		for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
			mutate(newPopulation.getTour(i));
		}
		return newPopulation;
	}

	private static void mutate(Tour tour) {
		for (int tourPos1 = 0; tourPos1 < tour.tourSize(); tourPos1++) {
			if (Math.random() < mutationRate) {
				int tourPos2 = (int) (tour.tourSize() * Math.random());

				City city1 = tour.getCity(tourPos1);
				City city2 = tour.getCity(tourPos2);

				tour.setCity(tourPos2, city1);
				tour.setCity(tourPos1, city2);
			}
		}

	}

	public static Tour crossover(Tour parent1, Tour parent2) {
		// Create new child tour
		Tour child = new Tour();

		// Get start and end sub tour positions for parent1's tour
		int startPos = (int) (Math.random() * parent1.tourSize());
		int endPos = (int) (Math.random() * parent1.tourSize());

		// Loop and add the sub tour from parent1 to our child
		for (int i = 0; i < child.tourSize(); i++) {
			// If our start position is less than the end position
			if (startPos < endPos && i > startPos && i < endPos) {
				child.setCity(i, parent1.getCity(i));
			} // If our start position is larger
			else if (startPos > endPos) {
				if (!(i < startPos && i > endPos)) {
					child.setCity(i, parent1.getCity(i));
				}
			}
		}

		// Loop through parent2's city tour
		for (int i = 0; i < parent2.tourSize(); i++) {
			// If child doesn't have the city add it
			if (!child.containsCity(parent2.getCity(i))) {
				// Loop to find a spare position in the child's tour
				for (int ii = 0; ii < child.tourSize(); ii++) {
					// Spare position found, add city
					if (child.getCity(ii) == null) {
						child.setCity(ii, parent2.getCity(i));
						break;
					}
				}
			}
		}
		return child;
	}

	private static Tour tournamentSelection(Population pop) {

		Population turnament = new Population(turnamentSize, false);

		for (int i = 0; i < turnamentSize; i++) {
			int randomId = (int) (Math.random() * pop.populationSize());
			turnament.saveTour(i, pop.getTour(randomId));
		}
		Tour fittnest = turnament.getFittnest();
		return fittnest;
	}

}
