public class FeedAction extends AbstractAction{

    public FeedAction(String actionName) {
        super("Feed");
    }

    @Override
    public void performAction(Pet pet) {
        pet.feed();
    }
}
