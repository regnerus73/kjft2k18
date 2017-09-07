package de.regnerus.kjft.game;

import org.springframework.util.StringUtils;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class GameLayout {
	private final Button addNewBtn;

	private final GameEditor editor;

	final TextField filter;

	final Grid<Game> grid;

	private final GameRepository repo;

	public GameLayout(GameRepository repo, GameEditor editor) {
		this.repo = repo;
		this.editor = editor;
		this.grid = new Grid<>(Game.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("Neues Team", FontAwesome.PLUS);
		this.actions = new HorizontalLayout(filter, addNewBtn);

		init();
	}

	public void init() {
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
		});

		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(e -> editor.editGame(new Game("")));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listCustomers(filter.getValue());
		});

		layout = new VerticalLayout(actions, grid, editor);

		// Initialize listing
		listCustomers(null);
	}

	public VerticalLayout getLayout() {
		return layout;
	}

	// tag::listCustomers[]
	void listCustomers(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(repo.findAll());
		} else {
			grid.setItems(repo.findByNameStartsWithIgnoreCase(filterText));
		}
	}
	// end::listCustomers[]

	HorizontalLayout actions;

	private VerticalLayout layout;

}
