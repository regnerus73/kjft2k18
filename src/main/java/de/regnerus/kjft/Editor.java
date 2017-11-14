package de.regnerus.kjft;

import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public abstract class Editor<T> extends VerticalLayout {

	Button save = new Button("Speichern", VaadinIcons.SAFE);
	Button cancel = new Button("Abbrechen");
	Button delete = new Button("Löschen", VaadinIcons.TRASH);
	CssLayout actions = new CssLayout(save, cancel, delete);

	public Editor() {
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		addActionButtonClickListeners();

		setSpacing(true);
		setVisible(false);
	}

	public abstract void addActionButtonClickListeners();

	public abstract void edit(T item);

	public interface ChangeHandler {

		void onChange();
	}

	private static final long serialVersionUID = 6910481065286242080L;

	public void setChangeHandler(ChangeHandler h) {
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}

	public Button getSaveButton() {
		return save;
	}

	public Button getCancelButton() {
		return cancel;
	}

	public Button getDeleteButton() {
		return delete;
	}

	public CssLayout getActionsLayout() {
		return actions;
	}

}
