package de.regnerus.kjft.gameresult;

import java.util.Comparator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import de.regnerus.kjft.team.Team;

@Entity
public class GameResult {

	@Id
	@GeneratedValue
	private Long id;

	@OneToOne
	private Team team;

	private Integer result = 0;

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public GameResult() {
	}

	public GameResult(Team team, Integer result) {
		this.team = team;
		this.result = result;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "GameResult [id=" + id + ", result=" + result + ", team=" + team + "]";
	}

	public static class ResultComparator implements Comparator<GameResult> {

		@Override
		public int compare(GameResult o1, GameResult o2) {
			return o1.getResult().compareTo(o2.getResult());
		}
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

}
