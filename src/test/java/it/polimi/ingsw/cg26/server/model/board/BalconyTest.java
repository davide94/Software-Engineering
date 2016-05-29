package it.polimi.ingsw.cg26.server.model.board;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;

public class BalconyTest {
	private int capacity;
	private Queue<Councillor> councillors1;
	private Queue<Councillor> councillors2;
	private Councillor c1;
	private Councillor c2;
	private Councillor c3;
	private Councillor c4;
	private Councillor c5;
	private PoliticColor color1;
	private PoliticColor color2;
	private PoliticColor color3;
	private Balcony balcony1;
	private Balcony balcony2;
	private Balcony balcony3;
	
	
	
	@Before
    public void setUp() throws Exception {
		capacity=4;
		councillors1=new LinkedList<>();
		councillors2=new LinkedList<>();
		color1= new PoliticColor("Nero");
		color2= new PoliticColor("Blu");
		color3= new PoliticColor("Bianco");
		c1= Councillor.createCouncillor(color1);
		c2= Councillor.createCouncillor(color2);
		c3= Councillor.createCouncillor(color1);
		c4= Councillor.createCouncillor(color3);
		c5= Councillor.createCouncillor(color2);
		
		councillors1.add(c1);
		councillors1.add(c2);
		councillors1.add(c3);
		councillors1.add(c4);
		balcony1= Balcony.createBalcony(capacity);
		balcony2= Balcony.createBalcony(capacity);
		balcony3= Balcony.createBalcony(capacity);
		councillors2.add(c2);
		councillors2.add(c3);
		councillors2.add(c4);
		councillors2.add(c5);
		(balcony1.getCouncillors()).add(c1);
		
		
				
		
	}
	
	
	@Test
	public void testCheckPoliticCards() {
		
		Collection<PoliticCardDTO> politicCards;
		
		//DA IMPLEMENTARE
		
		
		
	}
	
	
	
	@Test
	public void testShouldElectCouncillorC1() {
		
		Balcony b=Balcony.createBalcony(4);
		b.elect(c1);
		assertEquals(b,balcony1);
		assertEquals(b.getCouncillors(),balcony1.getCouncillors());
		
	}
	
	@Test
	public void testIfTwoBalconiesAreEquals() {
		
		Balcony b=Balcony.createBalcony(4);
		Balcony b2=null;
		Balcony b3=Balcony.createBalcony(5);
		Balcony b4=Balcony.createBalcony(4);
		CityColor blu= CityColor.createCityColor("blu");
		b.elect(c1);
		
		
		assertTrue(b.equals(balcony1));
		assertTrue(b.equals(b));
		assertFalse(b.equals(b2));
		assertFalse(b.equals(blu));
		assertFalse(b.equals(b3));
		assertFalse(b.equals(b4));
		
		
		
		
		
	}

	
	@Test (expected=IllegalArgumentException.class)
	public void testShouldNotCreateBalconyWithNoCouncillor() {
		Balcony.createBalcony(0);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testShouldNotCreateBalconyWithANegativeNumberoOfCouncillor() {
		Balcony.createBalcony(-1);
	}
	
	@Test
	public void testCreateBalcony() {
		
		Balcony b=Balcony.createBalcony(4);
		assertEquals(b, balcony2);
		
	}
	
	@Test
	public void testgetBalcony() {
		
		Balcony b=Balcony.createBalcony(4);
		b.elect(c1);
		b.elect(c2);
		b.elect(c3);
		b.elect(c4);
		
		assertEquals(b.getCouncillors(), councillors1);
		
	}
	
	@Test
	public void testPutTheFifthCouncillorAndTakeTheDroppedCouncillor() {
		
		Balcony b=Balcony.createBalcony(4);
		b.elect(c1);
		b.elect(c2);
		b.elect(c3);
		b.elect(c4);
		Councillor counsDropped=b.elect(c5);
		
		
		assertEquals(counsDropped, c1);
		assertEquals(b.getCouncillors(),councillors2);
		
	}
	
	
	
	@Test
	public void testToString() {
		
		assertEquals(balcony1.toString(),"Balcony{" +
				"councillors=" + "[Councillor{color=PoliticColor{color='Nero'}}]" +
				'}');
				
	}
	
	

	

	@Test (expected=NullPointerException.class)
	public void testShouldNotElectBecauseCouncillorIsNull() {
		
		Balcony b=Balcony.createBalcony(4);
		b.elect(null);
		
	}
	
	


	
	@Test
	public void testGetState() {
		balcony1.getState();
		
	}
	
	
	@Test
	public void testHashCode() {
		
		
		assertEquals(balcony1.hashCode(), balcony1.hashCode());
		assertNotEquals(balcony1.hashCode(), balcony2.hashCode());
		
		
	}

}