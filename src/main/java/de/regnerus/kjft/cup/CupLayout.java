package de.regnerus.kjft.cup;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class CupLayout {
	private final Button addNewBtn;

	private final CupEditor editor;

	final Grid<Cup> grid;

	private final CupRepository repo;

	private VerticalLayout left;

	private CupResultLayout right;

	public CupLayout(CupRepository repo, CupEditor editor) {
		this.repo = repo;
		this.editor = editor;
		this.grid = new Grid<>(Cup.class);
		this.addNewBtn = new Button("Neuer Pokal", VaadinIcons.PLUS);
		this.actions = new HorizontalLayout(addNewBtn);

		init();
	}

	public void init() {
		grid.setHeight(150, Unit.PIXELS);
		grid.setColumns("id", "name");

		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.edit(e.getValue());
			if (e.getValue() != null)
				right.setData(e.getValue().getCupResult());
		});

		addNewBtn.addClickListener(e -> editor.edit(new Cup("")));

		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			grid.setItems(repo.findAll());
		});

		left = new VerticalLayout(actions, grid, editor);
		right = new CupResultLayout();
		layout = new HorizontalLayout(left, right);

		grid.setItems(repo.findAll());
	}

	public AbstractOrderedLayout getLayout() {
		return layout;
	}

	HorizontalLayout actions;

	private AbstractOrderedLayout layout;

}
