//class for storing measurement information

public class Measurement {
	
	private double[] energy;
	private double[] magn;
	private int nMeasurements;
	
	Measurement(int n){
		this.energy = new double[n];
		this.magn = new double[n];
		this.nMeasurements =n;
		
		for (int i=0;i<n;i++){
			this.energy[i]=0.0;
			this.magn[i]=0.0;
		}
		
	}
	
	public double getAvgMagnPow(int pow){
		
		double avgmagn=0.0;
		//compute <m>
		for (int i=0;i<this.nMeasurements;i++){
			avgmagn = avgmagn + Math.pow(this.magn[i],pow);
		}
		
		avgmagn = avgmagn / this.nMeasurements;
		
		return avgmagn;
	}
	
	public double getAvgEnePow(int pow){
		
		double avgenergy=0.0;
		//compute <E>
		
		for (int i=0;i<this.nMeasurements;i++){
			avgenergy = avgenergy + Math.pow(this.energy[i],pow);
		}
		
		avgenergy = avgenergy/ this.nMeasurements;
		
		return avgenergy;
	}
	
	
	public int getNumMeasures(){
		return this.nMeasurements;
	}
	
	public double getEnergy(){
		return 0.0;
	}
	
	public void setMagnetisation(double mag, int i){
		this.magn[i]=mag;
	}
	
	public void setEnergy(double ene, int i){
		this.energy[i]=ene;
	}
	


}
