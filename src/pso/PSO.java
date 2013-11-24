package pso;


import com.google.gson.Gson;

import java.util.Scanner;

public class PSO {
	
	public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("How many particles should be in the swarm? (1 - 4000)");
        int particles = sc.nextInt();
        System.out.println("Do you want to add inertia weight to a particles probability each iteration? (true or false)");
        boolean inertiaWeightEnabled = sc.nextBoolean();
        System.out.println("Should a particle's volume be taken into account? (true or false)");
        boolean isVolumeSignificant = sc.nextBoolean();

        System.out.println();
        System.out.println("Generating a 0-1 Knapsack solution using 500 iterations.");
        System.out.println("The algorithm uses " + particles + " particles each having 2001 packages.");
        System.out.println("Inertia weight is enabled: " + inertiaWeightEnabled);
        System.out.println("Consider a package's volume: " + isVolumeSignificant);
        System.out.println("Progress update is output every 10 iterations...");
        System.out.println();



		KnapsackPSO knapsack = new KnapsackPSO(inertiaWeightEnabled);
		knapsack.createPackages("packages.txt", isVolumeSignificant);
		knapsack.initializeSwarm(particles);

        Gson gson = new Gson();
        String json = gson.toJson(knapsack.run());
    }
}