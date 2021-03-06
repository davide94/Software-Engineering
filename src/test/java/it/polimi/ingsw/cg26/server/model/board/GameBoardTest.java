package it.polimi.ingsw.cg26.server.model.board;

import it.polimi.ingsw.cg26.common.dto.*;
import it.polimi.ingsw.cg26.common.dto.bonusdto.EmptyBonusDTO;
import it.polimi.ingsw.cg26.server.exceptions.CityNotFoundException;
import it.polimi.ingsw.cg26.server.exceptions.ExistingEmporiumException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingCardsException;
import it.polimi.ingsw.cg26.server.model.state.Scheduler;
import it.polimi.ingsw.cg26.server.model.bonus.*;
import it.polimi.ingsw.cg26.server.model.cards.*;
import it.polimi.ingsw.cg26.server.model.market.Market;
import it.polimi.ingsw.cg26.server.model.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class GameBoardTest {
	
	private PoliticDeck politicDeck; //CREATO

	private Collection<Councillor> councillorsPool; //CREATO

	private KingDeck kingDeck; //CREATO

	private Balcony kingBalcony; //CREATO

	private Collection<Region> regions; //CREATO

	private NobilityTrack nobilityTrack; //CREATO

	private King king; //CREATO

	private Market market; //CREATO
	
	private Map<CityColor, Bonus> colorBonuses; //CREATO

	private Scheduler scheduler;
	
	
	private Player Davide;
	private Player Luca;
	private Player Marco;

	
	@Before
	public void setUp() throws Exception {
		
		market= new Market();
		
		colorBonuses= new HashMap<>();
	    colorBonuses.put(CityColor.createCityColor("gold"), new VictoryBonus(new EmptyBonus(), 20));
	    colorBonuses.put(CityColor.createCityColor("silver"), new VictoryBonus(new EmptyBonus(), 15));
	    colorBonuses.put(CityColor.createCityColor("bronze"), new VictoryBonus(new EmptyBonus(), 10));
	    colorBonuses.put(CityColor.createCityColor("iron"), new VictoryBonus(new EmptyBonus(), 5));
		
		
		NobilityCell cell7= NobilityCell.createNobilityCell(7, null, new EmptyBonus());
		NobilityCell cell6= NobilityCell.createNobilityCell(6, cell7, new EmptyBonus());
		NobilityCell cell5= NobilityCell.createNobilityCell(5, cell6, new EmptyBonus());
		NobilityCell cell4= NobilityCell.createNobilityCell(4, cell5, new EmptyBonus());
		NobilityCell cell3= NobilityCell.createNobilityCell(3, cell4, new EmptyBonus());
		NobilityCell cell2= NobilityCell.createNobilityCell(2, cell3, new EmptyBonus());
		NobilityCell cell1= NobilityCell.createNobilityCell(1, cell2, new EmptyBonus());
		NobilityCell cell0= NobilityCell.createNobilityCell(0, cell1, new EmptyBonus());
		nobilityTrack= NobilityTrack.createNobilityTrack(cell0);
		
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
		cards.add(blueCard);
		cards.add(pinkCard);
		cards.add(blackCard);
		cards.add(whiteCard);
		cards.add(orangeCard);
		cards.add(purpleCard);
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
		kingBalcony= Balcony.createBalcony(4);
		
		Councillor consBlack= Councillor.createCouncillor(new PoliticColor("black"));
		Councillor consWhite= Councillor.createCouncillor(new PoliticColor("white"));
		Councillor consBlue= Councillor.createCouncillor(new PoliticColor("blue"));
		Councillor consPink= Councillor.createCouncillor(new PoliticColor("pink"));
		Councillor consOrange= Councillor.createCouncillor(new PoliticColor("orange"));
		Councillor consPurple= Councillor.createCouncillor(new PoliticColor("purple"));
		
		sudBalcony.elect(consBlue);
		sudBalcony.elect(consPink);
		sudBalcony.elect(consBlue);
		sudBalcony.elect(consWhite);
		
		
		centroBalcony.elect(consBlack);
		centroBalcony.elect(consPurple);
		centroBalcony.elect(consOrange);
		centroBalcony.elect(consBlue);
		
		
		nordBalcony.elect(consBlue);
		nordBalcony.elect(consBlack);
		nordBalcony.elect(consPurple);
		nordBalcony.elect(consWhite);
		
		
		kingBalcony.elect(consBlack);
		kingBalcony.elect(consOrange);
		kingBalcony.elect(consPink);
		kingBalcony.elect(consOrange);
		
		
		councillorsPool= new LinkedList<>();
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
		
		Collection<RewardTile> kingTiles= new LinkedList<>();
		kingTiles.add(new RewardTile(new VictoryBonus(new EmptyBonus(), 50)));
		kingTiles.add(new RewardTile(new VictoryBonus(new EmptyBonus(), 40)));
		kingTiles.add(new RewardTile(new VictoryBonus(new EmptyBonus(), 30)));
		kingTiles.add(new RewardTile(new VictoryBonus(new EmptyBonus(), 20)));
		kingTiles.add(new RewardTile(new VictoryBonus(new EmptyBonus(), 10)));
		
		kingDeck= new KingDeck(kingTiles);
		
		
		
		
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
		
		
		
		regions= new LinkedList<>();
		regions.add(sud);
		regions.add(centro);
		regions.add(nord);
		
		
		
		
		
		king= King.createKing(roma);
		
		
		
		Davide=new Player(1234, "Davide", cell1, 0, new LinkedList<>(), new LinkedList<>());
		Luca=new Player(1235, "Luca", cell2, 0, new LinkedList<>(), new LinkedList<>());
		Marco=new Player(1236, "Marco", cell3, 0, new LinkedList<>(), new LinkedList<>());
	    
	    
	    
	}
	
	
	
	
	

	@Test
	public void testShouldCreateGameBoardCorrectly() {
		
		
		GameBoard board= GameBoard.createGameBoard(politicDeck, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, kingDeck, colorBonuses);
		
		
		assertNotNull(board);
		
	}
	
	
	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateGameBoardWithNullPolitickDeck() {
		
		GameBoard board= GameBoard.createGameBoard(null, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, kingDeck, colorBonuses);
		
				
	}
	
	
	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateGameBoardWithNullCouncillorsPoll() {
		
		GameBoard board= GameBoard.createGameBoard(politicDeck, null, kingBalcony, regions, nobilityTrack, king, market, kingDeck, colorBonuses);
		
				
	}
	
	
	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateGameBoardWithNullKingBalcony() {
		
		GameBoard board= GameBoard.createGameBoard(politicDeck, councillorsPool, null, regions, nobilityTrack, king, market, kingDeck, colorBonuses);
		
				
	}
	
	
	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateGameBoardWithNullNobilityTrack() {
		
		GameBoard board= GameBoard.createGameBoard(politicDeck, councillorsPool, kingBalcony, regions, null, king, market, kingDeck, colorBonuses);
		
				
	}
	
	
	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateGameBoardWithNullKing() {
		
		GameBoard board= GameBoard.createGameBoard(politicDeck, councillorsPool, kingBalcony, regions, nobilityTrack, null, market, kingDeck, colorBonuses);
		
				
	}
	
	
	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateGameBoardWithNullMarket() {
		
		GameBoard board= GameBoard.createGameBoard(politicDeck, councillorsPool, kingBalcony, regions, nobilityTrack, king, null, kingDeck, colorBonuses);
		
				
	}
	
	
	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateGameBoardWithNullKingDeck() {
		
		GameBoard board= GameBoard.createGameBoard(politicDeck, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, null, colorBonuses);
		
				
	}
	
	
	
	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateGameBoardWithNullColorBonuses() {
		
		GameBoard board= GameBoard.createGameBoard(politicDeck, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, kingDeck, null);
		
				
	}
	
	
	
	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateGameBoardWithNullRegions() {
		
		GameBoard board= GameBoard.createGameBoard(politicDeck, councillorsPool, kingBalcony, null, nobilityTrack, king, market, kingDeck, colorBonuses);
		
				
	}
	
	
	

	

	@Test
	public void testGetKingBalcony() {
		
		GameBoard board= GameBoard.createGameBoard(politicDeck, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, kingDeck, colorBonuses);
		
		Balcony kBalc=Balcony.createBalcony(4);
		Balcony kBalc2=Balcony.createBalcony(4);
		
		Councillor consBlack= Councillor.createCouncillor(new PoliticColor("black"));
		Councillor consPink= Councillor.createCouncillor(new PoliticColor("pink"));
		Councillor consOrange= Councillor.createCouncillor(new PoliticColor("orange"));
		
		
		kBalc.elect(consBlack);
		kBalc.elect(consOrange);
		kBalc.elect(consPink);
		kBalc.elect(consOrange);
		
		assertEquals(board.getKingBalcony(), kBalc);
		assertFalse((board.getKingBalcony()).equals(kBalc2));
		
		assertEquals(board.getKingBalcony(), kingBalcony);
		
		
	}

	@Test
	public void testGetKingDeck() {
		
		GameBoard board= GameBoard.createGameBoard(politicDeck, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, kingDeck, colorBonuses);
		
		 
		Collection<RewardTile> kTiles= new LinkedList<>();
		kTiles.add(new RewardTile(new VictoryBonus(new EmptyBonus(), 50)));
		kTiles.add(new RewardTile(new VictoryBonus(new EmptyBonus(), 20)));
		kTiles.add(new RewardTile(new VictoryBonus(new EmptyBonus(), 30)));
		kTiles.add(new RewardTile(new VictoryBonus(new EmptyBonus(), 40)));
		kTiles.add(new RewardTile(new VictoryBonus(new EmptyBonus(), 10)));
		
		Collection<RewardTile> kTiles2= new LinkedList<>();
		kTiles2.add(new RewardTile(new VictoryBonus(new EmptyBonus(), 50)));
		kTiles2.add(new RewardTile(new VictoryBonus(new EmptyBonus(), 40)));
		kTiles2.add(new RewardTile(new VictoryBonus(new EmptyBonus(), 30)));
		kTiles2.add(new RewardTile(new VictoryBonus(new EmptyBonus(), 20)));
		kTiles2.add(new RewardTile(new VictoryBonus(new EmptyBonus(), 10)));
		
		KingDeck kDeck= new KingDeck(kTiles);
		KingDeck kDeck2= new KingDeck(kTiles2);
		
		assertNotEquals(board.getKingDeck(), kDeck);
		assertEquals(board.getKingDeck(), kDeck2);

		assertEquals(board.getKingDeck(), kingDeck);
		
		
	}
	
	
	@Test
	public void testGetKing() {
		
		GameBoard board= GameBoard.createGameBoard(politicDeck, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, kingDeck, colorBonuses);
		
		King re= King.createKing(City.createCity("Roma", CityColor.createCityColor("violet"), new VictoryBonus(new EmptyBonus(), 1)));
		
		assertEquals(board.getKing(), re);
		assertEquals(board.getKing(), king);
				
				
	}

	
	@Test
	public void testGetCouncillorsPool() {
		
		GameBoard board= GameBoard.createGameBoard(politicDeck, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, kingDeck, colorBonuses);
		
		
		Councillor consBlack= Councillor.createCouncillor(new PoliticColor("black"));
		Councillor consWhite= Councillor.createCouncillor(new PoliticColor("white"));
		Councillor consPink= Councillor.createCouncillor(new PoliticColor("pink"));
		Councillor consOrange= Councillor.createCouncillor(new PoliticColor("orange"));
		Councillor consPurple= Councillor.createCouncillor(new PoliticColor("purple"));
		
		Collection<Councillor> councPool= new LinkedList<>();
		councPool.add(consPurple);
		councPool.add(consPurple);
		councPool.add(consWhite);
		councPool.add(consWhite);
		councPool.add(consBlack);
		councPool.add(consPink);
		councPool.add(consPink);
		councPool.add(consOrange);
		
		assertEquals(board.getCouncillorsPool(), councPool);
		assertEquals(board.getCouncillorsPool(), councillorsPool);
		
		
	}

	

	@Test (expected=CityNotFoundException.class)
	public void testShouldNotGetACityThatIsNotInRegions() throws Throwable {
		
		GameBoard board= GameBoard.createGameBoard(politicDeck, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, kingDeck, colorBonuses);
		
		
		Collection<EmporiumDTO> emporiums= new ArrayList<>();
		Collection<String> nearCities= new ArrayList<>();
		CityDTO udine= new CityDTO("Udine", new CityColorDTO("iron"),new EmptyBonusDTO(), emporiums, nearCities );
		board.getCity(udine);
		
	}
	
	
	
	@Test
	public void testShouldGetACityThatIsInRegions() throws Throwable {
		
        GameBoard board= GameBoard.createGameBoard(politicDeck, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, kingDeck, colorBonuses);
		
		
		Collection<EmporiumDTO> emporiums= new ArrayList<>();
		Collection<String> nearCities= new ArrayList<>();
		CityDTO milano= new CityDTO("Milano", new CityColorDTO("gold"),new EmptyBonusDTO(), emporiums, nearCities );
		
		
		City milanoReal = City.createCity("Milano", CityColor.createCityColor("gold"), new VictoryBonus(new EmptyBonus(), 3));
		assertEquals(board.getCity(milano), milanoReal );
		
	}
	
	

	@Test
	public void testGetPoliticDeck() {
		
		GameBoard board= GameBoard.createGameBoard(politicDeck, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, kingDeck, colorBonuses);
		
		PoliticCard blue= new PoliticCard(new PoliticColor("blue"));
		PoliticCard purple= new PoliticCard(new PoliticColor("purple"));
		PoliticCard black= new PoliticCard(new PoliticColor("black"));
		PoliticCard white= new PoliticCard(new PoliticColor("white"));
		PoliticCard orange= new PoliticCard(new PoliticColor("orange"));
		PoliticCard pink= new PoliticCard(new PoliticColor("pink"));
		
		Collection<PoliticCard> cards= new ArrayList<>();
		cards.add(blue);
		cards.add(pink);
		cards.add(black);
		cards.add(white);
		cards.add(orange);
		cards.add(purple);
		
		PoliticDeck deck=new PoliticDeck(cards);

        PoliticDeck got = board.getPoliticDeck();

        assertEquals(got, politicDeck);

        Collection<PoliticCard> gotCards = new LinkedList<>();
        while (true) {
            try {
                gotCards.add(got.draw());
            } catch (NoRemainingCardsException e) {
                break;
            }
        }

        Collection<PoliticCard> deckCards = new LinkedList<>();
        while (true) {
            try {
                deckCards.add(deck.draw());
            } catch (NoRemainingCardsException e) {
                break;
            }
        }

        assertTrue(gotCards.containsAll(deckCards) && deckCards.containsAll(gotCards));
    }

	@Test
	public void testGetNobilityTrack() {
		
		GameBoard board= GameBoard.createGameBoard(politicDeck, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, kingDeck, colorBonuses);
		
		assertEquals(board.getNobilityTrack(), nobilityTrack);
		
		NobilityCell cell7= NobilityCell.createNobilityCell(7, null, new EmptyBonus());
		NobilityCell cell6= NobilityCell.createNobilityCell(6, cell7, new EmptyBonus());
		NobilityCell cell5= NobilityCell.createNobilityCell(5, cell6, new EmptyBonus());
		NobilityCell cell4= NobilityCell.createNobilityCell(4, cell5, new EmptyBonus());
		NobilityCell cell3= NobilityCell.createNobilityCell(3, cell4, new EmptyBonus());
		NobilityCell cell2= NobilityCell.createNobilityCell(2, cell3, new EmptyBonus());
		NobilityCell cell1= NobilityCell.createNobilityCell(1, cell2, new EmptyBonus());
		NobilityCell cell0= NobilityCell.createNobilityCell(0, cell1, new EmptyBonus());
		NobilityTrack track= NobilityTrack.createNobilityTrack(cell0);
		
		assertEquals(board.getNobilityTrack(), track);
	}
	
	
	@Test
	public void testGetMarket() {
		
        GameBoard board= GameBoard.createGameBoard(politicDeck, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, kingDeck, colorBonuses);
		
		assertEquals(board.getMarket(), market);
		
		
		
	}
	
	

	@Test
	public void testToString() {
		
		GameBoard board= GameBoard.createGameBoard(politicDeck, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, kingDeck, colorBonuses);
		
		assertEquals(board.toString(), "GameBoard{" +
				"councillorsPool=" + board.getCouncillorsPool() +
				", kingBalcony=" + board.getKingBalcony() +
				", regions=" + board.getRegions() +
				", nobilityTrack=" + board.getNobilityTrack() +
				", king=" + board.getKing() +
				'}');
		
	}

	

	@Test
	public void testCheckGoldBonusAndKingBonusHaveBeenAppliedToPlayer() throws Throwable {
		
		GameBoard board= GameBoard.createGameBoard(politicDeck, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, kingDeck, colorBonuses);
		
		Collection<EmporiumDTO> emporiums= new ArrayList<>();
		Collection<String> nearCities= new ArrayList<>();
		
		CityDTO milanoDTO= new CityDTO("Milano", new CityColorDTO("gold"),new EmptyBonusDTO(), emporiums, nearCities );
		CityDTO torinoDTO= new CityDTO("Torino", new CityColorDTO("gold"),new EmptyBonusDTO(), emporiums, nearCities );
		CityDTO veneziaDTO= new CityDTO("Venezia", new CityColorDTO("gold"),new EmptyBonusDTO(), emporiums, nearCities );
		CityDTO firenzeDTO= new CityDTO("Firenze", new CityColorDTO("gold"),new EmptyBonusDTO(), emporiums, nearCities );
		CityDTO napoliDTO= new CityDTO("Napoli", new CityColorDTO("gold"),new EmptyBonusDTO(), emporiums, nearCities );
		
		
		board.getCity(milanoDTO).build(Luca);
		board.getCity(torinoDTO).build(Luca);
		board.getCity(firenzeDTO).build(Luca);
		board.getCity(veneziaDTO).build(Luca);
		board.getCity(napoliDTO).build(Luca);
		
    
		board.getCity(milanoDTO).build(Davide);
		board.getCity(torinoDTO).build(Davide);
		board.getCity(firenzeDTO).build(Davide);
		board.getCity(veneziaDTO).build(Davide);
		board.getCity(napoliDTO).build(Davide);
		
		board.checkBonuses(Luca, CityColor.createCityColor("gold"));
		board.checkBonuses(Davide, CityColor.createCityColor("gold"));
		
		assertEquals(Luca.getVictoryPoints(), 75);
		assertEquals(Davide.getVictoryPoints(), 5);
		
	}
	
	
	@Test
	public void testWhatHappensWhenPlayersTakeAllKingTiles() throws ExistingEmporiumException, NoRemainingCardsException, CityNotFoundException {
		
		GameBoard board= GameBoard.createGameBoard(politicDeck, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, kingDeck, colorBonuses);
		
		Collection<EmporiumDTO> emporiums= new ArrayList<>();
		Collection<String> nearCities= new ArrayList<>();
		
		CityDTO milanoDTO= new CityDTO("Milano", new CityColorDTO("gold"),new EmptyBonusDTO(), emporiums, nearCities );
		CityDTO torinoDTO= new CityDTO("Torino", new CityColorDTO("gold"),new EmptyBonusDTO(), emporiums, nearCities );
		CityDTO veneziaDTO= new CityDTO("Venezia", new CityColorDTO("gold"),new EmptyBonusDTO(), emporiums, nearCities );
		CityDTO genovaDTO= new CityDTO("Genova", new CityColorDTO("silver"),new EmptyBonusDTO(), emporiums, nearCities );
		CityDTO triesteDTO= new CityDTO("Trieste", new CityColorDTO("bronze"),new EmptyBonusDTO(), emporiums, nearCities );
		
		CityDTO romaDTO= new CityDTO("Roma", new CityColorDTO("violet"),new EmptyBonusDTO(), emporiums, nearCities );
		CityDTO firenzeDTO= new CityDTO("Firenze", new CityColorDTO("gold"),new EmptyBonusDTO(), emporiums, nearCities );
		CityDTO bolognaDTO= new CityDTO("Bologna", new CityColorDTO("silver"),new EmptyBonusDTO(), emporiums, nearCities );
		CityDTO cagliariDTO= new CityDTO("Cagliari", new CityColorDTO("bronze"),new EmptyBonusDTO(), emporiums, nearCities );
		CityDTO parmaDTO= new CityDTO("Parma", new CityColorDTO("iron"),new EmptyBonusDTO(), emporiums, nearCities );
		
		CityDTO napoliDTO= new CityDTO("Napoli", new CityColorDTO("gold"),new EmptyBonusDTO(), emporiums, nearCities );
		CityDTO bariDTO= new CityDTO("Bari", new CityColorDTO("silver"),new EmptyBonusDTO(), emporiums, nearCities );
		CityDTO palermoDTO= new CityDTO("Palermo", new CityColorDTO("silver"),new EmptyBonusDTO(), emporiums, nearCities );
		CityDTO catanzaroDTO= new CityDTO("Catanzaro", new CityColorDTO("bronze"),new EmptyBonusDTO(), emporiums, nearCities );
		CityDTO potenzaDTO= new CityDTO("Potenza", new CityColorDTO("iron"),new EmptyBonusDTO(), emporiums, nearCities );
		
		
		
		board.getCity(genovaDTO).build(Marco);
		board.getCity(potenzaDTO).build(Luca);
		board.getCity(catanzaroDTO).build(Davide);
		board.getCity(bolognaDTO).build(Marco);
		board.getCity(cagliariDTO).build(Davide);
		board.getCity(bariDTO).build(Marco);
		board.getCity(parmaDTO).build(Luca);
		
		
		//Luca costruisce nelle due città iron e prende i bonus
		board.checkBonuses(Luca, CityColor.createCityColor("iron"));
		assertEquals(Luca.getVictoryPoints(), 55);
		
		board.getCity(triesteDTO).build(Davide);
		
		//Davide costruisce nelle tre città bronze e prende i bonus
		board.checkBonuses(Davide, CityColor.createCityColor("bronze"));
		assertEquals(Davide.getVictoryPoints(), 50);
		
		
        board.getCity(romaDTO).build(Davide);
		
		//Davide costruisce nella città viola e non prende bonus speciali ma solo
        //quello sulla città
		board.checkBonuses(Davide, CityColor.createCityColor("violet"));
		assertEquals(Davide.getVictoryPoints(), 51);
		
		
	
		
		//Marco costruisce sulle due città iron e non 
		//prende alcun bonus
		board.getCity(potenzaDTO).build(Marco);
		board.getCity(parmaDTO).build(Marco);
		board.checkBonuses(Marco, CityColor.createCityColor("iron"));
		assertEquals(Marco.getVictoryPoints(), 0);
		
		
		//Marco costruisce sulle città silver e questa volta prende i bonus
		board.getCity(palermoDTO).build(Marco);
		board.checkBonuses(Marco, CityColor.createCityColor("silver"));
		assertEquals(Marco.getVictoryPoints(), 45);
		
		
		board.getCity(bariDTO).build(Luca);
		board.getCity(napoliDTO).build(Luca);
		board.getCity(catanzaroDTO).build(Luca);
		board.getCity(palermoDTO).build(Luca);
		
		
		//Luca costruisce in tutte le città del sud e prende i bonus
		board.checkBonuses(Luca, CityColor.createCityColor("silver"));
		assertEquals(Luca.getVictoryPoints(), 82);
		
		
		//Marco costruisce in tutte le città del sud ma non prende bonus
		//speciali perchè sono già stati presi
		board.getCity(catanzaroDTO).build(Marco);
		board.getCity(napoliDTO).build(Marco);
		board.checkBonuses(Marco, CityColor.createCityColor("gold"));
		assertEquals(Marco.getVictoryPoints(), 47);
		
		
		//Davide costruisce in tutte le città del sud e prende i bonus
		//tra cui l'ultimo king tile
		board.getCity(firenzeDTO).build(Davide);
		board.getCity(bolognaDTO).build(Davide);
		board.getCity(parmaDTO).build(Davide);
		board.checkBonuses(Davide, CityColor.createCityColor("iron"));
		assertEquals(Davide.getVictoryPoints(), 66);
		
		
		//Marco costruisce in tutte le città del nord e prende solo i bonus
	    //città e regione ma non il king tile perchè finito
		board.getCity(torinoDTO).build(Marco);
		board.getCity(veneziaDTO).build(Marco);
		board.getCity(triesteDTO).build(Marco);
		board.getCity(milanoDTO).build(Marco);
		board.checkBonuses(Marco, CityColor.createCityColor("gold"));
		assertEquals(Marco.getVictoryPoints(), 55);
		
		
				
		
		
		
		
	}
	
	
	
	@Test
	public void testCheckNordRegionBonusAndKingBonusHaveBeenAppliedToPlayer() throws ExistingEmporiumException, NoRemainingCardsException, CityNotFoundException {
		
		GameBoard board= GameBoard.createGameBoard(politicDeck, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, kingDeck, colorBonuses);
		
		Collection<EmporiumDTO> emporiums= new ArrayList<>();
		Collection<String> nearCities= new ArrayList<>();
		
		CityDTO milanoDTO= new CityDTO("Milano", new CityColorDTO("gold"),new EmptyBonusDTO(), emporiums, nearCities );
		CityDTO torinoDTO= new CityDTO("Torino", new CityColorDTO("gold"),new EmptyBonusDTO(), emporiums, nearCities );
		CityDTO veneziaDTO= new CityDTO("Venezia", new CityColorDTO("gold"),new EmptyBonusDTO(), emporiums, nearCities );
		CityDTO genovaDTO= new CityDTO("Genova", new CityColorDTO("silver"),new EmptyBonusDTO(), emporiums, nearCities );
		CityDTO triesteDTO= new CityDTO("Trieste", new CityColorDTO("bronze"),new EmptyBonusDTO(), emporiums, nearCities );
		
		
		board.getCity(milanoDTO).build(Luca);
		board.getCity(torinoDTO).build(Luca);
		board.getCity(genovaDTO).build(Luca);
		board.getCity(veneziaDTO).build(Luca);
		board.getCity(triesteDTO).build(Luca);
		
		board.getCity(milanoDTO).build(Davide);
		board.getCity(torinoDTO).build(Davide);
		board.getCity(genovaDTO).build(Davide);
		board.getCity(veneziaDTO).build(Davide);
		board.getCity(triesteDTO).build(Davide);
		
		board.checkBonuses(Luca, CityColor.createCityColor("bronze"));
		board.checkBonuses(Davide, CityColor.createCityColor("bronze"));
		
		assertEquals(Luca.getVictoryPoints(), 58);
		assertEquals(Davide.getVictoryPoints(), 3);
		
		
	}
	
	
	@Test
	public void testCheckGoldBonusAndNordRegionBonusAndKingBonusHaveBeenAppliedToPlayer() throws ExistingEmporiumException, NoRemainingCardsException, CityNotFoundException {
		
		GameBoard board= GameBoard.createGameBoard(politicDeck, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, kingDeck, colorBonuses);
		
		Collection<EmporiumDTO> emporiums= new ArrayList<>();
		Collection<String> nearCities= new ArrayList<>();
		
		CityDTO milanoDTO= new CityDTO("Milano", new CityColorDTO("gold"),new EmptyBonusDTO(), emporiums, nearCities );
		CityDTO torinoDTO= new CityDTO("Torino", new CityColorDTO("gold"),new EmptyBonusDTO(), emporiums, nearCities );
		CityDTO veneziaDTO= new CityDTO("Venezia", new CityColorDTO("gold"),new EmptyBonusDTO(), emporiums, nearCities );
		CityDTO firenzeDTO= new CityDTO("Firenze", new CityColorDTO("gold"),new EmptyBonusDTO(), emporiums, nearCities );
		CityDTO napoliDTO= new CityDTO("Napoli", new CityColorDTO("gold"),new EmptyBonusDTO(), emporiums, nearCities );
		CityDTO genovaDTO= new CityDTO("Genova", new CityColorDTO("silver"),new EmptyBonusDTO(), emporiums, nearCities );
		CityDTO triesteDTO= new CityDTO("Trieste", new CityColorDTO("bronze"),new EmptyBonusDTO(), emporiums, nearCities );
		
		
		board.getCity(milanoDTO).build(Luca);
		board.getCity(firenzeDTO).build(Luca);
		board.getCity(veneziaDTO).build(Luca);
		board.getCity(genovaDTO).build(Luca);
		board.getCity(triesteDTO).build(Luca);
		board.getCity(napoliDTO).build(Luca);
		board.getCity(torinoDTO).build(Luca);
		
		
		board.checkBonuses(Luca, CityColor.createCityColor("gold"));
		
		assertEquals(Luca.getVictoryPoints(), 120);
		
	}
	

	
	
	
	@Test
	public void testCheckGoldCitiesBonuses() throws Throwable {
		
		GameBoard board= GameBoard.createGameBoard(politicDeck, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, kingDeck, colorBonuses);
		
		Collection<EmporiumDTO> emporiums= new ArrayList<>();
		Collection<String> nearCities= new ArrayList<>();
		
		CityDTO milanoDTO= new CityDTO("Milano", new CityColorDTO("gold"),new EmptyBonusDTO(), emporiums, nearCities );
		CityDTO torinoDTO= new CityDTO("Torino", new CityColorDTO("gold"),new EmptyBonusDTO(), emporiums, nearCities );
		CityDTO veneziaDTO= new CityDTO("Venezia", new CityColorDTO("gold"),new EmptyBonusDTO(), emporiums, nearCities );
		CityDTO firenzeDTO= new CityDTO("Firenze", new CityColorDTO("gold"),new EmptyBonusDTO(), emporiums, nearCities );
		CityDTO napoliDTO= new CityDTO("Napoli", new CityColorDTO("gold"),new EmptyBonusDTO(), emporiums, nearCities );
		
		
		board.getCity(milanoDTO).build(Luca);
		board.getCity(torinoDTO).build(Luca);
		board.getCity(firenzeDTO).build(Luca);
		board.getCity(veneziaDTO).build(Luca);
		board.getCity(napoliDTO).build(Luca);
		
		
		board.getCity(milanoDTO).build(Davide);
		board.getCity(torinoDTO).build(Davide);
		board.getCity(veneziaDTO).build(Davide);
		
		board.getCity(napoliDTO).build(Marco);
		board.getCity(firenzeDTO).build(Marco);
		
		assertTrue(board.checkColorBonuses(Luca, CityColor.createCityColor("gold")));
		assertFalse(board.checkColorBonuses(Davide, CityColor.createCityColor("gold")));
		assertFalse(board.checkColorBonuses(Marco, CityColor.createCityColor("gold")));
		
		
		
		
		
	}

	@Test
	public void testGetColorBonus() {
		
		GameBoard board= GameBoard.createGameBoard(politicDeck, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, kingDeck, colorBonuses);
		
		
		CityColor gold=CityColor.createCityColor("gold");
		CityColor silver=CityColor.createCityColor("silver");
		CityColor bronze=CityColor.createCityColor("bronze");
		CityColor iron=CityColor.createCityColor("iron");
		CityColor violet=CityColor.createCityColor("violet");
		
		
		assertEquals(board.getColorBonus(gold), new VictoryBonus(new EmptyBonus(), 20));
		assertEquals(board.getColorBonus(silver), new VictoryBonus(new EmptyBonus(), 15));
		assertEquals(board.getColorBonus(bronze), new VictoryBonus(new EmptyBonus(), 10));
		assertEquals(board.getColorBonus(iron), new VictoryBonus(new EmptyBonus(), 5));
		assertEquals(board.getColorBonus(violet), null);
		
	}
	
	
	
	
	
	@Test 
	public void testShouldNotGetARegionThatIsNotInBoard() {
		
		GameBoard board= GameBoard.createGameBoard(politicDeck, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, kingDeck, colorBonuses);
		
		
		CouncillorDTO c1 = new CouncillorDTO(new PoliticColorDTO("c1"));
        CouncillorDTO c2 = new CouncillorDTO(new PoliticColorDTO("c2"));
        CouncillorDTO c3 = new CouncillorDTO(new PoliticColorDTO("c3"));
        CouncillorDTO c4 = new CouncillorDTO(new PoliticColorDTO("c4"));
        LinkedList<CouncillorDTO> councillors = new LinkedList<>();
        councillors.add(c1);
        councillors.add(c2);
        councillors.add(c3);
        councillors.add(c4);
        
        LinkedList<String> cities = new LinkedList<>();
        cities.add("city1Name");
        cities.add("city2Name");
        LinkedList<BusinessPermissionTileDTO> cards = new LinkedList<>();
        cards.add(new BusinessPermissionTileDTO(cities, new EmptyBonusDTO(), 0, "playerName"));
        BusinessPermissionTileDeckDTO deck = new BusinessPermissionTileDeckDTO(cards);
        
		Collection<CityDTO> citiesInRegion= new ArrayList<>();
		
		RegionDTO molise= new RegionDTO("Molise", citiesInRegion, deck, new BalconyDTO(councillors), new EmptyBonusDTO());
		
		assertEquals(board.getRegion(molise), null);
		
		
		
	}
	
	
	@Test 
	public void testShouldGetARegionThatIsInBoard() {
		
		GameBoard board= GameBoard.createGameBoard(politicDeck, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, kingDeck, colorBonuses);
		
		CouncillorDTO c1 = new CouncillorDTO(new PoliticColorDTO("c1"));
        CouncillorDTO c2 = new CouncillorDTO(new PoliticColorDTO("c2"));
        CouncillorDTO c3 = new CouncillorDTO(new PoliticColorDTO("c3"));
        CouncillorDTO c4 = new CouncillorDTO(new PoliticColorDTO("c4"));
        LinkedList<CouncillorDTO> councillors = new LinkedList<>();
        councillors.add(c1);
        councillors.add(c2);
        councillors.add(c3);
        councillors.add(c4);
        
        LinkedList<String> cities = new LinkedList<>();
        cities.add("city1Name");
        cities.add("city2Name");
        LinkedList<BusinessPermissionTileDTO> cards = new LinkedList<>();
        cards.add(new BusinessPermissionTileDTO(cities, new EmptyBonusDTO(), 0, "playerName"));
        BusinessPermissionTileDeckDTO deck = new BusinessPermissionTileDeckDTO(cards);
        
		Collection<CityDTO> citiesInRegion= new ArrayList<>();
		
		RegionDTO sudDTO= new RegionDTO("Sud", citiesInRegion, deck, new BalconyDTO(councillors), new EmptyBonusDTO());
		
		
		
		

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
		
		
		Balcony sudBalcony= Balcony.createBalcony(4);
		Councillor consWhite= Councillor.createCouncillor(new PoliticColor("white"));
		Councillor consBlue= Councillor.createCouncillor(new PoliticColor("blue"));
		Councillor consPink= Councillor.createCouncillor(new PoliticColor("pink"));
		
		(sudBalcony.getCouncillors()).add(consBlue);
		(sudBalcony.getCouncillors()).add(consPink);
		(sudBalcony.getCouncillors()).add(consBlue);
		(sudBalcony.getCouncillors()).add(consWhite);
		
		Collection<BusinessPermissionTile> sudBPTCards= new LinkedList<BusinessPermissionTile>();
		BusinessPermissionTileDeck sudBPTDeck= new BusinessPermissionTileDeck(sudBPTCards);
		
		
		Region sud= Region.createRegion("Sud", sudCities, sudBPTDeck, sudBalcony ,new VictoryBonus(new EmptyBonus(), 5));
		
		
		
		assertEquals(board.getRegion(sudDTO), sud);
		
		
	}
	
	
	
	@Test
	public void testGetState() {
		/*
		GameBoard board= GameBoard.createGameBoard(politicDeck, councillorsPool, kingBalcony, regions, nobilityTrack, king, market, kingDeck, colorBonuses);
		scheduler= new Scheduler(board);
		
		*/
	}
	
	
	
	
	
	
	
	

}
