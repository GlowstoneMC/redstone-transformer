package net.glowstone.datapack.loader.model.external.advancement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.glowstone.datapack.loader.model.external.text.RawJsonText;

public class Display {
    private final Icon icon;
    private final RawJsonText title;
    private final String frame;
    private final String background;
    private final RawJsonText description;
    private final boolean showToast;
    private final boolean announceToChat;
    private final boolean hidden;

    @JsonCreator
    public Display(
        @JsonProperty("icon") Icon icon,
        @JsonProperty("title") RawJsonText title,
        @JsonProperty("frame") String frame,
        @JsonProperty("background") String background,
        @JsonProperty("description") RawJsonText description,
        @JsonProperty("show_toast") boolean showToast,
        @JsonProperty("announce_to_chat") boolean announceToChat,
        @JsonProperty("hidden") boolean hidden) {
        this.icon = icon;
        this.title = title;
        this.frame = frame;
        this.background = background;
        this.description = description;
        this.showToast = showToast;
        this.announceToChat = announceToChat;
        this.hidden = hidden;
    }

    public Icon getIcon() {
        return icon;
    }

    public RawJsonText getTitle() {
        return title;
    }

    public String getFrame() {
        return frame;
    }

    public String getBackground() {
        return background;
    }

    public RawJsonText getDescription() {
        return description;
    }

    public boolean getShowToast() {
        return showToast;
    }

    public boolean getAnnounceToChat() {
        return announceToChat;
    }

    public boolean getHidden() {
        return hidden;
    }
}
