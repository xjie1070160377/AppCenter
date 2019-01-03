package cn.mooc.app.module.cms.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.data.entity.InfoDetail;

public class DangshiUtils {

	private static String heardImg = "/uploads/p/20181213/jpg/791b0890-b60c-49b6-b836-f92737957ede.jpg";
	private static String footImg = "/uploads/p/20181213//6c8ff2d9-6ab4-41da-acb5-eec769bae4c0";
	private static String[] contentImgs = { "/uploads/p/20181214/png/414c3f39-5abb-48d2-a545-4f947d59a24e.png",
			"/uploads/p/20181214/png/de53489e-4210-44fe-98f0-ecdcc8a42898.png",
			"/uploads/p/20181214/png/5115ce0b-29cb-4f78-abd7-a0f43952ed76.png",
			"/uploads/p/20181214/png/27a8df06-d303-4c03-813a-689d47b8ddfb.png",
			"/uploads/p/20181214/png/0dbae872-9c1a-45dc-b53a-679b8b6bd0f7.png" };
	private static String heard = "<p>\n" + "    <img src=\"" + heardImg + "\" title=\"?????????.jpg\"/>\n" + "</p>\n"
			+ "<section>\n" + "    <section class=\"V5\" powered-by=\"xiumi.us\">\n"
			+ "        <section style=\"position: static;\">\n" + "            <section>\n" + "                <p>\n"
			+ "                    <br/>\n" + "                </p>\n" + "            </section>\n"
			+ "        </section>\n" + "    </section>\n"
			+ "    <section class=\"V5\" style=\"position: static;\" powered-by=\"xiumi.us\">\n"
			+ "        <section style=\"margin-top: 10px; margin-bottom: 10px; text-align: center; position: static;\">\n"
			+ "            <section style=\"border-bottom: 1px solid rgb(214, 10, 2); padding-top: 3px; width: 100%; margin-bottom: -3px;\"></section>\n"
			+ "            <section style=\"height: 5px; line-height: 5px;\">\n"
			+ "                <section style=\"display: inline-block; vertical-align: top;\">\n"
			+ "                    <span style=\"width: 5px; height: 5px; float: left; border-radius: 50%; background-color: rgb(214, 10, 2);\"></span><span style=\"width: 5px; height: 5px; float: left; border-radius: 50%; margin-left: 5px; background-color: rgb(214, 10, 2);\"></span><span style=\"width: 5px; height: 5px; float: left; border-radius: 50%; margin-left: 5px; background-color: rgb(214, 10, 2);\"></span><span style=\"width: 5px; height: 5px; float: left; border-radius: 50%; margin-left: 5px; background-color: rgb(214, 10, 2);\"></span><span style=\"width: 5px; height: 5px; float: left; border-radius: 50%; margin-left: 5px; background-color: rgb(214, 10, 2);\"></span>\n"
			+ "                </section>\n" + "                <section style=\"clear: both;\"></section>\n"
			+ "            </section>\n" + "        </section>\n" + "    </section>\n"
			+ "    <section class=\"V5\" powered-by=\"xiumi.us\">\n" + "        <section style=\"position: static;\">\n"
			+ "            <section>\n" + "                <p>\n" + "                    <br/>\n"
			+ "                </p>\n" + "            </section>\n" + "        </section>\n" + "    </section>\n"
			+ "   \n" + "    <section class=\"V5\" powered-by=\"xiumi.us\">\n"
			+ "        <section style=\"position: static;\">\n" + "            <section>";
	private static String str = "<section>\n" + "                    <section class=\"V5\" powered-by=\"xiumi.us\">\n"
			+ "                        <section style=\"position: static;\">\n"
			+ "                            <section>\n" + "                                <p>\n"
			+ "                                    <br/>\n" + "                                </p>\n"
			+ "                            </section>\n" + "                        </section>\n"
			+ "                    </section>\n"
			+ "                    <section class=\"V5\" style=\"position: static;\" powered-by=\"xiumi.us\">\n"
			+ "                        <section style=\"text-align: center; margin: 30px 0% 10px; position: static;\">\n"
			+ "                            <section style=\"display: inline-block; width: 80%; vertical-align: top; background-color: rgb(249, 235, 230); padding: 15px;\">\n"
			+ "                                <section class=\"V5\" style=\"position: static;\" powered-by=\"xiumi.us\">\n"
			+ "                                    <section style=\"margin: -40px 0% 10px; text-align: left; font-size: 11px; transform: translate3d(-45px, 0px, 0px); position: static;\">\n"
			+ "                                        <section style=\"width: 5em; height: 5em; display: inline-block; border-radius: 100%; transform: rotate(0deg); padding: 3px; background-color: rgb(255, 255, 255);\">\n"
			+ "                                            <section style=\"width: 100%; height: 100%; border-radius: 100%; background-position: center center; background-repeat: no-repeat; background-size: cover; background-image: url(\">\n"
			+ "                                                <section style=\"width: 100%; height: 100%; overflow: hidden; line-height: 0;\">\n"
			+ "                                                    <img style=\"width: 100%; height: 100%;\" src=\"/uploads/p/20181212/png/69135bbc-545e-413d-9369-88e2c1a2eed9.png\" data-ratio=\"1\" data-w=\"256\" _width=\"100%\"/>\n"
			+ "                                                </section>\n"
			+ "                                            </section>\n"
			+ "                                        </section>\n"
			+ "                                    </section>\n" + "                                </section>\n"
			+ "                                <section class=\"V5\" style=\"position: static;\" powered-by=\"xiumi.us\">\n"
			+ "                                    <section style=\"margin-top: -30px; margin-right: 0%; margin-left: 0%; position: static;\">\n"
			+ "                                        <section style=\"text-align: justify; color: rgb(214, 10, 2); padding-right: 10px; padding-left: 10px;\">\n"
			+ "                                            <p>\n"
			+ "                                                1936年12月12日　西安事变\n"
			+ "                                            </p>\n"
			+ "                                        </section>\n"
			+ "                                    </section>\n" + "                                </section>\n"
			+ "                                <section class=\"V5\" powered-by=\"xiumi.us\">\n"
			+ "                                    <section style=\"position: static;\">\n"
			+ "                                        <section style=\"text-align: left;\">\n"
			+ "                                            <p>\n"
			+ "                                                <br/>\n"
			+ "                                            </p>\n"
			+ "                                        </section>\n"
			+ "                                    </section>\n" + "                                </section>\n"
			+ "                                <section class=\"V5\" powered-by=\"xiumi.us\">\n"
			+ "                                    <section style=\"position: static;\">\n"
			+ "                                        <section style=\"text-align: justify; line-height: 1.8;\">\n"
			+ "                                            <p>\n"
			+ "                                                1936年12月12日　以张学良为首的国民党东北军和以杨虎城为首的国民党第十七路军，为了停止内战、一致抗日，在多次进谏蒋介石无效反而遭到斥责后，被迫发动“兵谏”，扣押蒋介石。同时，控制西安全城，囚禁陪同蒋介石到西安的国民党军政要员。张、杨并向全国发出停止内战、一致抗日的通电。西安事变发生后，中共中央经过认真研究，派遣周恩来于12月17日到达西安。在弄清情况后，以中华民族利益的大局为重，确定了用和平方式解决西安事变的方针。根据这一方针，周恩来与张学良、杨虎城共同努力，经过谈判，迫使蒋介石作出了“停止剿共，联红抗日”等六项承诺。西安事变在国共两党重新合作的客观形势渐次成熟的时候，起了促成这个合作的作用。西安事变的和平解决，成为时局转换的枢纽。自此以后，内战在事实上大体停止下来，国共两党关系开始改善。\n"
			+ "                                            </p>\n"
			+ "                                        </section>\n"
			+ "                                    </section>\n" + "                                </section>\n"
			+ "                            </section>\n" + "                        </section>\n"
			+ "                    </section>\n"
			+ "                    <section class=\"V5\" powered-by=\"xiumi.us\">\n"
			+ "                        <section style=\"position: static;\">\n"
			+ "                            <section>\n" + "                                <p>\n"
			+ "                                    <br/>\n" + "                                </p>\n"
			+ "                            </section>\n" + "                        </section>\n"
			+ "                    </section>\n" + "                </section>";
	private static String foot = "<p>\n" + "                    <br/>\n" + "                </p>\n"
			+ "            </section>\n" + "        </section>\n" + "    </section>\n"
			+ "    <section class=\"V5\" style=\"position: static;\" powered-by=\"xiumi.us\">\n"
			+ "        <section style=\"margin-top: 10px; margin-bottom: 10px; text-align: center; position: static;\">\n"
			+ "            <section style=\"border-bottom: 1px solid rgb(214, 10, 2); padding-top: 3px; width: 100%; margin-bottom: -3px;\"></section>\n"
			+ "            <section style=\"height: 5px; line-height: 5px;\">\n"
			+ "                <section style=\"display: inline-block; vertical-align: top;\">\n"
			+ "                    <span style=\"width: 5px; height: 5px; float: left; border-radius: 50%; background-color: rgb(214, 10, 2);\"></span><span style=\"width: 5px; height: 5px; float: left; border-radius: 50%; margin-left: 5px; background-color: rgb(214, 10, 2);\"></span><span style=\"width: 5px; height: 5px; float: left; border-radius: 50%; margin-left: 5px; background-color: rgb(214, 10, 2);\"></span><span style=\"width: 5px; height: 5px; float: left; border-radius: 50%; margin-left: 5px; background-color: rgb(214, 10, 2);\"></span><span style=\"width: 5px; height: 5px; float: left; border-radius: 50%; margin-left: 5px; background-color: rgb(214, 10, 2);\"></span>\n"
			+ "                </section>\n" + "                <section style=\"clear: both;\"></section>\n"
			+ "            </section>\n" + "        </section>\n" + "    </section>\n"
			+ "    <section class=\"V5\" powered-by=\"xiumi.us\">\n" + "        <section style=\"position: static;\">\n"
			+ "            <section>\n" + "                <p>\n" + "                    <br/>\n"
			+ "                </p>\n" + "            </section>\n" + "        </section>\n" + "    </section>\n"
			+ "    <section class=\"V5\" powered-by=\"xiumi.us\">\n" + "        <section style=\"position: static;\">\n"
			+ "            <section>\n" + "                <p>\n" + "                    <br/>\n"
			+ "                </p>\n" + "            </section>\n" + "        </section>\n" + "    </section>\n"
			+ "    <section class=\"V5\" style=\"position: static;\" powered-by=\"xiumi.us\">\n"
			+ "        <section style=\"text-align: center; margin-top: 10px; margin-right: 0%; margin-left: 0%; position: static;\">\n"
			+ "            <section style=\"max-width: 100%; vertical-align: middle; display: inline-block; box-shadow: rgb(0, 0, 0) 0px 0px 0px;\">\n"
			+ "                <img src=\"" + footImg
			+ "\" style=\"vertical-align: middle;\" data-ratio=\"0.2125\" data-w=\"480\"/>\n"
			+ "            </section>\n" + "        </section>\n" + "    </section>\n" + "</section>\n" + "<p>\n"
			+ "    <br/>\n" + "</p>";

