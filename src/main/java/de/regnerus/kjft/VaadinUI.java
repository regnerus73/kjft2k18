package de.regnerus.kjft;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;

import de.regnerus.kjft.cup.CupEditor;
import de.regnerus.kjft.cup.CupLayout;
import de.regnerus.kjft.cup.CupRepository;
import de.regnerus.kjft.game.GameEditor;
import de.regnerus.kjft.game.GameLayout;
import de.regnerus.kjft.game.GameRepository;
import de.regnerus.kjft.team.TeamEditor;
import de.regnerus.kjft.team.TeamLayout;
import de.regnerus.kjft.team.TeamRepository;

@SpringUI
public class VaadinUI extends UI {
	private static final long serialVersionUID = 1L;
	TeamLayout teamLayout;
	GameLayout gameLayout;
	CupLayout cupLayout;

	@Autowired
	public VaadinUI(TeamRepository teamRepo, TeamEditor teamEditor, GameRepository gameRepo, GameEditor gameEditor,
			CupRepository cupRepo, CupEditor cupEditor) {
		teamLayout = new TeamLayout(teamRepo, teamEditor);
		gameLayout = new GameLayout(gameRepo, gameEditor, teamRepo);
		cupLayout = new CupLayout(cupRepo, cupEditor);
		// this.repo = repo;
		// this.editor = editor;
		// this.grid = new Grid<>(Team.class);
		// this.filter = new TextField();
		// this.addNewBtn = new Button("Neues Team", FontAwesome.PLUS);
	}

	@Override
	protected void init(VaadinRequest request) {
		// teamLayout.init();
		// build layout
		// HorizontalLayout mainLayout = new
		// HorizontalLayout(teamLayout.getLayout(), gameLayout.getLayout());
		TabSheet tabsheet = new TabSheet();
		tabsheet.addTab(teamLayout.getLayout(), "Gruppen");
		tabsheet.addTab(gameLayout.getLayout(), "Spiele");
		tabsheet.addTab(cupLayout.getLayout(), "Pokale");

		setContent(tabsheet);

	}

}
