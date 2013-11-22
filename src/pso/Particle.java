package pso;

import java.util.ArrayList;
import java.util.Random;

public class Particle {
	double bestLocalPerfomance, localAttraction;
	ArrayList<Double> currentPosition, bestLocalPosition, velocity;
	Random random = new Random();
	boolean firstRound;
	
	public Particle(ArrayList<Double> position){
		this.currentPosition = position;
		this.bestLocalPosition = getCopiedPositon();
		this.bestLocalPerfomance = Double.MAX_VALUE;
		this.localAttraction = 0.2;
		this.velocity = new ArrayList<Double>();
		this.firstRound = true;
	}

	public Particle(Particle particleToCopy) {
		this.currentPosition = particleToCopy.getCopiedPositon();
		this.bestLocalPosition = particleToCopy.getCopiedBestLocalPositon();
		this.bestLocalPerfomance = particleToCopy.bestLocalPerfomance;
		this.localAttraction = particleToCopy.localAttraction;
		this.velocity = particleToCopy.getCopiedVelocity();
		this.firstRound = particleToCopy.getIfFirstRound();
	}

	public void compareLocalPerformance() {
		if(evaluate() > this.bestLocalPerfomance){
			this.bestLocalPerfomance = evaluate();
			this.bestLocalPosition = getCopiedPositon();
		}
		
	}
	
	public boolean getIfFirstRound(){
		return this.firstRound;
	}

	public ArrayList<Double> getCurrentPosition(){
		return this.currentPosition;
	}
	public double evaluate() {
		double fitness = 0.0;
		for (Double dimension: this.currentPosition){
			fitness += Math.pow(dimension, 2);
		}
		return fitness;
	}

	public ArrayList<Double> getCopiedPositon() {
		ArrayList<Double> copiedPosition = new ArrayList<Double>();
		for(Double dimension: this.currentPosition){
			copiedPosition.add(dimension); //Might be buggy, but do not think so
		}
		return copiedPosition;
	}
	
	private ArrayList<Double> getCopiedBestLocalPositon() {
		ArrayList<Double> copiedPosition = new ArrayList<Double>();
		for(Double dimension: this.bestLocalPosition){
			copiedPosition.add(dimension); //Might be buggy, but do not think so
		}
		return copiedPosition;
	}
	
	private ArrayList<Double> getCopiedVelocity() {
		ArrayList<Double> copiedVelocity = new ArrayList<Double>();
		for(Double dimension: this.velocity){
			copiedVelocity.add(dimension); //Might be buggy, but do not think so
		}
		return copiedVelocity;
	}

	public void changeVelocity(double globalAttraction,
			ArrayList<Double> bestGlobalPosition) {
		double r1 = random.nextDouble();
		double r2 = random.nextDouble();
		
		for (int i = 0; i < currentPosition.size(); i++) {
			double particleSelfMemory = (bestLocalPosition.get(i) - currentPosition.get(i)) * r1 * this.localAttraction;
			double globalInfluence = (bestGlobalPosition.get(i) - currentPosition.get(i)) * r2 * globalAttraction;
			double inertia = 0;
			if(firstRound){
				inertia = 0;
			} else{
				inertia = velocity.get(i);
			}
			double newVelocity = inertia + particleSelfMemory + globalInfluence;
			
			//Clamping
			if(newVelocity > 1.0){
				newVelocity = 1.0;
			}
			if(newVelocity < -1.0){
				newVelocity = -1.0;
			}
			if(!firstRound){
				this.velocity.remove(i);
			}
			this.velocity.add(i, newVelocity);
			
		}
		firstRound = false;
		
	}

	public void updatePosition() {
		for (int i = 0; i < currentPosition.size(); i++) {
			Double newPosition = currentPosition.get(i) + velocity.get(i);
			currentPosition.remove(i);
			currentPosition.add(i, newPosition);
		}
		
	}
	
	public String toString(){
		String out = "[";
		for (Double dimension: this.currentPosition){
			out += dimension + ","; 
		}
		out += "]";
		return out;
	}
}
