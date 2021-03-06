package de.regnerus.kjft.cup;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.ValueProvider;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import de.regnerus.kjft.cup.Cup.CupResult;

public class CupResultLayout extends VerticalLayout {
	private static final long serialVersionUID = 1L;

	Grid<CupResult> resultGrid = new Grid<>();
	CupResultExporter exporter = new CupResultExporter();

	@Autowired
	public CupResultLayout() {
		final Label label = new Label("Ergebnisse");
		final Button exportButton = new Button("Exportieren");
		exportButton.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					exporter.export();
				} catch (final FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		addComponents(new HorizontalLayout(label, exportButton), resultGrid);

		resultGrid.setHeight(800, Unit.PIXELS);

		resultGrid.addColumn(new ValueProvider<CupResult, String>() {

			private static final long serialVersionUID = 6066162776997463059L;

			@Override
			public String apply(CupResult source) {
				return source.getTeam().getName();
			}
		}).setCaption("Team");

		resultGrid.sort(resultGrid.addColumn(new ValueProvider<CupResult, Double>() {

			private static final long serialVersionUID = 1417808520409095655L;

			@Override
			public Double apply(CupResult source) {
				return source.getResult();
			}
		}).setCaption("Punkte"), SortDirection.DESCENDING);
	}

	public void setData(String name, List<CupResult> results) {
		resultGrid.setItems(results);
		exporter.setData(name, results);

	}
}
