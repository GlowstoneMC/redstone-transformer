package net.glowstone.block.data.states.reports;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.bukkit.Note;

public class NoteStateReport extends StateReport<Note> {
    public NoteStateReport(String defaultValue, String... validValues) {
        super(Note.class, new Note(Integer.parseInt(defaultValue)), Arrays.stream(validValues).map((v) -> new Note(Integer.parseInt(v))).collect(Collectors.toSet()));
    }

    @Override
    public String stringifyValue(Note value) {
        return Byte.toString(value.getId());
    }

    @Override
    public Note parseValue(String value) {
        return new Note(Integer.parseInt(value));
    }
}
