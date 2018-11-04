package fr.drakogia.api.permissions;

import java.util.List;

public interface IPermissions<T> {

	void addPermission(String value);

	void addPermission(Integer rank_id, String value);

	void removePermissionByName(Integer rank_id, String value);

	void removePermissionById(Integer permission_id);

	void removePermissionByRankId(Integer rank_id);

	List<T> getPermissions(Integer rank_id);

}
