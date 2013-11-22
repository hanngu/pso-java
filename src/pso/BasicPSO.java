package pso;

import java.util.ArrayList;

import sun.tools.tree.ThisExpression;

public class BasicPSO {
	
	int dimensions, numberOfParticles;
	double bestGlobalPerfomance, globalAttraction;
	ArrayList<Double> bestGlobalPosition;
	ArrayList<Particle> swarm;
	//Particle bestParticle;
	
	public BasicPSO(int dimensions, int numberOfParticles){
		this.dimensions = dimensions;
		this.numberOfParticles = numberOfParticles;
		this.globalAttraction = 1.5;
		this.bestGlobalPerfomance = Double.MAX_VALUE;
		this.bestGlobalPosition = new ArrayList<Double>();
		this.swarm = new ArrayList<Particle>();
		//this.bestParticle = null;
	}
	
	public ArrayList<Double> run(){
		initializeSwarm();
		int numberOfIterations = 0;
		while(bestGlobalPerfomance > 0.001 && numberOfIterations < 1000){
			for(Particle particle: this.swarm){
				particle.compareLocalPerformance();
				if(particle.evaluate() < this.bestGlobalPerfomance){
					bestGlobalPerfomance = particle.evaluate();
					bestGlobalPosition = particle.getCopiedPositon();
					//bestParticle = new Particle(particle);
				}
				particle.changeVelocity(globalAttraction, bestGlobalPosition);
				particle.updatePosition();
			}
			numberOfIterations ++;
		}

		System.out.println("Number of iterations: " + numberOfIterations);
		System.out.println("Best global performance: " + bestGlobalPerfomance);
		return bestGlobalPosition;
	}

	private void initializeSwarm() {
		ArrayList<Double> particlePosition;
		for (int i = 0; i < this.numberOfParticles; i++) {
			 particlePosition = new ArrayList<Double>();
			 for (int j = 0; j < this.dimensions; j++) {
				 particlePosition.add((0 + (Math.random() * ((200 - 0) + 1))) - 100);
			 }
			 this.swarm.add(new Particle(particlePosition));
		}
		
	}
	public String toString(){
		String out = "[ ";
		for(Particle p : this.swarm){
			out = p.toString();
		}
		out += " ]";
		return out;
	}

}
