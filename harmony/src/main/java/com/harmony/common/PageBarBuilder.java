package com.harmony.common;

import java.util.List;
import java.util.Map;

public class PageBarBuilder {
	
	public static String pageBarBuilder(int cPage, int numPerpage, int totalData, int pageBarSize, String requestURI) {
		int totalPage = (int)Math.ceil((double)totalData/numPerpage);
		int pageNo = ((cPage-1)/pageBarSize)*pageBarSize+1;
		int pageEnd = pageNo + pageBarSize -1;
		StringBuilder pageBar = new StringBuilder();
		
		pageBar.append("<ul class='pagination pagination-sm justify-content-center'>");
		if(pageNo==1) {
			pageBar.append("<li class='page-item disabled'><a class='page-link' href='#'>&laquo;</a></li>");
		}else {
			pageBar.append("<li class='page-item'><a class='page-link' href='"+requestURI+"?cPage="+(pageNo-1)+"'>&laquo;</a></li>");
		}
		while(!(pageNo>pageEnd||pageNo>totalPage)) {
			if(pageNo==cPage) {
				pageBar.append("<li class='page-item active'><a class='page-link' href='#'>"+pageNo+"</a></li>");
			}else {
				pageBar.append("<li class='page-item'><a class='page-link' href='"+requestURI+"?cPage="+pageNo+"'>"+pageNo+"</a></li>");
			}
			pageNo++;
		}
		if(pageNo>=totalPage) {
			pageBar.append("<li class='page-item disabled'><a class='page-link' href='#'>&raquo;</a></li>");
		}else {
			pageBar.append("<li class='page-item'><a class='page-link' href='"+requestURI+"?cPage="+(pageNo)+"'>&raquo;</a></li>");
		}
		pageBar.append("</ul>");
		
		return pageBar.toString();
	}
	
	public static String pageBarBuilderWithCategoryTagRegion(int cPage, int numPerpage, int totalData, int pageBarSize, String requestURI, String category, String tag, String region) {
		int totalPage = (int)Math.ceil((double)totalData/numPerpage);
		int pageNo = ((cPage-1)/pageBarSize)*pageBarSize+1;
		int pageEnd = pageNo + pageBarSize -1;
		StringBuilder pageBar = new StringBuilder();

		pageBar.append("<ul class='pagination pagination-sm justify-content-center'>");
		if(pageNo==1) {
			pageBar.append("<li class='page-item disabled'><a class='page-link' href='#'>&laquo;</a></li>");
		}else {
			pageBar.append("<li class='page-item'><a class='page-link' href='"+requestURI+"?cPage="+(pageNo-1)+"&category="+category+"&tag="+tag+"&region="+region+"'>&laquo;</a></li>");
		}
		while(!(pageNo>pageEnd||pageNo>totalPage)) {
			if(pageNo==cPage) {
				pageBar.append("<li class='page-item active'><a class='page-link' href='#'>"+pageNo+"</a></li>");
			}else {
				pageBar.append("<li class='page-item'><a class='page-link' href='"+requestURI+"?cPage="+pageNo+"&category="+category+"&tag="+tag+"&region="+region+"'>"+pageNo+"</a></li>");
			}
			pageNo++;
		}
		if(pageNo>=totalPage) {
			pageBar.append("<li class='page-item disabled'><a class='page-link' href='#'>&raquo;</a></li>");
		}else {
			pageBar.append("<li class='page-item'><a class='page-link' href='"+requestURI+"?cPage="+(pageNo)+"&category="+category+"&tag="+tag+"&region="+region+"'>&raquo;</a></li>");
		}
		pageBar.append("</ul>");
		
		return pageBar.toString();
	}
	
