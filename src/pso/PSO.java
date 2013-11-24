package pso;


import com.google.gson.Gson;

public class PSO {
	
	public static void main(String[] args) {
		
//		NeighbourAwarePso basicPso = new NeighbourAwarePso(2, 15);
//		basicPso.run();
//		double withInertiaWeightValue = 0.0;
//		double withoutInertiaWeightValue = 0.0;
//		
//		for (int i = 0; i < 10; i++) {
//			KnapsackPSO knapsack = new KnapsackPSO(false);
//			knapsack.createPackages("packages.txt");
//			knapsack.initializeSwarm(100);
//			withoutInertiaWeightValue += knapsack.run();
//		}
//		
//		for (int i = 0; i < 10; i++) {
//			KnapsackPSO knapsack = new KnapsackPSO(true);
//			knapsack.createPackages("packages.txt");
//			knapsack.initializeSwarm(100);
//			withInertiaWeightValue += knapsack.run();
//		}
//		
//		System.out.println("With inertia weight: " + (withInertiaWeightValue/10));
//		System.out.println("Without  inertia weight: " + (withoutInertiaWeightValue/10));
		
		
		KnapsackPSO knapsack = new KnapsackPSO(true);
		knapsack.createPackages("packages.txt");
		knapsack.initializeSwarm(4000);

        Gson gson = new Gson();
        String json = gson.toJson(knapsack.run());
        System.out.println(json);
	}
}