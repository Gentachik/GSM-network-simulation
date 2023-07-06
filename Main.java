public class Main {
    public static void main(String[] args) {
        Window window=new Window();
        Logic logic=new Logic();
        window.setLogic(logic);
        logic.run();
    }
}