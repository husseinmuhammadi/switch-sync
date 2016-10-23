package com.dpi.financial.ftcom.model.converter;

import org.jpos.core.InvalidCardException;
import org.jpos.core.Track1;
import org.jpos.core.Track2;

import javax.persistence.AttributeConverter;

/**
 * Created by h.mohammadi on 9/14/2016.
 */
public class Track2Converter implements AttributeConverter<Track2, String> {
    @Override
    public String convertToDatabaseColumn(Track2 track2) {
        if (track2 != null)
            return track2.toString();

        return null;
    }

    @Override
    public Track2 convertToEntityAttribute(String track) {
        Track2.Builder builder = Track2.builder();
        try {
            builder.track(track);
        } catch (InvalidCardException e) {
            e.printStackTrace();
        }
        return new Track2(builder);
    }
}
