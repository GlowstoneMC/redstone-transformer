package net.glowstone.datapack.loader.model.external.advancement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class Advancement {
    private final Display display;
    private final String parent;
    private final Map<String, Criteria> criteria;
    private final List<List<String>> requirements;
    private final Rewards rewards;

    @JsonCreator
    public Advancement(
        @JsonProperty("display") Display display,
        @JsonProperty("parent") String parent,
        @JsonProperty("criteria") Map<String, Criteria> criteria,
        @JsonProperty("requirements") List<List<String>> requirements,
        @JsonProperty("rewards") Rewards rewards) {
        this.display = display;
        this.parent = parent;
        this.criteria = criteria;
        this.requirements = requirements;
        this.rewards = rewards;
    }

    public Display getDisplay() {
        return display;
    }

    public String getParent() {
        return parent;
    }

    public Map<String, Criteria> getCriteria() {
        return criteria;
    }

    public List<List<String>> getRequirements() {
        return requirements;
    }

    public Rewards getRewards() {
        return rewards;
    }
}
