package cn.mooc.app.module.cms.model;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.context.SysContext;
import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.data.entity.SysRoleEntity;
import cn.mooc.app.core.model.SysUserInfo;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.utils.MessageFormatUtil;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.module.ad.data.entity.Ad;
import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.data.entity.InfoCollect;
import cn.mooc.app.module.cms.data.entity.InfoDetail;
import cn.mooc.app.module.cms.data.entity.InfoImage;
import cn.mooc.app.module.cms.data.entity.Node;
import cn.mooc.app.module.cms.data.entity.Special;
import cn.mooc.app.module.cms.data.entity.WorkflowStep;
import cn.mooc.app.module.cms.data.entity.WorkflowStepRole;
import cn.mooc.app.module.cms.service.InfoService;
import cn.mooc.app.module.cms.service.WorkflowStepRoleService;
import cn.mooc.app.module.cms.util.ShowUserNameUtil;
import cn.mooc.app.module.interact.data.entity.InteractComment;
import cn.mooc.app.module.interact.data.entity.InteractMark;
import cn.mooc.app.module.interact.enums.CommentType;
import cn.mooc.app.module.interact.service.InteractCommentService;

@Service
public class MobileModelConvert {

	private final static String nodeNumber = "readilyTake";

	private static boolean useRtmp = false;

	private static String audio_rtmp_url = "rtmp://mapi.nudt.edu.cn:8002/vod/";

	private static String info_share_url = "https://mapp.nudt.edu.cn/widget/share/detail/{}";
	//private static String info_share_url = "http://172.20.201.103/widget/share/detail/{}";
	private static boolean useCommonCss = true;

	@Autowired
	private SysContext sysContext;

	// private static String getUserNickNameOrUserName(SysUserEntity user,
	// SysUserExt userExt){
	// String userName = null;
	// if(userExt != null){
	// userName = userExt.getNickName() == null ? userExt.getRealName() :
	// userExt.getNickName();
	// }
	// if(userName == null){
	// userName = user.getUserName();
	// }
	// return userName;
	// }
	public static MobileArticleInfo convertInfo(Info info, boolean addContent, boolean isList) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		if (info == null)
			return null;

		String siteUrl = info.getSite().getSiteUrl();
		InfoDetail infoDetail = info.getDetail();
		Node node = info.getNode();

		MobileArticleInfo articleInfo = new MobileArticleInfo();
		articleInfo.setArticleId(info.getId());
		articleInfo.setTitle(infoDetail.getTitle());
		articleInfo.setListTitle(info.getFullTitle() == null ? infoDetail.getTitle() : info.getFullTitle());
		articleInfo.setSubtitle(info.getSubtitle() == null ? "" : info.getSubtitle());
		Map<String, String> customs = info.getCustomers();
		articleInfo.setFullTitle(customs.get("yt") == null ? "" : customs.get("yt"));
		SysUserInfo sysUserInfo = modelConvert.webContext.getSysUserFullInfo(info.getCreatorId());
		// SysUserEntity sysUserEntity =
		// modelConvert.sysDataService.getUserInfoById(info.getCreatorId());
		// SysUserExt sysUserExt =
		// modelConvert.sysDataService.getSysUserExt(info.getCreatorId());
		// String authorName = getUserNickNameOrUserName(sysUserEntity, sysUserExt);
		String authorName = sysUserInfo.getUName();

		String auditName = customs.get("auditUserName");
		// auditName =
		// StringUtil.isNotEmpty(auditName)?auditName:(StringUtil.isNotEmpty(infoDetail.getAuthor())?infoDetail.getAuthor():authorName);
		auditName = StringUtil.isNotEmpty(auditName) ? auditName : authorName;

		if (StringUtil.isNotEmpty(infoDetail.getAuthor())) {
			articleInfo.setAuthor(infoDetail.getAuthor());
		} else {
			articleInfo.setAuthor(authorName);
		}

