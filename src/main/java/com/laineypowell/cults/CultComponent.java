package com.laineypowell.cults;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.HashMap;
import java.util.Map;

public final class CultComponent implements AutoSyncedComponent {
    private final Map<String, Cult> cults = new HashMap<>();

    @Override
    public void readFromNbt(CompoundTag compoundTag) {
        cults.clear();

        var listTag = compoundTag.getList("Entries", Tag.TAG_COMPOUND);
        var i = 0;
        while (i < listTag.size()) {
            var tag = listTag.getCompound(i++);

            var cult = new Cult();
            cult.read(tag.getCompound("Value"));
            cults.put(tag.getString("Key"), cult);
        }

    }

    @Override
    public void writeToNbt(CompoundTag compoundTag) {
        var entries = new ListTag();
        for (var entry : cults.entrySet()) {
            var tag = new CompoundTag();
            tag.putString("Key", entry.getKey());

            var value = new CompoundTag();
            entry.getValue().write(value);
            tag.put("Value", value);

            entries.add(tag);
        }

        compoundTag.put("Entries", entries);

    }
}
