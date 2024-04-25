public class PlayAction extends AbstractAction {

    public PlayAction(String actionName) {
        super("Play");
    }

    @Override
    public void performAction(Pet pet) {
        pet.play();
    }
}
