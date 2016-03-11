package cl.nisum.ubb;

public class Car {

	private String type; 
	private boolean available; 
	
	public Car(String type){
		this.type = type;
		
	}
	
	public boolean isAvailable() {
		return available;
	}

	public String getType() {
		return type;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

}
