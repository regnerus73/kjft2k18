package de.regnerus.kjft.cup;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import de.regnerus.kjft.game.GameRepository;

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
public class CupEditor extends VerticalLayout {
	private static final long serialVersionUID = 1L;
	private GameRepository gameRepo;
	private final CupRepository repository;

	/**
	 * The currently edited customer
	 */
	private Cup cup;

	/* Fields to edit properties in Customer entity */
	TextField name = new TextField("Name");

	/* Action buttons */
	Button save = new Button("Speichern", VaadinIcons.SAFE);
	Button cancel = new Button("Abbrechen");
	Button delete = new Button("Löschen", VaadinIcons.TRASH);
	CssLayout actions = new CssLayout(save, cancel, delete);

	Binder<Cup> binder = new Binder<>(Cup.class);

	@Autowired
	public CupEditor(CupRepository repository, GameRepository gameRepo) {
		this.repository = repository;
		this.gameRepo = gameRepo;

		addComponents(name, actions);

		// bind using naming convention
		binder.bindInstanceFields(this);

		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		// save.addClickListener(e -> repository.save(cup));
		save.addClickListener(event -> {
			cup.addGame(this.gameRepo.findAll().get(0));
			repository.save(CupEditor.this.cup);
		});

		delete.addClickListener(e -> repository.delete(cup));
		cancel.addClickListener(e -> editCup(cup));
		setVisible(false);
	}

	public interface ChangeHandler {

		void onChange();
	}

	public final void editCup(Cup c) {
		if (c == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			cup = repository.findOne(c.getId());
		} else {
			cup = c;
		}
		cancel.setVisible(persisted);

		// Bind customer properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(cup);

		setVisible(true);

		// A hack to ensure the whole form is visible
		save.focus();
		// Select all text in firstName field automatically
		name.selectAll();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}

}
