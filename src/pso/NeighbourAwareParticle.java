package pso;

import java.util.ArrayList;
import java.util.TreeMap;

public class NeighbourAwareParticle extends Particle{
	
	ArrayList<Double> bestNeighbourPosition;
	double bestNeighbourEvaluation;
	
	public NeighbourAwareParticle(ArrayList<Double> position) {
		super(position);
		this.bestNeighbourPosition = this.currentPosition;
		this.bestNeighbourEvaluation = Double.MAX_VALUE;
	}
	public void compareWithNeighbours( ArrayList<NeighbourAwareParticle> neighbourAwareSwarm) {
		ArrayList<NeighbourAwareParticle> neighbours = calculateNeighbours(neighbourAwareSwarm);
		this.bestNeighbourEvaluation = evaluate();
		this.bestNeighbourPosition = this.currentPosition;
		for(NeighbourAwareParticle n : neighbours){
			if(n.evaluate() < this.bestNeighbourEvaluation){
				this.bestNeighbourEvaluation = n.evaluate();
				this.bestNeighbourPosition = n.getCopiedPositon();
			}
		}
		
	}

	private ArrayList<NeighbourAwareParticle> calculateNeighbours(ArrayList<NeighbourAwareParticle> neighbourAwareSwarm) {
		TreeMap<Double, NeighbourAwareParticle> neighbours = new TreeMap<Double, NeighbourAwareParticle>();
		ArrayList<NeighbourAwareParticle> closestNeighbours = new ArrayList<NeighbourAwareParticle>();
		for(NeighbourAwareParticle particle : neighbourAwareSwarm){
			if(!particle.equals(this)){
				double euclideanDistance = 0.0;
				for (int i = 0; i < currentPosition.size(); i++) {
					euclideanDistance += Math.pow((currentPosition.get(i) - particle.getCurrentPosition().get(i)), 2);
				}
				euclideanDistance = Math.sqrt(euclideanDistance);
				neighbours.put(euclideanDistance, particle);
			}
		}
		for (int i = 0; i < 3; i++) {
			closestNeighbours.add(neighbours.firstEntry().getValue());
			neighbours.remove(neighbours.firstEntry().getKey());
		}
		return closestNeighbours;
	}
	public void changeVelocity(double globalAttraction) {
		double r1 = random.nextDouble();
		double r2 = random.nextDouble();
		
		for (int i = 0; i < currentPosition.size(); i++) {
			double particleSelfMemory = (bestLocalPosition.get(i) - currentPosition.get(i)) * r1 * this.localAttraction;
			double globalInfluence = (bestNeighbourPosition.get(i) - currentPosition.get(i)) * r2 * globalAttraction;
			double inertia = velocity.get(i);
			double newVelocity = inertia + particleSelfMemory + globalInfluence;
			
			//Clamping
			if(newVelocity > 1.0){
				newVelocity = 1.0;
			}
			if(newVelocity < -1.0){
				newVelocity = -1.0;
			}

			this.velocity.remove(i);
			this.velocity.add(i, newVelocity);
			
		}
		
	}



}
