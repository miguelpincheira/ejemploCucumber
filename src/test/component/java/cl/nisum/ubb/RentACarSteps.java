package cl.nisum.ubb;

import java.util.List;
import java.util.Map;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.mockito.Mockito;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

//Implementar una clase abstracta, a modo de repositorio (Mapa) para pasar datos entre Steps compartidos.

public class RentACarSteps {

	private User user;
	private CarType carType;
	private DateRange dateRange;
	private RentalPreferences rentalPreferences = new RentalPreferences();
	private RentalService rentalService;
	private MultasClientStub multasClientStub = new MultasClientStub();
	private CarService carServiceMock;
	private Booking booking; 
	
	@Before
	public void setup() {
		rentalService = new RentalService();
		rentalService.setMultasClient(multasClientStub  );
		carServiceMock = Mockito.mock(CarService.class);
		rentalService.setCarService(carServiceMock );
		
	}

	@Given("^Usuario con id (\\d+) sin multas$")
	public void usuario_sin_multas(int id) throws Throwable {
		
		user = new User(id);
		multasClientStub.clear();
		
	}
	
	@Given("^Rango de Fechas es$")
	public void rango_de_fechas(Map<String,String> table) throws Throwable {
		dateRange = new DateRange(table.get("fecha_inicio"),table.get("fecha_fin"));
	}	

	@Given("^Auto (\\w+) (disponible|no disponible)$")
	public void auto_disponible(String carType, String disponible) throws Throwable {
		this.carType = new CarType(carType);
		Car car = new Car(carType);			
		car.setAvailable("disponible".equals(disponible));
		Mockito.when(carServiceMock.findScheduleForCar(Mockito.eq(this.carType), Mockito.any(RentalPreferences.class), Mockito.eq(dateRange))).thenReturn(car);
		
	}
		

	@When("^Realizamos Reserva$")
	public void realizamos_Reserva() throws Throwable {
		booking = rentalService.rent(carType, user, dateRange, rentalPreferences);
		
	}

	@Then("^(Se|No se) realizar la Reserva$")
	public void se_realizar_la_Reserva(String flagReserva) throws Throwable {
		if("Se".equals(flagReserva)){
			Assert.assertEquals(booking.getClass() , CarBooking.class);
		}else{
			Assert.assertEquals(booking.getClass() , NullBooking.class);
		}
	}

	@Then("^Los datos de reserva son$")
	public void los_datos_de_reserva_son(Map<String,String> map) throws Throwable {
		Car car = booking.getCar();
		User user = booking.getUser();
		
		Assert.assertThat(car.getType(),Is.is(map.get("tipo_auto")));
		Assert.assertThat(user.getId(),Is.is(Integer.valueOf(map.get("user_id"))));
		
	}


}
