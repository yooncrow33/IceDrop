package sc.model.overlay;

public class MessageConfig {
    final String title;

    public MessageConfig(String title, String l1, String l2, String l3, String l4, MessageKey.key key) {
        this.title = title;
        this.l1 = l1;
        this.l2 = l2;
        this.l3 = l3;
        this.l4 = l4;
        MessageManager.putOverlayMessage(this,key);

    }

    final String l1;
    final String l2;
    final String l3;
    final String l4;

    public String getTitle() {
        return title;
    }

    public String getL1() {
        return l1;
    }

    public String getL2() {
        return l2;
    }

    public String getL3() {
        return l3;
    }

    public String getL4() {
        return l4;
    }

}
