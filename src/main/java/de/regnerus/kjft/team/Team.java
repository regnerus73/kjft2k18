package de.regnerus.kjft.team;

import java.util.List;
import java.util.Objects;

import javax.persistence.*;

import de.regnerus.kjft.gameresult.GameResult;

@Entity
public class Team implements Comparable<Team> {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	private Double averageAge = 0.0;

	public Double getAverageAge() {
		return averageAge;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(id, team.id) &&
                Objects.equals(name, team.name) &&
                Objects.equals(averageAge, team.averageAge);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, averageAge);
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
