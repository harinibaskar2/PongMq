package hbaskar;


// Darien Rodrigo

public class EmojiDecorator implements ScoreDisplay {
    private ScoreDisplay wrapped;
    
    public EmojiDecorator(ScoreDisplay wrapped) {
        this.wrapped = wrapped;
    }
    
    @Override
    public String getDisplay(int score) {
        String base = wrapped.getDisplay(score);
        String emoji = score >= 5 ? "🔥" : "🏓";
        return base + " " + emoji;
    }
}