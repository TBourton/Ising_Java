

public class Ising {
	
	public static final int coldStart = 1;
	public static final int hotStart = 2;
	public static final int size=10;
	public static final double J=1.0;
	public static final double h=0.0;
	public static final double beta=0.5;
	public static final int nTherm=(int) (5* Math.pow(size, 4)); //number of thermalisation steps
	public static final int nMeasurements=100; //number of measurement steps

	public static void main(String[] args) {
		
		Lattice Lat = new Lattice(size, coldStart);
		Measurement Meas = new Measurement(nMeasurements); 
		
		Lat.printLattice();
		
		Lat.thermalise(nTherm);
		
		Lat.printLattice();
		
		Lat.doMeasurements(Meas);
		
		System.out.println(Meas.getAvgEnePow(2));
		

	}
	

}