		articleInfo.setCreator(auditName);
		articleInfo.setAuthorId(info.getCreatorId());
		// articleInfo.setAuthorPhoto(sysUserExt == null || sysUserExt.getAvatarUrl() ==
		// null ? "" : fullLinkPrefix(sysUserExt.getAvatarUrl(), siteUrl));
		articleInfo.setAuthorPhoto(sysUserInfo.getAvatarUrlFull(siteUrl));
		articleInfo.setCateId(node.getId());
		articleInfo.setCategroy(node.getName());
		articleInfo.setDescription(info.getDescription() == null ? "" : info.getDescription());
		if (addContent) {
			String contentHtml = info.getText() == null ? "" : fullContentPrefix(info.getText(), siteUrl);
			if (useCommonCss) {
				// 为了很好的控制手机端展现效果，引入统一的样式文件
				contentHtml = "<link rel='stylesheet' type='text/css' href='https://mapp.nudt.edu.cn/static/base/css/appCommon.css'/>"
						+ contentHtml;
			}

			articleInfo.setContent(contentHtml);
		} else {
			articleInfo.setContent("");
		}

		if (useRtmp) {
			// 视频统一改为rtmp方式播放
			articleInfo.setVideo(info.getVideo() == null ? "" : fullLinkPrefix(info.getVideo(), audio_rtmp_url));
		} else {
			articleInfo.setVideo(info.getVideo() == null ? "" : fullLinkPrefix(info.getVideo(), siteUrl));
		}

		articleInfo.setVideoName(info.getVideoName() == null ? "" : info.getVideoName());
		articleInfo.setVideoSize(info.getVideoSize() == null ? "" : info.getVideoSize());
		articleInfo.setVideoTime(info.getVideoTime() == null ? "" : info.getVideoTime());

		articleInfo.setAudio(info.getAudio() == null ? "" : fullLinkPrefix(info.getAudio(), siteUrl));
		articleInfo.setAudioName(info.getAudioName() == null ? "" : info.getAudioName());
		articleInfo.setAudioSize(info.getAudioSize() == null ? "" : info.getAudioSize());
		articleInfo.setAudioTime(info.getAudioTime() == null ? "" : info.getAudioTime());

		articleInfo.setDetailUrl(info.getLink());
		// articleInfo.setDetailUrl(StringUtils.isNotBlank(info.getLink()) ?
		// fullLinkPrefix(info.getLink(), siteUrl) : siteUrl + "/info/" + info.getId()
		// + ".htx");
		articleInfo.setDates(info.getPublishDate() != null ? sdf.format(info.getPublishDate()) : "");
		articleInfo.setImage(fullLinkPrefix(info.getSmallImageUrl(), siteUrl));
		articleInfo.setBrowsers(info.getViews() == null ? 0 : info.getViews());
		articleInfo.setDiggs(info.getDiggs() == null ? 0 : info.getDiggs());
		List<InteractComment> ls = modelConvert.commentService.findBySFid(info.getId(), InteractComment.AUDITED);
		articleInfo.setComments(ls.size());
		if (isList) {
			if (info.getSourceFlag().equals(InfoDetail.show_flag)) {
				articleInfo.setSource(info.getSource() == null ? "" : info.getSource());
				articleInfo.setSourceUrl(info.getSourceUrl() == null ? "" : info.getSourceUrl());
				articleInfo.setSourceImage(
						info.getInfoSource() == null ? "" : fullLinkPrefix(info.getInfoSource().getImage(), siteUrl));
			} else {
				articleInfo.setSource("");
				articleInfo.setSourceUrl("");
				articleInfo.setSourceImage("");
			}
		} else {
			articleInfo.setSource(info.getSource() == null ? "" : info.getSource());
			articleInfo.setSourceUrl(info.getSourceUrl() == null ? "" : info.getSourceUrl());
			articleInfo.setSourceImage(
					info.getInfoSource() == null ? "" : fullLinkPrefix(info.getInfoSource().getImage(), siteUrl));
		}
		articleInfo.setShowType((info.getP4() == null || info.getP4().intValue() == 0) ? 1 : info.getP4());

		articleInfo.setEditionUnit(customs.get("editionUnit") == null ? "" : customs.get("editionUnit"));

