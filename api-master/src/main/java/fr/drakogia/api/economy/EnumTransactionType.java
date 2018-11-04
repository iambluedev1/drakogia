package fr.drakogia.api.economy;

import lombok.Getter;

public enum EnumTransactionType {

	PAY(1),
	GIVE(2),
	REMOVE(3),
	SHOP(4),
	GIFT(5)
	
	;
	
	@Getter
	private Integer type;
	
	private EnumTransactionType(Integer type){
		this.type = type;
	}
	
}
