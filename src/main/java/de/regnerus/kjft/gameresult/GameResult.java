package hello.gameResult;

import java.util.Comparator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import hello.team.Team;

@Entity
public class GameResult {

	@Id
	@GeneratedValue
	private Long id;

	@OneToOne
	private Team team;

	private Integer result;

	public Integer getResult() {
		return result;
	}

	protected GameResult() {
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

}
