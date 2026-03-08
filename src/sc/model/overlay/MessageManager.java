package sc.model.overlay;

import java.util.LinkedHashMap;
import java.util.Map;

public class MessageManager {
    public static Map<MessageKey.key,MessageConfig> messageConfigMap = new LinkedHashMap<>();
    public static void putOverlayMessage(MessageConfig messageConfig, MessageKey.key key) {
        messageConfigMap.put(key,messageConfig);
    }

    public static MessageConfig getOverlayMessage(MessageKey.key key) {
        return messageConfigMap.get(key);
    }
}
