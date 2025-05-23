package hbaskar;

public class BasicScoreDisplay implements ScoreDisplay {
    @Override
    public String getDisplay(int score) {
        return "Score: " + score;
    }
}