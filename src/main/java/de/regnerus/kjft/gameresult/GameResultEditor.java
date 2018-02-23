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
	TextField fairnessScore = new TextField("Fairness");

	Binder<GameResult> binder = new Binder<>(GameResult.class);

	private Game game;

	private TeamRepository teamRepository;

	@Autowired
	public GameResultEditor(GameRepository repository, TeamRepository teamRepository) {
		this.teamRepository = teamRepository;
		this.gameRepository = repository;

		addComponents(team, resultField, fairnessScore, getActionsLayout());

		binder.forField(resultField).withConverter(new StringToIntegerConverter("Es sind nur ganze Zahlen möglich"))
				.bind(GameResult::getResult, GameResult::setResult);

		binder.forField(fairnessScore).withConverter(new StringToIntegerConverter("Es sind nur ganze Zahlen möglich"))
				.bind(GameResult::getFairnessScore, GameResult::setFairnessScore);

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
		team.setItems(teamRepository.findAll());
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
		if (gameResult.getFairnessScore() == null)
			gameResult.setFairnessScore(0);
		// }
		// cancel.setVisible(true);

		// Bind customer properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(gameResult);
		setVisible(true);

		getSaveButton().focus();

	}

	public void setGame(Game game) {
		this.game = game;
	}
}
