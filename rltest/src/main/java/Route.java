import com.github.chen0040.rl.learning.qlearn.QLearner;

import java.util.function.Consumer;

public class Route {

    private Bot bot;
    private RouteStates states = new RouteStates();
    private QLearner learner;

    Route(){
        int actionCount = 3;// 0 : do nothing, 1 : leave bus, 2 : take bus 생각중
        learner = new QLearner(states.count(), actionCount);

        learner.getModel().setAlpha(0.7);
        learner.getModel().setGamma(1.0);

    }

    private void start(){
        int score;
        bot = new QBot(this, learner);
        bot.act(1,2,3); //현 상태 제공


        CrashInfo crashInfo =  null;//GamePhysics.checkCrash(player_x, player_y, lowerPipes, upperPipes, assets); 크래시 정보 계산
        //현 상태를 제공하여 충돌 체크
        if(crashInfo.isCrashed()){
            bot.updateStrategy(); // 한 세트의 게임이 끝나면 학습시킨다.
        }


    }
    public int mapState(int xdif, int ydif, int vel) {
        return states.mapState(xdif, ydif, vel);
    }

    public String stateText(int newState) {
        return "";
    }
}
