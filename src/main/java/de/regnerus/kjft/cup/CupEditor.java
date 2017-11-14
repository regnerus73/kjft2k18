package de.regnerus.kjft.cup;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;

import de.regnerus.kjft.Editor;
import de.regnerus.kjft.game.Game;
import de.regnerus.kjft.game.GameRepository;

@SpringComponent
@UIScope
public class CupEditor extends Editor {
	private static final long serialVersionUID = 1L;
	private final CupRepository repository;

	private Cup cup;

	TextField name = new TextField("Name");

	TwinColSelect<Game> games;

	Binder<Cup> binder = new Binder<>(Cup.class);

	@Autowired
	public CupEditor(CupRepository repository, GameRepository gameRepo) {
		this.repository = repository;
		games = new TwinColSelect<Game>(null, gameRepo.findAll());
		games.setLeftColumnCaption("Verfügbar");
		games.setRightColumnCaption("Ausgewählt");

		addComponents(name, games, getActionsLayout());

		binder.bindInstanceFields(this);

	}

	public final void editCup(Cup c) {
		if (c == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			cup = repository.findOne(c.getId());
		} else {
			cup = c;
		}
		getCancelButton().setVisible(persisted);

		binder.setBean(cup);

		setVisible(true);

		getSaveButton().focus();
		name.selectAll();
	}

	@Override
	public void addActionButtonClickListeners() {
		getSaveButton().addClickListener(event -> {
			cup.setGames(games.getValue());
			repository.save(CupEditor.this.cup);
		});
		getDeleteButton().addClickListener(e -> repository.delete(cup));
		getCancelButton().addClickListener(e -> editCup(cup));
	}

}
