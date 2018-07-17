package de.regnerus.kjft;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.LoginForm.LoginEvent;
import com.vaadin.ui.LoginForm.LoginListener;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
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
import fairnesscup.FairnessCupLayout;

@SpringUI
public class VaadinUI extends UI {
	private static final long serialVersionUID = 1L;
	TeamLayout teamLayout;
	GameLayout gameLayout;
	CupLayout cupLayout;
	private FairnessCupLayout fairnessCupLayout;

	@Autowired
	public VaadinUI(TeamRepository teamRepo, TeamEditor teamEditor, GameRepository gameRepo, GameEditor gameEditor,
			CupRepository cupRepo, CupEditor cupEditor) {
		teamLayout = new TeamLayout(teamRepo, teamEditor);
		gameLayout = new GameLayout(gameRepo, gameEditor, teamRepo);
		cupLayout = new CupLayout(cupRepo, cupEditor);
		fairnessCupLayout = new FairnessCupLayout(gameRepo);
	}

	@Override
	protected void init(VaadinRequest request) {
		final TabSheet tabsheet = new TabSheet();
		final LoginForm form = new LoginForm();
		form.addLoginListener(new LoginListener() {

			@Override
			public void onLogin(LoginEvent event) {
				if (event.getLoginParameter("username").equals("foo")
						&& event.getLoginParameter("password").equals("bar")) {
					tabsheet.addTab(teamLayout.getLayout(), "Teams");
					tabsheet.addTab(gameLayout.getLayout(), "Spiele");
					tabsheet.addTab(cupLayout.getLayout(), "Pokale");
					tabsheet.addTab(fairnessCupLayout.getLayout(), "Fairness-Pokal");
					tabsheet.removeComponent(form);
				}

			}
		});
		tabsheet.addTab(form, "Login");

		tabsheet.addSelectedTabChangeListener(new TabSheet.SelectedTabChangeListener() {

			@Override
			public void selectedTabChange(SelectedTabChangeEvent event) {
				fairnessCupLayout.refresh();
				cupLayout.refresh();
			}
		});

		setContent(tabsheet);

	}

}
