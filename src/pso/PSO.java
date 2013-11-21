package pso;

public class PSO {
	
	public static void main(String[] args) {
		KnapsackPSO knapsack = new KnapsackPSO();
		knapsack.createPackages("packages.txt");
		knapsack.initializeSwarm();
		knapsack.run();
	}

}
