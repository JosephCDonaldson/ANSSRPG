/*The MIT License (MIT)

Copyright (c) 2015 Disconsented, James Kerr

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/
/**
 * 
 */
package disconsented.anssrpg.perk;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

import net.minecraft.entity.EntityList;

/**
 * @author Disconsented
 *
 */
public class EntityPerk extends Perk {

	public EntityPerk() {
		super();
	}

	public EntityPerk(String name, ArrayList<Requirement> requirements,
			String description, int pointCost) {
		super(name, requirements, description, pointCost);
		// TODO Auto-generated constructor stub
	}
	private Class entity;
	@Expose
	public String entityName = "default_entityName";
	/**
	 * @return the entity
	 */
	public Class getEntity() {
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	protected void setEntity(Class entity) {
		this.entity = entity;		
	}

	@Override
	public void touchUp () {
			this.entity = (Class) EntityList.stringToClassMapping.get(entityName);
			if (entity == null){
				throw new NullPointerException();
			}
			this.setSlug();
	}

}
