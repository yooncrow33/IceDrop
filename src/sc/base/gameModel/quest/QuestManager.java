package sc.base.gameModel.quest;

import sc.base.gameModel.quest.object.IQuest;
import sc.base.gameModel.quest.object.LongTimeQuest;
import sc.base.gameModel.quest.object.Quest;
import sc.lang.Lang;
import sc.view.iGameModel.IGameModel;

public class QuestManager {

    boolean longTimeQuestCompleted = false;
    boolean longTimeQuestReward = false;

    IGameModel iGameModel;

    Quest firstQuest;
    Quest secondQuest;
    LongTimeQuest thirdQuest;

    int lastIceBasicCC = 0;
    int lastIceRareCC = 0;
    int lastLegendaryCC = 0;
    int lastSessionPlayTime = 0;
    Lang l;

    public Quest getFirstQuest() {
        return firstQuest;
    }

    public Quest getSecondQuest() {
        return secondQuest;
    }

    public LongTimeQuest getThirdQuest() {
        return thirdQuest;
    }

    public QuestManager(IGameModel iGameModel, Lang l) {
        this.l = l;
        firstQuest = new Quest(iGameModel, l);
        secondQuest = new Quest(iGameModel, l);
        thirdQuest = new LongTimeQuest(iGameModel, l);

        this.iGameModel = iGameModel;
    }

    public void update() {
        firstQuest.update(getCurrentQuestProgress(firstQuest.getQuestIndex()));
        secondQuest.update(getCurrentQuestProgress(secondQuest.getQuestIndex()));
        thirdQuest.update(iGameModel.getIceManager().getIceBasicTotalCollectCount());

        // 추가: 퀘스트 객체의 상태를 Manager의 필드에 동기화 (저장용)
        longTimeQuestCompleted = thirdQuest.getIsCompleted();
        longTimeQuestReward = thirdQuest.getIsRewarded();
    }

    public void clamRewardedQuest(int questNumber) {
        if (questNumber == 1) {
            firstQuest.claimRewardedQuest();
        } else if (questNumber == 2) {
            secondQuest.claimRewardedQuest();
        } else if (questNumber == 3) {
            thirdQuest.claimRewardedQuest();
        }
    }

    public boolean isThirdQuestComplete() {
        return longTimeQuestCompleted;
    }

    public int getBonus() {
        return thirdQuest.getICE_COLLECT_BONUS();
    }

    public int getCurrentQuestProgress(int questId) {
        switch (questId) {
            case 1: // Collect 10 Ice_Basic
                return iGameModel.getIceManager().getIceBasicCollectedCount() - lastIceBasicCC;
            case 2: // Collect 5 Ice_Rare
                return iGameModel.getIceManager().getIceRareCollectedCount() - lastIceRareCC;
            case 3: // Collect 1 Ice_Legendary
                return iGameModel.getIceManager().getIceLegendaryCollectedCount() - lastLegendaryCC;
            case 4: // Play for 10 min (sessionPlayTime)
            case 5: // Play for 30 min (sessionPlayTime)
            case 6: // Play for 1 hour (sessionPlayTime)
                // 퀘스트 4, 5, 6은 모두 세션 플레이 시간을 목표로 합니다.
                return iGameModel.getTickManager().getSessionPlayTime() - lastSessionPlayTime;
            default:
                return 0;
        }
    }

    public void refreshQuests() {
        if (iGameModel.getShopManager().getCoin() >= 400) {
            iGameModel.getShopManager().addCoin(-400);
            firstQuest = new Quest(iGameModel, l);
            secondQuest = new Quest(iGameModel, l);
            iGameModel.getEffectManager().addInfo(l.getQuestRefreshedMsg(), "- 400", l.getSKillPresentCoin() + iGameModel.getShopManager().getCoin());
        } else {
            iGameModel.getEffectManager().addStrEffect(l.getQuestNotEnoughMoneyMsg());
        }
        lastIceBasicCC = iGameModel.getIceManager().getIceBasicCollectedCount();
        lastIceRareCC = iGameModel.getIceManager().getIceRareCollectedCount();
        lastLegendaryCC = iGameModel.getIceManager().getIceLegendaryCollectedCount();
        lastSessionPlayTime = iGameModel.getTickManager().getSessionPlayTime();
    }
    public int getRefreshCost() {
        return 400;
    }

    public void loadCompleted(boolean b) {
        longTimeQuestCompleted = true;
    }

    public void loadRewarded(boolean b) {
        longTimeQuestReward = b;
    }
}
