package de.regnerus.kjft.game;

import org.springframework.util.StringUtils;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.regnerus.kjft.team.TeamRepository;

public class GameLayout {
	private final Button addNewBtn;

	private final GameEditor editor;

	final TextField filter;

	final Grid<Game> grid;

	private final GameRepository gameRepository;

	private AbstractOrderedLayout left;

	private AbstractOrderedLayout right;

	private GameScoreLayout gameScoreLayout;

	private final TeamRepository teamRepository;

	public GameLayout(GameRepository gameRepository, GameEditor editor, TeamRepository teamRepository) {
		this.gameRepository = gameRepository;
		this.teamRepository = teamRepository;
		this.editor = editor;
		this.grid = new Grid<>(Game.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("Neues Spiel", FontAwesome.PLUS);
		this.actions = new HorizontalLayout(filter, addNewBtn);

		init();
	}

	private void init() {
		grid.setHeight(300, Unit.PIXELS);
		grid.setColumns("id", "name");

		filter.setPlaceholder("Filter per Name");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listCustomers(e.getValue()));

		// Connect selected Customer to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editGame(e.getValue());
			gameScoreLayout.setGame(e.getValue());
		});

		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(e -> editor.editGame(new Game("")));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listCustomers(filter.getValue());
		});

		left = new VerticalLayout(actions, grid, editor);
		gameScoreLayout = new GameScoreLayout(gameRepository, teamRepository);
		layout = new HorizontalLayout(left, gameScoreLayout.getLayout());

		// Initialize listing
		listCustomers(null);
	}

	public AbstractOrderedLayout getLayout() {
		return layout;
	}

	// tag::listCustomers[]
	void listCustomers(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(gameRepository.findAll());
		} else {
			grid.setItems(gameRepository.findByNameStartsWithIgnoreCase(filterText));
		}
	}
	// end::listCustomers[]

	HorizontalLayout actions;

	private AbstractOrderedLayout layout;

}
