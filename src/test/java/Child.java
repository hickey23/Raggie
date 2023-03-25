public class Child extends Parent{

    private Integer score;

    public Child(String name, Integer age, Integer score) {
        super(name, age);
        this.score = score;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
