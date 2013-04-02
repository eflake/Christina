package com.eflake.christina;

import  android.support.v4.app.Fragment;

public class FragmentFactory {
	public final static int FRAGMENT_ONE_INDEX = 0;
	public final static int FRAGMENT_TWO_INDEX = 1;
	public final static int FRAGMENT_THREE_INDEX = 2;
	public static Fragment product(int index){
		if (index == FRAGMENT_ONE_INDEX) {
			return new FragmentOne(); 
		}else if (index == FRAGMENT_TWO_INDEX) {
			return new FragmentTwo(); 
		}else if (index == FRAGMENT_THREE_INDEX) {
			return  new FragmentThree(); 
		}
			return null;
	}  
}
