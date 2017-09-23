package de.regnerus.kjft.team;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import de.regnerus.kjft.gameresult.GameResult;

@Entity
public class Team {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@OneToMany
	private List<GameResult> gameResults;

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

}
