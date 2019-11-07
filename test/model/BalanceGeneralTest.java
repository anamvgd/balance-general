package model;

import static org.junit.Assert.*;

import org.junit.Test;

class BalanceGeneralTest {
	
	private BalanceGeneral bg;
	
	private void setupScenary1() {
		
	}
	
	private void setupScenary2() {
		bg = new BalanceGeneral("Atlas Ltda.");
	}

	@Test
	public void balanceGeneralTest() {
		setupScenary1();
		String empresa = "Atlas Ltda.";
		bg = new BalanceGeneral(empresa);
		assertNotNull("la empresa no fue creada", bg != null);
		
	}

	@Test
	public void sumarActivosCorrientesTest() {
		setupScenary2();
		assertNotNull("The list is null", bg.getActivos()!=null);
		assertNotNull("The list is null", bg.getGastos()!=null);
		assertNotNull("The list is null", bg.getPatrimonio()!=null);
		assertNotNull("The list is null", bg.getPasivos()!=null);
		assertNotNull("The list is null", bg.getIngresos()!=null);
		
	}

}
