package de.regnerus.kjft.team;

import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.converter.StringToLongConverter;
import de.regnerus.kjft.gameresult.GameResult;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.TextField;

import de.regnerus.kjft.Editor;

@SpringComponent
@UIScope
public class TeamEditor extends Editor<Team> {
	private static final long serialVersionUID = 1L;

	private final TeamRepository repository;

	private Team team;

	TextField name = new TextField("Name");
	TextField averageAge = new TextField("Durchschnittsalter");

	Binder<Team> binder = new Binder<>(Team.class);

	@Autowired
	public TeamEditor(TeamRepository repository) {
		this.repository = repository;

		addComponents(name, averageAge, getActionsLayout());
		binder.forField(averageAge).withConverter(new StringToDoubleConverter("Es sind nur Zahlen möglich"))
				.bind(Team::getAverageAge, Team::setAverageAge);
		binder.forField(name).bind(Team::getName, Team::setName);
		//binder.bindInstanceFields(this);
	}

	@Override
	public void addActionButtonClickListeners() {
		getSaveButton().addClickListener(e -> team = repository.save(team));
		getDeleteButton().addClickListener(e -> repository.delete(team));
		getCancelButton().addClickListener(e -> edit(team));
	}

	@Override
	public void edit(Team item) {
		if (item == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = item.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			team = repository.findById(item.getId()).orElse(null);
		} else {
			team = item;
		}
		getCancelButton().setVisible(persisted);

		if(team.getAverageAge() == null)
			team.setAverageAge(0.0);

		binder.setBean(team);

		setVisible(true);

		getSaveButton().focus();
		name.selectAll();
	}
}
