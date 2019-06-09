package de.regnerus.kjft.game;

import de.regnerus.kjft.gameresult.GameResult;
import de.regnerus.kjft.team.Team;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity
public class Game {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private boolean biggerIsBetter = true;
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private Set<GameResult> results;

    protected Game() {
    }

    public Game(String name) {
        this.name = name;
    }

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

    public void addResult(GameResult result) {
        results.add(result);
    }

    public Set<GameResult> getGameResults() {
        return results;
    }

    public Map<Team, Double> getScoreByTeam() {
        ArrayList<GameResult> resultList = new ArrayList<>();
        resultList.addAll(results);
        if (isBiggerIsBetter()) {
            resultList.sort(new GameResult.ResultComparator().reversed());
        } else {
            resultList.sort(new GameResult.ResultComparator());
        }


        final HashMap<Team, Double> scoreByTeam = new HashMap<>();

        double score = resultList.size();
        Double previousResult = null;
        for (int i = 0; i < resultList.size(); i++) {
            final GameResult gameResult = resultList.get(i);
            scoreByTeam.put(gameResult.getTeam(), getNumberOfWorseResults(resultList, gameResult));
        }

//		final HashMap<Team, Double> scoreByTeam = new HashMap<>();
//
//		double score = resultList.size();
//		Double previousResult = null;
//		for (int i = 0; i < resultList.size(); i++) {
//			final GameResult gameResult = resultList.get(i);
//			if (gameResult.getResult() == previousResult) {
//				scoreByTeam.put(gameResult.getTeam(), score + 1.0);
//			} else {
//				scoreByTeam.put(gameResult.getTeam(), score);
//			}
//			score--;
//			previousResult = gameResult.getResult();
//		}

        return scoreByTeam;

    }

    private Double getNumberOfWorseResults(ArrayList<GameResult> resultList, GameResult gameResult) {
        Double count = 0.0;
        for (GameResult result : resultList) {
            if (isBiggerIsBetter()) {
                if (result.getResult() < gameResult.getResult()) {
                    count++;
                }
            } else {
                if (result.getResult() > gameResult.getResult()) {
                    count++;
                }
            }
        }
        return count;
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
