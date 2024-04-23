public abstract class AbstractAction {
    protected String actionName;

    public AbstractAction(String actionName) {
        this.actionName = actionName;
    }

    public abstract void performAction(Pet pet);

    @Override
    public String toString() {
        return actionName;
    }
}
