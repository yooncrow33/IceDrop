package sc.base.gameModel.managers.stastics;

import sc.base.gameModel.managers.stastics.object.Data;
import sc.view.IGameModel;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;

public class StatisticsManager {
    private int clickCount = 0;
    private int iceClickCount = 0;
    private float hitRate = 0;
    private int currentCoin = 0;
    private int totalGetCoin = 0;
    private int currentXp = 0;
    private int totalGetSkillPoint = 0;
    private int currentLevel = 0;
    private int currentQuestRewardedCount = 0;
    private int iceBasicRushUsedCount = 0;
    private int iceRareRushUsedCount = 0;
    private int iceLegendaryRushUsedCount = 0;
    private int iceVacuumUsedCount = 0;
    private int iceAutoCollectedCount = 0;
    private int questsCompletedCount = 0;
    private int questRefreshedCount = 0;
    private int iceBasicCollectedCount = 0;
    private int iceRareCollectedCount = 0;
    private int iceLegendaryCollectedCount = 0;
    private int lastIceBasicCollectCount;
    private int lastIceRareCollectCount;
    private int lastIceLegendaryCollectCount;

    private int dataLength;

    private final IGameModel iGameModel;

    private final String KEY = "qwmsppreqtriteey";
    private final SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), "AES");

    ArrayList<Data> datas = new ArrayList<>();

    public StatisticsManager(IGameModel iGameModel) {
        this.iGameModel = iGameModel;
        load();
    }

    public void update() {
        if (clickCount > 0) {
            hitRate = (float) iceClickCount / clickCount * 100.0f;
        } else {
            hitRate = 0.0f;
        }
    }

    private String encrypt(String strToEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes()));
        } catch (Exception e) { return null; }
    }

    private String decrypt(String strToDecrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) { return null; }
    }

    public void loadData() {
        Properties props = new Properties();
        File file = new File(profilePath(iGameModel.getCurrentProfileId()));

        if (!file.exists()) return;

        try {
            String encryptedData = Files.readString(Path.of(profilePath(iGameModel.getCurrentProfileId())));
            String decryptedData = decrypt(encryptedData);

            if (decryptedData == null) throw new Exception("wrong decryption");

            props.load(new StringReader(decryptedData));

            // 1. 저장된 데이터의 개수 확인
            this.dataLength = Integer.parseInt(props.getProperty("dataLength", "0"));
            datas.clear();

            // 2. dataLength만큼 루프를 돌며 객체 복구 및 리스트에 추가
            for (int i = 0; i < dataLength; i++) {
                // 임시 StatisticsManager를 만들어 값을 채운 뒤 Data 객체 생성 (생성자 구조 활용)
                // 혹은 Data 클래스에 매개변수가 많은 생성자를 추가하여 직접 대입해도 됩니다.
                // 여기서는 기존 Data(StatisticsManager) 생성자를 활용하기 위해 임시 객체를 사용합니다.
                StatisticsManager temp = new StatisticsManager(iGameModel);

                temp.setClickCount(Integer.parseInt(props.getProperty(String.format("data_%d_clickCount", i), "0")));
                temp.setIceClickCount(Integer.parseInt(props.getProperty(String.format("data_%d_iceClickCount", i), "0")));
                temp.setHitRate(Float.parseFloat(props.getProperty(String.format("data_%d_hitRate", i), "0.0")));
                temp.setCurrentCoin(Integer.parseInt(props.getProperty(String.format("data_%d_currentCoin", i), "0")));
                temp.setTotalGetCoin(Integer.parseInt(props.getProperty(String.format("data_%d_totalGetCoin", i), "0")));
                temp.setCurrentXp(Integer.parseInt(props.getProperty(String.format("data_%d_currentXp", i), "0")));
                temp.setTotalGetSkillPoint(Integer.parseInt(props.getProperty(String.format("data_%d_totalGetSkillPoint", i), "0")));
                temp.setCurrentLevel(Integer.parseInt(props.getProperty(String.format("data_%d_currentLevel", i), "0")));
                temp.setCurrentQuestRewardedCount(Integer.parseInt(props.getProperty(String.format("data_%d_currentQuestRewardedCount", i), "0")));
                temp.setIceBasicRushUsedCount(Integer.parseInt(props.getProperty(String.format("data_%d_iceBasicRushUsedCount", i), "0")));
                temp.setIceRareRushUsedCount(Integer.parseInt(props.getProperty(String.format("data_%d_iceRareRushUsedCount", i), "0")));
                temp.setIceLegendaryRushUsedCount(Integer.parseInt(props.getProperty(String.format("data_%d_iceLegendaryRushUsedCount", i), "0")));
                temp.setIceVacuumUsedCount(Integer.parseInt(props.getProperty(String.format("data_%d_iceVacuumUsedCount", i), "0")));
                temp.setIceAutoCollectedCount(Integer.parseInt(props.getProperty(String.format("data_%d_iceAutoCollectedCount", i), "0")));
                temp.setQuestsCompletedCount(Integer.parseInt(props.getProperty(String.format("data_%d_questsCompletedCount", i), "0")));
                temp.setQuestRefreshedCount(Integer.parseInt(props.getProperty(String.format("data_%d_questRefreshedCount", i), "0")));
                temp.loadIceBasicCollectedCount(Integer.parseInt(props.getProperty(String.format("data_%d_iceBasicCollectedCount", i), "0")));
                temp.loadIceRareCollectedCount(Integer.parseInt(props.getProperty(String.format("data_%d_iceRareCollectedCount", i), "0")));
                temp.loadIceLegendaryCollectedCount(Integer.parseInt(props.getProperty(String.format("data_%d_iceLegendaryCollectedCount", i), "0")));

                datas.add(new Data(temp));
            }
        } catch (Exception e) {
            System.err.println("리스트 데이터 로드 실패: " + e.getMessage());
        }
    }

    public void saveData() {
        Properties props = new Properties();

        // 1. 현재 매니저의 기본 상태 저장 (기존 save() 로직 활용 가능)
        // ... (생략) ...

        // 2. datas 리스트에 담긴 객체들을 스트링 포맷 키값으로 저장
        this.dataLength = datas.size();
        props.setProperty("dataLength", String.valueOf(dataLength));

        for (int i = 0; i < dataLength; i++) {
            Data d = datas.get(i);
            props.setProperty(String.format("data_%d_clickCount", i), String.valueOf(d.getClickCount()));
            props.setProperty(String.format("data_%d_iceClickCount", i), String.valueOf(d.getIceClickCount()));
            props.setProperty(String.format("data_%d_hitRate", i), String.valueOf(d.getHitRate()));
            props.setProperty(String.format("data_%d_currentCoin", i), String.valueOf(d.getCurrentCoin()));
            props.setProperty(String.format("data_%d_totalGetCoin", i), String.valueOf(d.getTotalGetCoin()));
            props.setProperty(String.format("data_%d_currentXp", i), String.valueOf(d.getCurrentXp()));
            props.setProperty(String.format("data_%d_totalGetSkillPoint", i), String.valueOf(d.getTotalGetSkillPoint()));
            props.setProperty(String.format("data_%d_currentLevel", i), String.valueOf(d.getCurrentLevel()));
            props.setProperty(String.format("data_%d_currentQuestRewardedCount", i), String.valueOf(d.getCurrentQuestRewardedCount()));
            props.setProperty(String.format("data_%d_iceBasicRushUsedCount", i), String.valueOf(d.getIceBasicRushUsedCount()));
            props.setProperty(String.format("data_%d_iceRareRushUsedCount", i), String.valueOf(d.getIceRareRushUsedCount()));
            props.setProperty(String.format("data_%d_iceLegendaryRushUsedCount", i), String.valueOf(d.getIceLegendaryRushUsedCount()));
            props.setProperty(String.format("data_%d_iceVacuumUsedCount", i), String.valueOf(d.getIceVacuumUsedCount()));
            props.setProperty(String.format("data_%d_iceAutoCollectedCount", i), String.valueOf(d.getIceAutoCollectedCount()));
            props.setProperty(String.format("data_%d_questsCompletedCount", i), String.valueOf(d.getQuestsCompletedCount()));
            props.setProperty(String.format("data_%d_questRefreshedCount", i), String.valueOf(d.getQuestRefreshedCount()));
            props.setProperty(String.format("data_%d_iceBasicCollectedCount", i), String.valueOf(d.getIceBasicCollectedCount()));
            props.setProperty(String.format("data_%d_iceRareCollectedCount", i), String.valueOf(d.getIceRareCollectedCount()));
            props.setProperty(String.format("data_%d_iceLegendaryCollectedCount", i), String.valueOf(d.getIceLegendaryCollectedCount()));
        }

        try {
            StringWriter writer = new StringWriter();
            props.store(writer, "Statistics Data with History");
            String encryptedData = encrypt(writer.toString());
            if (encryptedData != null) {
                Files.writeString(Path.of(profilePath(iGameModel.getCurrentProfileId())), encryptedData);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "리스트 저장 실패: " + e.getMessage());
        }
    }

    public String profilePath(int id) {
        String homeDir = System.getProperty("user.home") + File.separator + "SC" + File.separator + "save";
        return homeDir + File.separator + "IceDropStatisticsSaveFile" + id + ".properties";
    }

    public void load() {
        Properties props = new Properties();
        File file = new File(profilePath(iGameModel.getCurrentProfileId()));

        if (!file.exists()) {
            save();
        }

        try {
            String encryptedData = Files.readString(Path.of(profilePath(iGameModel.getCurrentProfileId())));
            String decryptedData = decrypt(encryptedData);

            if (decryptedData == null) {
                throw new Exception("wrong decryption");
            }

            props.load(new StringReader(decryptedData));

            this.clickCount = Integer.parseInt(props.getProperty("clickCount", "0"));
            this.iceClickCount = Integer.parseInt(props.getProperty("iceClickCount", "0"));
            this.hitRate = Float.parseFloat(props.getProperty("hitRate", "0.0"));
            this.currentCoin = Integer.parseInt(props.getProperty("currentCoin", "0"));
            this.totalGetCoin = Integer.parseInt(props.getProperty("totalGetCoin", "0"));
            this.currentXp = Integer.parseInt(props.getProperty("currentXp", "0"));
            this.totalGetSkillPoint = Integer.parseInt(props.getProperty("totalGetSkillPoint", "0"));
            this.currentLevel = Integer.parseInt(props.getProperty("currentLevel", "1"));
            this.currentQuestRewardedCount = Integer.parseInt(props.getProperty("currentQuestRewardedCount", "0"));

            loadLastIceBasicCollectCount(Integer.parseInt(props.getProperty("lastIceBasicCollectCount", "0")));
            loadLastIceRareCollectCount(Integer.parseInt(props.getProperty("lastIceRareCollectCount", "0")));
            loadLastIceLegendaryCollectCount(Integer.parseInt(props.getProperty("lastIceLegendaryCollectCount", "0")));

            // 추가 변수 로드
            this.iceBasicRushUsedCount = Integer.parseInt(props.getProperty("iceBasicRushUsedCount", "0"));
            this.iceRareRushUsedCount = Integer.parseInt(props.getProperty("iceRareRushUsedCount", "0"));
            this.iceLegendaryRushUsedCount = Integer.parseInt(props.getProperty("iceLegendaryRushUsedCount", "0"));
            this.iceVacuumUsedCount = Integer.parseInt(props.getProperty("iceVacuumUsedCount", "0"));
            this.iceAutoCollectedCount = Integer.parseInt(props.getProperty("iceAutoCollectedCount", "0"));
            this.questsCompletedCount = Integer.parseInt(props.getProperty("questsCompletedCount", "0"));
            this.questRefreshedCount = Integer.parseInt(props.getProperty("questRefreshedCount", "0"));

            loadData();

        } catch (Exception e) {
            System.err.println("통계 데이터 로드 실패: " + e.getMessage());
        }
    }

    public void save() {
        Properties props = new Properties();

        props.setProperty("clickCount", String.valueOf(clickCount));
        props.setProperty("iceClickCount", String.valueOf(iceClickCount));
        props.setProperty("hitRate", String.valueOf(hitRate));
        props.setProperty("currentCoin", String.valueOf(currentCoin));
        props.setProperty("totalGetCoin", String.valueOf(totalGetCoin));
        props.setProperty("currentXp", String.valueOf(currentXp));
        props.setProperty("totalGetSkillPoint", String.valueOf(totalGetSkillPoint));
        props.setProperty("currentLevel", String.valueOf(currentLevel));
        props.setProperty("currentQuestRewardedCount", String.valueOf(currentQuestRewardedCount));

        props.setProperty("lastIceBasicCollectCount", String.valueOf(getLastIceBasicCollectCount() + getIceBasicCollectedCount()));
        props.setProperty("lastIceRareCollectCount", String.valueOf(getLastIceRareCollectCount() + getIceRareCollectedCount()));
        props.setProperty("lastIceLegendaryCollectCount", String.valueOf(getLastIceLegendaryCollectCount() + getIceLegendaryCollectedCount()));

        // 추가 변수 저장
        props.setProperty("iceBasicRushUsedCount", String.valueOf(iceBasicRushUsedCount));
        props.setProperty("iceRareRushUsedCount", String.valueOf(iceRareRushUsedCount));
        props.setProperty("iceLegendaryRushUsedCount", String.valueOf(iceLegendaryRushUsedCount));
        props.setProperty("iceVacuumUsedCount", String.valueOf(iceVacuumUsedCount));
        props.setProperty("iceAutoCollectedCount", String.valueOf(iceAutoCollectedCount));
        props.setProperty("questsCompletedCount", String.valueOf(questsCompletedCount));
        props.setProperty("questRefreshedCount", String.valueOf(questRefreshedCount));

        saveData();

        try {
            StringWriter writer = new StringWriter();
            props.store(writer, "Statistics Data");
            String encryptedData = encrypt(writer.toString());

            if (encryptedData != null) {
                Files.writeString(Path.of(profilePath(iGameModel.getCurrentProfileId())), encryptedData);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "통계 저장 실패: " + e.getMessage());
        }
    }

    // 기존 Getter/Setter/Add
    public int getClickCount() { return clickCount; }
    public void setClickCount(int clickCount) { this.clickCount = clickCount; }
    public void addClickCount() { this.clickCount++; }

    public int getIceClickCount() { return iceClickCount; }
    public void setIceClickCount(int iceClickCount) { this.iceClickCount = iceClickCount; }
    public void addIceClickCount() { this.iceClickCount++; }

    public float getHitRate() { return hitRate; }
    public void setHitRate(float hitRate) { this.hitRate = hitRate; }
    public void addHitRate() { this.hitRate++; }

    public int getCurrentCoin() { return currentCoin; }
    public void setCurrentCoin(int currentCoin) { this.currentCoin = currentCoin; }
    public void addCurrentCoin() { this.currentCoin++; }

    public int getTotalGetCoin() { return totalGetCoin; }
    public void setTotalGetCoin(int totalGetCoin) { this.totalGetCoin = totalGetCoin; }
    public void addTotalGetCoin() { this.totalGetCoin++; }

    public int getCurrentXp() { return currentXp; }
    public void setCurrentXp(int currentXp) { this.currentXp = currentXp; }
    public void addCurrentXp() { this.currentXp++; }

    public int getTotalGetSkillPoint() { return totalGetSkillPoint; }
    public void setTotalGetSkillPoint(int totalGetSkillPoint) { this.totalGetSkillPoint = totalGetSkillPoint; }
    public void addTotalGetSkillPoint() { this.totalGetSkillPoint++; }

    public int getCurrentLevel() { return currentLevel; }
    public void setCurrentLevel(int currentLevel) { this.currentLevel = currentLevel; }
    public void addCurrentLevel() { this.currentLevel++; }

    public int getCurrentQuestRewardedCount() { return currentQuestRewardedCount; }
    public void setCurrentQuestRewardedCount(int currentQuestRewardedCount) { this.currentQuestRewardedCount = currentQuestRewardedCount; }
    public void addCurrentQuestRewardedCount() { this.currentQuestRewardedCount++; }

    // 추가된 변수용 Getter/Setter/Add
    public int getIceBasicRushUsedCount() { return iceBasicRushUsedCount; }
    public void setIceBasicRushUsedCount(int count) { this.iceBasicRushUsedCount = count; }
    public void addIceBasicRushUsedCount() { this.iceBasicRushUsedCount++; }

    public int getIceRareRushUsedCount() { return iceRareRushUsedCount; }
    public void setIceRareRushUsedCount(int count) { this.iceRareRushUsedCount = count; }
    public void addIceRareRushUsedCount() { this.iceRareRushUsedCount++; }

    public int getIceLegendaryRushUsedCount() { return iceLegendaryRushUsedCount; }
    public void setIceLegendaryRushUsedCount(int count) { this.iceLegendaryRushUsedCount = count; }
    public void addIceLegendaryRushUsedCount() { this.iceLegendaryRushUsedCount++; }

    public int getIceVacuumUsedCount() { return iceVacuumUsedCount; }
    public void setIceVacuumUsedCount(int count) { this.iceVacuumUsedCount = count; }
    public void addIceVacuumUsedCount() { this.iceVacuumUsedCount++; }

    public int getIceAutoCollectedCount() { return iceAutoCollectedCount; }
    public void setIceAutoCollectedCount(int count) { this.iceAutoCollectedCount = count; }
    public void addIceAutoCollectedCount() { this.iceAutoCollectedCount++; }

    public int getQuestsCompletedCount() { return questsCompletedCount; }
    public void setQuestsCompletedCount(int count) { this.questsCompletedCount = count; }
    public void addQuestsCompletedCount() { this.questsCompletedCount++; }

    public int getQuestRefreshedCount() { return questRefreshedCount; }
    public void setQuestRefreshedCount(int count) { this.questRefreshedCount = count; }
    public void addQuestRefreshedCount() { this.questRefreshedCount++; }

    public void loadIceBasicCollectedCount(int iceBasicCollectedCount) { this.iceBasicCollectedCount = iceBasicCollectedCount; }
    public void loadIceRareCollectedCount(int iceRareCollectedCount) { this.iceRareCollectedCount = iceRareCollectedCount; }
    public void loadIceLegendaryCollectedCount(int iceLegendaryCollectedCount) { this.iceLegendaryCollectedCount = iceLegendaryCollectedCount; }
    public void loadLastIceBasicCollectCount(int lastIceBasicCollectCount) { this.lastIceBasicCollectCount = lastIceBasicCollectCount; }
    public void loadLastIceRareCollectCount(int lastIceRareCollectCount) { this.lastIceRareCollectCount = lastIceRareCollectCount; }
    public void loadLastIceLegendaryCollectCount(int lastIceLegendaryCollectCount) { this.lastIceLegendaryCollectCount = lastIceLegendaryCollectCount; }

    public void addIceBasicCollectCount() { iceBasicCollectedCount++; }
    public void addIceRareCollectCount() { iceRareCollectedCount++; }

    public int getLastIceBasicCollectCount() { return lastIceBasicCollectCount; }
    public int getLastIceRareCollectCount() { return lastIceRareCollectCount; }
    public int getLastIceLegendaryCollectCount() { return lastIceLegendaryCollectCount; }
    public void addIceLegendaryCollectCount() { iceLegendaryCollectedCount++; }

    public int getIceBasicCollectedCount() { return iceBasicCollectedCount; }
    public int getIceRareCollectedCount() { return  iceRareCollectedCount; }
    public int getIceLegendaryCollectedCount() { return iceLegendaryCollectedCount; }
    public int getIceBasicTotalCollectCount() {return iceBasicCollectedCount + lastIceBasicCollectCount; }
}