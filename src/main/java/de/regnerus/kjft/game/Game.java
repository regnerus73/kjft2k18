package de.regnerus.kjft.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import de.regnerus.kjft.gameresult.GameResult;
import de.regnerus.kjft.team.Team;

@Entity
public class Game {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Game other = (Game) obj;
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

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	private List<GameResult> results;

	public void addResult(GameResult result) {
		results.add(result);
	}

	public List<GameResult> getGameResults() {
		return results;
	}

	public Map<Team, Integer> getScoreByTeam() {
		results.sort(new GameResult.ResultComparator());
		HashMap<Team, Integer> scoreByTeam = new HashMap<Team, Integer>();

		int score = results.size() + 1;
		for (int i = 0; i < results.size(); i++) {
			GameResult gameResult = results.get(i);
			scoreByTeam.put(gameResult.getTeam(), score);
			score--;
		}
		return scoreByTeam;

	}

	protected Game() {
	}

	public Game(String name) {
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
		return name;
	}

	public void removeResult(GameResult gameResult) {
		results.remove(gameResult);
	}

}
