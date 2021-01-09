package net.glowstone.datapack.loader.model.external.structures;

// TODO: Actually parse the NBT.
public class Structure {
    private final String nbt;

    public Structure(String nbt) {
        this.nbt = nbt;
    }

    public String getNbt() {
        return nbt;
    }
}
