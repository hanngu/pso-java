package pso;


public class PSO {
	
	public static void main(String[] args) {
		
//		NeighbourAwarePso basicPso = new NeighbourAwarePso(2, 15);
//		basicPso.run();

		KnapsackPSO knapsack = new KnapsackPSO(true);
		knapsack.createPackages("packages.txt");
		knapsack.initializeSwarm(200);
		System.out.println(knapsack.run());
	}
}