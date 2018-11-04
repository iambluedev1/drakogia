package fr.drakogia.rank.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.drakogia.api.permissions.IPermissions;
import fr.drakogia.rank.object.Permission;
import fr.iambluedev.pine.api.field.IField;
import fr.iambluedev.pine.api.field.IFields;
import xyz.anana.pine.core.field.Field;
import xyz.anana.pine.core.table.Table;

public class Permissions extends Table implements IPermissions<Permission> {

	private static Permissions instance;

	private Permissions() {
		super("permissions");
	}

	public static Permissions getInstance() {
		if (instance == null) {
			instance = new Permissions();
		}

		return instance;
	}

	@Override
	public void addPermission(String value) {

	}

	@Override
	public void addPermission(Integer rank_id, String value) {
		this.insert(Arrays.asList(new Field("rank_id", rank_id), new Field("permission_value", value)));
	}

	@Override
	public void removePermissionByName(Integer rank_id, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removePermissionById(Integer permission_id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removePermissionByRankId(Integer rank_id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Permission> getPermissions(Integer rank_id) {
		List<Permission> permissions = new ArrayList<Permission>();
		
		for (IFields tmp1 : this.selectList(Arrays.asList(new Field("rank_id", rank_id)))) {
			List<IField> fields = tmp1.getFields();
			
			String permission = "";
			Integer id = -1;
			
			for (IField tmp2 : fields) {
				Field field = (Field) tmp2;
				if (field.getName().equals("permission_value")) {
					permission = (String) field.getValue();
				} else if (field.getName().equals("permission_id")) {
					id = (Integer) field.getValue();
				}
			}
			
			permissions.add(new Permission(permission, id));
		}
			
		return permissions;
	}

}
