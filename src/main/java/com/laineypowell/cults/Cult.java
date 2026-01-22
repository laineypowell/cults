package com.laineypowell.cults;

import com.mojang.authlib.GameProfile;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Cult {
    private final Map<UUID, Cultist> cultists = new HashMap<>();

    public String name;
    public String displayName;

    public void add(GameProfile gameProfile) {
        cultists.put(gameProfile.getId(), new Cultist(gameProfile.getName()));
    }

    public void read(CompoundTag tag) {
        cultists.clear();

        var listTag = tag.getList("Entries", Tag.TAG_COMPOUND);
        var i = 0;
        while (i < listTag.size()) {
            var compoundTag = listTag.getCompound(i++);
            var value = compoundTag.getCompound("Value");

            cultists.put(compoundTag.getUUID("Key"), new Cultist(value.getString("Name")));
        }

        name = tag.getString("Name");
        displayName = tag.getString("DisplayName");

    }

    public void write(CompoundTag tag) {
        var entries = new ListTag();
        for (var entry : cultists.entrySet()) {
            var compoundTag = new CompoundTag();
            compoundTag.putUUID("Key", entry.getKey());
            compoundTag.put("Value", entry.getValue().compoundTag());

            entries.add(compoundTag);
        }
        tag.put("Entries", entries);

        tag.putString("Name", name);
        tag.putString("DisplayName", displayName);

    }

    public static final class Cultist {
        private final String name;

        public Cultist(String name) {
            this.name = name;
        }

        public CompoundTag compoundTag() {
            var tag = new CompoundTag();
            tag.putString("Name", name);
            return tag;
        }

    }

    public static boolean invalidateName(String name) {
        for (var c : name.toCharArray()) {
            if ((c >= 'a' && c <= 'z') || c == '-' || c == '_') {
                continue;
            }

            return true;
        }

        return false;

    }

}
