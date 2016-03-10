package cl.nisum.ubb;

public abstract class Booking {


	public static Booking nullBooking() {
		return new NullBooking();
	}

}
