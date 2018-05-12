package de.regnerus.kjft.cup;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.ValueProvider;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import de.regnerus.kjft.cup.Cup.CupResult;

public class CupResultLayout extends VerticalLayout {
	private static final long serialVersionUID = 1L;

	Grid<CupResult> resultGrid = new Grid<>();

	@Autowired
	public CupResultLayout() {
		final Label label = new Label("Ergebnisse");
		addComponents(label, resultGrid);

		resultGrid.setHeight(800, Unit.PIXELS);

		resultGrid.addColumn(new ValueProvider<CupResult, String>() {

			private static final long serialVersionUID = 6066162776997463059L;

			@Override
			public String apply(CupResult source) {
				return source.getTeam().getName();
			}
		}).setCaption("Team");

		resultGrid.sort(resultGrid.addColumn(new ValueProvider<CupResult, String>() {

			private static final long serialVersionUID = 1417808520409095655L;

			@Override
			public String apply(CupResult source) {
				return source.getResult().toString();
			}
		}).setCaption("Punkte"), SortDirection.DESCENDING);

	}

	public void setData(List<CupResult> results) {
		resultGrid.setItems(results);

	}
}
