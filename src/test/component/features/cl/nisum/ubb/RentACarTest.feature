Feature: RentACar

Scenario Outline: Successful Rent a Car
	Given Usuario con id <user_id> sin multas	
	And Rango de Fechas es 
	| fecha_inicio | 2015-01-01  |
	| fecha_fin    | 2015-01-02  |	
	And Auto <car_type> disponible
	When Realizamos Reserva 
	Then Se realizar la Reserva
	And Los datos de reserva son 
	| tipo_auto  | <car_type>   |
	| user_id    | <user_id>    |
	
Examples: 
	| user_id |   car_type  |   
	|  1      |  deportivo  |
	|  2      |  sedan      |
		

Scenario Outline: Failed Rent a Car
	Given Usuario con id <user_id> sin multas	
	And Rango de Fechas es 
	| fecha_inicio | 2015-01-01  |
	| fecha_fin    | 2015-01-02  |	
	And Auto <car_type> no disponible
	When Realizamos Reserva 
	Then No se realizar la Reserva
	
Examples: 
	| user_id |   car_type  |   
	|  1      |  deportivo  |
	|  2      |  sedan      |
		