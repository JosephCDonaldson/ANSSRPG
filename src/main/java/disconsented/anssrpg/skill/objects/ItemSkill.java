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
package disconsented.anssrpg.skill.objects;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

/**
 * @author Disconsented
 *
 */
public class ItemSkill extends Skill {
	
	@Expose
	private ArrayList<ItemXP> exp = new ArrayList<ItemXP>();
	
	public ItemSkill(){	
		exp.add(new ItemXP());
		exp.add(new ItemXP());
	}

	@Override
	public void touchUp() {
		for (XPGain xp : exp){
			ItemXP thing = new ItemXP();
			thing.name = xp.name;
			thing.xp = xp.xp;
			thing.touchUp();
		}
		
	}

	@Override
	public ArrayList<ItemXP> getExp() {
		return exp;
	}

	@Override
	public void setExp(ArrayList exp) {
		this.exp = exp;
		
	}

}