package com.almond.api.scrap.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.almond.api.scrap.domain.Scrap;

public interface ScrapMapper {
	public int scrapListTotalCount(@Param(value="nationCode") String nationCode,
								   @Param(value="cityIdx") int[] cityIdx, 
								   @Param(value="categoryIdx") int[] categoryIdx,
								   @Param(value="nickname") String nickname) throws Exception;
	public ArrayList<Scrap> scrapList(@Param(value="nationCode") String nationCode,
									  @Param(value="cityIdx") int[] cityIdx, 
									  @Param(value="categoryIdx") int[] categoryIdx, 
									  @Param(value="limit") int limit, 
									  @Param(value="page") int page, 
									  @Param(value="userIdx") int userIdx,
									  @Param(value="nickname") String nickname) throws Exception;
	public int scrapRegister(Scrap scrap) throws Exception;
	public int getScrapLike(@Param(value="userIdx") int userIdx, 
			 				@Param(value="scrapIdx") int scrapIdx) throws Exception;
	public int getScrapLikeCount(@Param(value="scrapIdx") int scrapIdx) throws Exception;
	public int insertScrapLike(@Param(value="userIdx") int userIdx,
						 	   @Param(value="scrapIdx") int scrapIdx) throws Exception;
	public int scrapDelete(@Param(value="userIdx") int userIdx,
						   @Param(value="scrapIdx") int scrapIdx) throws Exception;
	public int useScrapLike(@Param(value="userIdx") int userIdx,
		 	   				@Param(value="scrapIdx") int scrapIdx) throws Exception;
	public int deleteScrapLike(@Param(value="userIdx") int userIdx,
							   @Param(value="scrapIdx") int scrapIdx) throws Exception;
}
