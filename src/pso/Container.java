package pso;

import java.util.ArrayList;
import java.util.Random;

public class Container {
	
	ArrayList<Package> packages;
	ArrayList<Integer> bestLocalPosition = new ArrayList<Integer>();
	double bestLocalPerformance = 0.0;
	double localAttraction = 1.5;
	double inertiaWeight = 1.0;
	Random random = new Random(); 
	
	public Container(ArrayList<Package> packages){
		this.packages = packages;
	}
	
	public Container (Container copy) {
		this.packages = new ArrayList<Package>();
		for (Package p: copy.getPackages()) {
			this.packages.add(new Package(p));
		}
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
	
	public double getTotalWeight(){
		double totalWeight = 0.0;
		for(Package p : this.packages){
			if(p.getPosition() == 1){
				totalWeight += p.getWeight();
			}
		}
		return totalWeight;
	}
	
	public double getTotalVolume() {
		double volume = 0;
		for (Package p : this.packages) {
			if (p.getPosition() == 1) {
				volume += p.getVolume();
			}
		}
		return volume;
	}
	
	public int getPackagesInContainer() {
		int numberOfPackages = 0;
		for (Package p : this.packages) {
			if (p.getPosition() == 1 ) {
				numberOfPackages++;
			}
		}
		return numberOfPackages;
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
		double inertia = 0;
		
		if(addSupportForInertiaWeight){
			if((this.inertiaWeight*0.7) > 0.4){
				this.inertiaWeight *= 0.7;
			} else{
				inertia = 0.4;
			}
		} else {
			inertia = 1.0;
		}
		for (int i = 0; i < this.packages.size(); i++) {
			Package currentPackage = this.packages.get(i);
			int currentPositon = packages.get(i).getPosition();
			
			double particleSelfMemory = (bestLocalPosition.get(i) - currentPositon) * r1 * this.localAttraction;
			double globalInfluence = (bestGlobalPosition.get(i) - currentPositon) * r2 * globalAttraction;
			inertia *= this.packages.get(i).getProbability(); 
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
