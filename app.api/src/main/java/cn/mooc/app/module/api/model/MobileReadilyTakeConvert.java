package cn.mooc.app.module.api.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.data.entity.SysUserExt;
import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.data.entity.InfoImage;
import cn.mooc.app.module.cms.model.MobileModelConvert;
import cn.mooc.app.module.cms.util.ShowUserNameUtil;

public class MobileReadilyTakeConvert {

	public static MobileReadilyTake convertInfo(Info info, SysUserEntity creator, SysUserExt creatorExt, String siteUrl){
		MobileReadilyTake take = new MobileReadilyTake();
		Map<String, String> customs = info.getCustomers();
		if(customs != null){
			take.setAddress(customs.get("address"));
			take.setLongitude(customs.get("longitude"));
			take.setLatitude(customs.get("latitude"));
			take.setShwoPhotoUrl(customs.get("shwoPhotoUrl"));
		}
		take.setId(info.getId());
		take.setTitle(info.getTitle());
		take.setMetaDescription(info.getMetaDescription());
		List<InfoImage> images = info.getImages(); 
		List<String> yimages = new ArrayList<String>();
		if(images != null && images.size() > 0){
			for(InfoImage image : images){
				yimages.add(image.getImage() == null ? "" : MobileModelConvert.fullLinkPrefix(image.getImage(), siteUrl));
			}
		}
		take.setImages(yimages);
		
		take.setDiggs(info.getDiggs() == null ? 0 : info.getDiggs());
		take.setComments(info.getComments() == null ? 0 : info.getComments());
		
		take.setPhoto(creatorExt.getAvatarUrl() == null ? "" : MobileModelConvert.fullLinkPrefix(creatorExt.getAvatarUrl(), siteUrl));
		take.setUserId(creator.getIntId());
		
		String userName = ShowUserNameUtil.getMUserName(creator.getUserName(), creatorExt.getNickName(), creatorExt.getRealName());
		take.setUsername(userName);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		take.setCreateTime(sdf.format(info.getPublishDate()));
		return take;
	}
}
