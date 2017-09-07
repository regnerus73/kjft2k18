package de.regnerus.kjft.cup;

import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import de.regnerus.kjft.game.Game;
import de.regnerus.kjft.team.Team;

@Entity
public class Cup {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@OneToMany
	private List<Game> games;

	protected Cup() {
	}

	public TreeMap<Team, Integer> getScoreByTeam() {
		TreeMap<Team, Integer> result = new TreeMap<Team, Integer>();
		for (Game game : games) {
			for (Entry<Team, Integer> entry : game.getScoreByTeam().entrySet()) {
				Integer score = result.get(entry.getKey());
				if (score == null) {
					score = entry.getValue();
				} else {
					score = score + entry.getValue();
				}
				result.put(entry.getKey(), score);
			}
		}
		return result;
	}

	public Cup(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Cup [id=" + id + ", name=" + name + ", games=" + games + "]";
	}

}
