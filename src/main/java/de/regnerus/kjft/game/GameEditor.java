package de.regnerus.kjft.game;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.TextField;

import de.regnerus.kjft.Editor;

@SpringComponent
@UIScope
public class GameEditor extends Editor<Game> {

	private static final long serialVersionUID = 1L;

	private final GameRepository repository;

	private Game game;

	TextField name = new TextField("Name");

	CheckBox biggerIsBetter = new CheckBox("Größerer Wert ist besser");

	Binder<Game> binder = new Binder<>(Game.class);

	@Autowired
	public GameEditor(GameRepository repository) {
		this.repository = repository;
		addComponents(name, biggerIsBetter, getActionsLayout());
		binder.bindInstanceFields(this);
	}

	@Override
	public void addActionButtonClickListeners() {
		getSaveButton().addClickListener(e -> repository.save(game));
		getDeleteButton().addClickListener(e -> repository.delete(game));
		getCancelButton().addClickListener(e -> edit(game));
	}

	@Override
	public void edit(Game item) {
		if (item == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = item.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			game = repository.findById(item.getId()).orElse(null);
		} else {
			game = item;
		}
		getCancelButton().setVisible(persisted);

		binder.setBean(game);

		setVisible(true);

		getSaveButton().focus();
		name.selectAll();

	}
}
