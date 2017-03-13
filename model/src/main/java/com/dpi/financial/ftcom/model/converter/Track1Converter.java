package com.dpi.financial.ftcom.model.converter;

import org.jpos.core.InvalidCardException;
import org.jpos.core.Track1;

import javax.persistence.AttributeConverter;

/**
 * Created by h.mohammadi on 9/14/2016.
 */
public class Track1Converter implements AttributeConverter<Track1, String> {
    @Override
    public String convertToDatabaseColumn(Track1 track1) {
        if (track1 != null)
            return track1.toString();

        return null;
    }

    @Override
    public Track1 convertToEntityAttribute(String track) {
        Track1.Builder builder = Track1.builder();
        try {
            builder.track(track);
        } catch (InvalidCardException e) {
            e.printStackTrace();
        }
        return new Track1(builder);
    }
}
