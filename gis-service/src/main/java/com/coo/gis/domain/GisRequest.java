
package com.coo.gis.domain;

import java.util.List;

public class GisRequest {

    public String eventEndTimestamp;
    public String eventStartTimestamp;
    public String sourceSystemCode;
    public String touchpointTransactionContextCode;
    public String transactionSuccessIndicator;
    public List<PartyIdentification> partyIdentification = null;
    public List<ExtensionDataElement> extensionDataElement = null;

}
