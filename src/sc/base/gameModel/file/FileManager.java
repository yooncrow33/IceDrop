package sc.base.gameModel.file;

import sc.view.iGameModel.IGameModel;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Properties;

public class FileManager {
    private final String KEY = "iamsoprettyitset";
    private final SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), "AES");

    IGameModel iGameModel;

    public FileManager(IGameModel iGameModel) {
        this.iGameModel = iGameModel;
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


    public void save(int currentProfileId) {
        Properties props = new Properties();

        props.setProperty("coin", String.valueOf(iGameModel.getShopManager().getCoin()));
        props.setProperty("last", String.valueOf(iGameModel.getTickManager().getLastPlayTime() + iGameModel.getTickManager().getSessionPlayTime()));
        props.setProperty("lastIceBasicCollectCount", String.valueOf(iGameModel.getIceManager().getLastIceBasicCollectCount() + iGameModel.getIceManager().getIceBasicCollectedCount()));
        props.setProperty("lastIceRareCollectCount", String.valueOf(iGameModel.getIceManager().getLastIceRareCollectCount() + iGameModel.getIceManager().getIceRareCollectedCount()));
        props.setProperty("lastIceLegendaryCollectCount", String.valueOf(iGameModel.getIceManager().getLastIceLegendaryCollectCount() + iGameModel.getIceManager().getIceLegendaryCollectedCount()));
        props.setProperty("thirdQuestCompleted", String.valueOf(iGameModel.getQuestManager().getThirdQuest().getIsCompleted()));
        props.setProperty("level", String.valueOf(iGameModel.getSkillManager().getLevel()));
        props.setProperty("xp", String.valueOf(iGameModel.getSkillManager().getXp()));
        props.setProperty("skillPoint", String.valueOf(iGameModel.getSkillManager().getSkillPoint()));
        props.setProperty("skillPointUsed", String.valueOf(iGameModel.getSkillManager().getSkillPointUsed()));
        props.setProperty("itemCoolTimeLevel", String.valueOf(iGameModel.getSkillManager().getItemCoolTimeLevel()));
        props.setProperty("clickOffsetLevel", String.valueOf(iGameModel.getSkillManager().getClickOffsetLevel()));
        props.setProperty("iceBasicSpawnChanceLevel", String.valueOf(iGameModel.getSkillManager().getIceBasicSpawnChanceLevel()));
        props.setProperty("iceRareSpawnChanceLevel", String.valueOf(iGameModel.getSkillManager().getIceRareSpawnChanceLevel()));
        props.setProperty("iceLegendarySpawnChanceLevel", String.valueOf(iGameModel.getSkillManager().getIceLegendarySpawnChanceLevel()));
        props.setProperty("iceBasicRushItemCount", String.valueOf(iGameModel.getShopManager().getIceBasicRushItemCount()));
        props.setProperty("iceRareRushItemCount", String.valueOf(iGameModel.getShopManager().getIceRareRushItemCount()));
        props.setProperty("iceLegendaryRushItemCount", String.valueOf(iGameModel.getShopManager().getIceLegendaryRushItemCount()));
        props.setProperty("iceAutoCollectLevel", String.valueOf(iGameModel.getShopManager().getIceAutoCollectLevel()));
        props.setProperty("iceVacuumCount", String.valueOf(iGameModel.getShopManager().getIceVacuumCount()));
        props.setProperty("thirdQuestReward", String.valueOf(iGameModel.getQuestManager().getThirdQuest().getIsRewarded()));

        String homeDir = System.getProperty("user.home")+ File.separator + "SC" + File.separator + "save";

        String fullPath1 = homeDir + File.separator + "IceDropSaveProfile1.properties";
        String fullPath2 = homeDir + File.separator + "IceDropSaveProfile2.properties";
        String fullPath3 = homeDir + File.separator + "IceDropSaveProfile3.properties";
        String paths[] = {"empty", fullPath1, fullPath2, fullPath3};

        try {

            StringWriter writer = new StringWriter();
            props.store(writer, "User Save Data"); // 메모리상에 먼저 텍스트로 씀
            String encryptedData = encrypt(writer.toString()); // 통째로 암호화

            Files.writeString(Path.of(paths[currentProfileId]), encryptedData); // 파일에 저장

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "저장 실패: " + e.getMessage() + "\n경로: " + paths[currentProfileId]);
        }

    }

    public void load(int currentProfileId) {
        Properties props = new Properties();
        String homeDir = System.getProperty("user.home")+ File.separator + "SC" + File.separator + "save";

        String fullPath1 = homeDir + File.separator + "IceDropSaveProfile1.properties";
        String fullPath2 = homeDir + File.separator + "IceDropSaveProfile2.properties";
        String fullPath3 = homeDir + File.separator + "IceDropSaveProfile3.properties";

        String paths[] = {"empty", fullPath1, fullPath2, fullPath3};

        try (FileInputStream in = new FileInputStream(paths[currentProfileId])) {
            String encryptedData = Files.readString(Path.of(paths[currentProfileId]));

            // 2. 복호화 진행
            String decryptedData = decrypt(encryptedData);

            if (decryptedData == null) {
                throw new Exception("worng decryption");
            }

            // 3. 복호화된 문자열을 Properties 객체에 로드
            props.load(new StringReader(decryptedData));
            props.load(in);

            iGameModel.getShopManager().loadCoin(Integer.parseInt(props.getProperty("coin", "7")));
            iGameModel.getTickManager().loadLastPlayTime(Integer.parseInt(props.getProperty("last", "0")));
            iGameModel.getIceManager().loadLastIceBasicCollectCount(Integer.parseInt(props.getProperty("lastIceBasicCollectCount", "0")));
            iGameModel.getIceManager().loadLastIceRareCollectCount(Integer.parseInt(props.getProperty("lastIceRareCollectCount", "0")));
            iGameModel.getIceManager().loadLastIceLegendaryCollectCount(Integer.parseInt(props.getProperty("lastIceLegendaryCollectCount", "0")));
            iGameModel.getSkillManager().loadLevel(Integer.parseInt(props.getProperty("level", "1")));
            iGameModel.getSkillManager().loadXp(Integer.parseInt(props.getProperty("xp", "0")));
            iGameModel.getSkillManager().loadSkillPoint(Integer.parseInt(props.getProperty("skillPoint", "0")));
            iGameModel.getSkillManager().loadSkillPointUsed(Integer.parseInt(props.getProperty("skillPointUsed", "0")));
            iGameModel.getSkillManager().loadItemCoolTimeLevel(Integer.parseInt(props.getProperty("itemCoolTimeLevel", "0")));
            iGameModel.getSkillManager().loadClickOffsetLevel(Integer.parseInt(props.getProperty("clickOffsetLevel", "0")));
            iGameModel.getSkillManager().loadIceBasicSpawnChanceLevel(Integer.parseInt(props.getProperty("iceBasicSpawnChanceLevel", "1")));
            iGameModel.getSkillManager().loadIceRareSpawnChanceLevel(Integer.parseInt(props.getProperty("iceRareSpawnChanceLevel", "1")));
            iGameModel.getSkillManager().loadIceLegendarySpawnChanceLevel(Integer.parseInt(props.getProperty("iceLegendarySpawnChanceLevel", "1")));
            iGameModel.getShopManager().loadIceBasicRushItemCount(Integer.parseInt(props.getProperty("iceBasicRushItemCount", "0")));
            iGameModel.getShopManager().loadIceRareRushItemCount(Integer.parseInt(props.getProperty("iceRareRushItemCount", "0")));
            iGameModel.getShopManager().loadIceLegendaryRushItemCount(Integer.parseInt(props.getProperty("iceLegendaryRushItemCount", "0")));
            iGameModel.getShopManager().loadIceAutoCollectLevel(Integer.parseInt(props.getProperty("iceAutoCollectLevel", "0")));
            iGameModel.getShopManager().loadIceVacuumCount(Integer.parseInt(props.getProperty("iceVacuumCount", "0")));
            iGameModel.getQuestManager().getThirdQuest().load(Boolean.parseBoolean(props.getProperty("thirdQuestCompleted", "false")),Boolean.parseBoolean(props.getProperty("thirdQuestReward", "false")));


        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "저장파일 인식 실패");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
