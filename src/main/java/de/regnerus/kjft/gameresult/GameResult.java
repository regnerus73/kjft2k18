package de.regnerus.kjft.gameresult;

import java.util.Comparator;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import de.regnerus.kjft.team.Team;

@Entity
public class GameResult {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (this.result == null ? 0 : this.result.hashCode());
		result = prime * result + (team == null ? 0 : team.hashCode());
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
		final GameResult other = (GameResult) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (result == null) {
			if (other.result != null) {
				return false;
			}
		} else if (!result.equals(other.result)) {
			return false;
		}
		if (team == null) {
			if (other.team != null) {
				return false;
			}
		} else if (!team.equals(other.team)) {
			return false;
		}
		return true;
	}

	@Id
	@GeneratedValue
	private Long id;

	@OneToOne(fetch = FetchType.EAGER)
	private Team team;
	private Integer result = 0;
	private Integer fairnessScore = 0;

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public Integer getFairnessScore() {
		return fairnessScore;
	}

	public void setFairnessScore(int scrore) {
		fairnessScore = scrore;
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
