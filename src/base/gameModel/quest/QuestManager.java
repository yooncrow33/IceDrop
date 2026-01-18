package base.gameModel.quest;

import base.gameModel.quest.object.IQuest;
import base.gameModel.quest.object.LongTimeQuest;
import base.gameModel.quest.object.Quest;
import model.effects.IInfo;

public class QuestManager implements IQuest {

    boolean longTimeQuestCompleted = false;
    boolean longTimeQuestReward = false;

    IQuestManager iq;

    Quest firstQuest;
    Quest secondQuest;
    LongTimeQuest thirdQuest;

    int lastIceBasicCC = 0;
    int lastIceRareCC = 0;
    int lastLegendaryCC = 0;
    int lastSessionPlayTime = 0;

    IInfo iInfo;

    public Quest getFirstQuest() {
        return firstQuest;
    }

    public Quest getSecondQuest() {
        return secondQuest;
    }

    public LongTimeQuest getThirdQuest() {
        return thirdQuest;
    }

    public QuestManager(IQuestManager iq, IInfo iInfo) {
        firstQuest = new Quest(iInfo, this);
        secondQuest = new Quest(iInfo, this);
        thirdQuest = new LongTimeQuest(iInfo, this);

        this.iq = iq;
        this.iInfo = iInfo;
    }

    public void update() {
        firstQuest.update(getCurrentQuestProgress(firstQuest.getQuestIndex()));
        secondQuest.update(getCurrentQuestProgress(secondQuest.getQuestIndex()));
        thirdQuest.update(iq.getIceManager().getIceBasicTotalCollectCount());

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
                return iq.getIceManager().getIceBasicCollectedCount() - lastIceBasicCC;
            case 2: // Collect 5 Ice_Rare
                return iq.getIceManager().getIceRareCollectedCount() - lastIceRareCC;
            case 3: // Collect 1 Ice_Legendary
                return iq.getIceManager().getIceLegendaryCollectedCount() - lastLegendaryCC;
            case 4: // Play for 10 min (sessionPlayTime)
            case 5: // Play for 30 min (sessionPlayTime)
            case 6: // Play for 1 hour (sessionPlayTime)
                // 퀘스트 4, 5, 6은 모두 세션 플레이 시간을 목표로 합니다.
                return iq.getSessionPlayTime() - lastSessionPlayTime;
            default:
                return 0;
        }
    }

    public void refreshQuests() {
        if (iq.getShopManager().getCoin() >= 400) {
            iq.getShopManager().addCoin(-400);
            firstQuest = new Quest(iInfo, this);
            secondQuest = new Quest(iInfo, this);
            iq.getEffectManager().addInfo("Quest refresh!", "- 400", "present coin = " + iq.getShopManager().getCoin());
        } else {
            iq.getEffectManager().addStrEffect("Not enough money!");
        }
        System.out.println("[debug]QuestRefresh");
        lastIceBasicCC = iq.getIceManager().getIceBasicCollectedCount();
        lastIceRareCC = iq.getIceManager().getIceRareCollectedCount();
        lastLegendaryCC = iq.getIceManager().getIceLegendaryCollectedCount();
        lastSessionPlayTime = iq.getSessionPlayTime();
    }

    @Override public void addCoin(int addCoinValue) {iq.getShopManager().addCoin(addCoinValue);}
    @Override public void addXp(int addXpValue) {iq.getSkillManager().addXp(addXpValue);}
    @Override public int getCoin() {return iq.getShopManager().getCoin();}
    @Override public void loadIsRewarded(boolean b) {this.longTimeQuestReward = b;thirdQuest.load(longTimeQuestCompleted, longTimeQuestReward);}
    @Override public void loadIsCompleted(boolean b) {this.longTimeQuestCompleted = b;thirdQuest.load(longTimeQuestCompleted, longTimeQuestReward);}

}
