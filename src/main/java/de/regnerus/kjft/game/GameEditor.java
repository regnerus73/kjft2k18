package de.regnerus.kjft.game;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.TextField;

import de.regnerus.kjft.Editor;

@SpringComponent
@UIScope
public class GameEditor extends Editor {

	private static final long serialVersionUID = 1L;

	private final GameRepository repository;

	private Game game;

	TextField name = new TextField("Name");

	Binder<Game> binder = new Binder<>(Game.class);

	@Autowired
	public GameEditor(GameRepository repository) {
		this.repository = repository;
		addComponents(name, getActionsLayout());
		binder.bindInstanceFields(this);
	}

	public final void editGame(Game c) {
		if (c == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			game = repository.findOne(c.getId());
		} else {
			game = c;
		}
		getCancelButton().setVisible(persisted);

		binder.setBean(game);

		setVisible(true);

		getSaveButton().focus();
		name.selectAll();
	}

	@Override
	public void addActionButtonClickListeners() {
		getSaveButton().addClickListener(e -> repository.save(game));
		getDeleteButton().addClickListener(e -> repository.delete(game));
		getCancelButton().addClickListener(e -> editGame(game));
	}
}
