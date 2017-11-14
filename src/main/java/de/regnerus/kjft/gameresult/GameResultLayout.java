package de.regnerus.kjft.gameresult;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;

import de.regnerus.kjft.game.Game;
import de.regnerus.kjft.game.GameRepository;
import de.regnerus.kjft.team.TeamRepository;

public class GameResultLayout {
	private final Button addNewBtn;
	private AbstractOrderedLayout layout = new VerticalLayout();
	private Grid<GameResult> resultGrid;
	private GameResultEditor gameResultEditor;
	private Game game;

	public GameResultLayout(GameRepository gameRepository, TeamRepository teamRepository) {
		this.addNewBtn = new Button("Neues Ergebnis", VaadinIcons.PLUS);
		addNewBtn.addClickListener(event -> gameResultEditor.editGameResult(game, new GameResult()));
		getLayout().addComponent(addNewBtn);

		resultGrid = new Grid<GameResult>();
		resultGrid.setHeight(300, Unit.PIXELS);
		resultGrid.addColumn(GameResult::getTeam).setCaption("Gruppe");
		resultGrid.addColumn(GameResult::getResult).setCaption("Ergebnis");

		getLayout().addComponent(resultGrid);
		gameResultEditor = new GameResultEditor(gameRepository, teamRepository);
		gameResultEditor.setChangeHandler(new de.regnerus.kjft.Editor.ChangeHandler() {

			@Override
			public void onChange() {
				resultGrid.setItems(game.getGameResults());
			}
		});
		getLayout().addComponent(gameResultEditor);

		resultGrid.asSingleSelect().addValueChangeListener(e -> {
			gameResultEditor.editGameResult(game, e.getValue());
		});
	}

	public AbstractOrderedLayout getLayout() {
		return layout;
	}

	public void setGame(Game game) {
		this.game = game;
		resultGrid.setItems(this.game.getGameResults());
		gameResultEditor.setVisible(false);
	}

}
