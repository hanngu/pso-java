package pso;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class KnapsackPSOWithVolume extends KnapsackPSO {

	public KnapsackPSOWithVolume(boolean addSupportForInertiaWeight) {
		super(addSupportForInertiaWeight);
	}

	@Override
	public void createPackages(String path){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
            String line = br.readLine();
            while (line != null){
            	String[] s = line.split(",");
            	double value = Double.parseDouble(s[0]);
            	double weight = Double.parseDouble(s[1]);
            	double volume = 1 + (Math.random() * ((5 - 1) + 1));
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
	
	@Override
	protected void knapsack(ArrayList<Package> packages) {
		boolean capacityReached = false;
		double remaingWeight = 1000.0;
		double remainingVolume = 1000.0;
		ArrayList<Package> possiblePackages = new ArrayList<Package>();
		for(Package p: packages){
			double sigmoid = 1/(1 + Math.exp(-p.getProbability()));
			if (sigmoid > random.nextDouble()){
				p.setPosition(1);
				possiblePackages.add(p);
			} else{
				p.setPosition(0);
			}
		}
		
		// Sorts by value so we pick the most valuable particles first.
		Collections.sort(possiblePackages);
		
		while(! capacityReached){
			Package particle = possiblePackages.get(0);
			if ((remaingWeight - particle.getWeight() >= 0) && (remainingVolume - particle.getVolume() >= 0)) {
				remaingWeight -= particle.getWeight();
				remainingVolume -= particle.getVolume();
				possiblePackages.remove(0);
			} else {
				capacityReached = true;
			}
		}
		for(Package p : possiblePackages){
			p.setPosition(0);
		}
	}
}
