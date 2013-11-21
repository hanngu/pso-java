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
	Random random;
	public KnapsackPSO(){
		packages = new ArrayList<Package>();
		swarm = new ArrayList<Container>();
		bestGlobalPerfomance = 0.0;
		bestGlobalPosition = new ArrayList<Integer>();
		globalAttraction = 1.5;
		random = new Random();
	}
	
	public void run(){
		int numberOfIterations = 0;
		while(numberOfIterations < 500) {
			for(Container container: swarm) {
				knapsackConatiner(container.getPackages());
				container.compareLocalPerfomance();
				if(container.evaluate() > bestGlobalPerfomance){
					bestGlobalPerfomance = container.evaluate();
					bestGlobalPosition = new ArrayList<Integer>(container.getPositionVector()); //Er detta lov? 
					container.updatePositon(bestGlobalPosition, globalAttraction);
				}
			}
			numberOfIterations += 1;
			System.out.println("Best global performance: " + this.bestGlobalPerfomance);
		}
		System.out.println("Best global position: " + this.bestGlobalPosition);
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
	
	public void initializeSwarm(){
		Random random = new Random();
		for (int i = 0; i < 4000; i++) {
			ArrayList<Package> p = new ArrayList<Package>(packages); //Er detta lov? 
			
			for(Package pack : p){
				double r = (random.nextDouble() * 8.5) - 4.25; 
				pack.setProbability(r);
			}
			
			for (int j = 0; j < (packages.size()/4); j++) {
				int randomPacakage = random.nextInt(2000);
				Package randomSelectedPackage = packages.get(randomPacakage);
				randomSelectedPackage.setPosition(1);
			}
			this.swarm.add(new Container(p));
		}
	}
	
	private void knapsackConatiner(ArrayList<Package> containerPackages) {
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
}
