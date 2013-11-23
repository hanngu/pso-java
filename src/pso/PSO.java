package pso;


import com.google.gson.Gson;

public class PSO {
	
	public static void main(String[] args) {
		
//		NeighbourAwarePso basicPso = new NeighbourAwarePso(2, 15);
//		basicPso.run();

		KnapsackPSO knapsack = new KnapsackPSO(true);
		knapsack.createPackages("packages.txt");
		knapsack.initializeSwarm(4000);

        Gson gson = new Gson();
        String json = gson.toJson(knapsack.run());
        System.out.println(json);
	}
}