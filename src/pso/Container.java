package pso;

import java.util.ArrayList;
import java.util.Random;

public class Container {
	
	private ArrayList<Package> packages;
	private ArrayList<Integer> bestLocalPosition = new ArrayList<Integer>();
	private double bestLocalPerformance = 0.0;
	private double inertiaWeight = 1.0;
	private Random random = new Random();
	
	public Container(ArrayList<Package> packages){
		this.packages = packages;
	}

    public double getBestLocalPerformance() {
        return bestLocalPerformance;
    }

    public ArrayList<Package> getPackages() {
		return this.packages;

	}

    public void setBestLocalPerformance(double bestLocalPerformance) {
        this.bestLocalPerformance = bestLocalPerformance;
    }

    public void setBestLocalPosition(ArrayList<Integer> bestLocalPosition) {
        this.bestLocalPosition = bestLocalPosition;
    }

	public double evaluate() {
		double evaluation = 0;
		for(Package p : this.packages){
			if(p.getPosition() == 1){
				evaluation += p.getValue();
			}
		}
		return evaluation;
	}

	public ArrayList<Integer> getPositionVector() {
		ArrayList<Integer> positionVector = new ArrayList<Integer>();
		for(Package p : this.packages){
			positionVector.add(p.getPosition());
		}
		return positionVector;
	}

	public void updatePosition(ArrayList<Integer> bestGlobalPosition,
                               double globalAttraction, boolean inertiaWeightEnabled) {
		double r1 = random.nextDouble();
		double r2 = random.nextDouble();
        double localAttraction = 0.2;

        double inertiaFactor = 0.9991;
		if(inertiaWeight * inertiaFactor > 0.4 && inertiaWeightEnabled){
			inertiaWeight *= inertiaFactor;
		}
        if (inertiaWeight * inertiaFactor < 0.4 && inertiaWeightEnabled){
			inertiaWeight = 0.4;
		}
		for (int i = 0; i < this.packages.size(); i++) {
			Package currentPackage = this.packages.get(i);
			int currentPosition = packages.get(i).getPosition();
			
			double particleSelfMemory = (bestLocalPosition.get(i) - currentPosition) * r1 * localAttraction;
			double globalInfluence = (bestGlobalPosition.get(i) - currentPosition) * r2 * globalAttraction;
			double inertia = this.packages.get(i).getProbability() * inertiaWeight;
			currentPackage.setProbability(particleSelfMemory+globalInfluence + inertia);
			
			//Clamping
			if(currentPackage.getProbability() > 4.25){
				currentPackage.setProbability(4.25);
			}
			if(currentPackage.getProbability() < -4.25){
				currentPackage.setProbability(-4.25);
			}
		}
	}
	
	public String toString(){
		String out = "[";
		for(Package p : this.packages){
			out += "" + p.getPosition();
		}
		out += "]";
		out += "\n";
		return out;
	}

}
