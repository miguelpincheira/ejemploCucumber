package cl.nisum.ubb;

import java.util.ArrayList;
import java.util.List;

public class MultasClientStub implements MultasClient {

	private List<Multa> multas = new ArrayList<Multa>();

	@Override
	public List<Multa> getMultas(User user) {
		return multas ;
	}
	
	public void clear() {
		multas.clear();
	}
	
	public void add(Multa multa){
		multas.add(multa);
	}

}
