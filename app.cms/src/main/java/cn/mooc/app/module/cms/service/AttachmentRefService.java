package cn.mooc.app.module.cms.service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.module.cms.data.entity.Attachment;
import cn.mooc.app.module.cms.data.entity.AttachmentRef;
import cn.mooc.app.module.cms.data.entity.Global;
import cn.mooc.app.module.cms.data.entity.PublishPoint;
import cn.mooc.app.module.cms.data.rds.AttachmentRefRepository;


@Service
public class AttachmentRefService {
	public void update(Set<String> urls, String ftype, Integer fid, Global global) {
		if (urls == null) {
			urls = Collections.emptySet();
		}
		PublishPoint point = global.getUploadsPublishPoint();
		String prefix = point.getUrlPrefix();
		Set<String> names = new HashSet<String>(urls.size());
		int lenth = prefix.length();
		for (String url : urls) {
			if (StringUtils.startsWith(url, prefix)) {
				names.add(url.substring(lenth));
			}
		}
		List<AttachmentRef> list = attachmentRefRepository.findByFtypeAndFid(ftype, fid);
		// 先删除
		Set<AttachmentRef> tobeDelete = new HashSet<AttachmentRef>();
		for (AttachmentRef ref : list) {
			Attachment attachment = ref.getAttachment();
			String name = attachment.getName();
			if (!names.contains(name)) {
				Set<AttachmentRef> refs = attachment.getRefs();
				refs.remove(ref);
				// attachment.setUserd(!refs.isEmtpy());
				tobeDelete.add(ref);
			}
		}
		attachmentRefRepository.delete(tobeDelete);
		list.removeAll(tobeDelete);
		// 再新增
		for (String name : names) {
			if (StringUtils.isBlank(name)) {
				continue;
			}
			boolean contains = false;
			for (AttachmentRef ref : list) {
				String n = ref.getAttachment().getName();
				if (n.equals(name)) {
					contains = true;
					break;
				}
			}
			if (!contains) {
				Attachment attachment = attachmentService.findByName(name);
				if (attachment != null) {
					save(ftype, fid, attachment);
				}
			}
		}
	}

	@Transactional
	public void delete(String ftype, Integer fid, Global global) {
		Set<String> urls = Collections.emptySet();
		update(urls, ftype, fid, global);
	}

	@Transactional
	private AttachmentRef save(String ftype, Integer fid, Attachment attachment) {
		AttachmentRef bean = new AttachmentRef();
		bean.setFtype(ftype);
		bean.setFid(fid);
		bean.setAttachment(attachment);
		bean.setSite(attachment.getSite());
		bean.applyDefaultValue();
		bean = attachmentRefRepository.save(bean);
		Set<AttachmentRef> refs = attachment.getRefs();
		refs.add(bean);
		return bean;
	}

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private AttachmentRefRepository attachmentRefRepository;
}
