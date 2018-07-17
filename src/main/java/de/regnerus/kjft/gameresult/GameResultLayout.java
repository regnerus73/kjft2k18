package de.regnerus.kjft.gameresult;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;

import de.regnerus.kjft.game.Game;
import de.regnerus.kjft.game.GameRepository;
import de.regnerus.kjft.team.TeamRepository;

public class GameResultLayout {
	private final Button addNewBtn;
	private final VerticalLayout layout = new VerticalLayout();
	private final Grid<GameResult> resultGrid;
	private GameResultEditor gameResultEditor;
	private Game game;

	public GameResultLayout(GameRepository gameRepository, TeamRepository teamRepository) {
		this.addNewBtn = new Button("Neues Ergebnis", VaadinIcons.PLUS);
		addNewBtn.addClickListener(event -> gameResultEditor.edit(new GameResult()));
		getLayout().addComponent(addNewBtn);

		resultGrid = new Grid<>();
		resultGrid.setHeight("300px");
		resultGrid.addColumn(GameResult::getTeam).setCaption("Gruppe");
		resultGrid.sort(resultGrid.addColumn(GameResult::getResult).setCaption("Ergebnis"), SortDirection.DESCENDING);
		resultGrid.addColumn(GameResult::getFairnessScore).setCaption("Fairness");

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
			gameResultEditor.edit(e.getValue());
		});
	}

	public VerticalLayout getLayout() {
		return layout;
	}

	public void setGame(Game game) {
		if (game != null) {
			this.game = game;
			resultGrid.setItems(this.game.getGameResults());
			gameResultEditor.setGame(game);
			gameResultEditor.setVisible(false);
		}
	}

}
