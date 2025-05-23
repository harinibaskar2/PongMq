package hbaskar;


// @author Daniel Alexander Miranda
public class BasicScoreDisplay implements ScoreDisplay {
    @Override
    public String getDisplay(int score) {
        return "Score: " + score;
    }
}