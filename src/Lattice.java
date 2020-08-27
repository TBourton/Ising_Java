
import java.util.Random;

public class Lattice {
	private long seed = 88;
	private Random rnd = new Random(seed);
	
	private int[][] spins;
	private int size;
	
	//Constructor for initalising the Lattice
	Lattice(int size, int startType){ 
		
		this.spins = new int[size][size];
		this.size = size;
		
		for (int i=0;i<size;i++){
			for (int j=0;j<size;j++){
				if (startType==Ising.coldStart) this.spins[i][j]=1;
				else if (startType==Ising.hotStart){
					if (rnd.nextDouble()<0.5) this.spins[i][j]=-1;
					else this.spins[i][j]=1;
				}
			}
		}
		
	}
	
	//getters
	public int[][] getLattice(){
		//gets the current lattice spin configurations
		return this.spins;
	}
	
	public int getLatticeSize(){
		return this.size;
	}
	
	
	public void printLattice(){
		//prints the current lattice configuration
		final String uparrow = "u";
		final String downarrow = "d";
		
		for (int i=0;i<size;i++){
			for (int j=0;j<size;j++){
				if (this.spins[i][j]==1) System.out.print(uparrow);
				else if (this.spins[i][j]==-1)  System.out.print(downarrow);
				else System.out.print("There is a spin that is something other than " + uparrow + " or " + downarrow);
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}
	
	public void thermalise(int nTherm){
		for (int p=0;p<nTherm;p++) doUpdateStep();
	}
	
	public void doMeasurements(Measurement Meas){
		int n=Meas.getNumMeasures();
		
		for (int p=0;p<n;p++) {
			doUpdateStep();
			writeObservables(Meas,p);
		}
	}
	
	private void doUpdateStep(){
		
		// a single update step consists of choosing a random spin site from the lattice
		// then using our acceptance algorithm of choice we make decision as to whether to
		// flip the chosen site or not
		
		//First, select a random site
		int irand = rnd.nextInt(this.size);
		int jrand = rnd.nextInt(this.size);
		
		//Call the update algo
		this.spins[irand][jrand]=metropolis(irand,jrand);
		
	}
	 
	private void writeObservables(Measurement Meas, int p){
		
		double mag = 0.0;
		double ene = 0.0;
		double vol = (double) (this.size * this.size);
		
		
		for (int i=0;i<this.size;i++){
			for (int j=0;j<this.size;j++){
				mag = mag + this.spins[i][j];
				ene = ene + this.spins[i][j]*(nearestNeighbours(i,j)[2]+nearestNeighbours(i,j)[3]);
			}
		}
		Meas.setMagnetisation( Math.abs(mag)/vol,p);
		Meas.setEnergy(ene/vol,p);
		
	}
	
	private int metropolis(int i, int j){
		// good thing -> energy change from single proposed flip s depends
		// only on dE=2Js(\sum_{neighbours}s_i) in the current configuration
		
		double deltaE=0.0;
		int[] neighbourSpins = this.nearestNeighbours(i, j);
		int s=this.spins[i][j];
		
		for (int p=0;p<neighbourSpins.length;p++){
			deltaE=deltaE + 2 * Ising.J * s * neighbourSpins[p];
		}
		
		//if it minimises the energy or prob check true then flip the spin
		if ((deltaE < 0.0) || (rnd.nextDouble() < Math.exp(- Ising.beta * deltaE))){
			s = (-1) * s; 
		}

		return s;
	}
	
	private int[] nearestNeighbours(int i,int j){
		
		//get the four nearest neighbours of site s=(i,j)
		//  0   
		//1 s 2
		//  3
		//
		
		//periodic BCs
		int[] siteList = {0,0,0,0};

		siteList[0]=this.spins[Math.floorMod(i-1, this.size)][j];
		siteList[1]=this.spins[i][Math.floorMod(j-1, this.size)];
		siteList[2]=this.spins[i][Math.floorMod(j+1, this.size)];
		siteList[3]=this.spins[Math.floorMod(i+1, this.size)][j];
		
		return siteList;
	}

}
