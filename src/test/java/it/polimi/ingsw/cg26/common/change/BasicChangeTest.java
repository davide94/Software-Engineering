package it.polimi.ingsw.cg26.common.change;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.cg26.common.update.change.BasicChange;
import it.polimi.ingsw.cg26.common.update.change.Change;

public class BasicChangeTest {

	@Test
	public void test() {
		Change change = new BasicChange();
		assertNotNull(change);
	}

}
