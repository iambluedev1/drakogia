package fr.drakogia.rank.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.drakogia.api.ranks.IRanks;
import fr.drakogia.rank.object.Rank;
import fr.iambluedev.pine.api.field.IField;
import fr.iambluedev.pine.api.field.IFields;
import xyz.anana.pine.core.field.Field;
import xyz.anana.pine.core.table.Table;

public class Ranks extends Table implements IRanks<Rank> {

	private static Ranks instance;

	private Ranks() {
		super("ranks");
	}

	public static Ranks getInstance() {
		if (instance == null) {
			instance = new Ranks();
		}

		return instance;
	}

	@Override
	public List<Rank> getRanks() {
		List<Rank> ranks = new ArrayList<Rank>();
		for (IFields tmp1 : this.selectAll()) {
			List<IField> fields = tmp1.getFields();

			String name = "";
			String prefix = "";
			String tablist = "";
			boolean isDefault = false;
			Integer id = -1;

			for (IField tmp2 : fields) {
				Field field = (Field) tmp2;
				if (field.getName().equals("rank_name")) {
					name = (String) field.getValue();
				} else if (field.getName().equals("rank_prefix")) {
					prefix = (String) field.getValue();
				} else if (field.getName().equals("rank_tablist")) {
					tablist = (String) field.getValue();
				} else if (field.getName().equals("rank_default")) {
					isDefault = (boolean) field.getValue();
				} else if (field.getName().equals("rank_id")) {
					id = (Integer) field.getValue();
				}
			}

			ranks.add(new Rank(id, name, prefix, tablist, isDefault, Permissions.getInstance().getPermissions(id)));
		}
		return ranks;
	}

	@Override
	public void removeRank(String name) {
		
	}

	@Override
	public Integer addRank(String name, String prefix, String tablist, boolean isDefault) {
		this.insert(Arrays.asList(
			new Field("rank_name", name),
			new Field("rank_prefix", prefix),
			new Field("rank_tablist", tablist),
			new Field("rank_default", ((isDefault) ? 1 : 0))
		));
		
		return (Integer) this.getRequestHandler().select("SELECT LAST_INSERT_ID() as last_id FROM ranks", "last_id");
	}

	@Override
	public void updateRank(Rank rank) {
		this.update(Arrays.asList(
			new Field("rank_name", rank.getName()),
			new Field("rank_prefix", rank.getPrefix()),
			new Field("rank_tablist", rank.getTablist()),
			new Field("rank_default", ((rank.isDefault()) ? 1 : 0))
		), new Field("rank_id", rank.getId()));
	}

}
