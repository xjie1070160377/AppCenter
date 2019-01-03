package cn.mooc.app.module.api.model;

import java.util.ArrayList;
import java.util.List;

import cn.mooc.app.module.cms.data.entity.Special;
import cn.mooc.app.module.cms.model.MobileModelConvert;
import cn.mooc.app.module.points.data.entity.CurrencyRule;
import cn.mooc.app.module.points.data.entity.CurrencySpecial;
import cn.mooc.app.module.points.data.entity.PointsInfo;
import cn.mooc.app.module.points.data.entity.PointsRule;
import cn.mooc.app.module.points.service.CurrencySpecialService;
import cn.mooc.app.module.points.service.PointsInfoService;


public class MobilePointsConvert {
	
	public static MobilePointsRule convert(PointsRule rule, PointsInfoService infoService){
		MobilePointsRule mrule = new MobilePointsRule();
		mrule.setCaption(rule.getCaption());
		mrule.setIsas(rule.getIsas());
		mrule.setIsspecial(rule.getIsspecial());
		mrule.setMaxPoints(rule.getMaxPoints());
		mrule.setPoints(rule.getPoints());
		mrule.setRuleName(rule.getRuleName());
		if(rule.getIsspecial()!=null && rule.getIsspecial()==1){
			List<PointsInfo> ainfos = infoService.findListByRule(rule.getId());
			List<MobileRuleInfo> pinfos = new ArrayList<MobileRuleInfo>();
			for(PointsInfo i : ainfos){
				MobileRuleInfo minfo = new MobileRuleInfo();
				minfo.setHascomment(i.getHascomments());
				minfo.setHaslike(i.getHaslike());
				minfo.setInfoId(i.getInfo().getId());
				minfo.setTitle(i.getInfo().getTitle());
				
				String siteUrl = i.getInfo().getSite().getSiteUrl();
				minfo.setImage(MobileModelConvert.fullLinkPrefix(i.getInfo().getSmallImageUrl(), siteUrl));
				minfo.setDescription(i.getInfo().getDescription() == null ? "" : i.getInfo().getDescription());
				minfo.setFile(i.getInfo().getFile()==null?"":MobileModelConvert.fullLinkPrefix(i.getInfo().getFile(), siteUrl));
				List<Special> specials = i.getInfo().getSpecials();
				if(specials != null  && specials.size() > 0){
					minfo.setSpecialId(specials.get(0).getId()+"");
					minfo.setIsSpecial(1);
				}
				if (i.getInfo().getImages() != null && !i.getInfo().getImages().isEmpty()) {
					minfo.setHasImages(1);
				}
				if (i.getInfo().getWithVideo() != null && i.getInfo().getWithVideo()) {
					minfo.setHasVideo(1);
				}
				minfo.setVideo(i.getInfo().getVideo() == null ? "" : MobileModelConvert.fullLinkPrefix(i.getInfo().getVideo(), siteUrl));
				
				
				pinfos.add(minfo);
			}
			mrule.setInfos(pinfos);
		}
		return mrule;
	}
	
	public static MobileCurrencyRule convert(CurrencyRule rule, CurrencySpecialService infoService){
		MobileCurrencyRule mrule = new MobileCurrencyRule();
		mrule.setCaption(rule.getCaption());
		mrule.setIsspecial(rule.getIsspecial());
		mrule.setRuleName(rule.getRuleName());
		mrule.setTotal(rule.getTotal());
		mrule.setMaxTotal(rule.getMaxTotal());
		if(rule.getIsspecial()!=null && rule.getIsspecial()==1){
			List<CurrencySpecial> ainfos = infoService.findByRuleId(rule.getId());
			List<MobileCurrencyInfo> pinfos = new ArrayList<MobileCurrencyInfo>();
			for(CurrencySpecial i : ainfos){
				MobileCurrencyInfo minfo = new MobileCurrencyInfo();
				minfo.setInfoId(i.getInfo().getId());
				minfo.setTitle(i.getInfo().getTitle());
				
				String siteUrl = i.getInfo().getSite().getSiteUrl();
				minfo.setImage(MobileModelConvert.fullLinkPrefix(i.getInfo().getSmallImageUrl(), siteUrl));
				minfo.setDescription(i.getInfo().getDescription() == null ? "" : i.getInfo().getDescription());
				minfo.setFile(i.getInfo().getFile()==null?"":MobileModelConvert.fullLinkPrefix(i.getInfo().getFile(), siteUrl));
				List<Special> specials = i.getInfo().getSpecials();
				if(specials != null  && specials.size() > 0){
					minfo.setSpecialId(specials.get(0).getId()+"");
					minfo.setIsSpecial(1);
				}
				if (i.getInfo().getImages() != null && !i.getInfo().getImages().isEmpty()) {
					minfo.setHasImages(1);
				}
				if (i.getInfo().getWithVideo() != null && i.getInfo().getWithVideo()) {
					minfo.setHasVideo(1);
				}
				minfo.setVideo(i.getInfo().getVideo() == null ? "" : MobileModelConvert.fullLinkPrefix(i.getInfo().getVideo(), siteUrl));
				
				
				pinfos.add(minfo);
			}
			mrule.setInfos(pinfos);
		}
		return mrule;
	}
}
