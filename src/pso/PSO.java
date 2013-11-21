package pso;


public class PSO {
	
	public static void main(String[] args) {
		

		KnapsackPSO knapsack = new KnapsackPSO(true);
		knapsack.createPackages("packages.txt");
		knapsack.initializeSwarm(4000);
		knapsack.run();
			

	}
	
}
