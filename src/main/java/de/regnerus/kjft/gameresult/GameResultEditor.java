package de.regnerus.kjft.gameresult;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import de.regnerus.kjft.game.Game;
import de.regnerus.kjft.game.GameRepository;
import de.regnerus.kjft.team.Team;
import de.regnerus.kjft.team.TeamRepository;

/**
 * A simple example to introduce building forms. As your real application is
 * probably much more complicated than this example, you could re-use this form
 * in multiple places. This example component is only used in VaadinUI.
 * <p>
 * In a real world application you'll most likely using a common super class for
 * all your forms - less code, better UX. See e.g. AbstractForm in Viritin
 * (https://vaadin.com/addon/viritin).
 */
@SpringComponent
@UIScope
public class GameResultEditor extends VerticalLayout {

	private final GameRepository gameRepository;

	/**
	 * The currently edited customer
	 */
	private GameResult gameResult;

	/* Fields to edit properties in Customer entity */
	NativeSelect<Team> team = new NativeSelect<>("Gruppe");
	TextField resultField = new TextField();

	/* Action buttons */
	Button save = new Button("Speichern", FontAwesome.SAVE);
	Button cancel = new Button("Abbrechen");
	Button delete = new Button("Löschen", FontAwesome.TRASH_O);
	CssLayout actions = new CssLayout(save, cancel, delete);

	Binder<GameResult> binder = new Binder<>(GameResult.class);

	private Game game;

	@Autowired
	public GameResultEditor(GameRepository repository, TeamRepository teamRepository) {
		team.setItems(teamRepository.findAll());
		this.gameRepository = repository;

		addComponents(team, resultField, actions);

		// bind using naming convention
		binder.forField(resultField).withConverter(new StringToIntegerConverter("Must enter a number"))
				.bind(GameResult::getResult, GameResult::setResult);

		binder.forField(team).bind(GameResult::getTeam, GameResult::setTeam);

		binder.bindInstanceFields(this);

		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent e) {
				game.addResult(gameResult);
				repository.save(game);
			}
		});
		// delete.addClickListener(e -> repository.delete(gameResult));
		cancel.addClickListener(e -> editGameResult(null, null));
		setVisible(false);
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editGameResult(Game game, GameResult result) {
		this.game = game;
		if (result == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = result.getId() != null;
		// if (persisted) {
		// // Find fresh entity for editing
		// // gameResult = gameRepository.findOne(c.getId());
		// } else {
		gameResult = result;
		// }
		cancel.setVisible(persisted);

		// Bind customer properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(gameResult);

		setVisible(true);

		// A hack to ensure the whole form is visible
		save.focus();
		// Select all text in firstName field automatically
		// team.selectAll();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}

}
