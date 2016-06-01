package it.polimi.ingsw.cg26.server.model.board;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.BonusDTO;
import it.polimi.ingsw.cg26.common.dto.CityColorDTO;
import it.polimi.ingsw.cg26.common.dto.CityDTO;
import it.polimi.ingsw.cg26.common.dto.EmporiumDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.common.dto.RewardTileDTO;
import it.polimi.ingsw.cg26.server.model.bonus.*;
import it.polimi.ingsw.cg26.server.model.cards.*;
import it.polimi.ingsw.cg26.server.model.player.Player;


public class RegionTest {
	
	private City foggia;
	private City bari;
	private City brindisi;
	private City taranto;
	private City lecce;
	private Collection<City> cities;
	private Balcony balcony;
	private RewardTile premio;
	
	private List<Bonus> bonuses1;
	private List<Bonus> bonuses2;
	private List<Bonus> bonuses3;
	private List<Bonus> bonuses4;
	private List<Bonus> bonuses5;
	private List<Bonus> bonusesRegion;
	
	private RewardTile tile1;
	private RewardTile tile2;
	private RewardTile tile3;
	private RewardTile tile4;
	private RewardTile tile5;
	
	private Collection<BusinessPermissionTile> cards;
	private BusinessPermissionTileDeck deck;
	private Player Luca;
		
	@Before
	public void setUp() throws Exception {
		
		 bonuses1=new LinkedList<>();
		 bonuses2=new LinkedList<>();
		 bonuses3=new LinkedList<>();
		 bonuses4=new LinkedList<>();
		 bonuses5=new LinkedList<>();
		 bonusesRegion=new LinkedList<>();
		 bonusesRegion.add(new VictoryBonus(5));
		 
		 
		 tile1=new RewardTile(bonuses1);
		 tile2=new RewardTile(bonuses2);
		 tile3=new RewardTile(bonuses3);
		 tile4=new RewardTile(bonuses4);
		 tile5=new RewardTile(bonuses5);
		 
		
		
		cards= new LinkedList<BusinessPermissionTile>();
		deck= new BusinessPermissionTileDeck(cards);
		
		balcony= Balcony.createBalcony(4);
		premio= new RewardTile(bonusesRegion);
		
		foggia = City.createCity("Foggia", CityColor.createCityColor("nero"), tile1);
		bari = City.createCity("Bari", CityColor.createCityColor("bianco"), tile2);
		brindisi = City.createCity("Brindisi", CityColor.createCityColor("blu"), tile3);
		taranto = City.createCity("Taranto", CityColor.createCityColor("rosso"), tile4);
		lecce = City.createCity("Lecce", CityColor.createCityColor("giallo"), tile5);
		
		cities= new LinkedList<>();
		cities.add(foggia);
		cities.add(bari);
		cities.add(brindisi);
		cities.add(taranto);
		cities.add(lecce);
		
		Luca=new Player(1235, "Luca", NobilityCell.createNobilityCell(11, null, new RewardTile(new LinkedList<Bonus>())), 10, new LinkedList<>(), new LinkedList<>());
		
		
	}

	

	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateRegionIfNameIsNull() {
		
		Region.createRegion(null, cities, deck, balcony, premio);
		
	}
	

	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateRegionIfTheListOfCitiesIsNull() {
		
		Region.createRegion("Puglia", null, deck, balcony, premio);
		
	}
	
	

	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateRegionIfBPTDeckIsNull() {
		
		Region.createRegion("Puglia", cities, null, balcony, premio);
	}
	
	

	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateRegionIfBalconyIsNull() {
		
		Region.createRegion("Puglia", cities, deck, null, premio);
	}
	
	

	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateRegionIfRewardBonusRegionIsNull() {
		
		Region.createRegion("Puglia", cities, deck, balcony, null);
		
	}
	

	@Test
	public void testShouldCreateRegion() {
		
		Region puglia=Region.createRegion("Puglia", cities, deck, balcony, premio);
		
		assertNotNull(puglia);
		
		
	}
	


