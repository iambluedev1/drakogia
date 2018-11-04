package fr.drakogia.rank.object;

import fr.drakogia.api.permissions.IPermission;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Permission implements IPermission {

	private String permission;
	private Integer id;

}
