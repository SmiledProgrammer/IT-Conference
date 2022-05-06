package pl.sii.itconference.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.sii.itconference.enums.ContentTrack;

@Getter
@AllArgsConstructor
public class ContentTrackSummaryDto {

    private ContentTrack contentTrack;
    private float fillPercent;
}