		if (info.getImages() != null && !info.getImages().isEmpty()) {
			for (InfoImage infoImage : info.getImages()) {
				infoImage.setImage(fullLinkPrefix(infoImage.getImage(), siteUrl));
			}
			articleInfo.setImages(info.getImages());
			articleInfo.setHasImages(1);
		}
		if (info.getWithVideo() != null && info.getWithVideo()) {
			articleInfo.setHasVideo(1);
		}
		if (info.getWithAudio() != null && info.getWithAudio()) {
			articleInfo.setHasAudio(1);
		}
		List<Special> specials = info.getSpecials();
		if (specials != null && specials.size() > 0) {
			articleInfo.setSpecialId(specials.get(0).getId() + "");
			articleInfo.setIsSpecial(1);
			articleInfo.setSpecialName(specials.get(0).getTitle());
			articleInfo.setSpecialImage(fullLinkPrefix(specials.get(0).getSmallImage(), siteUrl));
		}

		String fileUrl = info.getFile();
		articleInfo.setFile(fileUrl == null ? "" : fullLinkPrefix(fileUrl, siteUrl));
		articleInfo.setFileName(info.getFileName());
		articleInfo.setFileLength(info.getFileLength());
		if (StringUtil.isNotEmpty(fileUrl)) {
			String end = FilenameUtils.getExtension(fileUrl).toLowerCase();
			articleInfo.setReaderSuffix(end);
			if (end.equals("txt") || end.equals("epub")) {
				articleInfo.setIsReader(1);
			}
		}

		articleInfo.setInfoLevel((info.getP6() != null && info.getP6().intValue() > 1) ? info.getP6() : 1);

		if (articleInfo.getInfoLevel() != null && articleInfo.getInfoLevel().equals(3)) {
			String shareUrl = MessageFormatUtil.format(info_share_url, info.getId());
			articleInfo.setShareUrl(shareUrl);
		}

