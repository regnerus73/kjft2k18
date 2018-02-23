package fairnesscup;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import de.regnerus.kjft.cup.Cup;
import de.regnerus.kjft.cup.Cup.CupResult;
import de.regnerus.kjft.game.Game;
import de.regnerus.kjft.game.GameRepository;
import de.regnerus.kjft.gameresult.GameResult;
import de.regnerus.kjft.team.Team;

public class FairnessCupLayout {
	final Grid<CupResult> grid;

	private final GameRepository gameRepo;

	public FairnessCupLayout(GameRepository repo) {
		this.gameRepo = repo;
		this.grid = new Grid<>(CupResult.class);

		init();
	}

	public void init() {
		grid.setHeight(400, Unit.PIXELS);
		grid.setColumns("team", "result");

		layout = new VerticalLayout(grid);

		setGridValues();
	}

	private void setGridValues() {
		Map<Team, Integer> fairnessByTeam = new HashMap<>();
		for (Game game : gameRepo.findAll()) {
			for (GameResult gameResult : game.getGameResults()) {
				Integer fairnessScore = fairnessByTeam.getOrDefault(gameResult.getTeam(), 0);
				fairnessByTeam.put(gameResult.getTeam(), gameResult.getFairnessScore() + fairnessScore);
			}
		}

		grid.setItems(Cup.convertScoreByTeamMap(fairnessByTeam));
	}

	public VerticalLayout getLayout() {
		return layout;
	}

	HorizontalLayout actions;

	private VerticalLayout layout;

	public void refresh() {
		setGridValues();
		
	}

}
