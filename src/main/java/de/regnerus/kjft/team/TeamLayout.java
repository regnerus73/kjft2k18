package de.regnerus.kjft.team;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class TeamLayout {
	private final Button addNewBtn;

	private final TeamEditor editor;

	final Grid<Team> grid;

	private final TeamRepository repo;

	public TeamLayout(TeamRepository repo, TeamEditor editor) {
		this.repo = repo;
		this.editor = editor;
		this.grid = new Grid<>(Team.class);
		this.addNewBtn = new Button("Neue Gruppe", VaadinIcons.PLUS);
		this.actions = new HorizontalLayout(addNewBtn);

		init();
	}

	public void init() {
		grid.setHeight(300, Unit.PIXELS);
		grid.setColumns("id", "name");

		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.edit(e.getValue());
		});

		addNewBtn.addClickListener(e -> editor.edit(new Team("")));

		editor.setChangeHandler(() -> {
			editor.setVisible(false);
		});

		layout = new VerticalLayout(actions, grid, editor);

		grid.setItems(repo.findAll());
	}

	public VerticalLayout getLayout() {
		return layout;
	}

	HorizontalLayout actions;

	private VerticalLayout layout;

}
