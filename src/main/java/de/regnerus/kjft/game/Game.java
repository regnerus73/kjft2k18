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
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (name == null ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Game other = (Game) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	public boolean isBiggerIsBetter() {
		return biggerIsBetter;
	}

	public void setBiggerIsBetter(boolean biggerIsBetter) {
		this.biggerIsBetter = biggerIsBetter;
	}

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	private boolean biggerIsBetter = true;

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	private List<GameResult> results;

	public void addResult(GameResult result) {
		results.add(result);
	}

	public List<GameResult> getGameResults() {
		return results;
	}

	public Map<Team, Integer> getScoreByTeam() {
		if (isBiggerIsBetter()) {
			results.sort(new GameResult.ResultComparator().reversed());
		} else {
			results.sort(new GameResult.ResultComparator());
		}

		final HashMap<Team, Integer> scoreByTeam = new HashMap<>();

		int score = results.size() + 1;
		Integer previousResult = null;
		for (int i = 0; i < results.size(); i++) {
			final GameResult gameResult = results.get(i);
			if (gameResult.getResult() == previousResult) {
				scoreByTeam.put(gameResult.getTeam(), score + 1);
			} else {
				scoreByTeam.put(gameResult.getTeam(), score);
			}
			score--;
			previousResult = gameResult.getResult();
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
