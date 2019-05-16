package de.regnerus.kjft.gameresult;

import java.util.List;

import com.vaadin.data.converter.StringToDoubleConverter;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
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

	private final TeamRepository teamRepository;

	@Autowired
	public GameResultEditor(GameRepository repository, TeamRepository teamRepository) {
		this.teamRepository = teamRepository;
		this.gameRepository = repository;

		addComponents(team, resultField, fairnessScore, getActionsLayout());

		binder.forField(resultField).withConverter(new StringToDoubleConverter("Es sind nur ganze Zahlen möglich"))
				.bind(GameResult::getResult, GameResult::setResult);

		binder.forField(fairnessScore).withConverter(new StringToDoubleConverter("Es sind nur ganze Zahlen möglich"))
				.bind(GameResult::getFairnessScore, GameResult::setFairnessScore);

		binder.forField(team).bind(GameResult::getTeam, GameResult::setTeam);

		team.addValueChangeListener(new ValueChangeListener<Team>() {

			@Override
			public void valueChange(ValueChangeEvent<Team> event) {
				getSaveButton().setEnabled(event.getValue() != null);
			}

		});
	}

	@Override
	public void addActionButtonClickListeners() {
		getSaveButton().addClickListener(e -> {
			if (gameResult.getTeam() == null) {
				return;
			}

			if (gameResult.getId() == null) {
				game.addResult(gameResult);
			}
			gameRepository.save(game);
			edit(new GameResult());
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

		fillTeamPopup(item.getId() == null);

		gameResult = item;
		if (gameResult.getFairnessScore() == null) {
			gameResult.setFairnessScore(0);
		}

		binder.setBean(gameResult);
		setVisible(true);

		getSaveButton().focus();

	}

	private void fillTeamPopup(boolean hideTeamsWithExistingResult) {
		final List<Team> teams = teamRepository.findAll();
		if (hideTeamsWithExistingResult) {
			for (final GameResult gameResult2 : game.getGameResults()) {
				teams.remove(gameResult2.getTeam());
			}
		}
		team.setItems(teams);
	}

	public void setGame(Game game) {
		this.game = game;
	}
}
