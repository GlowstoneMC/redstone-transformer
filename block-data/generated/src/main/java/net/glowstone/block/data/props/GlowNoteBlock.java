package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.InstrumentStateReport;
import net.glowstone.block.data.states.reports.IntegerStateReport;
import net.glowstone.block.data.states.reports.NoteStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.block.data.type.NoteBlock;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowNoteBlock.Constants.INSTRUMENT_PROP_NAME,
            reportType = InstrumentStateReport.class
        ),
        @PropertyAssociation(
            propName = GlowNoteBlock.Constants.NOTE_PROP_NAME,
            reportType = NoteStateReport.class
        )
    },
    interfaceClass = NoteBlock.class
)
public interface GlowNoteBlock extends StatefulBlockData, NoteBlock {
    class Constants {
        public static final String INSTRUMENT_PROP_NAME = "instrument";
        public static final Class<Instrument> INSTRUMENT_STATE_TYPE = Instrument.class;
        public static final String NOTE_PROP_NAME = "note";
        public static final Class<Note> NOTE_STATE_TYPE = Note.class;
    }

    @Override
    default Instrument getInstrument() {
        return getValue(Constants.INSTRUMENT_PROP_NAME, Constants.INSTRUMENT_STATE_TYPE);
    }

    @Override
    default void setInstrument(Instrument instrument) {
        setValue(Constants.INSTRUMENT_PROP_NAME, Constants.INSTRUMENT_STATE_TYPE, instrument);
    }

    @Override
    default Note getNote() {
        return getValue(Constants.NOTE_PROP_NAME, Constants.NOTE_STATE_TYPE);
    }

    @Override
    default void setNote(Note note) {
        setValue(Constants.NOTE_PROP_NAME, Constants.NOTE_STATE_TYPE, note);
    }
}
