package fr.drakogia.rank.manager;

import java.util.List;

import fr.drakogia.api.permissions.IPermissionManager;
import fr.drakogia.rank.object.Permission;
import fr.drakogia.rank.table.Permissions;
import lombok.Getter;

@Getter
public class PermissionManager implements IPermissionManager<Permission> {

	private static PermissionManager instance;

	private PermissionManager() {}

	public static PermissionManager getInstance() {
		if (instance == null) {
			instance = new PermissionManager();
		}

		return instance;
	}

	@Override
	public void addPermission(String value) {
		Permissions.getInstance().addPermission(value);
	}

	@Override
	public void addPermission(Integer rank_id, String value) {
		Permissions.getInstance().addPermission(rank_id, value);
	}

	@Override
	public void removePermissionByName(Integer rank_id, String value) {
		Permissions.getInstance().removePermissionByName(rank_id, value);
	}

	@Override
	public void removePermissionById(Integer permission_id) {
		Permissions.getInstance().removePermissionById(permission_id);
	}

	@Override
	public void removePermissionByRankId(Integer rank_id) {
		Permissions.getInstance().removePermissionByRankId(rank_id);
	}

	@Override
	public List<Permission> getPermissions(Integer rank_id) {
		return Permissions.getInstance().getPermissions(rank_id);
	}
}
