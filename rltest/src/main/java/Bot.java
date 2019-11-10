import java.util.ArrayList;
import java.util.List;

public abstract class Bot {

    protected int lastState;
    protected int lastAction;
    protected List<Move> moves = new ArrayList<>(); //history
    protected final Route route;


    public static final int PLAYER_HEIGHT = 32;
    public static final int PLAYER_WIDTH = 64;

    public Bot(Route route) {
        this.route = route;
    }

    protected abstract int selectAction(int state);

    public abstract void updateStrategy();

    public int act(int xdif, int ydif, int vel){ // 플레이어의 현 상태들

        //Chooses the best action with respect to the current state - Chooses 0 (don't flap) to tie-break

        int state = route.mapState(xdif, ydif, vel); // 현 상태의 idx를 얻는다.

        if(lastState != -1) {
            this.moves.add(new Move(lastState, lastAction, state, 1)); // Add the experience to the history
        }

        int action = selectAction(state); //Q러닝을 참조할 것, 지금 상태에서 제일 좋은 할일을 골라준다.

        lastState = state; // Update the last_state with the current state
        lastAction = action;

        return action;
    }

    public void reset() {
        lastState = -1;
        lastAction = -1;
        moves.clear();
    }
}