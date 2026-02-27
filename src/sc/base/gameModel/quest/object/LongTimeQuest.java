package sc.base.gameModel.quest.object;

import sc.lang.Lang;
import sc.model.effects.IInfo;
import sc.view.iGameModel.IGameModel;

import java.awt.*;

public class LongTimeQuest {
    private String questsExplanation;

    private final int QUEST_GOAL = 5000;
    public boolean questCompleted = false;
    private boolean questReward = false;
    IGameModel iGameModel;
    final int ICE_COLLECT_BONUS = 5;
    Lang l;

    public LongTimeQuest(IGameModel iGameModel, Lang l) {
        this.iGameModel = iGameModel;
        this.l = l;
        questsExplanation = l.getQuestThirdRewardMsg();
    }

    public void update(int iceBasicCollectCount) {
        if (!questCompleted) {
            if (iceBasicCollectCount >= QUEST_GOAL) {
                questCompleted = true;
                iGameModel.getQuestManager().loadCompleted(true);
            }
        }
    }

    public void claimRewardedQuest() {
        if (questReward) return;

        if (questCompleted) {
            iGameModel.getShopManager().addCoin(50000);
            iGameModel.getSkillManager().addXp(4000);
            iGameModel.getEffectManager().addInfo(l.getQuestThirdRewardMsg(),l.getQuestThirdRewardMsg(), l.getSKillPresentCoin() + iGameModel.getShopManager().getCoin());
            questReward = true;
            iGameModel.getQuestManager().loadRewarded(true);
        }
    }

    public void load(boolean completed, boolean rewarded) {
        this.questCompleted = completed;
        this.questReward = rewarded;
    }

    public String getExplanation() {
        return questsExplanation;
    }

    public int getRewardCoin() { return 50000; }
    public int getRewardXp() { return 4000;}
    public int getQuestGoal() {return 5000;}
    public int getICE_COLLECT_BONUS() { return ICE_COLLECT_BONUS; }
    public boolean getIsRewarded() { return questReward; }
    public boolean getIsCompleted() {return  questCompleted;}
}

