package net.glowstone.datapack.loader.model.external.text;

import static net.glowstone.datapack.loader.jackson.JsonNodeHandling.valueAsBoolean;
import static net.glowstone.datapack.loader.jackson.JsonNodeHandling.valueAsList;
import static net.glowstone.datapack.loader.jackson.JsonNodeHandling.valueAsObject;
import static net.glowstone.datapack.loader.jackson.JsonNodeHandling.valueAsString;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import java.util.List;
import java.util.Optional;

public class RawJsonText {
    @JsonCreator
    public static RawJsonText fromJson(JsonNode rootNode) throws JsonMappingException {
        if (rootNode.isTextual()) {
            return fromJsonObject(JsonNodeFactory.instance.objectNode().set("text", rootNode));
        } else if (rootNode.isArray()) {
            return fromJsonObject(JsonNodeFactory.instance.objectNode().set("extra", rootNode));
        } else if (rootNode.isObject()) {
            return fromJsonObject(rootNode);
        }
        throw new JsonMappingException(null, "Cannot create RawJsonText");
    }

    private static RawJsonText fromJsonObject(JsonNode rootNode) throws JsonMappingException {
        return new RawJsonText(
            valueAsString(rootNode, "text"),
            valueAsString(rootNode, "translate"),
            valueAsList(rootNode, "with", RawJsonText::fromJson),
            valueAsObject(rootNode, "score", Score::fromJson),
            valueAsString(rootNode, "selector"),
            valueAsString(rootNode, "keybind"),
            valueAsString(rootNode, "nbt"),
            valueAsBoolean(rootNode, "interpret"),
            valueAsString(rootNode, "block"),
            valueAsString(rootNode, "entity"),
            valueAsString(rootNode, "storage"),
            valueAsString(rootNode, "color"),
            valueAsBoolean(rootNode, "bold"),
            valueAsBoolean(rootNode, "italic"),
            valueAsBoolean(rootNode, "underlined"),
            valueAsBoolean(rootNode, "strikethrough"),
            valueAsBoolean(rootNode, "obfuscated"),
            valueAsString(rootNode, "insertion"),
            valueAsObject(rootNode, "clickEvent", ClickEvent::fromJson),
            valueAsObject(rootNode, "hoverEvent", HoverEvent::fromJson),
            valueAsList(rootNode, "extra", RawJsonText::fromJson)
        );
    }

    private final String text;
    private final String translate;
    private final List<RawJsonText> with;
    private final Score score;
    private final String selector;
    private final String keybind;
    private final String nbt;
    private final Boolean interpret;
    private final String block;
    private final String entity;
    private final String storage;
    private final String color;
    private final boolean bold;
    private final boolean italic;
    private final boolean underlined;
    private final boolean strikethrough;
    private final boolean obfuscated;
    private final String insertion;
    private final ClickEvent clickEvent;
    private final HoverEvent hoverEvent;
    private final List<RawJsonText> extra;

    private RawJsonText(
        Optional<String> text,
        Optional<String> translate,
        Optional<List<RawJsonText>> with,
        Optional<Score> score,
        Optional<String> selector,
        Optional<String> keybind,
        Optional<String> nbt,
        Optional<Boolean> interpret,
        Optional<String> block,
        Optional<String> entity,
        Optional<String> storage,
        Optional<String> color,
        Optional<Boolean> bold,
        Optional<Boolean> italic,
        Optional<Boolean> underlined,
        Optional<Boolean> strikethrough,
        Optional<Boolean> obfuscated,
        Optional<String> insertion,
        Optional<ClickEvent> clickEvent,
        Optional<HoverEvent> hoverEvent,
        Optional<List<RawJsonText>> extra) {
        this.text = text.orElse(null);
        this.translate = translate.orElse(null);
        this.with = with.orElse(null);
        this.score = score.orElse(null);
        this.selector = selector.orElse(null);
        this.keybind = keybind.orElse(null);
        this.nbt = nbt.orElse(null);
        this.interpret = interpret.orElse(null);
        this.block = block.orElse(null);
        this.entity = entity.orElse(null);
        this.storage = storage.orElse(null);
        this.color = color.orElse(null);
        this.bold = bold.orElse(false);
        this.italic = italic.orElse(false);
        this.underlined = underlined.orElse(false);
        this.strikethrough = strikethrough.orElse(false);
        this.obfuscated = obfuscated.orElse(false);
        this.insertion = insertion.orElse(null);
        this.clickEvent = clickEvent.orElse(null);
        this.hoverEvent = hoverEvent.orElse(null);
        this.extra = extra.orElse(null);
    }

    public String getText() {
        return text;
    }

    public String getTranslate() {
        return translate;
    }

    public List<RawJsonText> getWith() {
        return with;
    }

    public Score getScore() {
        return score;
    }

    public String getSelector() {
        return selector;
    }

    public String getKeybind() {
        return keybind;
    }

    public String getNbt() {
        return nbt;
    }

    public Boolean getInterpret() {
        return interpret;
    }

    public String getBlock() {
        return block;
    }

    public String getEntity() {
        return entity;
    }

    public String getStorage() {
        return storage;
    }

    public String getColor() {
        return color;
    }

    public boolean isBold() {
        return bold;
    }

    public boolean isItalic() {
        return italic;
    }

    public boolean isUnderlined() {
        return underlined;
    }

    public boolean isStrikethrough() {
        return strikethrough;
    }

    public boolean isObfuscated() {
        return obfuscated;
    }

    public String getInsertion() {
        return insertion;
    }

    public ClickEvent getClickEvent() {
        return clickEvent;
    }

    public HoverEvent getHoverEvent() {
        return hoverEvent;
    }

    public List<RawJsonText> getExtra() {
        return extra;
    }

}
