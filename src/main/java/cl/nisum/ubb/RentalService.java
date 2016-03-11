package cl.nisum.ubb;

import java.util.ArrayList;
import java.util.List;

public class RentalService {

	private static final int MAX_MULTAS = 10;
	
	private MultasClient multasClient;
	private ErrorNotificationService errorNotificationService;
	private CarService carService;
	
	public Booking rent(CarType carType, User user, DateRange dateRange, RentalPreferences rentalPreferences) throws RentalException{
		
		List<Multa> multas = getMultas(user);
		
		Booking booking = Booking.nullBooking();
		
		if(multas.size() < MAX_MULTAS ) {
			Car car = carService.findScheduleForCar(carType,rentalPreferences,dateRange);
			
			if(car.isAvailable()) {
				booking = new CarBooking(car,user, dateRange, rentalPreferences);
			}
		}
		
		return booking;
	}

	private List<Multa> getMultas(User user) throws RentalException {
		
		List<Multa> multas = new ArrayList<Multa>();
		
		try {
			multas = multasClient.getMultas(user);
		} catch (Exception e) {
			errorNotificationService.notify("Could not retrieve multas for User " + user.getId(),e);
			throw new RentalException(e);
		}
		return multas;
	}

	public MultasClient getMultasClient() {
		return multasClient;
	}

	public void setMultasClient(MultasClient multasClient) {
		this.multasClient = multasClient;
	}

	public ErrorNotificationService getErrorNotificationService() {
		return errorNotificationService;
	}

	public void setErrorNotificationService(
			ErrorNotificationService errorNotificationService) {
		this.errorNotificationService = errorNotificationService;
	}

	public CarService getCarService() {
		return carService;
	}

	public void setCarService(CarService carService) {
		this.carService = carService;
	}
	
}
