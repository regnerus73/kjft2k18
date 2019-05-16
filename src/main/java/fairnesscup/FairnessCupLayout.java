package fairnesscup;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import de.regnerus.kjft.cup.Cup;
import de.regnerus.kjft.cup.Cup.CupResult;
import de.regnerus.kjft.cup.CupResultExporter;
import de.regnerus.kjft.game.Game;
import de.regnerus.kjft.game.GameRepository;
import de.regnerus.kjft.gameresult.GameResult;
import de.regnerus.kjft.team.Team;

public class FairnessCupLayout {
	final Grid<CupResult> grid;

	private final GameRepository gameRepo;
	private final CupResultExporter cupResultExporter = new CupResultExporter();

	public FairnessCupLayout(GameRepository repo) {
		this.gameRepo = repo;
		this.grid = new Grid<>(CupResult.class);

		init();
	}

	public void init() {
		final Button exportButton = new Button("Exportieren");
		exportButton.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(Button.ClickEvent event) {
				try {
					cupResultExporter.export();
				} catch (final FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		grid.setHeight(400, Unit.PIXELS);
		grid.setColumns("team", "result");

		layout = new VerticalLayout(exportButton, grid);

		setGridValues();
	}

	private void setGridValues() {
		Map<Team, Double> fairnessByTeam = new HashMap<>();
		for (Game game : gameRepo.findAll()) {
			for (GameResult gameResult : game.getGameResults()) {
				Double fairnessScore = fairnessByTeam.getOrDefault(gameResult.getTeam(), 0.0);
				fairnessByTeam.put(gameResult.getTeam(), gameResult.getFairnessScore() + fairnessScore);
			}
		}
		cupResultExporter.setData("Fairness",Cup.convertScoreByTeamMap(fairnessByTeam));
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
