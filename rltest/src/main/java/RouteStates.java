import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteStates {

    //여기서는 스테이트들에 대해서 모두 계산한 후에 매핑하여 잘 넣어놓습니다.


    protected Map<String, Integer> state_word2idx = new HashMap<>();
    protected Map<Integer, String> state_idx2word = new HashMap<>();
    //양방향 찾기 쉬우라고

    // Flag if the bird died in the top pipe 이런 식으로 한 상태의 수치를 얻을 수 있음
    //int y_dif = Integer.parseInt(route.stateText(moves.get(moves.size()-1).newState).split("_")[1]);



    public RouteStates() { // x,y,속도의 경우의 수를 모두 계산해서 넣어놓는다.

        List<Integer> xValues = new ArrayList<>();
        List<Integer> yValues = new ArrayList<>();
        List<Integer> vValues = new ArrayList<>();

        for(int i=-40; i < 140; i+=10){
            xValues.add(i);
        }
        for(int i=140; i < 421; i+=70) {
            xValues.add(i);
        }
        xValues.sort(Integer::compare);

        for(int i=-300; i < 180; i+=10) {
            yValues.add(i);
        }
        for(int i=180; i < 421; i+=60) {
            yValues.add(i);
        }
        yValues.sort(Integer::compare);

        for(int i=-10; i < 11; i++) {
            vValues.add(i);
        }

        int idx = 0;
        for(Integer x : xValues){
            for(Integer y : yValues) {
                for(Integer v : vValues) {
                    String word = x + "_" + y + "_" + v;
                    state_idx2word.put(idx, word);
                    state_word2idx.put(word, idx);
                    idx++;
                }
            }
        }
    }


    public int count() {
        // 구간의 갯수란
        return state_idx2word.size();
    }

    public int mapState(int xdif, int ydif, int vel) { //맵의 상태를 idx로 바꾸어준다.

        // Map the (xdif, ydif, vel)to the respective state, with regards to the grids
        // The state is a string, "xdif_ydif_vel"
        // X ->[-40, -30.. .120]U[140, 210 ...420]
        // Y ->[-300, -290 ...160]U[180, 240 ...420]

        if(xdif < 140) {
            xdif = xdif - (xdif % 10);
        } else {
            xdif = xdif - (xdif % 70);
        }

        if(ydif < 180) {
            ydif = ydif - (ydif % 10);
        } else {
            ydif = ydif - (ydif % 60);
        }

        String state_word = xdif + "_" + ydif + "_" + vel;

        //logger.info(state_word);

        return state_word2idx.get(state_word);
    }

    public String stateText(int stateIdx) {
        return state_idx2word.get(stateIdx);
    }
}
