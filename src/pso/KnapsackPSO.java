package pso;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class KnapsackPSO {
	private ArrayList<Package> packages;
	private ArrayList<Container> swarm;
	private double bestGlobalPerformance;
	private ArrayList<Integer> bestGlobalPosition;
	private double globalAttraction;
	private boolean inertiaWeightEnabled;
	private Random randomGenerator;
	private ArrayList<HashMap<String, Double>> chartDataContainer = new ArrayList<HashMap<String, Double>>();
	
	public KnapsackPSO(boolean inertiaWeightEnabled){
		this.inertiaWeightEnabled = inertiaWeightEnabled;
		this.packages = new ArrayList<Package>();
		this.swarm = new ArrayList<Container>();
		this.bestGlobalPerformance = 0.0;
		this.bestGlobalPosition = new ArrayList<Integer>();
		this.globalAttraction = 0.2;
		this.randomGenerator = new Random();
	}
	
	public ArrayList<HashMap<String, Double>> run(){
		int numberOfIterations = 0;
        while(numberOfIterations < 500) {
			for(Container container: swarm) {

                double evaluation = container.evaluate();

                if (evaluation > container.bestLocalPerformance) {
                    container.setBestLocalPerformance(evaluation);
                    container.setBestLocalPosition(container.getPositionVector());
                }

				if(evaluation > bestGlobalPerformance){
					bestGlobalPerformance = container.evaluate();
					bestGlobalPosition = new ArrayList<Integer>(container.getPositionVector());
				}

			}
            changeProbabilities();
            knapsackSwarm();

            numberOfIterations += 1;
            HashMap<String, Double> bestGlobalPerformanceDataPoint = new HashMap<String, Double>();
            bestGlobalPerformanceDataPoint.put("x", (double) numberOfIterations);
            bestGlobalPerformanceDataPoint.put("y", bestGlobalPerformance);

			chartDataContainer.add(bestGlobalPerformanceDataPoint);
        }
        System.out.println("Solution found with " + numberOfIterations + " iterations.");
	    return chartDataContainer;
    }

    private void changeProbabilities() {
        for (Container c : swarm) {
            c.updatePosition(bestGlobalPosition, globalAttraction, inertiaWeightEnabled);
        }
    }
	public void createPackages(String path, boolean volumeIsSignificant) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
            String line = br.readLine();
            while (line != null){
            	String[] s = line.split(",");
            	double value = Double.parseDouble(s[0]);
            	double weight = Double.parseDouble(s[1]);
                double volume = volumeIsSignificant ? 1 + (Math.random() * ((100 - 1) + 1)) : 0;

                packages.add(new Package(value, weight, 0, -1, volume));
            	line = br.readLine();
            }
        }catch (FileNotFoundException e) {
            System.err.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	public void initializeSwarm(int numberOfContainers){
		Random random = new Random();
		for (int i = 0; i < numberOfContainers; i++) {
			ArrayList<Package> p = new ArrayList<Package>();
			for(Package packageToCopy: this.packages){
				p.add(new Package(packageToCopy));
			}
			
			for(Package pack : p){
				double r = (random.nextDouble() * 8.5) - 4.25; 
				pack.setProbability(r);
			}
			
			for (int j = 0; j < (packages.size()/4); j++) {
				int randomPackage = random.nextInt(2001);
				Package randomSelectedPackage = packages.get(randomPackage);
				randomSelectedPackage.setPosition(1);
			}
			this.swarm.add(new Container(p));
		}
        knapsackSwarm();
	}
	
	protected void knapsackSwarm() {
        for (Container container : swarm) {
            ArrayList<Package> packages = container.getPackages();

            boolean capacityReached = true;
            double weightRemaining = 1000.0;
            double volumeRemaining = 1000.0;
            ArrayList<Package> possiblePackages = new ArrayList<Package>();
            for(Package p: packages) {
                double sigmoid = 1/(1 + Math.exp(-p.getProbability()));
                if (sigmoid > randomGenerator.nextDouble()){
                    p.setPosition(1);
                    possiblePackages.add(p);
                } else{
                    p.setPosition(0);
                }
            }
            Collections.sort(possiblePackages);
            while(capacityReached){
                Package pack = possiblePackages.get(0);
                double weight = pack.getWeight();
                double volume = pack.getVolume();
                if((weightRemaining - weight) >= 0 && volumeRemaining - volume >= 0) {
                    volumeRemaining -= volume;
                    weightRemaining -= weight;
                    possiblePackages.remove(0);
                } else {
                    capacityReached = false;
                }
            }
            for(Package p : possiblePackages){
                p.setPosition(0);
            }
        }
	}
	
	public String toString(){
		String out = "";
		for (Container c : this.swarm){
			out += c;
		}
		return out;
	}
}
