package fr.drakogia.rank.object;

import java.util.List;

import fr.drakogia.api.ranks.IRank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Rank implements IRank<Permission> {

	private Integer id;
	private String name;
	private String prefix;
	private String tablist;
	private boolean isDefault;
	private List<Permission> permissions;

}
