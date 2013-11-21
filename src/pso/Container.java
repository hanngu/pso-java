package pso;

import java.util.ArrayList;
import java.util.Random;

public class Container {
	
	ArrayList<Package> packages;
	ArrayList<Integer> bestLocalPosition;
	double bestLocalPerformance, localAttraction, inertiaWeight;
	Random random; 
	public Container(ArrayList<Package> packages){
		this.packages = packages;
		bestLocalPosition = new ArrayList<Integer>();
		bestLocalPerformance = 0.0;
		localAttraction = 0.2;
		inertiaWeight = 1.0;
		random = new Random();
	}

	public ArrayList<Package> getPackages() {
		return this.packages;
	}

	public void compareLocalPerfomance() {
		if (evaluate() > bestLocalPerformance){
			bestLocalPerformance = evaluate();
			bestLocalPosition = getPositionVector();
		}	
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
	
	public double getTotaltWeight(){
		double totalWeight = 0.0;
		for(Package p : this.packages){
			if(p.getPosition() == 1){
				totalWeight += p.getWeight();
			}
		}
		return totalWeight;
	}

	public ArrayList<Integer> getPositionVector() {
		ArrayList<Integer> positonVector = new ArrayList<Integer>();
		for(Package p : this.packages){
			positonVector.add(p.getPosition());
		}
		return positonVector;
	}

	public void updatePositon(ArrayList<Integer> bestGlobalPosition,
			double globalAttraction, boolean addSupportForInertiaWeight) {
		double r1 = random.nextDouble();
		double r2 = random.nextDouble();
		
		for (int i = 0; i < this.packages.size(); i++) {
			Package currentPackage = this.packages.get(i);
			int currentPositon = packages.get(i).getPosition();
			
			double particleSelfMemory = (bestLocalPosition.get(i) - currentPositon) * r1 * this.localAttraction;
			double globalInfluence = (bestGlobalPosition.get(i) - currentPositon) * r2 * globalAttraction;
			double inertia = 0;
			if(addSupportForInertiaWeight){
				if((this.inertiaWeight*0.99) > 0.4){
					inertia = (currentPackage.getProbability() * (this.inertiaWeight*0.991));
					this.inertiaWeight *= 0.991;
				}
				else{
					inertia = currentPackage.getProbability()*0.4;
				}
			}
			else {
				inertia = currentPackage.getProbability();
			}
			currentPackage.setProbability(particleSelfMemory+globalInfluence+inertia);
			
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
