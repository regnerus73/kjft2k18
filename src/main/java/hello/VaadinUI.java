package hello;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;

import hello.game.GameEditor;
import hello.game.GameLayout;
import hello.game.GameRepository;
import hello.team.TeamEditor;
import hello.team.TeamLayout;
import hello.team.TeamRepository;

@SpringUI
public class VaadinUI extends UI {

	TeamLayout teamLayout;
	GameLayout gameLayout;

	@Autowired
	public VaadinUI(TeamRepository teamRepo, TeamEditor teamEditor, GameRepository gameRepo, GameEditor gameEditor) {
		teamLayout = new TeamLayout(teamRepo, teamEditor);
		gameLayout = new GameLayout(gameRepo, gameEditor);
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
		HorizontalLayout mainLayout = new HorizontalLayout(teamLayout.getLayout(), gameLayout.getLayout());

		setContent(mainLayout);

	}

}
