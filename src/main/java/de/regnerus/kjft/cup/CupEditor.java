package de.regnerus.kjft.cup;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;

import de.regnerus.kjft.Editor;
import de.regnerus.kjft.game.Game;
import de.regnerus.kjft.game.GameRepository;

@SpringComponent
@UIScope
public class CupEditor extends Editor<Cup> {
	private static final long serialVersionUID = 1L;
	private final CupRepository repository;

	private Cup cup;

	TextField name = new TextField("Name");

	TwinColSelect<Game> games;

	Binder<Cup> binder = new Binder<>(Cup.class);
	private GameRepository gameRepo;

	@Autowired
	public CupEditor(CupRepository repository, GameRepository gameRepo) {
		this.repository = repository;
		this.gameRepo = gameRepo;
		games = new TwinColSelect<>(null, gameRepo.findAll());
		games.setLeftColumnCaption("Verfügbar");
		games.setRightColumnCaption("Ausgewählt");

		addComponents(name, games, getActionsLayout());

		binder.bindInstanceFields(this);
	}

	@Override
	public void addActionButtonClickListeners() {
		getSaveButton().addClickListener(event -> {
			cup.setGames(games.getValue());
			CupEditor.this.cup = repository.save(CupEditor.this.cup);
		});
		getDeleteButton().addClickListener(e -> repository.delete(cup));
		getCancelButton().addClickListener(e -> edit(cup));
	}

	@Override
	public void edit(Cup item) {
		if (item == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = item.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			cup = repository.findById(item.getId()).orElse(null);
		} else {
			cup = item;
		}
		getCancelButton().setVisible(persisted);

		binder.setBean(cup);

		setVisible(true);

		getSaveButton().focus();
		name.selectAll();
	}

	public void refresh() {
		games.setDataProvider(DataProvider.ofCollection(gameRepo.findAll()));
	}

}
