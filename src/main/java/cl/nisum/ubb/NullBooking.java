package cl.nisum.ubb;

public class NullBooking extends Booking {

	@Override
	public Car getCar() {
		return null;
	}

	@Override
	public User getUser() {
		return null;
	}

}
