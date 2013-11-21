package pso;

public class Package implements Comparable<Package>{
	
	double value, weight, probability;
	int position;
	
	public Package(double value, double weight, int position, double probability){
		this.value = value;
		this.weight = weight;
		this.position = position;
		this.probability = probability;
	}
	
	public Package(Package packageToCopy){
		this.value = packageToCopy.getValue();
		this.weight = packageToCopy.getWeight();
		this.position = packageToCopy.getPosition();
		this.probability = packageToCopy.getProbability();
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public double getValue() {
		return value;
	}

	public double getWeight() {
		return weight;
	}

	//Kanskje buggy?
	public int compareTo(Package packageToCompare) {
		if(this.getValue() < packageToCompare.getValue()){
			return 1;
		}
		return -1;
	}

}
