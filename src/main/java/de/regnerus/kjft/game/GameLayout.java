package de.regnerus.kjft.game;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import de.regnerus.kjft.gameresult.GameResultLayout;
import de.regnerus.kjft.team.TeamRepository;

public class GameLayout {
	private final Button addNewBtn;

	private final GameEditor editor;

	final Grid<Game> grid;

	private final GameRepository gameRepository;

	private AbstractOrderedLayout left;

	private GameResultLayout gameResultLayout;

	private final TeamRepository teamRepository;

	public GameLayout(GameRepository gameRepository, GameEditor editor, TeamRepository teamRepository) {
		this.gameRepository = gameRepository;
		this.teamRepository = teamRepository;
		this.editor = editor;
		this.grid = new Grid<>(Game.class);
		this.addNewBtn = new Button("Neues Spiel", VaadinIcons.PLUS);
		this.actions = new HorizontalLayout(addNewBtn);

		init();
	}

	private void init() {
		grid.setHeight(300, Unit.PIXELS);
		grid.setColumns("name");

		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.edit(e.getValue());
			gameResultLayout.setGame(e.getValue());
		});

		addNewBtn.addClickListener(e -> editor.edit(new Game("")));

		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			grid.setItems(gameRepository.findAll());
		});

		left = new VerticalLayout(actions, grid, editor);
		gameResultLayout = new GameResultLayout(gameRepository, teamRepository);
		layout = new HorizontalLayout(left, gameResultLayout.getLayout());

		grid.setItems(gameRepository.findAll());
	}

	public AbstractOrderedLayout getLayout() {
		return layout;
	}

	HorizontalLayout actions;

	private AbstractOrderedLayout layout;

}
