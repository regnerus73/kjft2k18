package de.regnerus.kjft.team;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import de.regnerus.kjft.gameresult.GameResult;

@Entity
public class Team implements Comparable<Team> {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	private Double averageAge = 0.0;

	@OneToMany
	private List<GameResult> gameResults;

	public Double getAverageAge() {
		return averageAge;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Team team = (Team) o;

		if (!id.equals(team.id)) return false;
		if (!name.equals(team.name)) return false;
		if (averageAge != null ? !averageAge.equals(team.averageAge) : team.averageAge != null) return false;
		return gameResults.equals(team.gameResults);
	}

	@Override
	public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + name.hashCode();
		result = 31 * result + (averageAge != null ? averageAge.hashCode() : 0);
		result = 31 * result + gameResults.hashCode();
		return result;
	}

	public void setAverageAge(Double avergeAge) {
		this.averageAge = avergeAge;
	}

	protected Team() {
	}

	public Team(String name) {
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

	@Override
	public int compareTo(Team o) {
		return name.compareTo(o.getName());
	}

}
