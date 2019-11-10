import com.github.chen0040.rl.learning.qlearn.QLearner;

public class QBot extends Bot {

    private final QLearner agent;

    public QBot(Route route, QLearner agent) {
        super(route);
        this.agent = agent;

    }

    @Override
    protected int selectAction(int state) {
        double q0 = this.agent.getModel().getQ(state, 0);
        double q1 = this.agent.getModel().getQ(state, 1);
        //뭐가 좋을지 확률을 가져오자.

        if(q0 >= q1) return 0;
        return 1;
        //그것을 보고
    }

    @Override
    public void updateStrategy() {

        //한 게임이 끝나면 지금까지의 히스토리를 모두 모아서 학습한다.
        //한 움직임씩
        //moves는 Bot에 정의
        for(int i=moves.size()-1; i >=0; --i){
            Move move = moves.get(i);
            //리워드 결정
            double r = move.reward; //기본 리워드는 1
            //지금 상태의 수치적 리워드


            if(i == moves.size()-1 || i == moves.size() - 2) {
                r = -1000;
            } else if(/* high_death_flag && 이런 식으로 특정 조건에서 리워드 조지기 가능 */ move.action == 1) {
                r = -1000;

            }
            //특수 경우의 임의적 리워드


            agent.update(move.oldState, move.action, move.newState, r);
            //or agent.update(move.oldState, move.action, move.newState, world.getActionsAvailableAtState(nextStateId), move.reward);
            //or  agent.update(move.action, move.newState, r);
        }

        reset();

    }
}