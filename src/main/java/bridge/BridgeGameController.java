package bridge;

import View.InputView;
import View.OutputView;
import java.util.List;

public class BridgeGameController {
    static final BridgeMaker bridgemaker = new BridgeMaker(new BridgeRandomNumberGenerator());
    static final InputView inputView = new InputView();
    static final OutputView outputView = new OutputView();

    private int gameCount = 0;
    private boolean isGameSuccess = true;
    private List<List<String>> result;
    private List<String> answerBridge;

    public BridgeGameController(List<String> answerBridge) {
        this.answerBridge = answerBridge;
    }

    static List<String> startSettingBridge(){
        outputView.printStart();
        outputView.printReadBridgeSize();
        int bridgeSize = inputView.readBridgeSize();
        return bridgemaker.makeBridge(bridgeSize);
    }

    List<List<String>> start() {
        BridgeGame bridgeGame = new BridgeGame(answerBridge);
        for(int movingIndex = 0; movingIndex < answerBridge.size(); movingIndex++) {
            result = move(bridgeGame, movingIndex);
            if(bridgeGame.isGameFailed(result)) {
                result = processFailed(bridgeGame);
                break;
            }
        }
        gameCount++;
        return result;
    }

    List<List<String>> processFailed(BridgeGame bridgeGame) {
        outputView.printReadGameCommand();
        String gameCommand = inputView.readGameCommand();
        if (bridgeGame.retry(gameCommand)) {
            return start();
        }
        isGameSuccess = false;
        return result;
    }

    void end(List<List<String>> result){
        outputView.printResult(result,isGameSuccess,gameCount);
    }

    List<List<String>> move (BridgeGame bridgeGame, int index){
        outputView.printReadMoving();
        String movingBlock = inputView.readMoving();
        List<List<String>> movedBridge = bridgeGame.move(movingBlock, index);
        outputView.printMap(movedBridge);
        return movedBridge;
    }
}
