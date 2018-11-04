package fr.drakogia.game.crafts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;
import com.google.gson.JsonSyntaxException;

import fr.drakogia.game.api.crafts.ICraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;

public class CustomCraft<T> implements ICraft<T> {

	private List<String[]> lines;
	private Map<String, ItemStack> items;
	private ItemStack output;
	private String group;
	private int width;
	private int height;

	private Class<T> clazz;
	
	public CustomCraft(Class<T> clazz){
		this.clazz = clazz;
		this.lines = new ArrayList<String[]>();
		this.items =  new HashMap<String, ItemStack>();
	}
	
	@Override
	public CustomCraft<T> addLine(String[] line) {
		if(this.width < line.length){
			this.width = line.length;
		}
		this.height++;
		this.lines.add(line);
		return this;
	}
	
	@Override
	public CustomCraft<T> addItem(String code, ItemStack itemStack) {
		this.items.put(code, itemStack);
		return this;
	}

	@Override
	public CustomCraft<T> setOutput(ItemStack output){
		this.output = output;
		return this;
	}
	
	@Override
	public IRecipe build(){
		String className = this.clazz.getSimpleName();
		T instance = null;
		
		if(className.equals("ShapedRecipes")){
			instance = (T) new ShapedRecipes(
				(this.group != null) ? this.group : "",
				this.width,
				this.height,
				this.generateItemsList(),
				this.output
			);
		}
		
		return (IRecipe) instance;
	}

	private NonNullList<Ingredient> generateItemsList()
    {
        NonNullList<Ingredient> nonnulllist = NonNullList.<Ingredient>func_191197_a(this.width * this.height, Ingredient.field_193370_a);
        Set<String> set = Sets.newHashSet(this.items.keySet());
        set.remove(" ");

        for (int i = 0; i < this.lines.size(); ++i)
        {
            for (int j = 0; j < this.lines.get(i).length; ++j)
            {
                String s = this.lines.get(i)[j];
                ItemStack item = null;
                if(s != ""){
                	item = this.items.get(s);
                } else {
                	item = ItemStack.field_190927_a;
                }
                
                Ingredient ingredient = Ingredient.func_193369_a(item);

                if (ingredient == null)
                {
                    throw new JsonSyntaxException("Pattern references symbol '" + s + "' but it's not defined in the key");
                }

                set.remove(s);
                nonnulllist.set(j + this.width * i, ingredient);
            }
        }

        if (!set.isEmpty())
        {
            throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
        }
        else
        {
            return nonnulllist;
        }
    } 
	
}
