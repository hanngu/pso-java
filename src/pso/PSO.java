package pso;


import com.google.gson.Gson;

public class PSO {
	
	public static void main(String[] args) {
		KnapsackPSO knapsack = new KnapsackPSO(false);
		knapsack.createPackages("packages.txt", false);
		knapsack.initializeSwarm(100);

        Gson gson = new Gson();
        String json = gson.toJson(knapsack.run());
        System.out.println(json);
	}
}