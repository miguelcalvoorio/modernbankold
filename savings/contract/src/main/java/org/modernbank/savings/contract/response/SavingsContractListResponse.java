package org.modernbank.savings.contract.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel
@JsonInclude(Include.NON_NULL)
public class SavingsContractListResponse extends RepresentationModel<SavingsContractResponse> {
    
    @ApiModelProperty(value = "Savings contracts list")
    private List<SavingsContractResponse> contractList;
}
