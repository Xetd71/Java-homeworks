package sample;

enum Regime {
    Start("Start"),
    Stop("Stop");

    String name;

    private Regime(String name) {
        this.name = name;
    }
}
