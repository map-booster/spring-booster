package com.coo.touchpoint.domain;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Document(collection = "touchpoint")
public class TouchpointEvent {

	@Id
	private String id;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss.SSSZ")
	private Date eventEndTimestamp;
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss.SSSZ")
	private Date eventStartTimestamp;
	@NotNull
	private String sourceSystemCode;
	@NotNull
	private String touchpointTransactionContextCode;
	private String transactionSuccessIndicator;
	@NotNull
	private List<PartyIdentification> partyIdentification = null;
	@NotNull
	private List<ExtensionDataElement> extensionDataElement = null;

}