	public static String getHtml(String requestUrl) {
		StringBuffer buffer = null;
		BufferedReader bufferedReader = null;
		InputStreamReader inputStreamReader = null;
		InputStream inputStream = null;
		HttpURLConnection httpUrlConn = null;

		try {
			// 建立get请求
			URL url = new URL(requestUrl);
			httpUrlConn = (HttpURLConnection) url.openConnection();
			httpUrlConn.setDoInput(true);
			httpUrlConn.setRequestMethod("GET");

			// 获取输入流
			inputStream = httpUrlConn.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream, "GBK");
			bufferedReader = new BufferedReader(inputStreamReader);

			// 从输入流读取结果
			buffer = new StringBuffer();
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputStreamReader != null) {
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (httpUrlConn != null) {
				httpUrlConn.disconnect();
			}
		}
		return buffer.toString();
	}

	private static List<HtmlEntity> htmlFiter(String html) {
		List<HtmlEntity> htmlEntities = new ArrayList<>();
		Pattern p = Pattern.compile("(.*)(<div class=\"p1_02 clearfix width884\">)(.*?)(</div>)(.*)");
		Matcher m = p.matcher(html);
		if (m.matches()) {
			String str1 = m.group(3);

			p = Pattern.compile("(.*)(<h2>)(.*?)(</h2>)(.*)");
			m = p.matcher(str1);
			Pattern pattern = Pattern.compile("h2>([^<]+)</h2>(.*?)<p>([^<]+)</p");
			Matcher macher = pattern.matcher(str1);
			while (macher.find()) {
				HtmlEntity htmlEntity = new HtmlEntity(macher.group(1), macher.group(3));
				htmlEntities.add(htmlEntity);
			}
		}
		return htmlEntities;
	}

	public static String repAttr(String source, String newStr, String element, String attr) {

		String img = "";
		Pattern p_image;
		Matcher m_image;
		String regEx_img = "<" + element + "[^<>]*?\\s" + attr + "=['\"]?(.*?)['\"]?(\\s.*?)?>";
		p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
		m_image = p_image.matcher(source);
		while (m_image.find()) {
			img = m_image.group();
			Matcher m = Pattern.compile(attr + "\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
			while (m.find()) {
				String srcVal = m.group(1);
				source = source.replace(srcVal, newStr);
			}
		}
		return source;

	}

	public static String repP(String source, String title, String content) {
		Pattern pattern = Pattern.compile("<p(.*?)>([^<]+)</p>");
		Matcher macher = pattern.matcher(source);
		int i = 0;
		while (macher.find()) {
			if (i == 0)
				source = source.replace(macher.group(2), title);
			if (i == 1)
				source = source.replace(macher.group(2), content);
			i++;

		}
		return source;
	}

	public static Info getHtmlRes(String url) {
		StringBuffer sb = new StringBuffer();
		List<HtmlEntity> list = htmlFiter(getHtml(url));
		sb.append(heard);

		for (int i = 0; i < list.size(); i++) {
			if (i >= contentImgs.length) {
				break;
			}
			HtmlEntity entity = list.get(i);
			sb.append(repP(repAttr(str, contentImgs[i], "img", "src"), entity.getTitle(), entity.getContent()));
		}
		sb.append(foot);
		InfoDetail infoDetail = new InfoDetail();
		infoDetail.setTitle(list.get(0).getTitle());
		infoDetail.setFullTitle(list.get(0).getTitle());
		Info info = new Info();
		info.setText(sb.toString());
		info.setDetail(infoDetail);
		return info;
	}

}
