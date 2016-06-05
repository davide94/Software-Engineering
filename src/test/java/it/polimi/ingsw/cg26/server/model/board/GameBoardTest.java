package it.polimi.ingsw.cg26.server.model.board;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.server.model.Scheduler;
import it.polimi.ingsw.cg26.server.model.bonus.*;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTileDeck;
import it.polimi.ingsw.cg26.server.model.cards.KingDeck;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import it.polimi.ingsw.cg26.server.model.market.Market;

public class GameBoardTest {
	
	private PoliticDeck politicDeck; //CREATO

	private Collection<Councillor> councillorsPool; //CREATO

	private KingDeck kingDeck;

	private Balcony kingBalcony; //CREATO

	private Collection<Region> regions; //CREATO

	private NobilityTrack nobilityTrack; //CREATO

	private King king; //CREATO

	private Market market; //CREATO
	
	private Map<CityColor, Bonus> colorBonuses;

	private Scheduler scheduler;
	
	
	@Before
	public void setUp() throws Exception {
		
		market= new Market();
		
		
		NobilityCell cell7= NobilityCell.createNobilityCell(4, null, new EmptyBonus());
		NobilityCell cell6= NobilityCell.createNobilityCell(4, cell7, new EmptyBonus());
		NobilityCell cell5= NobilityCell.createNobilityCell(4, cell6, new EmptyBonus());
		NobilityCell cell4= NobilityCell.createNobilityCell(4, cell5, new EmptyBonus());
		NobilityCell cell3= NobilityCell.createNobilityCell(4, cell4, new EmptyBonus());
		NobilityCell cell2= NobilityCell.createNobilityCell(4, cell3, new EmptyBonus());
		NobilityCell cell1= NobilityCell.createNobilityCell(4, cell2, new EmptyBonus());
		nobilityTrack= NobilityTrack.createNobilityTrack(cell1);
		
		PoliticCard blueCard= new PoliticCard(new PoliticColor("blue"));
		PoliticCard purpleCard= new PoliticCard(new PoliticColor("purple"));
		PoliticCard blackCard= new PoliticCard(new PoliticColor("black"));
		PoliticCard whiteCard= new PoliticCard(new PoliticColor("white"));
		PoliticCard orangeCard= new PoliticCard(new PoliticColor("orange"));
		PoliticCard pinkCard= new PoliticCard(new PoliticColor("pink"));
		
		Collection<PoliticCard> cards= new ArrayList<>();
		cards.add(blueCard);
		cards.add(pinkCard);
		cards.add(blackCard);
		cards.add(whiteCard);
		cards.add(orangeCard);
		cards.add(purpleCard);
				
		politicDeck= new PoliticDeck(cards);
		
		
		
		
		
		Balcony nordBalcony= Balcony.createBalcony(4);
		Balcony centroBalcony= Balcony.createBalcony(4);
		Balcony sudBalcony= Balcony.createBalcony(4);
		Balcony kingBalcony= Balcony.createBalcony(4);
		
		Councillor consBlack= Councillor.createCouncillor(new PoliticColor("black"));
		Councillor consWhite= Councillor.createCouncillor(new PoliticColor("white"));
		Councillor consBlue= Councillor.createCouncillor(new PoliticColor("blue"));
		Councillor consPink= Councillor.createCouncillor(new PoliticColor("pink"));
		Councillor consOrange= Councillor.createCouncillor(new PoliticColor("orange"));
		Councillor consPurple= Councillor.createCouncillor(new PoliticColor("purple"));
		
		(sudBalcony.getCouncillors()).add(consBlue);
		(sudBalcony.getCouncillors()).add(consPink);
		(sudBalcony.getCouncillors()).add(consBlue);
		(sudBalcony.getCouncillors()).add(consWhite);
		
		
		(centroBalcony.getCouncillors()).add(consBlack);
		(centroBalcony.getCouncillors()).add(consPurple);
		(centroBalcony.getCouncillors()).add(consOrange);
		(centroBalcony.getCouncillors()).add(consBlue);
		
		
		(nordBalcony.getCouncillors()).add(consBlue);
		(nordBalcony.getCouncillors()).add(consBlack);
		(nordBalcony.getCouncillors()).add(consPurple);
		(nordBalcony.getCouncillors()).add(consWhite);
		
		
		(kingBalcony.getCouncillors()).add(consBlack);
		(kingBalcony.getCouncillors()).add(consOrange);
		(kingBalcony.getCouncillors()).add(consPink);
		(kingBalcony.getCouncillors()).add(consOrange);
		
		
		councillorsPool= new LinkedList();
		councillorsPool.add(consPurple);
		councillorsPool.add(consPurple);
		councillorsPool.add(consWhite);
		councillorsPool.add(consWhite);
		councillorsPool.add(consBlack);
		councillorsPool.add(consPink);
		councillorsPool.add(consPink);
		councillorsPool.add(consOrange);
		
		
		
		
		
		
		
		Collection<BusinessPermissionTile> sudBPTCards= new LinkedList<BusinessPermissionTile>();
		BusinessPermissionTileDeck sudBPTDeck= new BusinessPermissionTileDeck(sudBPTCards);
		
		Collection<BusinessPermissionTile> centroBPTCards= new LinkedList<BusinessPermissionTile>();
		BusinessPermissionTileDeck centroBPTDeck= new BusinessPermissionTileDeck(centroBPTCards);
		
		Collection<BusinessPermissionTile> nordBPTCards= new LinkedList<BusinessPermissionTile>();
		BusinessPermissionTileDeck nordBPTDeck= new BusinessPermissionTileDeck(nordBPTCards);
		
		
		
		City napoli = City.createCity("Napoli", CityColor.createCityColor("gold"), new VictoryBonus(new EmptyBonus(), 2));
		City bari = City.createCity("Bari", CityColor.createCityColor("silver"), new AssistantBonus(new EmptyBonus(), 1));
		City palermo = City.createCity("Palermo", CityColor.createCityColor("silver"), new CoinBonus(new EmptyBonus(), 3));
		City catanzaro = City.createCity("Catanzaro", CityColor.createCityColor("bronze"), new NobilityBonus(new EmptyBonus(), 1));
		City potenza = City.createCity("Potenza", CityColor.createCityColor("iron"), new CardBonus(new EmptyBonus(), 1, politicDeck));
		
		Collection<City> sudCities= new LinkedList<>();
		sudCities.add(napoli);
		sudCities.add(bari);
		sudCities.add(palermo);
		sudCities.add(catanzaro);
		sudCities.add(potenza);
		Region sud= Region.createRegion("Sud", sudCities, sudBPTDeck, sudBalcony, new VictoryBonus(new EmptyBonus(), 5));
		
		
		
		City roma = City.createCity("Roma", CityColor.createCityColor("violet"), new VictoryBonus(new EmptyBonus(), 1));
		City firenze = City.createCity("Firenze", CityColor.createCityColor("gold"), new AssistantBonus(new EmptyBonus(), 3));
		City bologna = City.createCity("Bologna", CityColor.createCityColor("silver"), new CoinBonus(new EmptyBonus(), 5));
		City cagliari = City.createCity("Cagliari", CityColor.createCityColor("bronze"), new NobilityBonus(new EmptyBonus(), 2));
		City parma = City.createCity("Parma", CityColor.createCityColor("iron"), new CardBonus(new EmptyBonus(), 2, politicDeck));
		
		Collection<City> centroCities= new LinkedList<>();
		centroCities.add(roma);
		centroCities.add(firenze);
		centroCities.add(bologna);
		centroCities.add(cagliari);
		centroCities.add(parma);
		Region centro= Region.createRegion("Centro", centroCities, centroBPTDeck, centroBalcony, new VictoryBonus(new EmptyBonus(), 5));
		
		
		
		City milano = City.createCity("Milano", CityColor.createCityColor("gold"), new VictoryBonus(new EmptyBonus(), 3));
		City torino = City.createCity("Torino", CityColor.createCityColor("gold"), new AssistantBonus(new EmptyBonus(), 3));
		City venezia = City.createCity("venezia", CityColor.createCityColor("gold"), new CoinBonus(new EmptyBonus(), 1));
		City genova = City.createCity("Genova", CityColor.createCityColor("silver"), new NobilityBonus(new EmptyBonus(), 1));
		City trieste = City.createCity("Trieste", CityColor.createCityColor("bronze"), new CardBonus(new EmptyBonus(), 3, politicDeck));
		
		Collection<City> nordCities= new LinkedList<>();
		nordCities.add(milano);
		nordCities.add(torino);
		nordCities.add(venezia);
		nordCities.add(genova);
		nordCities.add(trieste);
		Region nord= Region.createRegion("Nord", nordCities, nordBPTDeck, nordBalcony, new VictoryBonus(new EmptyBonus(), 5));
		
		
		regions.add(sud);
		regions.add(centro);
		regions.add(nord);
		
		king= King.createKing(roma);
	
	
	}
	
	
	
	
	

	@Test
	public void testCreateGameBoard() {
		
	}

	@Test
	public void testGetState() {
		
	}

	@Test
	public void testGetFullPlayers() {
		
	}

	@Test
	public void testRegisterPlayer() {
		
	}

	@Test
	public void testActionPerformed() {
		
	}

	@Test
	public void testGetCurrentPlayer() {
		
	}

	@Test
	public void testGetRegion() {
		
	}

	@Test
	public void testGetKingBalcony() {
		
	}

	@Test
	public void testGetKingDeck() {
		
	}

	@Test
	public void testGetCouncillorsPool() {
		
	}

	@Test
	public void testGetKing() {
		
	}

	@Test
	public void testGetCity() {
		
	}

	@Test
	public void testGetPoliticDeck() {
		
	}

	@Test
	public void testGetNobilityTrack() {
		
	}

	@Test
	public void testToString() {
		
	}

	@Test
	public void testGetMarket() {
		
	}

	@Test
	public void testCheckBonuses() {
		
	}

	@Test
	public void testCheckColorBonuses() {
		
	}

	@Test
	public void testGetColorBonus() {
		
	}

}