	@Test
	public void testCheckRegionBonusesIsFalseBecauseThereAreNotEmporiumInCities() {
		Region puglia=Region.createRegion("Puglia", cities, deck, balcony, premio);
		
		assertFalse(puglia.checkRegionBonuses(Luca));
		
		
		
	}


	
	@Test
	public void testCheckRegionBonusesIsFalseBecauseThereAreNoEnoughEmporiumsInCities() {
		Region puglia=Region.createRegion("Puglia", cities, deck, balcony, premio);
		foggia.build(Luca);
		lecce.build(Luca);
		
		assertFalse(puglia.checkRegionBonuses(Luca));
		
	}


	@Test
	public void testCheckRegionBonusesIsTrueBecauseThereAreAllTheEmporiums() {
		
		Region puglia=Region.createRegion("Puglia", cities, deck, balcony, premio);
		
		foggia.build(Luca);
		lecce.build(Luca);
		bari.build(Luca);
		brindisi.build(Luca);
		taranto.build(Luca);
		
		assertTrue(puglia.checkRegionBonuses(Luca));

		
		
		
	}


	

	@Test
	public void testGetRegionBonus() { 
		Region puglia=Region.createRegion("Puglia", cities, deck, balcony, premio);
		
		assertEquals(puglia.getRegionBonus(), premio);
		
	}
		
	
	

	@Test
	public void testGetCities() {
		Region puglia=Region.createRegion("Puglia", cities, deck, balcony, premio);
		
		assertEquals(puglia.getCities(), cities);
		
	}
	
	

	@Test
	public void testGetCity() {
		Region puglia=Region.createRegion("Puglia", cities, deck, balcony, premio);

		Collection<EmporiumDTO> emporiums= new ArrayList<>();
		Collection<String> strings= new LinkedList<>();
		Collection<BonusDTO> bonuses= new ArrayList<>();

		CityDTO requiredCity= new CityDTO("Lecce", new CityColorDTO("marrone"),new RewardTileDTO (bonuses), emporiums, strings);

		assertEquals(puglia.getCity(requiredCity), lecce);

	}

	@Test
	public void testGetBalcony() {
		Region puglia=Region.createRegion("Puglia", cities, deck, balcony, premio);

		assertEquals(puglia.getBalcony(), balcony);



	}

	@Test
	public void testGetBPTDeck() {
		
		Region puglia=Region.createRegion("Puglia", cities, deck, balcony, premio);

		assertEquals(puglia.getBPTDeck(), deck);

	}

	@Test
	public void testGetName() {
		
		Region puglia=Region.createRegion("Puglia", cities, deck, balcony, premio);

		assertEquals(puglia.getName(), "Puglia");

	}


	
	@Test
	public void testHashCode() {
		
		Region puglia=Region.createRegion("Puglia", cities, deck, balcony, premio);



		assertNotEquals(puglia.hashCode(),0);


	}




	@Test
	public void testToString() {
		
		Region puglia=Region.createRegion("Puglia", cities, deck, balcony, premio);

		assertEquals(puglia.toString(), "Region{" +
				"name='" + "Puglia" + '\'' +
				", cities=" + cities +
				", balcony=" + balcony +
				", deck=" + deck +
				", bonus=" + premio +
				'}');

	}




	@Test
	public void testGetState() {
		
		Region puglia=Region.createRegion("Puglia", cities, deck, balcony, premio);

		RegionDTO pugliaDTO= puglia.getState();
		LinkedList<CityDTO> citiesState = new LinkedList<>();
		for (City c: cities)
			citiesState.add(c.getState());

		RegionDTO regionTest = new RegionDTO("Puglia", citiesState, deck.getState(), balcony.getState(), premio.getState());
		System.out.println(pugliaDTO);
		System.out.println(regionTest);
		assertEquals(pugliaDTO, regionTest);

	}




	@Test
	public void testEqualsObject() {

		Region puglia=Region.createRegion("Puglia", cities, deck, balcony, premio);
		Region puglia2=Region.createRegion("Puglia", cities, deck, balcony, premio);
		Region Molise=null;

		assertEquals(puglia, puglia2);
		assertFalse(puglia.equals(Molise));



	}

}
