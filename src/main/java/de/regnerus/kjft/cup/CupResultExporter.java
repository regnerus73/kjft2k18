package de.regnerus.kjft.cup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.regnerus.kjft.cup.Cup.CupResult;

public class CupResultExporter {

	private final class CupResultComparator implements Comparator<CupResult> {
		@Override
		public int compare(CupResult lhs, CupResult rhs) {
			return lhs.getResult().compareTo(rhs.getResult());
		}
	}

	public void export() throws FileNotFoundException {
		final PrintWriter pw = new PrintWriter(new File(name + ".csv"));
		final StringBuilder sb = new StringBuilder();
		sb.append("Platz");
		sb.append(',');
		sb.append("Name");
		sb.append(',');
		sb.append("Punkte");
		sb.append('\n');

		int lastUniqueIndex = 0;
		Integer lastResult = null;
		for (final CupResult result : results) {
			if (result.getResult() != lastResult) {
				lastUniqueIndex++;
				lastResult = result.getResult();
			}

			sb.append(lastUniqueIndex);
			sb.append(',');
			sb.append(result.getTeam());
			sb.append(',');
			sb.append(result.getResult());
			sb.append('\n');
		}

		pw.write(sb.toString());
		pw.close();
		System.out.println("done!");
	}

	private List<CupResult> results;
	private String name;

	public void setData(String name, List<CupResult> results) {
		this.name = name;
		this.results = results;
		Collections.sort(this.results, new CupResultComparator());
		Collections.reverse(this.results);
	}

}
