package cl.nisum.ubb;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RentalServiceTest {

	@InjectMocks
	private RentalService rentalService;
	@Mock
	private MultasClient multasClient;
	@Mock
	private ErrorNotificationService errorNotificationService;
	@Mock
	private CarService carService;
	@Mock	
	private Car car;
	@Mock
	private User user;
	
	
	private CarType carType;
	private DateRange dateRange;
	private RentalPreferences rentalPreferences;

		
	@Test
	public void userWithoutMultasCarAvailableDoCarBooking() throws RentalException{
		

		when(multasClient.getMultas(user)).thenReturn(new ArrayList<Multa>());	
		when(carService.findScheduleForCar(carType, rentalPreferences, dateRange)).thenReturn(car);
		when(car.isAvailable()).thenReturn(true);
		
		Booking booking = rentalService.rent(carType, user, dateRange, rentalPreferences);		
		
		verify(multasClient).getMultas(user);
		verify(carService).findScheduleForCar(carType, rentalPreferences, dateRange);
		verify(car).isAvailable();
		
		Assert.assertEquals(booking.getClass(),CarBooking.class);
		
	}
	
	@Test
	public void userWithFiveMultasCarAvailableDoCarBooking() throws RentalException{
		
		Mockito.when(multasClient.getMultas(user)).thenReturn(crearListaMultas(5));	
		Mockito.when(carService.findScheduleForCar(carType, rentalPreferences, dateRange)).thenReturn(car);
		Mockito.when(car.isAvailable()).thenReturn(true);
		
		Booking booking = rentalService.rent(carType, user, dateRange, rentalPreferences);		
		
		Assert.assertEquals(booking.getClass(),CarBooking.class);	
	}
	
	@Test
	public void userWithFifteenMultasNullBooking() throws RentalException{
		
		Mockito.when(multasClient.getMultas(user)).thenReturn(crearListaMultas(15));
		
		Booking booking = rentalService.rent(carType, user, dateRange, rentalPreferences);		
		
		Assert.assertEquals(booking.getClass(),NullBooking.class);
		
	}	
	
	@Test
	public void userWithoutMultasCarNotAvailableDoNullBooking() throws RentalException{
		
		Mockito.when(multasClient.getMultas(user)).thenReturn(new ArrayList<Multa>());	
		Mockito.when(carService.findScheduleForCar(carType, rentalPreferences, dateRange)).thenReturn(car);
		Mockito.when(car.isAvailable()).thenReturn(false);
		
		Booking booking = rentalService.rent(carType, user, dateRange, rentalPreferences);		
		
		Assert.assertEquals(booking.getClass(),NullBooking.class);
		
	}	
	
	
	@Test (expected=RentalException.class)
	public void errorWithMultasClientThrowsRentalException() throws RentalException{

		Mockito.when(multasClient.getMultas(user)).thenThrow(Exception.class);
		
		Booking booking = rentalService.rent(carType, user, dateRange, rentalPreferences);	
					
	}

	
	
	//Metodo Auxiliar 
	private List<Multa> crearListaMultas(int size){
		List<Multa> lista = new ArrayList<Multa>(size);
		for (int i = 0; i < size; i++) {
			lista.add(new Multa());
		}
		return lista;
	}

}
