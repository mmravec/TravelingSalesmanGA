package Annealing;

public class Population {
	
	Tour[] tours;
	
	public Population(int populationSize, boolean initialise){
		tours = new Tour[populationSize];
		
		if(initialise){
			for (int i = 0; i < populationSize(); i++) {
				Tour newTour = new Tour();
				newTour.generateIndividual();
				saveTour(i, newTour);
			}
		}
	}
	
	public void saveTour(int index, Tour tour){
		tours[index] = tour;
	}
	
	public Tour getTour(int index){
		return tours[index];
	}
	
	public Tour getFittnest(){
		Tour fittnest = tours[0];
		
		for (int i = 1; i < populationSize(); i++) {
			if(fittnest.getFitness() <= getTour(i).getFitness()){
				fittnest = getTour(i);
			}
		}
		return fittnest;
	}
	
	public int populationSize(){
		return tours.length;
	}

}
