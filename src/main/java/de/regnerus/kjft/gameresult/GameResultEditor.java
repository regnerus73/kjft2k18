package de.regnerus.kjft.gameresult;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;

import de.regnerus.kjft.Editor;
import de.regnerus.kjft.game.Game;
import de.regnerus.kjft.game.GameRepository;
import de.regnerus.kjft.team.Team;
import de.regnerus.kjft.team.TeamRepository;

@SpringComponent
@UIScope
public class GameResultEditor extends Editor<GameResult> {
	private static final long serialVersionUID = 1L;

	private final GameRepository gameRepository;

	private GameResult gameResult;

	NativeSelect<Team> team = new NativeSelect<>("Gruppe");
	TextField resultField = new TextField("Ergebnis");

	Binder<GameResult> binder = new Binder<>(GameResult.class);

	private Game game;

	@Autowired
	public GameResultEditor(GameRepository repository, TeamRepository teamRepository) {
		team.setItems(teamRepository.findAll());
		this.gameRepository = repository;

		addComponents(team, resultField, getActionsLayout());

		binder.forField(resultField).withConverter(new StringToIntegerConverter("Must enter a number"))
				.bind(GameResult::getResult, GameResult::setResult);

		binder.forField(team).bind(GameResult::getTeam, GameResult::setTeam);
	}

	@Override
	public void addActionButtonClickListeners() {
		getSaveButton().addClickListener(e -> {
			if (gameResult.getId() == null)
				game.addResult(gameResult);
			gameRepository.save(game);
		});
		getDeleteButton().addClickListener(e -> {
			game.removeResult(gameResult);
			gameRepository.save(game);
		});
		getCancelButton().addClickListener(e -> edit(null));
	}

	@Override
	public void edit(GameResult item) {
		if (item == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = item.getId() != null;
		// if (persisted) {
		// // Find fresh entity for editing
		// // gameResult = gameRepository.findOne(c.getId());
		// } else {
		gameResult = item;
		// }
		// cancel.setVisible(true);

		// Bind customer properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(gameResult);
		setVisible(true);

		getSaveButton().focus();

	}
}