	public static String pageBarBuilder(int cPage, int numPerpage, int totalData, int pageBarSize, String requestURI, List<Map<String,String>> filters) {
		int totalPage = (int)Math.ceil((double)totalData/numPerpage);
		int pageNo = ((cPage-1)/pageBarSize)*pageBarSize+1;
		int pageEnd = pageNo + pageBarSize -1;
		StringBuilder pageBar = new StringBuilder();
		StringBuilder filterList = new StringBuilder();
		if(filters!=null && filters.size()>0) {
			for(Map<String,String> filter:filters) {
				if(!filter.isEmpty()) {
					for(Map.Entry<String,String> f : filter.entrySet()) {
						if(f.getKey()!=null && f.getValue()!=null) {
							filterList.append("&"+f.getKey()+"="+f.getValue());
						}
					}
				}
			}
		}
		pageBar.append("<ul class='pagination pagination-sm justify-content-center'>");
		if(pageNo==1) {
			pageBar.append("<li class='page-item disabled'><a class='page-link' href='#'>&laquo;</a></li>");
		}else {
			pageBar.append("<li class='page-item'><a class='page-link' href='"+requestURI+"?cPage="+(pageNo-1));
			pageBar.append(filterList.toString());
			pageBar.append("'>&laquo;</a></li>");
		}
		while(!(pageNo>pageEnd||pageNo>totalPage)) {
			if(pageNo==cPage) {
				pageBar.append("<li class='page-item active'><a class='page-link' href='#'>"+pageNo+"</a></li>");
			}else {
				pageBar.append("<li class='page-item'><a class='page-link' href='"+requestURI+"?cPage="+pageNo);
				pageBar.append(filterList.toString());
				pageBar.append("'>"+pageNo+"</a></li>");
			}
			pageNo++;
		}
		if(pageNo>=totalPage) {
			pageBar.append("<li class='page-item disabled'><a class='page-link' href='#'>&raquo;</a></li>");
		}else {
			pageBar.append("<li class='page-item'><a class='page-link' href='"+requestURI+"?cPage="+(pageNo));
			pageBar.append(filterList.toString());
			pageBar.append("'>&raquo;</a></li>");
		}
		pageBar.append("</ul>");
		
		return pageBar.toString();
	}
	
	public static String pageBarBuilderForSearch(int cPage, int numPerpage, int totalData, int pageBarSize, String requestURI, String searchType, String query) {
	    int totalPage = (int)Math.ceil((double)totalData/numPerpage);
	    int pageNo = ((cPage-1)/pageBarSize)*pageBarSize+1;
	    int pageEnd = pageNo + pageBarSize -1;
	    StringBuilder pageBar = new StringBuilder();

	    pageBar.append("<ul class='pagination pagination-sm justify-content-center'>");
	    if(pageNo==1) {
	        pageBar.append("<li class='page-item disabled'><a class='page-link' href='#'>&laquo;</a></li>");
	    } else {
	        pageBar.append("<li class='page-item'><a class='page-link' href='"+requestURI+"?cPage="+(pageNo-1)+"&searchType="+searchType+"&query="+query+"'>&laquo;</a></li>");
	    }

	    while(!(pageNo>pageEnd||pageNo>totalPage)) {
	        if(pageNo==cPage) {
	            pageBar.append("<li class='page-item active'><a class='page-link' href='#'>"+pageNo+"</a></li>");
	        } else {
	            pageBar.append("<li class='page-item'><a class='page-link' href='"+requestURI+"?cPage="+pageNo+"&searchType="+searchType+"&query="+query+"'>"+pageNo+"</a></li>");
	        }
	        pageNo++;
	    }

	    if(pageNo>=totalPage) {
	        pageBar.append("<li class='page-item disabled'><a class='page-link' href='#'>&raquo;</a></li>");
	    } else {
	        pageBar.append("<li class='page-item'><a class='page-link' href='"+requestURI+"?cPage="+(pageNo)+"&searchType="+searchType+"&query="+query+"'>&raquo;</a></li>");
	    }
	    pageBar.append("</ul>");

	    return pageBar.toString();
	}
	
	public static String pageBarBuilderWithSortOption(int cPage, int numPerpage, int totalData, int pageBarSize, String requestURI, String sortOption) {
	    int totalPage = (int)Math.ceil((double)totalData/numPerpage);
	    int pageNo = ((cPage-1)/pageBarSize)*pageBarSize+1;
	    int pageEnd = pageNo + pageBarSize -1;
	    StringBuilder pageBar = new StringBuilder();

	    pageBar.append("<ul class='pagination pagination-sm justify-content-center'>");
	    if(pageNo==1) {
	        pageBar.append("<li class='page-item disabled'><a class='page-link' href='#'>&laquo;</a></li>");
	    } else {
	        pageBar.append("<li class='page-item'><a class='page-link' href='"+requestURI+"?cPage="+(pageNo-1)+"&sort="+sortOption+"'>&laquo;</a></li>");
	    }

	    while(!(pageNo>pageEnd||pageNo>totalPage)) {
	        if(pageNo==cPage) {
	            pageBar.append("<li class='page-item active'><a class='page-link' href='#'>"+pageNo+"</a></li>");
	        } else {
	            pageBar.append("<li class='page-item'><a class='page-link' href='"+requestURI+"?cPage="+pageNo+"&sort="+sortOption+"'>"+pageNo+"</a></li>");
	        }
	        pageNo++;
	    }

	    if(pageNo>=totalPage) {
	        pageBar.append("<li class='page-item disabled'><a class='page-link' href='#'>&raquo;</a></li>");
	    } else {
	        pageBar.append("<li class='page-item'><a class='page-link' href='"+requestURI+"?cPage="+(pageNo)+"&sort="+sortOption+"'>&raquo;</a></li>");
	    }
	    pageBar.append("</ul>");

	    return pageBar.toString();
	}


	
}