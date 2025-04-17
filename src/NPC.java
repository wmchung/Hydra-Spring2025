public class NPC {
    private String npcId;
    private String description;
    private String region;
    private String interaction;

    public NPC(String npcId, String description, String region, String interaction) {
        this.npcId = npcId;
        this.description = description;
        this.region = region;
        this.interaction = interaction;
    }

    public String getNpcId() {
        return npcId;
    }

    public void setNpcId(String npcId) {
        this.npcId = npcId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getInteraction() {
        return interaction;
    }

    public void setInteraction(String interaction) {
        this.interaction = interaction;
    }

    @Override
    public String toString() {
        return npcId + ": " + description + " (Region: " + region + ")\nInteraction: " + interaction;
    }
}
