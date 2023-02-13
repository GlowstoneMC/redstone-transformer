package net.glowstone.block.data.props;


import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BooleanStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.type.ChiseledBookshelf;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

@AssociatedWithProps(
        props = {
                @PropertyAssociation(
                        propName = GlowChiseledBookshelf.Constants.SLOT0_OCCUPIED_PROP_NAME,
                        reportType = BooleanStateReport.class
                ),
                @PropertyAssociation(
                        propName = GlowChiseledBookshelf.Constants.SLOT1_OCCUPIED_PROP_NAME,
                        reportType = BooleanStateReport.class
                ),
                @PropertyAssociation(
                        propName = GlowChiseledBookshelf.Constants.SLOT2_OCCUPIED_PROP_NAME,
                        reportType = BooleanStateReport.class
                ),
                @PropertyAssociation(
                        propName = GlowChiseledBookshelf.Constants.SLOT3_OCCUPIED_PROP_NAME,
                        reportType = BooleanStateReport.class
                ),
                @PropertyAssociation(
                        propName = GlowChiseledBookshelf.Constants.SLOT4_OCCUPIED_PROP_NAME,
                        reportType = BooleanStateReport.class
                ),
                @PropertyAssociation(
                        propName = GlowChiseledBookshelf.Constants.SLOT5_OCCUPIED_PROP_NAME,
                        reportType = BooleanStateReport.class
                )
        },
        interfaceClass = ChiseledBookshelf.class
)
public interface GlowChiseledBookshelf extends StatefulBlockData, ChiseledBookshelf {
    class Constants {
        public static final String SLOT0_OCCUPIED_PROP_NAME = "slot_0_occupied";
        public static final String SLOT1_OCCUPIED_PROP_NAME = "slot_1_occupied";
        public static final String SLOT2_OCCUPIED_PROP_NAME = "slot_2_occupied";
        public static final String SLOT3_OCCUPIED_PROP_NAME = "slot_3_occupied";
        public static final String SLOT4_OCCUPIED_PROP_NAME = "slot_4_occupied";
        public static final String SLOT5_OCCUPIED_PROP_NAME = "slot_5_occupied";
        public static final Class<Boolean> SLOT_OCCUPIED_TYPE = Boolean.class;
    }

    @Override
    default boolean isSlotOccupied(int slot) {
        switch (slot) {
            case 0:
                return getValue(Constants.SLOT0_OCCUPIED_PROP_NAME, Constants.SLOT_OCCUPIED_TYPE);
            case 1:
                return getValue(Constants.SLOT1_OCCUPIED_PROP_NAME, Constants.SLOT_OCCUPIED_TYPE);
            case 2:
                return getValue(Constants.SLOT2_OCCUPIED_PROP_NAME, Constants.SLOT_OCCUPIED_TYPE);
            case 3:
                return getValue(Constants.SLOT3_OCCUPIED_PROP_NAME, Constants.SLOT_OCCUPIED_TYPE);
            case 4:
                return getValue(Constants.SLOT4_OCCUPIED_PROP_NAME, Constants.SLOT_OCCUPIED_TYPE);
            case 5:
                return getValue(Constants.SLOT5_OCCUPIED_PROP_NAME, Constants.SLOT_OCCUPIED_TYPE);
            default:
                return false;
        }
    }

    @Override
    default void setSlotOccupied(int slot, boolean occupied){
        switch (slot) {
            case 0:
                setValue(Constants.SLOT0_OCCUPIED_PROP_NAME, Constants.SLOT_OCCUPIED_TYPE, occupied);
            case 1:
                setValue(Constants.SLOT1_OCCUPIED_PROP_NAME, Constants.SLOT_OCCUPIED_TYPE, occupied);
            case 2:
                setValue(Constants.SLOT2_OCCUPIED_PROP_NAME, Constants.SLOT_OCCUPIED_TYPE, occupied);
            case 3:
                setValue(Constants.SLOT3_OCCUPIED_PROP_NAME, Constants.SLOT_OCCUPIED_TYPE, occupied);
            case 4:
                setValue(Constants.SLOT4_OCCUPIED_PROP_NAME, Constants.SLOT_OCCUPIED_TYPE, occupied);
            case 5:
                setValue(Constants.SLOT5_OCCUPIED_PROP_NAME, Constants.SLOT_OCCUPIED_TYPE, occupied);
        }
    }

    @Override
    default @NotNull Set<Integer> getOccupiedSlots(){
        HashSet<Integer> result = new HashSet<>();
        if (getValue(Constants.SLOT0_OCCUPIED_PROP_NAME, Constants.SLOT_OCCUPIED_TYPE)) {
            result.add(0);
        }
        if (getValue(Constants.SLOT1_OCCUPIED_PROP_NAME, Constants.SLOT_OCCUPIED_TYPE)) {
            result.add(1);
        }
        if (getValue(Constants.SLOT2_OCCUPIED_PROP_NAME, Constants.SLOT_OCCUPIED_TYPE)) {
            result.add(2);
        }
        if (getValue(Constants.SLOT3_OCCUPIED_PROP_NAME, Constants.SLOT_OCCUPIED_TYPE)) {
            result.add(3);
        }
        if (getValue(Constants.SLOT4_OCCUPIED_PROP_NAME, Constants.SLOT_OCCUPIED_TYPE)) {
            result.add(4);
        }
        if (getValue(Constants.SLOT5_OCCUPIED_PROP_NAME, Constants.SLOT_OCCUPIED_TYPE)) {
            result.add(5);
        }
        return result;
    }

    @Override
    default int getMaximumOccupiedSlots(){
        return 6;
    }
}
