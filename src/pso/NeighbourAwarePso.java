package pso;

import java.util.ArrayList;

public class NeighbourAwarePso extends BasicPSO {
	
	ArrayList<NeighbourAwareParticle> neighbourAwareSwarm;
	
	public NeighbourAwarePso(int dimensions, int numberOfParticles) {
		super(dimensions, numberOfParticles);
		neighbourAwareSwarm = new ArrayList<NeighbourAwareParticle>();
		
	}
	
	public ArrayList<Double> run(){
		initializeSwarm();
		int numberOfIterations = 0;
		while(bestGlobalPerfomance > 0.001 && numberOfIterations < 1000){
			for(NeighbourAwareParticle particle: this.neighbourAwareSwarm){
				particle.compareLocalPerformance();
				particle.compareWithNeighbours(this.neighbourAwareSwarm);
				if(particle.evaluate() < this.bestGlobalPerfomance){
					bestGlobalPerfomance = particle.evaluate();
					bestGlobalPosition = particle.getCopiedPositon();
					//bestParticle = new Particle(particle);
				}
				particle.changeVelocity(globalAttraction);
				particle.updatePosition();
			}
			System.out.println("Best global performance so far: " + bestGlobalPerfomance);
			numberOfIterations ++;
		}
		System.out.println("Number of iterations: " + numberOfIterations);
		System.out.println("Best global performance: " + bestGlobalPerfomance);
		return bestGlobalPosition;
	}
	
	protected void initializeSwarm() {
		ArrayList<Double> particlePosition;
		for (int i = 0; i < this.numberOfParticles; i++) {
			 particlePosition = new ArrayList<Double>();
			 for (int j = 0; j < this.dimensions; j++) {
				 particlePosition.add((0 + (Math.random() * ((200 - 0) + 1))) - 100);
			 }
			 this.neighbourAwareSwarm.add(new NeighbourAwareParticle(particlePosition));
		}
		
	}

}
