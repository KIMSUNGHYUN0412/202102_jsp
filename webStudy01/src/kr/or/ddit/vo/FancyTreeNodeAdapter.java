package kr.or.ddit.vo;

import java.io.File;

public class FancyTreeNodeAdapter extends File implements FancyTreeNode{

	public FancyTreeNodeAdapter(File adaptee, String relativePath) {
		super(adaptee.getAbsolutePath()); 
		this.key = relativePath;
	}

	private String key; 
	
	@Override
	public String getTitle() {
		return getName();
	}

	@Override
	public boolean isFolder() {
		return isDirectory();
	}

	@Override
	public String getKey() {
		return this.key;
	}
	
	@Override
	public boolean isLazy() {
		return isFolder();  
	}
	

	@Override
	public int compareTo(File pathname) {
		boolean mine = isDirectory();
		boolean other = pathname.isDirectory();
		// ^ 배타적 or 연산자 : 둘의 값이 다를 때 true
		if(mine ^ other) {
		    return mine? -1 : 1; 
		}else {
			return super.compareTo(pathname); 
		}
	}
	
	
}
