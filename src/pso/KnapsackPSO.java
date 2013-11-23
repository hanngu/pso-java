package pso;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class KnapsackPSO {
	ArrayList<Package> packages;
	ArrayList<Container> swarm;
	double bestGlobalPerfomance; 
	ArrayList<Integer> bestGlobalPosition;
	double globalAttraction;
	boolean addSupportForInertiaWeight;
	Random random;
	Container winningContainer;
	ArrayList<Double> chartDataContainer = new ArrayList<Double>();
	
	public KnapsackPSO(boolean addSupportForInertiaWeight){
		this.addSupportForInertiaWeight = addSupportForInertiaWeight;
		this.packages = new ArrayList<Package>();
		this.swarm = new ArrayList<Container>();
		this.bestGlobalPerfomance = 0.0;
		this.bestGlobalPosition = new ArrayList<Integer>();
		this.globalAttraction = 1.5;
		this.random = new Random();
	}
	
	public ArrayList<Double> run(){
		int numberOfIterations = 0;
		while(numberOfIterations < 300) {
			for(Container container: swarm) {
				knapsack(container.getPackages());
				container.compareLocalPerfomance();
				if(container.evaluate() > bestGlobalPerfomance){
					bestGlobalPerfomance = container.evaluate();
					bestGlobalPosition = new ArrayList<Integer>(container.getPositionVector());
					winningContainer = new Container(container);
				}
				container.updatePositon(bestGlobalPosition, globalAttraction, addSupportForInertiaWeight);
			}
			if (numberOfIterations % 50 == 0) {
				System.out.println("Iteration number " + numberOfIterations);
				print();
			}
			numberOfIterations += 1;
			chartDataContainer.add(bestGlobalPerfomance);
		}
		System.out.println("Solution found with " + numberOfIterations + " iterations.");
		print();
		return chartDataContainer;
		}
	
	public void createPackages(String path){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
            String line = br.readLine();
            while (line != null){
            	String[] s = line.split(",");
            	double value = Double.parseDouble(s[0]);
            	double weight = Double.parseDouble(s[1]);
            	packages.add(new Package(value, weight, 0, -1));
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
				int randomPacakage = random.nextInt(2001);
				Package randomSelectedPackage = packages.get(randomPacakage);
				randomSelectedPackage.setPosition(1);
			}
			this.swarm.add(new Container(p));
		}
	}
	
	protected void knapsack(ArrayList<Package> containerPackages) {
		boolean stillFreeSpace = true;
		double remainingSpace = 1000.0;
		ArrayList<Package> possiblePackages = new ArrayList<Package>();
		for(Package p: containerPackages){
			double sigmoid = 1/(1 + Math.exp(-p.getProbability()));
			if (sigmoid > random.nextDouble()){
				p.setPosition(1);
				possiblePackages.add(p);
			} else{
				p.setPosition(0);
			}
		}
		Collections.sort(possiblePackages);
		while(stillFreeSpace){
			Package packageToPutInContainer = possiblePackages.get(0);
			if((remainingSpace - packageToPutInContainer.getWeight()) >= 0){
				remainingSpace -= packageToPutInContainer.getWeight();
				possiblePackages.remove(0);
			} else {
				stillFreeSpace = false;
			}
		}
		for(Package p : possiblePackages){
			p.setPosition(0);
		}
	}
	
	public String toString(){
		String out = "";
		for (Container c : this.swarm){
			out += c;
		}
		return out;
	}
	
	
	public void print() {
		double weight = winningContainer.getTotalWeight();
		double volume = winningContainer.getTotalVolume();
				
		
		String output = "Winning container specs:\n";
		output += "Weight: "+ weight + "\n";
		output += "Volume: " + volume + "\n";
		output += "Performance: " + winningContainer.evaluate();
		output += "Number of packages: " + winningContainer.getPackagesInContainer();
		System.out.println(output);
	}
}
