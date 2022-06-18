package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.IntegerStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.type.Candle;

@AssociatedWithProps(
        props = {
                @PropertyAssociation(
                        propName = GlowCandle.Constants.PROP_NAME,
                        reportType = IntegerStateReport.class
                )
        },
        interfaceClass = Candle.class
)
public interface GlowCandle extends StatefulBlockData, Candle {
    class Constants {
        public static final String PROP_NAME= "candles";
        public static final Class<Integer> STATE_TYPE = Integer.class;
    }

    @Override
    default int getCandles() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setCandles(int candles) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, candles);
    }

    @Override
    default int getMinimumCandles() {
        return getMinValue(GlowSeaPickle.Constants.PROP_NAME, GlowSeaPickle.Constants.STATE_TYPE);
    }

    @Override
    default int getMaximumCandles() {
        return getMaxValue(GlowSeaPickle.Constants.PROP_NAME, GlowSeaPickle.Constants.STATE_TYPE);
    }
}
