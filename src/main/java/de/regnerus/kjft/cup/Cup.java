package de.regnerus.kjft.cup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import de.regnerus.kjft.game.Game;
import de.regnerus.kjft.team.Team;

@Entity
public class Cup {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((games == null) ? 0 : games.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cup other = (Cup) obj;
		if (games == null) {
			if (other.games != null)
				return false;
		} else if (!games.equals(other.games))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Game> games = new ArrayList<>();

	protected Cup() {
	}

	private TreeMap<Team, Integer> getScoreByTeamMap() {
		TreeMap<Team, Integer> resultMap = new TreeMap<Team, Integer>();
		for (Game game : games) {
			for (Entry<Team, Integer> entry : game.getScoreByTeam().entrySet()) {
				Integer score = resultMap.get(entry.getKey());
				if (score == null) {
					score = entry.getValue();
				} else {
					score = score + entry.getValue();
				}
				resultMap.put(entry.getKey(), score);
			}
		}
		return resultMap;
	}

	public class CupResult {
		Team team;
		Integer result;

		public CupResult(Team team, Integer result) {
			this.team = team;
			this.result = result;
		}

		public Team getTeam() {
			return team;
		}

		public Integer getResult() {
			return result;
		}
	}

	public List<CupResult> getCupResult() {
		List<CupResult> results = new ArrayList<>();
		for (Entry<Team, Integer> scoreEntry : getScoreByTeamMap().entrySet()) {
			results.add(new CupResult(scoreEntry.getKey(), scoreEntry.getValue()));
		}
		return results;
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

	public void setGames(Set<Game> set) {
		games.clear();
		games.addAll(set);
	}

	public void setGames(List<Game> list) {
		games.clear();
		games.addAll(list);
	}

	public Set<Game> getGames() {
		return new HashSet<Game>(games);
	}
}