		return articleInfo;
	}

	public static Map<String, Object> convertCollectInfo(InteractMark infoCollect, Info info) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("collectId", infoCollect.getMarkId());
		map.put("articleId", infoCollect.getFid());
		map.put("title", infoCollect.getFtitle());
		map.put("collectDate", sdf.format(infoCollect.getCreateTime()));
		String siteUrl = info.getSite().getSiteUrl();

		map.put("image", fullLinkPrefix(info.getSmallImageUrl(), siteUrl));
		map.put("description", info.getDescription() == null ? "" : info.getDescription());
		List<Special> specials = info.getSpecials();
		if (specials != null && specials.size() > 0) {
			map.put("isSpecial", 1);
			map.put("specialId", specials.get(0).getId() + "");
		}
		if (info.getImages() != null && !info.getImages().isEmpty()) {
			map.put("hasImages", 1);
		}
		if (info.getWithVideo() != null && info.getWithVideo()) {
			map.put("hasVideo", 1);
		}
		map.put("video", info.getVideo() == null ? "" : fullLinkPrefix(info.getVideo(), siteUrl));
		map.put("comments", info.getComments() == null ? 0 : info.getComments());
		map.put("diggs", info.getDiggs() == null ? 0 : info.getDiggs());
		map.put("showType", (info.getP4() == null || info.getP4().intValue() == 0) ? 1 : info.getP4());
		map.put("browsers", info.getViews() == null ? 0 : info.getViews());
		map.put("file", info.getFile() == null ? "" : fullLinkPrefix(info.getFile(), siteUrl));

		return map;
	}

	public static Map<String, Object> convertCollectInfo(InfoCollect infoCollect, Info info) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("collectId", infoCollect.getId());
		map.put("articleId", infoCollect.getInfoId());
		map.put("title", infoCollect.getTitle());
		map.put("collectDate", sdf.format(infoCollect.getCollectDate()));
		String siteUrl = info.getSite().getSiteUrl();

		map.put("image", fullLinkPrefix(info.getSmallImageUrl(), siteUrl));
		map.put("description", info.getDescription() == null ? "" : info.getDescription());
		List<Special> specials = info.getSpecials();
		if (specials != null && specials.size() > 0) {
			map.put("isSpecial", 1);
			map.put("specialId", specials.get(0).getId() + "");
		}
		if (info.getImages() != null && !info.getImages().isEmpty()) {
			map.put("hasImages", 1);
		}
		if (info.getWithVideo() != null && info.getWithVideo()) {
			map.put("hasVideo", 1);
		}
		map.put("video", info.getVideo() == null ? "" : fullLinkPrefix(info.getVideo(), siteUrl));
		map.put("comments", info.getComments() == null ? 0 : info.getComments());
		map.put("diggs", info.getDiggs() == null ? 0 : info.getDiggs());
		map.put("showType", (info.getP4() == null || info.getP4().intValue() == 0) ? 1 : info.getP4());
		map.put("browsers", info.getViews() == null ? 0 : info.getViews());
		map.put("file", info.getFile() == null ? "" : fullLinkPrefix(info.getFile(), siteUrl));

		return map;
	}

	public static MobileCategoryInfo convertCategory(Node node) {
		if (node == null)
			return null;

		String urlFull = node.getSite().getSiteUrl();

		MobileCategoryInfo cateInfo = new MobileCategoryInfo();
		cateInfo.setCateId(node.getId());
		cateInfo.setCateName(node.getName());
		cateInfo.setNumber(node.getNumber() == null ? "" : node.getNumber());
		cateInfo.setDescription(node.getDescription() == null ? "" : node.getDescription());
		cateInfo.setSmallImage(fullLinkPrefix(node.getSmallImageUrl(), urlFull));
		cateInfo.setArticles(node.getRefers() == null ? 0 : node.getRefers());
		// cateInfo.setAttentions(node.getAttentions() == null ? 0 :
		// node.getAttentions());
		cateInfo.setTreeLevel(node.getTreeLevel());
		cateInfo.setTreeNumber(node.getTreeNumber());
		cateInfo.setTerminated((node.getChildren() == null || node.getChildren().isEmpty()) ? 1 : 0);
		if (node.getP2() != null && node.getP2() == 1) {
			cateInfo.setIsSpecial(1);
		}
		if (node.getP4() != null && node.getP4() == 1) {
			cateInfo.setTwoColumn(1);
		}
		cateInfo.setTypeId(node.getShow_type_id());

		if (node.getIsShowTitle() != null && node.getIsShowTitle() != 0) {
			cateInfo.setShowTitle(1);
		} else {
			cateInfo.setShowTitle(0);
		}
		cateInfo.setNodeModelId(node.getModel().getId());//栏目类型

		Map<String, String> map = node.getClobs();
		if(!StringUtil.isNotNull(map.get("showChildNode"))) {
			cateInfo.setShowChildNode("0");
		}else {
			cateInfo.setShowChildNode(map.get("showChildNode"));
		}
		cateInfo.setLinkType(map.get("linkType")==null?"":map.get("linkType"));
		cateInfo.setDetailUrl(StringUtils.isNotBlank(node.getLink()) ? node.getLink(): "");

		return cateInfo;
	}

	public static MobileUserInfo convertUser(SysUserInfo user, String siteUrl) {
		MobileUserInfo userInfo = new MobileUserInfo();
		userInfo.setUserId(user.getUserIdInt());
		userInfo.setUserName(user.getUserName());
		userInfo.setRealName(user.getRealName());
		userInfo.setNickname(user.getNickName());
		userInfo.setMobile(user.getPhone());
		userInfo.setEmail(user.getEmail());
		userInfo.setGender(user.getGenderName());
		userInfo.setBirthday("");
		userInfo.setPhoto(user.getAvatarUrlFull(siteUrl));
		userInfo.setUserType(user.getuType());
		userInfo.setBankCard(user.getBankCard());
		String url = user.getAvatarUrl();
		if (StringUtil.isNotEmpty(url) && url.lastIndexOf("_min.") > 0) {
			url = url.substring(0, url.indexOf("_min")) + url.substring(url.lastIndexOf("."));
			userInfo.setOrigphoto(fullLinkPrefix(url, siteUrl));
		} else {
			userInfo.setOrigphoto("");
		}
		return userInfo;
	}
	// public static MobileUserInfo convertUser(SysUserEntity sysUser, SysUserExt
	// sysUserExt, String siteUrl) {
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	// if (sysUser == null)
	// return null;
	// MobileUserInfo userInfo = new MobileUserInfo();
	// userInfo.setUserId((int)sysUser.getId());
	// userInfo.setUserName(sysUser.getUserName());
	//
	// if(sysUserExt!=null){
	// userInfo.setRealName(StringUtils.isNotBlank(sysUserExt.getRealName()) ?
	// sysUserExt.getRealName() : "");
	// userInfo.setNickname(StringUtils.isNotBlank(sysUserExt.getNickName()) ?
	// sysUserExt.getNickName() : "");
	// userInfo.setMobile(StringUtils.isNotBlank(sysUserExt.getPhone()) ?
	// sysUserExt.getPhone() : "");
	// userInfo.setEmail(StringUtils.isNotBlank(sysUserExt.getEmail()) ?
	// sysUserExt.getEmail() : "");
	// userInfo.setGender(sysUserExt.getGender() == 0 ? "" :
	// sysUserExt.getGender()==1 ? "男" : sysUserExt.getGender()==2 ? "女" : "");
	// userInfo.setBirthday("");
	// userInfo.setPhoto(sysUserExt.getAvatarUrl() == null ? "" :
	// fullLinkPrefix(sysUserExt.getAvatarUrl(), siteUrl));
	//
	// }
	//
	// return userInfo;
	// }

	// public static MobileUserInfo convertUser(SysUserEntity sysUser, String
	// siteUrl) {
	// if (sysUser == null)
	// return null;
	// SysUserExt sysUserExt =
	// modelConvert.sysDataService.getSysUserExt(sysUser.getId());
	// MobileUserInfo userInfo = new MobileUserInfo();
	// userInfo.setUserId((int)sysUser.getId());
	// userInfo.setUserName(sysUser.getUserName());
	// if(sysUserExt != null){
	// userInfo.setRealName(sysUserExt.getRealName());
	// userInfo.setNickname(sysUserExt.getNickName());
	// userInfo.setMobile(sysUserExt.getPhone());
	// userInfo.setEmail(sysUserExt.getEmail());
	// userInfo.setGender(sysUserExt.getGenderStr());
	// userInfo.setPhoto(sysUserExt.getAvatarUrl());
	// }
	// return userInfo;
	// }

	public void converComment(List<MobileCommentInfo> comments, InteractComment comment, String siteUrl) {
		converComment(comments, comment, siteUrl, "");
	}

	private void converComment(List<MobileCommentInfo> comments, InteractComment comment, String siteUrl,
							   String fromUser) {
		MobileCommentInfo commentInfo = convertComment(comment, siteUrl, fromUser);
		comments.add(commentInfo);
		List<InteractComment> list = modelConvert.commentService.findList(CommentType.Comment.toString(),
				comment.getCommentId());
		if (list != null && list.size() > 0) {
			for (InteractComment ct1 : list) {
				converComment(comments, ct1, siteUrl, commentInfo.getCreator());
			}
		}
	}

	private MobileCommentInfo convertComment(InteractComment comment, String siteUrl, String fromUser) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		if (comment == null)
			return null;
		MobileCommentInfo commentInfo = new MobileCommentInfo();
		commentInfo.setCommentId(comment.getCommentId());

		String content = sysContext.filterForbbidenContent(comment.getContent());
		commentInfo.setText(content);

		commentInfo.setFid(comment.getFid());
		commentInfo.setFtype(comment.getFtype().toString());
		commentInfo.setCreatorid(comment.getUserId());
		commentInfo.setFromUser(fromUser);
		SysUserInfo sysUserInfo = modelConvert.webContext.getSysUserFullInfo(comment.getUserId());
		String userName = ShowUserNameUtil.getMUserName(comment.getUserName(), sysUserInfo.getNickName(),
				sysUserInfo.getRealName());
		commentInfo.setCreator(userName);
		commentInfo.setCreatorPhoto(sysUserInfo.getAvatarUrlFull(siteUrl));
		commentInfo.setCreationDate(comment.getCreateTime() != null ? sdf.format(comment.getCreateTime()) : "");

		return commentInfo;
	}

	public void converUserComment(List<MobileCommentInfo> comments, InteractComment comment, String siteUrl) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		if (comment == null)
			return;
		MobileCommentInfo commentInfo = new MobileCommentInfo();
		commentInfo.setCommentId(comment.getCommentId());

		String content = sysContext.filterForbbidenContent(comment.getContent());
		commentInfo.setText(content);

		commentInfo.setFid(comment.getFid());
		commentInfo.setFtype(comment.getFtype().toString());

		SysUserInfo sysUserInfo = modelConvert.webContext.getSysUserFullInfo(comment.getUserId());

		String userName = ShowUserNameUtil.getMUserName(comment.getUserName(), sysUserInfo.getNickName(),
				sysUserInfo.getRealName());
		commentInfo.setCreator(userName);
		commentInfo.setCreatorid(comment.getUserId());
		commentInfo.setCreatorPhoto(sysUserInfo.getAvatarUrlFull(siteUrl));
		commentInfo.setCreationDate(comment.getCreateTime() != null ? sdf.format(comment.getCreateTime()) : "");

		commentInfo.setTitle(comment.getInfoTitle());

		commentInfo.setInfoId(comment.getFid());

		if (comment.getFtype().equals(CommentType.Comment)) {
			InteractComment ct = modelConvert.commentService.getInteractCommentByCommentId(comment.getSourceId());
			commentInfo.setFromUser(ct.getUserName());
		}
		Integer infoId = comment.getFid();
		if (infoId != null) {
			Info info = modelConvert.infoService.getInfoById(infoId);
			if (info != null) {

				String nodeCode = info.getNode().getNumber();
				commentInfo.setNodeCode(nodeCode);
				if (nodeNumber.equals(nodeCode)) {
					if (info.getImages() != null && !info.getImages().isEmpty()) {
						String imageurl = fullLinkPrefix(info.getImages().get(0).getImage(), siteUrl);
						if (StringUtil.isNotEmpty(imageurl) && imageurl.lastIndexOf(".") > 0) {
							imageurl = imageurl.substring(0, imageurl.lastIndexOf(".")) + "_min"
									+ imageurl.substring(imageurl.lastIndexOf("."));
						}
						commentInfo.setImage(imageurl);
					}
				} else {
					commentInfo.setImage(fullLinkPrefix(info.getSmallImageUrl(), siteUrl));
				}

				commentInfo.setVideo(info.getVideo() == null ? "" : fullLinkPrefix(info.getVideo(), siteUrl));

				if (info.getImages() != null && !info.getImages().isEmpty()) {
					commentInfo.setHasImages(1);
				}

				if (info.getWithVideo() != null && info.getWithVideo()) {
					commentInfo.setHasVideo(1);
				}
				List<Special> specials = info.getSpecials();
				if (specials != null && specials.size() > 0) {
					commentInfo.setIsSpecial(1);
				}

				commentInfo.setFile(info.getFile() == null ? "" : fullLinkPrefix(info.getFile(), siteUrl));
				commentInfo.setDescription(info.getDescription() == null ? "" : info.getDescription());

				if (StringUtil.isNull(commentInfo.getTitle())) {
					commentInfo.setTitle(info.getTitle());
				}
			}
		}
		comments.add(commentInfo);
	}

	public static void converEvalUserComment(List<MobileCommentInfo> comments, InteractComment comment,
											 String siteUrl) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		if (comment == null)
			return;
		MobileCommentInfo commentInfo = new MobileCommentInfo();
		commentInfo.setCommentId(comment.getCommentId());
		commentInfo.setText(comment.getContent());
		commentInfo.setFid(comment.getFid());
		commentInfo.setFtype(comment.getFtype().toString());
		SysUserInfo sysUserInfo = modelConvert.webContext.getSysUserFullInfo(comment.getUserId());

		String userName = ShowUserNameUtil.getMUserName(comment.getUserName(), sysUserInfo.getNickName(),
				sysUserInfo.getRealName());
		commentInfo.setCreator(userName);
		commentInfo.setCreatorid(comment.getUserId());
		commentInfo.setCreatorPhoto(sysUserInfo.getAvatarUrlFull(siteUrl));
		commentInfo.setCreationDate(comment.getCreateTime() != null ? sdf.format(comment.getCreateTime()) : "");

		commentInfo.setTitle(comment.getInfoTitle());
		commentInfo.setInfoId(comment.getFid());

		if (comment.getFtype().equals(CommentType.Comment)) {
			InteractComment ct = modelConvert.commentService.getInteractCommentByCommentId(comment.getSourceId());
			// Comment ct = modelConvert.service.get(comment.getFid());
			commentInfo.setFromUser(ct.getUserName());
		}
		Integer infoId = comment.getFid();
		Info info = modelConvert.infoService.getInfoById(infoId);

		String nodeCode = info.getNode().getNumber();
		commentInfo.setNodeCode(nodeCode);
		if (nodeNumber.equals(nodeCode)) {
			if (info.getImages() != null && !info.getImages().isEmpty()) {
				String imageurl = fullLinkPrefix(info.getImages().get(0).getImage(), siteUrl);
				if (StringUtil.isNotEmpty(imageurl) && imageurl.lastIndexOf(".") > 0) {
					imageurl = imageurl.substring(0, imageurl.lastIndexOf(".")) + "_min"
							+ imageurl.substring(imageurl.lastIndexOf("."));
				}
				commentInfo.setImage(imageurl);
			}
		} else {
			commentInfo.setImage(fullLinkPrefix(info.getSmallImageUrl(), siteUrl));
		}

		commentInfo.setVideo(info.getVideo() == null ? "" : fullLinkPrefix(info.getVideo(), siteUrl));

		if (info.getImages() != null && !info.getImages().isEmpty()) {
			commentInfo.setHasImages(1);
		}

		if (info.getWithVideo() != null && info.getWithVideo()) {
			commentInfo.setHasVideo(1);
		}
		List<Special> specials = info.getSpecials();
		if (specials != null && specials.size() > 0) {
			commentInfo.setIsSpecial(1);
		}

		commentInfo.setFile(info.getFile() == null ? "" : fullLinkPrefix(info.getFile(), siteUrl));
		commentInfo.setDescription(info.getDescription() == null ? "" : info.getDescription());
		comments.add(commentInfo);
	}

	public static String fullContentPrefix(String content, String prefix) {
		if (StringUtils.isNotBlank(content)) {
			content = content.replace("\"/upload", "\"" + prefix + "/upload");
		}
		return content;
	}

	public static String fullLinkPrefix(String link, String prefix) {
		if (StringUtils.isNotBlank(link) && link.startsWith("/")) {
			link = prefix + link;
		}
		return link;
	}

	@PostConstruct
	public void init() {
		modelConvert = this;
		modelConvert.sysDataService = this.sysDataService;
		modelConvert.commentService = this.commentService;
		modelConvert.infoService = this.infoService;
		modelConvert.webContext = this.webContext;
		modelConvert.workflowStepRoleService = this.workflowStepRoleService;
	}

	public static MobileModelConvert modelConvert;
	@Autowired
	private SysDataService sysDataService;
	@Autowired
	private InteractCommentService commentService;
	@Autowired
	private InfoService infoService;
	@Autowired
	private WebContext webContext;
	@Autowired
	private WorkflowStepRoleService workflowStepRoleService;

	public WorkflowStepRoleService getWorkflowStepRoleService() {
		return workflowStepRoleService;
	}

	public void setWorkflowStepRoleService(WorkflowStepRoleService workflowStepRoleService) {
		this.workflowStepRoleService = workflowStepRoleService;
	}

	public SysDataService getSysDataService() {
		return sysDataService;
	}

	public void setSysDataService(SysDataService sysDataService) {
		this.sysDataService = sysDataService;
	}

	public static MobileModelConvert getModelConvert() {
		return modelConvert;
	}

	public static void setModelConvert(MobileModelConvert modelConvert) {
		MobileModelConvert.modelConvert = modelConvert;
	}

	public InteractCommentService getCommentService() {
		return commentService;
	}

	public void setCommentService(InteractCommentService commentService) {
		this.commentService = commentService;
	}

	public InfoService getInfoService() {
		return infoService;
	}

	public void setInfoService(InfoService infoService) {
		this.infoService = infoService;
	}

	public static MobileFriendlink convertFriendLink(Ad friendlink, String siteUrl) {
		if (friendlink == null)
			return null;
		MobileFriendlink friendlinkInfo = new MobileFriendlink();
		friendlinkInfo.setAdvId(friendlink.getId());
		friendlinkInfo.setAdvTitle(StringUtils.isNotBlank(friendlink.getName()) ? friendlink.getName() : "");
		friendlinkInfo.setDescription(StringUtils.isNotBlank(friendlink.getText()) ? friendlink.getText() : "");
		friendlinkInfo.setImgUrl(
				StringUtils.isNotBlank(friendlink.getImage()) ? fullLinkPrefix(friendlink.getImage(), siteUrl) : "");
		Integer linkType = friendlink.getLinkType();
		String url = friendlink.getUrl();
		friendlinkInfo.setLinkType(linkType == null ? "" : "" + linkType);
		if (linkType != null) {
			if (Ad.LINK_TYPE_URL == linkType) {
				friendlinkInfo.setUrl(StringUtils.isNotBlank(url) ? fullLinkPrefix(url, siteUrl) : "");
			} else if (Ad.LINK_TYPE_INFO == linkType) {
				friendlinkInfo.setUrl(StringUtils.isNotBlank(url) ? (StringUtils.isNumeric(url) ? url : "") : "");
			} else if (Ad.LINK_TYPE_SPECIAL == linkType) {
				friendlinkInfo.setUrl(StringUtils.isNotBlank(url) ? (StringUtils.isNumeric(url) ? url : "") : "");
			} else if (Ad.LINK_TYPE_IMAGE == linkType) {
				friendlinkInfo.setUrl(StringUtils.isNotBlank(url) ? (StringUtils.isNumeric(url) ? url : "") : "");
			}else if(Ad.LINK_TYPE_NODE == linkType) {
				friendlinkInfo.setUrl(StringUtils.isNotBlank(url) ? (StringUtils.isNumeric(url) ? url : "") : "");
			}
		}

		return friendlinkInfo;
	}

	public static MobileSpecial convertSpecial(Special special) {
		if (special == null) {
			return null;
		}
		MobileSpecial mobileSpecial = new MobileSpecial();
		mobileSpecial.setId(special.getId());
		mobileSpecial.setTitle(special.getTitle());
		mobileSpecial.setCreatorId(special.getCreatorId());
		mobileSpecial.setCreationDate(special.getCreationDate());
		mobileSpecial.setMetaKeywords(special.getMetaKeywords());
		mobileSpecial.setMetaDescription(special.getMetaDescription());
		mobileSpecial.setSmallImage(special.getSmallImage());
		mobileSpecial.setLargeImage(special.getLargeImage());
		mobileSpecial.setVideo(special.getVideo());
		mobileSpecial.setVideoName(special.getVideoName());
		mobileSpecial.setVideoLength(special.getVideoLength());
		mobileSpecial.setVideoTime(special.getVideoTime());
		mobileSpecial.setRefers(special.getRefers());
		mobileSpecial.setViews(special.getViews());
		mobileSpecial.setWithImage(special.getWithImage());
		mobileSpecial.setRecommend(special.getRecommend());
		return mobileSpecial;
	}

	public static WorkflowStepModel convertWorkflowStep(WorkflowStep workflowStep) {
		WorkflowStepModel workflowStepModel = new WorkflowStepModel();
		workflowStepModel.setId(workflowStep.getId());
		workflowStepModel.setName(workflowStep.getName());
		// List<WorkflowStepRole> stepRoles = workflowStep.getStepRoles();
		List<WorkflowStepRole> stepRoles = modelConvert.workflowStepRoleService.findList(workflowStep.getId());
		String rolesName = "";
		for (WorkflowStepRole role : stepRoles) {
			SysRoleEntity sysRoleEntity = modelConvert.sysDataService.getSysRole(role.getRoleId());
			if (!rolesName.equals("")) {
				rolesName += ",";
			}
			rolesName += sysRoleEntity.getRoleName();
		}
		workflowStepModel.setRolesName(rolesName);
		return workflowStepModel;
	}

	public static boolean isUseRtmp() {
		return useRtmp;
	}

	public static void setUseRtmp(boolean useRtmp) {
		MobileModelConvert.useRtmp = useRtmp;
	}

	public static boolean isUseCommonCss() {
		return useCommonCss;
	}

	public static void setUseCommonCss(boolean useCommonCss) {
		MobileModelConvert.useCommonCss = useCommonCss;
	}

}
