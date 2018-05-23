
package com.coo.gis.domain;

import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class PartyIdentification {
	@NotNull
    public String identityName;
	@NotNull
    public String identityValue;

}
