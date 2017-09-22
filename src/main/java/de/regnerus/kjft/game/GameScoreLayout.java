package de.regnerus.kjft.game;

import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;

import de.regnerus.kjft.gameresult.GameResult;

public class GameScoreLayout {
private AbstractOrderedLayout layout = new VerticalLayout();
private Grid<GameResult> resultGrid;

public GameScoreLayout() {
resultGrid = new Grid<GameResult>();
getLayout().addComponent(resultGrid);
}

AbstractOrderedLayout getLayout()
{
return 	layout;
}

public void setGame(Game game) {
	resultGrid.setItems(game.getGameResults());
	}
}
