package de.regnerus.kjft.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import de.regnerus.kjft.gameresult.GameResult;
import de.regnerus.kjft.team.Team;

@Entity
public class Game {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@OneToMany
	private List<GameResult> results;

	public void addResult(GameResult result) {
		results.add(result);
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
		return "Game [id=" + id + ", name=" + name + "]";
	}

}
