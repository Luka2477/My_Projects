package application.model;

public enum Difficulty {
    EASY(1, 4), MEDIUM(5, 7), HARD(8, 22);

    private int minLengthWord;
    private int maxLengthWord;

    Difficulty (int minLengthWord, int maxLengthWord) {
        this.minLengthWord = minLengthWord;
        this.maxLengthWord = maxLengthWord;
    }

    public int getMinLengthWord() {
        return this.minLengthWord;
    }

    public int getMaxLengthWord () {
        return this.maxLengthWord;
    }
}
