package cl.nisum.ubb;

public class CarBooking extends Booking {	
	
	private Car car;
	private User user;
	
	public CarBooking(Car car, User user, DateRange dateRange,
			RentalPreferences rentalPreferences) {
		this.user = user;
		this.car = car;
	}

	@Override
	public Car getCar() {
		// TODO Auto-generated method stub
		return car;
	}

	@Override
	public User getUser() {
		// TODO Auto-generated method stub
		return user;
	}

}
